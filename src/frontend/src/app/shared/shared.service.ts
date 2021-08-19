import { Injectable } from '@angular/core';
import {Subject} from "rxjs";

@Injectable()
export class SharedService {

  private emitChangeSource = new Subject<any>();
  changeEmitted$ = this.emitChangeSource.asObservable();

  emitChange(change: any = null) {
    this.emitChangeSource.next(change);
  }
}
