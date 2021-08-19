import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {IdeaService} from "../services/idea.service";
import {Idea} from "../models/Idea";
import {Router} from "@angular/router";
import {SnackbarComponent} from "../shared/snackbar/snackbar.component";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-create-idea',
  templateUrl: './create-idea.component.html',
  styleUrls: ['./create-idea.component.css']
})
export class CreateIdeaComponent {

  public tags: string[];

  public mainForm: FormGroup = new FormGroup({
    title: new FormControl('', []),
    text: new FormControl('', []),
  });

  constructor(private ideaService: IdeaService,
              private router: Router,
              private _snackBar: MatSnackBar) { }

  create() {
    let formData = new FormData();

    formData.append('title', this.mainForm.get('title').value);
    formData.append('text', this.mainForm.get('text').value);

    this.ideaService.add(formData).subscribe((response: Idea) => {
      this.router.navigateByUrl('ideas');
      this._snackBar.openFromComponent(SnackbarComponent, {
        duration: 3000,
        horizontalPosition: "start",
        data: "Идея успешно создана!"
      });
    }, error => console.log(error));
  }

  setTags(tags: any) {
    this.tags = tags;
  }
}
