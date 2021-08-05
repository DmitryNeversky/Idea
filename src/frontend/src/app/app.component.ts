import {Component, OnInit} from '@angular/core';
import {IdeaService} from "./services/idea.service";
import {Idea} from "./models/Idea";
import {Tile} from "@angular/material/grid-list/tile-coordinator";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  tiles = [
    {text: 'Tile 1', cols: 2, rows: 1},
    {text: 'Tile 2', cols: 2, rows: 1},
    {text: 'Tile 3', cols: 2, rows: 1},
    {text: 'Tile 4', cols: 2, rows: 1},
  ];

  public ideas: Idea[] | undefined;

  constructor(private ideaService: IdeaService) {}

  ngOnInit(): void {

  }

}
