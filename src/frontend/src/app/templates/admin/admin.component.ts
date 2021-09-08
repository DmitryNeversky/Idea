import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {routesAnimation} from "../../animation/routes-animation";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
  animations: [routesAnimation]
})
export class AdminComponent {

  constructor() {}

  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData.animation;
  }
}
