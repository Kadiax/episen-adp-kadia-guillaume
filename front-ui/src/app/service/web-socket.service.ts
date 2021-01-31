import { Injectable } from '@angular/core';
// @ts-ignore
import {Notification} from '../../model/Notification';
@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  webSocket: WebSocket;
  notifs: Notification[] = [];

  constructor() { }

  public openWebSocket(uri: any){
    // this.webSocket = new WebSocket('ws://localhost:8080/chat/123456353');
    this.webSocket = new WebSocket(uri);

    this.webSocket.onopen = (event) => {
      console.log('Open: ', event);
    };

    this.webSocket.onmessage = (event) => {
      const notification = JSON.parse(event.data);
      this.notifs.push(notification);
    };

    this.webSocket.onclose = (event) => {
      console.log('Close: ', event);
    };
  }

  public closeWebSocket() {
    this.webSocket.close();
  }
}
