import {Component, OnInit} from '@angular/core';
import {IdeaService} from "./services/idea.service";
import {Idea} from "./models/Idea";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  public ideas: Idea[] | undefined;

  constructor() {}

}