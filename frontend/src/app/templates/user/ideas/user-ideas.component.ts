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
  public filteredIdeas: Idea[];

  public reverse: boolean = false;
  public status: string = "LOOKING";

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.user = this.activatedRoute.snapshot.data.user;
    this.ideas = this.user.ideas;
    this.filteredIdeas = this.ideas
  }

  sort(value: number) {
    switch (value) {
      case 1:
        this.filteredIdeas.sort((a: Idea, b: Idea) => {
          if(b.title.toLowerCase() < a.title.toLowerCase())
            return 1;
          else if(a.title.toLowerCase() < b.title.toLowerCase())
            return -1;
          else return 0;
        });
        break;
      case 2:
        this.filteredIdeas.sort((a, b) => {
          if (b.looks > a.looks)
            return 1
          else if (b.looks < a.looks)
            return -1
          else return 0
        });
        break;
      case 3:
        this.filteredIdeas.sort((a, b) => {
          if (b.rating > a.rating)
            return 1
          else if (b.rating < a.rating)
            return -1
          else return 0
        });
        break;
      case 4:
        this.filteredIdeas.sort((a, b) => {
          if (b.createdDate > a.createdDate)
            return 1
          else if (b.createdDate < a.createdDate)
            return -1
          else return 0
        });
        break;
    }

    if(this.reverse)
      this.filteredIdeas.reverse();
  }

  filter() {
    if (this.status) {
      this.filteredIdeas = this.ideas.filter((idea: Idea) => idea.status == this.status);
    }
  }

  reverseSort() {
    this.reverse = !this.reverse;
    this.filteredIdeas.reverse();
  }
}
