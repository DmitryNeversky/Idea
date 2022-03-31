import {Component} from '@angular/core';
import {routesAnimation} from "../../animation/routes-animation";
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-idea-fill',
  templateUrl: './idea-fill.component.html',
  styleUrls: ['./idea-fill.component.css'],
  animations: [routesAnimation]
})
export class IdeaFillComponent {

  constructor() { }

  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData.animation;
  }

}
