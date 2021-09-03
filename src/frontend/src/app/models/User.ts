import {Idea} from "./Idea";
import {Notification} from "./Notification";

export class User {
    id: string;
    username: string;
    password: string;
    role: string;
    name: string;
    phone: string;
    birthday: Date;
    post: string;
    ideas: Idea[];
    avatar: string;
    online: boolean;
    lastVisit: Date;
    registeredDate: Date;
    about: string;
    city: string;
    notifications: Notification[];
}