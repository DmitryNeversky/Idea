import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Idea} from "../models/Idea";
import {environment} from "../../environments/environment";
import {animate, state, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-idea-fill',
  templateUrl: './idea-fill.component.html',
  styleUrls: ['./idea-fill.component.css'],
  animations: [
    trigger('fade', [
      transition('void => *', [
        style({ visibility: 'hidden', opacity: 0 }),
        animate('.3s', style({ visibility: 'visible', opacity: 1 })),
      ]),
      transition('* => void', [
        style({ visibility: 'visible', opacity: 1 }),
        animate('.3s', style({ visibility: 'hidden', opacity: 0 })),
      ]),
    ]),
  ],
})
export class IdeaFillComponent implements OnInit {

  public uploadImagePath: string = environment.uploadPath + "images/";

  public idea: Idea;

  public resizableImage: string;

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.idea = this.activatedRoute.snapshot.data.idea;
  }

  resize(image: string) {
    this.resizableImage = image;
  }
}
