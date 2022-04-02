import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  // Using SockJS to pass any restrictions (f.e proxy, vpn ...)
  private socket = new SockJS('http://localhost:8080/chat');
  private stompClient = Stomp.over(this.socket);

  public receivedData: any[] = [];

  connect(): void {
    this.stompClient.connect({}, (frame) => {
      this.stompClient.subscribe('/topic/greetings', (data) => {
        this.onMessageReceived(data);
        console.log("HERE IS FROM GREETINGS :: " + data);
      });
    });
  }

  onMessageReceived(message): void {
    this.receivedData.push(message);
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
}
