import {Idea} from "./Idea";

export class User {
    id: string;
    username: string;
    password: string;
    role: string;
    name: string;
    phone: string;
    birthday: string;
    post: string;
    ideas: Idea[];
    online: boolean;
    lastVisit: Date;
    registeredDate: Date;
    about: string;
    city: string;
}