import {Component, Input} from '@angular/core';
import {Idea} from "../models/Idea";

@Component({
  selector: 'app-idea',
  templateUrl: './idea.component.html',
  styleUrls: ['./idea.component.css']
})
export class IdeaComponent {

  @Input()
  public idea: Idea;

  constructor() { }

}
