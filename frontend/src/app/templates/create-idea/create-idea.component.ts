import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {IdeaService} from "../../services/idea.service";
import {Idea} from "../../models/Idea";
import {ActivatedRoute, Router} from "@angular/router";
import {ImagesLoader} from "../../custom/ImagesLoader";
import {FilesLoader} from "../../custom/FilesLoader";
import {Tag} from "../../models/Tag";
import {SnackbarService} from "../../shared/snackbar/snackbar.service";
import {CurrentUserService} from "../../services/current-user.service";

@Component({
  selector: 'app-create-idea',
  templateUrl: './create-idea.component.html',
  styleUrls: ['./create-idea.component.css']
})
export class CreateIdeaComponent implements OnInit{

  public imagesLoader = new ImagesLoader();
  public filesLoader = new FilesLoader();

  public tags: Tag[];
  public addedTags: Tag[] = [];

  public mainForm: FormGroup = new FormGroup({
    title: new FormControl('', [Validators.minLength(8), Validators.maxLength(256), Validators.required]),
    text: new FormControl('', [Validators.minLength(256), Validators.maxLength(32768), Validators.required]),
  });

  constructor(private ideaService: IdeaService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private snackBar: SnackbarService) { }

  ngOnInit() {
    this.tags = this.activatedRoute.snapshot.data.tags;
  }

  create() {
    if(this.mainForm.invalid)
      return

    let idea: Idea = new Idea();

    idea.title = this.mainForm.get('title').value;
    idea.body = this.mainForm.get('text').value;

    let newTags: Tag[] = [];
    this.addedTags.forEach(e => {
      const newTag: Tag = new Tag();
      newTag.id = e.id;
      newTag.name = e.name;
      newTags.push(newTag)
    });

    idea.tags = newTags;

    const formData = new FormData();
    formData.append('idea', JSON.stringify(idea));

    for(let i = 0; i < this.imagesLoader.dataTransfer.files.length; i++)
      formData.append('attachedImages', this.imagesLoader.dataTransfer.files[i]);
    for (let i = 0; i < this.filesLoader.dataTransfer.files.length; i++)
      formData.append('attachedFiles', this.filesLoader.dataTransfer.files[i])

    this.ideaService.saveIdea(formData).subscribe((idea: Idea) => {
      this.router.navigate(['ideas']);
      this.snackBar.success('Идея успешно создана!');
      CurrentUserService.currentUser.ideas.push(idea);
    }, error => {
      console.log(error);
      this.snackBar.error();
    });
  }

  setTags(tags: Tag[]) {
    this.tags = tags;
  }
}
