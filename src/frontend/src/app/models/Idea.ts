import {User} from "./User";
import {Tag} from "./Tag";

export class Idea {
    id: string;
    title: string;
    text: string;
    status: string;
    rating: number;
    looks: number;
    createdDate: Date;
    tags: Tag[];
    images: string[];
    files: Map<string, string>;
    author: User;
    removeImages: string[];
    removeFiles: string[];
    ratedUsers: number[];
    unratedUsers: number[];
}