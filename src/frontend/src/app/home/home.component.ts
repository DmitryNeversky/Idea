import { Component, OnInit } from '@angular/core';
import {
  ActivatedRoute,
  NavigationCancel,
  NavigationEnd,
  NavigationError,
  NavigationStart,
  Router,
  RouterOutlet
} from "@angular/router";
import {SharedService} from "../shared/shared.service";
import {AuthService} from "../services/auth.service";
import {User} from "../models/User";
import {routesAnimation} from "../animation/routes-animation";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  animations: [routesAnimation]
})
export class HomeComponent implements OnInit {

  public loading: boolean = false;
  public expanded: boolean = false;
  public currentUser: User;

  constructor(private router: Router, private sharedService: SharedService, public authService: AuthService, private activatedRoute: ActivatedRoute) {
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

  ngOnInit() {
    this.currentUser = this.activatedRoute.snapshot.data.currentUser;
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
    this.authService.logout();
  }

}
