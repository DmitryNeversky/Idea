import {User} from "./User";

export class Idea {
    private _id: number | string;
    private _title: string;
    private _text: string;
    private _rating: number;
    private _looks: number;
    private _author: User;

    constructor(id: number | string, title: string, text: string, rating: number, looks: number, author: User) {
        this._id = id;
        this._title = title;
        this._text = text;
        this._rating = rating;
        this._looks = looks;
        this._author = author;
    }

    get id(): number | string {
        return this._id;
    }

    set id(value: number | string) {
        this._id = value;
    }

    get title(): string {
        return this._title;
    }

    set title(value: string) {
        this._title = value;
    }

    get text(): string {
        return this._text;
    }

    set text(value: string) {
        this._text = value;
    }

    get rating(): number {
        return this._rating;
    }

    set rating(value: number) {
        this._rating = value;
    }

    get looks(): number {
        return this._looks;
    }

    set looks(value: number) {
        this._looks = value;
    }

    get author(): User {
        return this._author;
    }

    set author(value: User) {
        this._author = value;
    }
}