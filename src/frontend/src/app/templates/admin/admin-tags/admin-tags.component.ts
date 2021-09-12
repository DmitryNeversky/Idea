import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TagService} from "../../../services/tag.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Tag} from "../../../models/Tag";
import {MatDialog} from "@angular/material/dialog";
import {DialogComponent} from "../../../shared/dialog/dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {SnackbarComponent} from "../../../shared/snackbar/snackbar.component";

@Component({
  selector: 'app-admin-tags',
  templateUrl: './admin-tags.component.html',
  styleUrls: ['./admin-tags.component.css']
})
export class AdminTagsComponent implements OnInit {

  public tags: Tag[];

  public createForm: FormGroup;
  public updateForm: FormGroup;

  public modalTag: Tag;
  public preloader: boolean = false;

  constructor(private activatedRoute: ActivatedRoute, private tagService: TagService,
              private _dialog: MatDialog, private _notification: MatSnackBar) { }

  ngOnInit(): void {
    this.tags = this.activatedRoute.snapshot.data.tags;
    this.createForm = new FormGroup({
      name: new FormControl('', [Validators.maxLength(64)])
    });
  }

  create() {
    if(!this.createForm.valid)
      return;

    let tag: Tag = new Tag();
    tag.name = this.createForm.get('name').value;

    this.tagService.saveTag(tag).subscribe((tag: Tag) => {
      this.tags.push(tag);
      this.createForm.reset();
      this._notification.openFromComponent(SnackbarComponent, {
        duration: 2000,
        horizontalPosition: 'left',
        data: 'Тэг успешно создан!'
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
    if(!this.updateForm.valid || this.modalTag.name == this.updateForm.get('name').value)
      return;

    let sendTag: Tag = this.modalTag;
    sendTag.name = this.updateForm.get('name').value;

    this.preloader = true;

    this.tagService.putTag(sendTag).subscribe(() => {
      this.preloader = false;
      this.hideModal();
      this._notification.openFromComponent(SnackbarComponent, {
        duration: 2000,
        horizontalPosition: 'left',
        data: 'Тэг успешно изменен!'
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

  delete(tag: Tag) {
    this._dialog.open(DialogComponent, {
      data: {
        title: 'Подтвердите действие',
        message: `Вы уверены что хотите удалить тэг "${tag.name}"? Возможно, есть идеи которые его используют.`
      }
    }).afterClosed().subscribe((result: boolean) => {
      this.preloader = true;
      if(result) {
        this.tagService.deleteTag(tag.id).subscribe(() => {
          this.preloader = false;
          this.hideModal();
          this.tags = this.tags.filter(t => t != tag);
          this._notification.openFromComponent(SnackbarComponent, {
            duration: 2000,
            horizontalPosition: 'left',
            data: 'Тэг успешно удален!'
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

  openModal(tag: Tag) {
    this.updateForm = new FormGroup({
      name: new FormControl(tag.name, [Validators.required, Validators.max(128)])
    });
    this.modalTag = tag;
  }

  hideModal(event: any = null) {
    if(event && event.target.classList.contains('modal-overlay')) {
      this.modalTag = null;
    } else if(!event) {
      this.modalTag = null;
    }
  }
}
