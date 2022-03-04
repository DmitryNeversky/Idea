import {Component, OnInit} from '@angular/core';
import {User} from "../../../models/User";
import {Idea} from "../../../models/Idea";
import {Status} from "../../../models/Status";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  public user: User;
  public ideas: Idea[];

  public acceptedIdeas: Idea[];
  public lookIdeas: Idea[];
  public refusedIdeas: Idea[];

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.user = this.activatedRoute.snapshot.data.user;
    this.ideas = this.user.ideas;
    this.acceptedIdeas = this.ideas.filter((idea: Idea) => idea.status == Status.ACCEPTED);
    this.lookIdeas = this.ideas.filter((idea: Idea) => idea.status == Status.LOOKING);
    this.refusedIdeas = this.ideas.filter((idea: Idea) => idea.status == Status.REFUSED);
  }
}
