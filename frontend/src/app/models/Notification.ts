export class Notification {
    id: string|number;
    title: string;
    text: string;
    createdDate: Date;

    constructor(id: string | number, title: string, text: string, createdDate: Date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdDate = createdDate;
    }
}