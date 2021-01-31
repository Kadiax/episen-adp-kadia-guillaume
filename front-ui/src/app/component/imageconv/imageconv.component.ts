import { Component, OnInit } from '@angular/core';
import {Image} from '../../model/Image';
import {Notification} from '../../model/Notification';
import {ImageService} from '../../service/image.service';
import {ActivatedRoute} from '@angular/router';
import {Client} from '../../model/Client';
import {WebSocketService} from '../../service/web-socket.service';

@Component({
  selector: 'app-imageconv',
  templateUrl: './imageconv.component.html',
  styleUrls: ['./imageconv.component.scss']
})
export class ImageconvComponent implements OnInit {

  constructor(private imageService: ImageService, private route : ActivatedRoute, public webSocketService: WebSocketService) { }
  static id: any;
  image: Image;
  response: Notification;
  client: Client;

  ngOnInit(): void {
    this.image = new Image();
    this.response = new Notification();
    this.client = new Client();
    ImageconvComponent.id = 0;
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngOnDestroy(): void {
    this.webSocketService.closeWebSocket();
  }

  sendMessage() {
    this.route.params.subscribe(params => {
      this.client.id = '' + (++ImageconvComponent.id);
      this.imageService.suscribe(this.client).subscribe(data => {
        console.log(data);
        this.client = data;
        this.webSocketService.openWebSocket(this.client.wslocation);
      }, error => console.log(error));
    });

    this.route.params.subscribe(params => {
      this.imageService.convert_image(this.image).subscribe(data => {
        console.log(data);
        // @ts-ignore
        this.response = data;
      }, error => console.log(error));
    });
  }
}
