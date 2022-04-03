import {Component, OnDestroy, OnInit} from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {

  private socket = null;
  private stompClient = null;

  public messages: any[] = [];

  constructor() { }

  ngOnInit(): void {
    this.socket = new SockJS('http://localhost:8084/chat');
    this.stompClient = Stomp.over(this.socket);
    this.connect();
  }

  ngOnDestroy(): void {
    this.disconnect();
  }

  connect(): void {
    this.stompClient.connect({}, (frame) => {
      this.stompClient.subscribe('/topic/greetings', (data) => {
        this.onMessageReceived(data);
        console.log("HERE IS FROM GREETINGS :: " + data);
      });
    });
  }

  disconnect(): void {
    if(this.stompClient !== null)
      this.stompClient.disconnect();
    console.log("Websocket has been disconnected.");
  }

  reconnect(): void {
    this.disconnect();
    this.connect();
  }

  sendMessage(message: string): void {
    this.stompClient.send("/app/hello", {}, JSON.stringify(message));
  }

  onMessageReceived(message): void {
    this.messages.push(message);
  }
}
