import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {User} from "../../models/User";
import {Idea} from "../../models/Idea";
import {Status} from "../../models/Status";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  public user: User;
  public ideas: Idea[];
  public acceptedIdeas: Idea[];
  public lookIdeas: Idea[];
  public refusedIdeas: Idea[];

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.user = this.activatedRoute.snapshot.data.user;
    this.ideas = this.user.ideas;
    this.acceptedIdeas = this.user.ideas.filter((idea: Idea) => idea.status == Status.ACCEPTED);
    this.lookIdeas = this.user.ideas.filter((idea: Idea) => idea.status == Status.LOOKING);
    this.refusedIdeas = this.user.ideas.filter((idea: Idea) => idea.status == Status.REFUSED);
  }

}
