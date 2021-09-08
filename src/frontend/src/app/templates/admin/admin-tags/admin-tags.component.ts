import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TagService} from "../../../services/tag.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Tag} from "../../../models/Tag";

@Component({
  selector: 'app-admin-tags',
  templateUrl: './admin-tags.component.html',
  styleUrls: ['./admin-tags.component.css']
})
export class AdminTagsComponent implements OnInit {

  public tags: Tag[];

  public tagForm: FormGroup;

  constructor(private activatedRoute: ActivatedRoute, private tagService: TagService) { }

  ngOnInit(): void {
    this.tags = this.activatedRoute.snapshot.data.tags;
    this.tagForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.max(64)])
    });
  }

  create() {
    if(!this.tagForm.valid)
      return;

    let tag: Tag = new Tag();
    tag.name = this.tagForm.get('name').value;

    this.tagService.saveTag(tag).subscribe((tag: Tag) => {
      this.tags.push(tag);
      this.tagForm.reset();
    }, error => console.log());
  }

  delete(tag: Tag) {
    this.tagService.deleteTag(tag.id).subscribe(() => {
      this.tags = this.tags.filter(t => t != tag);
    }, error => console.log(error));
  }
}
