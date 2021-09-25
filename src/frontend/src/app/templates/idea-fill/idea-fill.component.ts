import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Idea} from "../../models/Idea";
import {environment} from "../../../environments/environment";
import {animate, style, transition, trigger} from "@angular/animations";
import {User} from "../../models/User";
import {IdeaService} from "../../services/idea.service";
import {Role} from "../../models/Role";
import {Status} from "../../models/Status";
import {FormControl, FormGroup} from "@angular/forms";
import {SnackbarService} from "../../shared/snackbar/snackbar.service";

@Component({
  selector: 'app-idea-fill',
  templateUrl: './idea-fill.component.html',
  styleUrls: ['./idea-fill.component.css'],
  animations: [
    trigger('fade', [
      transition('void => *', [
        style({ visibility: 'hidden', opacity: 0 }),
        animate('.3s', style({ visibility: 'visible', opacity: 1 })),
      ]),
      transition('* => void', [
        style({ visibility: 'visible', opacity: 1 }),
        animate('.3s', style({ visibility: 'hidden', opacity: 0 })),
      ]),
    ]),
  ],
})
export class IdeaFillComponent implements OnInit {

  public voted: number;

  public uploadPath: string = environment.uploadPath;

  public idea: Idea;
  public currentUser: User;
  public status = Status;

  public roles: Role[] = [];

  public statusForm: FormGroup;

  public resizableImage: string;
  public isAdmin: boolean = false;

  constructor(private activatedRoute: ActivatedRoute, private ideaService: IdeaService,
              private snackBar: SnackbarService) { }

  ngOnInit(): void {
    this.idea = this.activatedRoute.snapshot.data.idea;
    this.currentUser = this.activatedRoute.snapshot.data.currentUser;
    this.roles = this.activatedRoute.snapshot.data.roles

    this.isAdmin = !!this.currentUser.roles.find(r => r.name == 'ADMIN' || r.name == 'SUPER_ADMIN');

    if(!!this.idea.ratedUsers.find(u => u.id == this.currentUser.id)) {
      this.voted = 1;
    } else if(!!this.idea.unratedUsers.find(u => u.id == this.currentUser.id)) {
      this.voted = -1;
    } else {
      this.voted = 0;
    }

    this.statusForm = new FormGroup({
      status: new FormControl(this.idea.status)
    });
  }

  resize(image: string) {
    this.resizableImage = image;
  }

  addRating() {
    if(this.voted == 1) {
      this.voted = 0;
      this.idea.rating -= 1;
    } else if(this.voted == 0) {
      this.voted = 1;
      this.idea.rating += 1;
    } else if(this.voted == -1) {
      this.voted = 1;
      this.idea.rating += 2;
    }

    this.ideaService.addRating(this.idea.id);
  }

  reduceRating() {
    if(this.voted == -1) {
      this.voted = 0;
      this.idea.rating += 1;
    } else if(this.voted == 0) {
      this.voted = -1;
      this.idea.rating -= 1;
    } else if(this.voted == 1) {
      this.voted = -1;
      this.idea.rating -= 2;
    }

    this.ideaService.reduceRating(this.idea.id);
  }

  changeStatus() {
    if(this.statusForm.get('status').value == this.idea.status)
      return;

    let status = this.statusForm.get('status').value;

    this.ideaService.changeStatus(this.idea.id, status).subscribe(() => {
      this.idea.status = status;
      this.snackBar.success('Статус идеи изменен.');
    },error => {
      console.log(error);
      this.snackBar.error();
    });
  }
}
