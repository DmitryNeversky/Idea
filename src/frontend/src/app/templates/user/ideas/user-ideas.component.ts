import { Component, OnInit } from '@angular/core';
import {User} from "../../../models/User";
import {ActivatedRoute} from "@angular/router";
import {Idea} from "../../../models/Idea";

@Component({
  selector: 'app-user-ideas',
  templateUrl: './user-ideas.component.html',
  styleUrls: ['./user-ideas.component.css']
})
export class UserIdeasComponent implements OnInit {

  public user: User;
  public ideas: Idea[];

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.user = this.activatedRoute.snapshot.data.user;
    this.ideas = this.user.ideas;
  }

}
