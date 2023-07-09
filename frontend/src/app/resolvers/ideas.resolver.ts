import {Injectable} from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import {Observable} from 'rxjs';
import {IdeaService} from "../services/idea.service";
import {Idea} from "../models/Idea";

@Injectable({
  providedIn: 'root'
})
export class IdeasResolver  {

  constructor(private ideaService: IdeaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Idea[]> | Promise<Idea[]>{

    return this.ideaService.getIdeas();
  }
}
