import {User} from "./User";

export class Idea {
    id: number | string;
    title: string;
    text: string;
    rating: number;
    looks: number;
    author: User;

    constructor(title: string, text: string) {
        this.title = title;
        this.text = text;
    }
}