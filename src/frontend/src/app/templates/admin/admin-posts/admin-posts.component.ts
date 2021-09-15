import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Post} from "../../../models/Post";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {ActivatedRoute} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PostService} from "../../../services/post.service";
import {DialogComponent} from "../../../shared/dialog/dialog.component";
import {SnackbarComponent} from "../../../shared/snackbar/snackbar.component";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-admin-posts',
  templateUrl: './admin-posts.component.html',
  styleUrls: ['./admin-posts.component.css']
})
export class AdminPostsComponent implements OnInit, AfterViewInit {

  modalPost: Post;

  createForm: FormGroup;
  updateForm: FormGroup;

  displayedColumns: string[] = ['id', 'name', 'users'];
  dataSource: MatTableDataSource<Post>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  public postExist: boolean = false;
  public changeExist: boolean = false;
  public preloader: boolean = false;

  constructor(private activatedRoute: ActivatedRoute, private postService: PostService,
              private _dialog: MatDialog, private _notification: MatSnackBar) { }

  ngOnInit(): void {
    const posts = this.activatedRoute.snapshot.data.posts;
    this.dataSource = new MatTableDataSource(posts);

    this.createForm = new FormGroup({
      name: new FormControl('', [Validators.maxLength(128)])
    });
  }

  ngAfterViewInit() {
    this.initPagination();
  }

  initPagination() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  create() {

    switch (true) {
      case this.createForm.valid:
      case !this.createForm.get('name').value:
      case this.dataSource.data.includes(this.createForm.get('name').value):
        return;
    }

    let post: Post = new Post();
    post.name = this.createForm.get('name').value;

    this.postService.savePost(post).subscribe((post: Post) => {
      this.dataSource.data.push(post);
      this.initPagination();
      this.createForm.reset();
      this._notification.openFromComponent(SnackbarComponent, {
        duration: 2000,
        horizontalPosition: 'left',
        data: 'Должность успешно создана!'
      });
    }, error => {
      console.log(error);
      this._notification.openFromComponent(SnackbarComponent, {
        duration: 3000,
        horizontalPosition: 'left',
        data: 'Произошел сбой, попробуйте позже.'
      });
    });
  }

  update() {
    if(!this.updateForm.valid || this.modalPost.name == this.updateForm.get('name').value)
      return;

    let sendPost: Post = new Post();
    sendPost.id = this.modalPost.id;
    sendPost.name = this.updateForm.get('name').value;

    this.preloader = true;

    this.postService.putPost(sendPost).subscribe(() => {
      this.dataSource.data.find(p => p.id == sendPost.id).name = sendPost.name;
      this.preloader = false;
      this.hideModal();
      this._notification.openFromComponent(SnackbarComponent, {
        duration: 2000,
        horizontalPosition: 'left',
        data: 'Должность успешно изменена!'
      });
    }, error => {
      this.preloader = false;
      this.hideModal();
      console.log(error);
      this._notification.openFromComponent(SnackbarComponent, {
        duration: 3000,
        horizontalPosition: 'left',
        data: 'Произошел сбой, попробуйте позже.'
      });
    });
  }

  delete(post: Post) {
    this._dialog.open(DialogComponent, {
      data: {
        title: 'Подтвердите действие',
        message: `Вы уверены что хотите удалить должность "${post.name}"? Возможно, есть пользователи которые её используют.`
      }
    }).afterClosed().subscribe((result: boolean) => {
      this.preloader = true;
      if(result) {
        this.postService.deletePost(post.id).subscribe(() => {
          this.preloader = false;
          this.dataSource.data = this.dataSource.data.filter(p => p != post);
          this.initPagination();
          this.hideModal();
          this._notification.openFromComponent(SnackbarComponent, {
            duration: 2000,
            horizontalPosition: 'left',
            data: 'Должность успешно удалена!'
          });
        }, error => {
          this.preloader = false;
          this.hideModal();
          console.log(error);
          this._notification.openFromComponent(SnackbarComponent, {
            duration: 3000,
            horizontalPosition: 'left',
            data: 'Произошел сбой, попробуйте позже.'
          });
        });
      }
    })
  }

  createListener(event: any) {
    this.postExist = !!this.dataSource.data.find(p => p.name == event.target.value);
  }

  changeListener(event: any) {
    this.changeExist = !!this.dataSource.data.find(p => p.name == event.target.value)
        && this.modalPost.name != event.target.value;
  }

  openModal(post: Post) {
    this.updateForm = new FormGroup({
      name: new FormControl(post.name, [Validators.required, Validators.max(128)])
    });
    this.modalPost = post;
  }

  hideModal(event: any = null) {
      if(event && event.target.classList.contains('modal-overlay')) {
        this.modalPost = null;
      } else if(!event) {
        this.modalPost = null;
      }
  }
}
