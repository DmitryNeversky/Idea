import { Component, OnInit } from '@angular/core';
import {Idea} from "../models/Idea";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-ideas',
  templateUrl: './ideas.component.html',
  styleUrls: ['./ideas.component.css']
})
export class IdeasComponent implements OnInit {

  public ideas: Idea[];

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.ideas = this.activatedRoute.snapshot.data.ideas;
  }

}
