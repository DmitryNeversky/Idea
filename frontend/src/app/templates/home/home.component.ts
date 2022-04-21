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
import {SharedService} from "../../shared/shared.service";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/User";
import {routesAnimation} from "../../animation/routes-animation";
import {Notification} from "../../models/Notification";
import {UserService} from "../../services/user.service";
// import {Stomp} from "@stomp/stompjs";
import * as Stomp from "stompjs";
import * as SockJS from "sockjs-client";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  animations: [routesAnimation]
})
export class HomeComponent implements OnInit {

  public loading: boolean = false;
  public expanded: boolean = false;
  public darkMode: boolean = false;
  public currentUser: User;

  constructor(private router: Router, private sharedService: SharedService,
              private authService: AuthService, private activatedRoute: ActivatedRoute,
              private userService: UserService) {
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

    sharedService.changeDarkMode$.subscribe(() => {
      this.darkMode = !!localStorage.getItem('dark-mode');
    });
  }

  ngOnInit() {
    this.currentUser = this.activatedRoute.snapshot.data.currentUser;
    if(!this.currentUser.notifications)
      this.currentUser.notifications = [];
    this.darkMode = !!localStorage.getItem('dark-mode');
    this.connectToWebSocket();
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

  deleteNotification(notification: Notification) {
    this.userService.deleteNotification(notification.id).subscribe(() => {
      this.currentUser.notifications = this.currentUser.notifications.filter(n => n != notification);
    }, error => console.log(error));
  }

  logout() {
    this.authService.logout();
  }

  connectToWebSocket() {
    const socket = new SockJS("http://localhost:8085/notifications");
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, frame => {
      stompClient.subscribe(`/topic/user/${this.currentUser.id}/notifications`, (notification: Notification) => {
        this.currentUser.notifications.push(notification);
      });
    }, function(error) {
      alert("STOMP error " + error);
    });
  }
}
