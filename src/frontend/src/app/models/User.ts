import {Idea} from "./Idea";

export class User {
    private _id: number | string;
    private _firstName: string;
    private _secondName: string;
    private _lastName: string;
    private _email: string;
    private _password: string;
    private _phone: string;
    private _ideas: Idea[];

    constructor(id: number | string, firstName: string, secondName: string, lastName: string, email: string, password: string, phone: string, ideas: Idea[]) {
        this._id = id;
        this._firstName = firstName;
        this._secondName = secondName;
        this._lastName = lastName;
        this._email = email;
        this._password = password;
        this._phone = phone;
        this._ideas = ideas;
    }

    get id(): number | string {
        return this._id;
    }

    set id(value: number | string) {
        this._id = value;
    }

    get firstName(): string {
        return this._firstName;
    }

    set firstName(value: string) {
        this._firstName = value;
    }

    get secondName(): string {
        return this._secondName;
    }

    set secondName(value: string) {
        this._secondName = value;
    }

    get lastName(): string {
        return this._lastName;
    }

    set lastName(value: string) {
        this._lastName = value;
    }

    get email(): string {
        return this._email;
    }

    set email(value: string) {
        this._email = value;
    }

    get password(): string {
        return this._password;
    }

    set password(value: string) {
        this._password = value;
    }

    get phone(): string {
        return this._phone;
    }

    set phone(value: string) {
        this._phone = value;
    }

    get ideas(): Idea[] {
        return this._ideas;
    }

    set ideas(value: Idea[]) {
        this._ideas = value;
    }
}