import {Component, Input, OnInit} from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
  animations: [
    trigger('fade', [
      transition('void => *', [
        style({ visibility: 'hidden', opacity: 0 }),
        animate('.2s', style({ visibility: 'visible', opacity: 1 })),
      ]),
      transition('* => void', [
        style({ visibility: 'visible', opacity: 1 }),
        animate('.1s', style({ visibility: 'hidden', opacity: 0 })),
      ]),
    ]),
  ],
})
export class ModalComponent implements OnInit {

  @Input()
  obj: Object;

  constructor() { }

  ngOnInit(): void {
  }
}
