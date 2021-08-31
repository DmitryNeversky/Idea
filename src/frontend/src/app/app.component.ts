import {Component} from '@angular/core';
import {NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router, RouterOutlet} from "@angular/router";
import {routesAnimation} from "./animation/routes-animation";
import {SharedService} from "./shared/shared.service";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [routesAnimation]
})
export class AppComponent {

  public loading: boolean = false;
  public expanded: boolean = false;

  constructor(private router: Router, private sharedService: SharedService, public auth: AuthService) {
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

    sharedService.changeEmitted$.subscribe(() => {
      this.scrollToTop();
    });
  }

  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData.animation;
  }

  togglePanel() {
    this.expanded = !this.expanded;
  }

  scrollToTop() {
    let scrollToTop = window.setInterval(() => {
      let pos = document.getElementById('content').scrollTop;
      if (pos > 0) {
        document.getElementById('content').scrollTo(0, pos - 20);
      } else {
        window.clearInterval(scrollToTop);
      }
    }, 16);
  }

  logout() {
    this.auth.logout();
  }
}