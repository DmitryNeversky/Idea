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

  public tagForm: FormGroup;

  constructor(private activatedRoute: ActivatedRoute, private tagService: TagService,
              private _dialog: MatDialog, private _notification: MatSnackBar) { }

  ngOnInit(): void {
    this.tags = this.activatedRoute.snapshot.data.tags;
    this.tagForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.max(64)])
    });
  }

  create() {
    if(!this.tagForm.valid)
      return;

    let tag: Tag = new Tag();
    tag.name = this.tagForm.get('name').value;

    this.tagService.saveTag(tag).subscribe((tag: Tag) => {
      this.tags.push(tag);
      this.tagForm.reset();
    }, error => console.log());
  }

  delete(tag: Tag) {
    this._dialog.open(DialogComponent, {
      data: {
        title: 'Подтвердите действие',
        message: `Вы уверены что хотите удалить тэг "${tag.name}"? Возможно, есть идеи которые его используют.`
      }
    }).afterClosed().subscribe((result: boolean) => {
      if(result) {
        this.tagService.deleteTag(tag.id).subscribe(() => {
          this.tags = this.tags.filter(t => t != tag);
          this._notification.openFromComponent(SnackbarComponent, {
            duration: 2000,
            horizontalPosition: 'left',
            data: 'Тэг успешно удален!'
          });
        }, error => {
          console.log(error);
          this.tags = this.tags.filter(t => t != tag);
          this._notification.openFromComponent(SnackbarComponent, {
            duration: 3000,
            horizontalPosition: 'left',
            data: 'Произошел сбой, попробуйте позже.'
          });
        });
      }
    })
  }
}
