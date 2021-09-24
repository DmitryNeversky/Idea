import {Component} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {appRoutesAnimation} from "./animation/app-routes-animation";
import {SharedService} from "./shared/shared.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [appRoutesAnimation]
})
export class AppComponent {

  darkMode: boolean = !!localStorage.getItem('dark-mode');

  constructor(private sharedService: SharedService) {
    sharedService.changeDarkMode$.subscribe(() => {
      this.darkMode = !!localStorage.getItem('dark-mode');
    });
  }

  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData.animation;
  }
}