import {Idea} from "./Idea";
import {Notification} from "./Notification";
import {Post} from "./Post";
import {Role} from "./Role";
import {Settings} from "./Settings";

export class User {
    id: string;
    username: string;
    password: string;
    roles: Role[];
    name: string;
    phone: string;
    birthday: Date;
    post: Post;
    ideas: Idea[];
    avatar: string;
    online: boolean;
    lastVisit: Date;
    registeredDate: Date;
    about: string;
    city: string;
    notifications: Notification[];
    settings: Settings;
    enabled: boolean;
}