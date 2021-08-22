import {Component, OnInit} from '@angular/core';
import {ImagesLoader} from "../custom/ImagesLoader";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {IdeaService} from "../services/idea.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Idea} from "../models/Idea";
import {SnackbarComponent} from "../shared/snackbar/snackbar.component";
import {environment} from "../../environments/environment";
import {FilesLoader} from "../custom/FilesLoader";

@Component({
  selector: 'app-update-idea',
  templateUrl: './update-idea.component.html',
  styleUrls: ['./update-idea.component.css']
})
export class UpdateIdeaComponent implements OnInit {

  public uploadPath = environment.uploadPath + "images/";

  public idea: Idea;

  public imagesLoader = new ImagesLoader();
  public filesLoader = new FilesLoader();

  public tags: string[];

  public mainForm: FormGroup;

  constructor(private ideaService: IdeaService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private _snackBar: MatSnackBar) { }

  ngOnInit() {
    this.idea = this.activatedRoute.snapshot.data.idea;

    this.mainForm = new FormGroup({
      title: new FormControl(this.idea.title, [Validators.minLength(8), Validators.maxLength(256), Validators.required]),
      text: new FormControl(this.idea.text, [Validators.minLength(64), Validators.maxLength(32768), Validators.required]),
    });
  }

  update() {
    if(this.mainForm.invalid)
      return

    let formData = new FormData();

    formData.append('title', this.mainForm.get('title').value);
    formData.append('text', this.mainForm.get('text').value);
    this.tags.forEach(t => formData.append('tags', t));
    this.imagesLoader.removeImagesList.forEach(x => formData.append('removeImages', x));
    for (let i = 0; i < this.imagesLoader.dataTransfer.files.length; i++)
      formData.append('addImages', this.imagesLoader.dataTransfer.files[i]);
    this.filesLoader.removeFilesList.forEach(x => formData.append('removeFiles', x));
    for (let i = 0; i < this.filesLoader.dataTransfer.files.length; i++)
      formData.append("addFiles", this.filesLoader.dataTransfer.files[i])

    this.ideaService.put(formData, this.idea.id).subscribe((response: Idea) => {
      this.router.navigateByUrl('ideas');
      this._snackBar.openFromComponent(SnackbarComponent, {
        duration: 3000,
        horizontalPosition: "start",
        data: "Идея успешно отредактирована!"
      });
    }, error => {
      this._snackBar.openFromComponent(SnackbarComponent, {
        duration: 3000,
        horizontalPosition: "start",
        data: "Произошла ошибка, попробуйте позже."
      });
    });
  }

  setTags(tags: any) {
    this.tags = tags;
  }
}
