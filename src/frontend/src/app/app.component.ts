import {Component} from '@angular/core';
import {Idea} from "./models/Idea";
import {NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  public loading: boolean = false;
  public expanded: boolean = false;

  public ideas: Idea[] | undefined;

  constructor(private router: Router) {
    this.router.events.subscribe((event: any) => {
      switch (true) {
        case event instanceof NavigationStart: {
          this.loading = true;
          break;
        }

        case event instanceof NavigationEnd:
        case event instanceof NavigationCancel:
        case event instanceof NavigationError: {
          this.loading = false;
          break;
        }
        default: {
          break;
        }
      }
    });
  }

  togglePanel() {
    this.expanded = !this.expanded;
  }
}