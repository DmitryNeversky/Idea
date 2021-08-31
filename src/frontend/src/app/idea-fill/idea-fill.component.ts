import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Idea} from "../models/Idea";
import {environment} from "../../environments/environment";
import {animate, style, transition, trigger} from "@angular/animations";
import {User} from "../models/User";
import {IdeaService} from "../services/idea.service";

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

  public voted: number;

  public uploadImagePath: string = environment.uploadPath + "images/";

  public idea: Idea;
  public currentUser: User;

  public resizableImage: string;

  constructor(private activatedRoute: ActivatedRoute, private ideaService: IdeaService) { }

  ngOnInit(): void {
    this.idea = this.activatedRoute.snapshot.data.idea;
    this.currentUser = this.activatedRoute.snapshot.data.currentUser;
    if(this.idea.ratedUsers.includes(+this.currentUser.id)) {
      this.voted = 1;
    } else if(this.idea.unratedUsers.includes(+this.currentUser.id)) {
      this.voted = -1;
    } else {
      this.voted = 0;
    }
  }

  resize(image: string) {
    this.resizableImage = image;
  }

  addRating() {
    if(this.voted == 1) {
      this.voted = 0;
      this.idea.rating -= 1;
    } else if(this.voted == 0) {
      this.voted = 1;
      this.idea.rating += 1;
    } else if(this.voted == -1) {
      this.voted = 1;
      this.idea.rating += 2;
    }
    const formData = new FormData();
    formData.append('ideaId', this.idea.id);
    formData.append('userId', this.currentUser.id);
    this.ideaService.addRating(formData);
  }

  reduceRating() {
    if(this.voted == -1) {
      this.voted = 0;
      this.idea.rating += 1;
    } else if(this.voted == 0) {
      this.voted = -1;
      this.idea.rating -= 1;
    } else if(this.voted == 1) {
      this.voted = -1;
      this.idea.rating -= 2;
    }
    const formData = new FormData();
    formData.append('ideaId', this.idea.id);
    formData.append('userId', this.currentUser.id);
    this.ideaService.reduceRating(formData);
  }
}
