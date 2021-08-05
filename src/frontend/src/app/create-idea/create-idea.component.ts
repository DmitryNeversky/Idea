import {Component} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {IdeaService} from "../services/idea.service";
import {Idea} from "../models/Idea";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-idea',
  templateUrl: './create-idea.component.html',
  styleUrls: ['./create-idea.component.css']
})
export class CreateIdeaComponent {

  public mainForm: FormGroup = new FormGroup({
    title: new FormControl('', []),
    text: new FormControl('', []),
  });

  constructor(private ideaService: IdeaService, private router: Router) { }

  create() {
    let formData = new FormData();

    formData.append('title', this.mainForm.get('title').value);
    formData.append('text', this.mainForm.get('text').value);

    console.log(this.mainForm.get('title').value)

    this.ideaService.add(formData).subscribe((response: Idea) => {
      this.router.navigateByUrl('ideas');
    }, error => console.log(error));
  }

}
