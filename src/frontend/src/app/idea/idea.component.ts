import {Component, Input, OnInit} from '@angular/core';
import {Idea} from "../models/Idea";
import {IdeaService} from "../services/idea.service";
import {User} from "../models/User";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-idea',
  templateUrl: './idea.component.html',
  styleUrls: ['./idea.component.css']
})
export class IdeaComponent implements OnInit {

  @Input()
  public idea: Idea;
  public currentUser: User;

  constructor(private ideaService: IdeaService, private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe((user: User) => {
      this.currentUser = user;
    }, error => console.log(error));
  }

  look() {
    const formData = new FormData();
    formData.append('ideaId', this.idea.id);
    formData.append('userId', this.currentUser.id);

    this.ideaService.addLook(formData).subscribe(() => {
      this.idea.looks++;
    }, error => console.log(error));
  }
}
