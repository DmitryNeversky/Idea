import { Component, OnInit } from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {routesAnimation} from "../animation/routes-animation";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css'],
  animations: [routesAnimation]
})
export class SettingsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData.animation;
  }

}
