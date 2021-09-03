import {Component, Input} from '@angular/core';
import {Idea} from "../../models/Idea";
import {IdeaService} from "../../services/idea.service";
import {User} from "../../models/User";

@Component({
  selector: 'app-idea',
  templateUrl: './idea.component.html',
  styleUrls: ['./idea.component.css']
})
export class IdeaComponent {

  @Input()
  public idea: Idea;
  @Input()
  public currentUser: User;

  constructor(private ideaService: IdeaService) { }

  look() {
    const formData = new FormData();
    formData.append('ideaId', this.idea.id);
    formData.append('userId', this.currentUser.id);

    this.ideaService.addLook(formData).subscribe(() => {
      this.idea.looks++;
    }, error => console.log(error));
  }
}
