import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChatService} from "../../services/chat.service";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {

  private messages: any = [];

  constructor(private stompService: ChatService) { }

  ngOnInit(): void {
    this.stompService.connect();
  }

  ngOnDestroy(): void {
    this.stompService.disconnect();
  }

  sendMessage(): void {
    this.stompService.sendMessage('hello, websocket');
  }
}
