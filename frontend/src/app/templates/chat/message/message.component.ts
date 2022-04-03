import {Component, Input, OnInit} from '@angular/core';
import {Message} from "../../../models/Message";

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  @Input()
  public message: Message;

  constructor() { }

  ngOnInit(): void {
  }

}
