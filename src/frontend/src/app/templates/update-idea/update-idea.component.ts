import {Component, OnInit} from '@angular/core';
import {ImagesLoader} from "../../custom/ImagesLoader";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {IdeaService} from "../../services/idea.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Idea} from "../../models/Idea";
import {environment} from "../../../environments/environment";
import {FilesLoader} from "../../custom/FilesLoader";
import {DialogComponent} from "../../shared/dialog/dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {Tag} from "../../models/Tag";
import {User} from "../../models/User";
import {SnackbarService} from "../../shared/snackbar/snackbar.service";

@Component({
  selector: 'app-update-idea',
  templateUrl: './update-idea.component.html',
  styleUrls: ['./update-idea.component.css']
})
export class UpdateIdeaComponent implements OnInit {

  public uploadPath = environment.uploadPath + "images/";

  public idea: Idea;
  public currentUser: User;

  public imagesLoader = new ImagesLoader();
  public filesLoader = new FilesLoader();

  public tags: Tag[];

  public mainForm: FormGroup;

  constructor(private ideaService: IdeaService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private _dialog: MatDialog,
              private snackBar: SnackbarService) { }

  ngOnInit() {
    this.idea = this.activatedRoute.snapshot.data.idea;
    this.tags = this.activatedRoute.snapshot.data.tags;
    this.currentUser = this.activatedRoute.snapshot.data.currentUser;

    this.mainForm = new FormGroup({
      title: new FormControl(this.idea.title, [Validators.minLength(8), Validators.maxLength(256), Validators.required]),
      text: new FormControl(this.idea.text, [Validators.minLength(256), Validators.maxLength(32768), Validators.required]),
    });
  }

  update() {
    if(this.mainForm.invalid)
      return

    const formData = new FormData();

    if(this.idea.removeImages == null)
      this.idea.removeImages = [];
    if(this.idea.removeFiles == null)
      this.idea.removeFiles = [];
    if(this.idea.tags == null)
      this.idea.tags = [];

    this.idea.title = this.mainForm.get('title').value;
    this.idea.text = this.mainForm.get('text').value;
    this.imagesLoader.removeImagesList.forEach(x => this.idea.removeImages.push(x));
    for (let i = 0; i < this.imagesLoader.dataTransfer.files.length; i++)
      formData.append('addImages', this.imagesLoader.dataTransfer.files[i]);
    this.filesLoader.removeFilesList.forEach(x => this.idea.removeFiles.push(x));
    for (let i = 0; i < this.filesLoader.dataTransfer.files.length; i++)
      formData.append('addFiles', this.filesLoader.dataTransfer.files[i])

    formData.append('idea', JSON.stringify(this.idea));

    this.ideaService.putIdea(formData).subscribe(() => {
      this.router.navigate(['ideas']);
      this.snackBar.success("Идея успешно отредактирована!");
    }, error => {
      console.log(error);
      this.snackBar.error();
    });
  }

  delete() {
    this._dialog.open(DialogComponent, {
      data: {
        title: "Предупреждение",
        message: "Восстановить идею будет невозможно. Вы уверены что хотите её удалить?"
      }
    }).afterClosed().subscribe(result => {
      if(result) {
        this.ideaService.deleteIdea(this.idea.id).subscribe(() => {
          this.router.navigate(['ideas']);
          this.snackBar.success("Идея успешно удалена!");
        }, error => {
          console.log(error);
          this.snackBar.error();
        });
      }
    });
  }

  setTags(tags: any) {
    this.tags = tags;
  }
}
