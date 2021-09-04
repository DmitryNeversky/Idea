import {Component, Input} from '@angular/core';
import {Idea} from "../../models/Idea";
import {IdeaService} from "../../services/idea.service";

@Component({
  selector: 'app-idea',
  templateUrl: './idea.component.html',
  styleUrls: ['./idea.component.css']
})
export class IdeaComponent {

  @Input()
  public idea: Idea;

  constructor(private ideaService: IdeaService) { }

  look() {
    this.ideaService.addLook(this.idea.id).subscribe(() => {

    }, error => console.log(error));
  }
}
