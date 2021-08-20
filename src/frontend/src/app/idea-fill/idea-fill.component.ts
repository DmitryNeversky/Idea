import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Idea} from "../models/Idea";

@Component({
  selector: 'app-idea-fill',
  templateUrl: './idea-fill.component.html',
  styleUrls: ['./idea-fill.component.css']
})
export class IdeaFillComponent implements OnInit {

  public idea: Idea;

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.idea = this.activatedRoute.snapshot.data.idea;
    console.log(this.idea);
  }

}
