import {Component, Input, OnInit} from '@angular/core';
import {Idea} from "../models/Idea";

@Component({
  selector: 'app-idea',
  templateUrl: './idea.component.html',
  styleUrls: ['./idea.component.css']
})
export class IdeaComponent implements OnInit {

  @Input()
  public idea: Idea;

  constructor() { }

  ngOnInit(): void {
  }

}
