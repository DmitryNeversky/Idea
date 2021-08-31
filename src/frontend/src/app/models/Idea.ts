import {User} from "./User";

export class Idea {
    id: string;
    title: string;
    text: string;
    status: string;
    rating: number;
    looks: number;
    createdDate: Date;
    tags: string[];
    images: string[];
    files: Map<string, string>;
    author: User;
    removeImages: string[];
    removeFiles: string[];
    ratedUsers: number[];
    unratedUsers: number[];
}