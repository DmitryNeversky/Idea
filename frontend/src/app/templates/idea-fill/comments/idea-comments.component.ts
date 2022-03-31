import { Component, OnInit } from '@angular/core';
import {Idea} from "../../../models/Idea";
import {User} from "../../../models/User";

@Component({
  selector: 'app-idea-comments',
  templateUrl: './idea-comments.component.html',
  styleUrls: ['./idea-comments.component.css']
})
export class IdeaCommentsComponent implements OnInit {

  public idea: Idea;
  public currentUser: User;

  constructor() { }

  ngOnInit(): void {
  }

}
