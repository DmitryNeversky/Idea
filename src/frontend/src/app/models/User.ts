import {Idea} from "./Idea";

export class User {
    id: string;
    username: string;
    password: string;
    name: string;
    phone: string;
    birthday: string;
    post: string;
    ideas: Idea[];
    online: boolean;
    lastVisit: Date;
    registeredDate: Date;
}