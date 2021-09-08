import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Post} from "../../../models/Post";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {ActivatedRoute} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PostService} from "../../../services/post.service";

@Component({
  selector: 'app-admin-posts',
  templateUrl: './admin-posts.component.html',
  styleUrls: ['./admin-posts.component.css']
})
export class AdminPostsComponent implements OnInit, AfterViewInit {

  createForm: FormGroup;

  displayedColumns: string[] = ['id', 'name', 'users'];
  dataSource: MatTableDataSource<Post>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private activatedRoute: ActivatedRoute, private postService: PostService) { }

  ngOnInit(): void {
    const posts = this.activatedRoute.snapshot.data.posts;
    this.dataSource = new MatTableDataSource(posts);

    this.createForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.max(128)])
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  create() {
    if(!this.createForm.valid)
      return;

    this.postService.savePost(null).subscribe();
  }
}
