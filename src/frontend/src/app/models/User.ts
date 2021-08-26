import {Idea} from "./Idea";

export class User {
    id: number | string;
    username: string;
    password: string;
    name: string;
    phone: string;
    birthday: Date;
    post: string;
    ideas: Idea[];
    online: boolean;
    lastVisit: Date;
    registeredDate: Date;
}