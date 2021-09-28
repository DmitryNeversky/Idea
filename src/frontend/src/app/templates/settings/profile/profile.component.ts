import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {User} from "../../../models/User";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ImageLoader} from "../../../custom/ImageLoader";
import {Post} from "../../../models/Post";
import {SnackbarService} from "../../../shared/snackbar/snackbar.service";
import {CurrentUserService} from "../../../services/current-user.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public user: User;
  public posts: Post[];
  public personalForm: FormGroup;
  public jobForm: FormGroup;
  public imageLoader: ImageLoader;
  public removeAvatar: boolean = false;

  constructor(private activatedRoute: ActivatedRoute, private userService: UserService,
              private _formBuilder: FormBuilder, private snackBar: SnackbarService) { }

  ngOnInit(): void {
    this.user = this.activatedRoute.snapshot.data.currentUser;
    this.posts = this.activatedRoute.snapshot.data.posts;
    this.imageLoader = new ImageLoader();

    let firstName = this.user.name.split(' ')[0];
    let secondName = this.user.name.split(' ')[1];
    let lastName = this.user.name.split(' ')[2];

    this.personalForm = this._formBuilder.group({
      firstName: [firstName, [Validators.maxLength(32), Validators.required]],
      secondName: [secondName, [Validators.maxLength(32), Validators.required]],
      lastName: [lastName, [Validators.maxLength(32), Validators.required]],
      phone: [this.user.phone, [Validators.minLength(10), Validators.maxLength(10), Validators.required]],
      birthday: [this.user.birthday, [Validators.required]],
      city: [this.user.city, [Validators.maxLength(255)]],
      about: [this.user.about, [Validators.maxLength(1024)]],
    });

    this.jobForm = this._formBuilder.group({
      post: [this.user.post ? this.posts.find(p => p.id == this.user.post.id) : '', [Validators.required, Validators.maxLength(96)]]
    });
  }

  save() {
    if (this.personalForm.invalid || this.jobForm.invalid)
      return

    let firstName = this.personalForm.get('firstName').value;
    firstName = firstName.charAt(0).toUpperCase() + firstName.substr(1).toLowerCase();
    let secondName = this.personalForm.get('secondName').value;
    secondName = secondName.charAt(0).toUpperCase() + secondName.substr(1).toLowerCase();
    let lastName = this.personalForm.get('lastName').value;
    lastName = lastName.charAt(0).toUpperCase() + lastName.substr(1).toLowerCase();

    this.user.name = firstName + " " + secondName + " " + lastName;
    this.user.phone = this.personalForm.get('phone').value;
    this.user.birthday = this.personalForm.get('birthday').value;
    this.user.city = this.personalForm.get('city').value;
    this.user.about = this.personalForm.get('about').value;
    this.user.post = this.jobForm.get('post').value;

    const formData = new FormData();
    formData.append('user', JSON.stringify(this.user));
    if (this.imageLoader.loadImage)
      formData.append('avatar', this.imageLoader.dataTransfer.files.item(0));
    if (this.removeAvatar)
      formData.append('removeAvatar', 'true')

    this.userService.putUser(this.user.id, formData).subscribe((user: User) => {
      CurrentUserService.currentUser = user;
      this.user = user;
      this.snackBar.success('Данные изменены!');
    }, error => {
      console.log(error);
      this.snackBar.error();
    });
  }
}
