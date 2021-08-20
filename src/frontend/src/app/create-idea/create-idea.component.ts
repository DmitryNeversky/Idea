import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {IdeaService} from "../services/idea.service";
import {Idea} from "../models/Idea";
import {ActivatedRoute, Router} from "@angular/router";
import {SnackbarComponent} from "../shared/snackbar/snackbar.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ImagesLoader} from "../custom/ImagesLoader";

@Component({
  selector: 'app-create-idea',
  templateUrl: './create-idea.component.html',
  styleUrls: ['./create-idea.component.css']
})
export class CreateIdeaComponent implements OnInit{

  public imagesLoader = new ImagesLoader();

  public tags: string[];

  public mainForm: FormGroup = new FormGroup({
    title: new FormControl('', [Validators.minLength(8), Validators.maxLength(256), Validators.required]),
    text: new FormControl('', [Validators.minLength(64), Validators.maxLength(32768), Validators.required]),
  });

  constructor(private ideaService: IdeaService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private _snackBar: MatSnackBar) { }

  ngOnInit() {
    this.tags = this.activatedRoute.snapshot.data.tags;
  }

  create() {
    if(this.mainForm.invalid)
      return

    let formData = new FormData();

    formData.append('title', this.mainForm.get('title').value);
    formData.append('text', this.mainForm.get('text').value);
    this.tags.forEach(t => formData.append('tags', t));
    for(let i = 0; i < this.imagesLoader.dataTransfer.files.length; i++)
      formData.append('images', this.imagesLoader.dataTransfer.files[i]);

    this.ideaService.add(formData).subscribe((response: Idea) => {
      this.router.navigateByUrl('ideas');
      this._snackBar.openFromComponent(SnackbarComponent, {
        duration: 3000,
        horizontalPosition: "start",
        data: "Идея успешно создана!"
      });
    }, error => {
      this._snackBar.openFromComponent(SnackbarComponent, {
        duration: 3000,
        horizontalPosition: "start",
        data: "Произошла ошибка, попробуйте позже."
      });
    });
  }

  save() {

  }

  setTags(tags: any) {
    this.tags = tags;
  }
}
