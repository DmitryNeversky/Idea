import {Component, ElementRef, OnInit, ViewChild} from "@angular/core";
import {User} from "../../../models/User";
import {Post} from "../../../models/Post";
import {ActivatedRoute} from "@angular/router";
import {SharedService} from "../../../shared/shared.service";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {

  public search: string;
  public filteredPost: string = 'all';

  public reverse: boolean = false;
  public loaded: boolean = false;

  public pageIndex: number = 0;
  public pageSize: number = 5;
  public pagers: number[] = [];
  public pagerSize: number = 0;

  public users: User[];
  public paginatedUsers: User[];
  public filteredUsers: User[];

  public posts: Post[];

  public currentUser: User;
  public modalUser: User;

  constructor(private activatedRoute: ActivatedRoute,
              private sharedService: SharedService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.posts = this.activatedRoute.snapshot.data.posts.map((p: Post) => p.name);

    this.userService.getUsers().subscribe((users: User[]) => {
      this.users = users;
      this.filteredUsers = this.users;

      this.sort(1);

      this.loaded = true;
    });
  }

  initPagination(size: number) {

    this.pagers = [];
    this.pagers.push(0);

    if(this.pageSize * size - this.filteredUsers.length == 0)
      size = size - 1;

    this.pagerSize = size;

    if(size > 7) {

      if(this.pageIndex < 4) {
        for(let i = 1; i < 6; i++)
          this.pagers.push(i);

      } else if (this.pageIndex >= 4) {
        this.pagers.push(this.pageIndex - 2);
        this.pagers.push(this.pageIndex - 1);

        if(this.pageIndex != size)
          this.pagers.push(this.pageIndex);

        if(this.pageIndex < size - 3) {
          this.pagers.push(this.pageIndex + 1);
          this.pagers.push(this.pageIndex + 2);
        } else if (this.pageIndex >= size - 3 && this.pageIndex < size - 1) {
          this.pagers.push(this.pageIndex + 1);
          if (this.pageIndex == size - 3)
            this.pagers.push(this.pageIndex + 2);
        }
      }
      this.pagers.push(size);
    } else {
      for (let i = 1; i <= size; i++)
        this.pagers.push(i);
    }

    this.sharedService.emitChange();
  }

  goIndex(index: number) {
    this.pageIndex = index;
    this.paginatedUsers = [];

    if(this.filteredUsers.length == 0) {
      this.filteredUsers = [];
    } else if (this.filteredUsers.length / this.pageSize) {
      for (let i = 0; i < this.pageSize; i++)
        if(this.filteredUsers[index * this.pageSize + i])
          this.paginatedUsers.push(this.filteredUsers[index * this.pageSize + i]);
    }

    this.initPagination(Math.floor(this.filteredUsers.length / this.pageSize));
  }

  filter() {
    this.filteredUsers = this.users;

    if (this.search) {
      this.filteredUsers = this.filteredUsers.filter((user: User) => user.username.toLowerCase()
          .includes(this.search.toLowerCase()) || user.name.toLowerCase().includes(this.search.toLowerCase()));
    }

    if (this.filteredPost && this.filteredPost != 'all') {
      this.filteredUsers = this.filteredUsers.filter((user: User) => user.post.name == this.filteredPost);
    }

    this.goIndex(0);
  }

  sort(value: number) {
    switch (value) {
      case 1:
        this.filteredUsers.sort((a, b) => {
          if(b.name.toLowerCase() < a.name.toLowerCase())
            return 1;
          else if(a.name.toLowerCase() < b.name.toLowerCase())
            return -1;
          else return 0;
        });
        break;
      case 2:
        this.filteredUsers = this.filteredUsers.sort((a, b) => {
          if (b.ideas.length > a.ideas.length)
            return 1
          else if (b.ideas.length < a.ideas.length)
            return -1
          else return 0
        });
        break;
      case 3:
        this.filteredUsers = this.filteredUsers.sort((a, b) => {
          if (b.birthday > a.birthday)
            return 1
          else if (b.birthday < a.birthday)
            return -1
          else return 0
        });
        break;
      case 4:
        this.filteredUsers = this.filteredUsers.sort((a, b) => {
          if (b.lastVisit > a.lastVisit)
            return 1
          else if (b.lastVisit < a.lastVisit)
            return -1
          else return 0
        });
        break;
    }

    if(this.reverse)
      this.filteredUsers.reverse();

    this.goIndex(0);
  }

  reverseSort() {
    this.reverse = !this.reverse;
    this.filteredUsers.reverse();
    this.goIndex(0);
  }

  setPageSize(value: number) {
    this.pageSize = value;
    this.goIndex(0);
  }

  @ViewChild('modal')
  private modal: ElementRef;

  openModal(user: User) {
    this.modalUser = user;
  }

  hideModal(event: any = null) {
    if(event && event.target.classList.contains('modal-overlay')) {
      this.modalUser = null;
    } else if(!event) {
      this.modalUser = null;
    }
  }
}
