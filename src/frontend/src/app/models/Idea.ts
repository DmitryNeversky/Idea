import {User} from "./User";

export class Idea {
    id: number | string;
    title: string;
    text: string;
    status: string;
    rating: number;
    looks: number;
    author: User;

    constructor(title: string, text: string, status: string) {
        this.title = title;
        this.text = text;
        this.status = status;
    }
}