import { Component, OnInit } from '@angular/core';
import {Idea} from "../models/Idea";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-ideas',
  templateUrl: './ideas.component.html',
  styleUrls: ['./ideas.component.css']
})
export class IdeasComponent implements OnInit {

  public search: string;
  public status: string;

  public pageIndex: number = 0;
  public pageSize: number = 1;
  public pages: number[] = [];

  public ideas: Idea[];
  public paginatedIdeas: Idea[];
  public filteredIdeas: Idea[];

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.ideas = this.activatedRoute.snapshot.data.ideas;

    this.filteredIdeas = this.ideas;
    this.goIndex(0);
  }

  initPagination(size: number) {

    this.pages = [];
    this.pages.push(0);

    if(size > 7) {

      if(this.pageIndex < 4) {
        for(let i = 1; i < 6; i++) {
          this.pages.push(i);
        }
      } else if (this.pageIndex >= 4) {
        this.pages.push(this.pageIndex - 2);
        this.pages.push(this.pageIndex - 1);

        if(this.pageIndex != size - 1)
          this.pages.push(this.pageIndex);

        if(this.pageIndex < size - 4) {
          this.pages.push(this.pageIndex + 1);
          this.pages.push(this.pageIndex + 2);
        } else if (this.pageIndex >= size - 4 && this.pageIndex < size - 2) {
          this.pages.push(this.pageIndex + 1);
          if (this.pageIndex == size - 4)
            this.pages.push(this.pageIndex + 2);
        }
      }
    } else {
      for (let i = 1; i < size - 1; i++)
        this.pages.push(i);
    }

    if(this.ideas.length > 1 && this.filteredIdeas.length > 1)
      this.pages.push(size - 1);
  }

  goIndex(index: number) {
    this.pageIndex = index;
    this.paginatedIdeas = [];

    if(this.filteredIdeas.length == 0) {
      this.filteredIdeas = [];
    } else {
      for (let i = 0; i < this.pageSize; i++)
        this.paginatedIdeas.push(this.filteredIdeas[index * this.pageSize + i]);
    }

    this.initPagination(this.filteredIdeas.length);
  }

  filter() {
    this.filteredIdeas = this.ideas;

    if (this.search) {
      if (this.search.includes('#'))
        this.filteredIdeas = this.filteredIdeas.filter((idea: Idea) => idea.id == this.search.slice(1));
      else
        this.filteredIdeas = this.filteredIdeas.filter((idea: Idea) => idea.title.toLowerCase().includes(this.search.toLowerCase()));
    }

    if (this.status)
      this.filteredIdeas = this.filteredIdeas.filter((idea: Idea) => idea.status == this.status);

    this.goIndex(0);
  }

  setPageSize(event: any) {
    this.pageSize = event.value;
    this.goIndex(0);
  }
}
