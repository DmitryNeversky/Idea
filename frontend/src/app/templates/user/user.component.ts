import {Component} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {routesAnimation} from "../../animation/routes-animation";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  animations: [routesAnimation]
})
export class UserComponent {

  constructor() { }

  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData.animation;
  }

}
