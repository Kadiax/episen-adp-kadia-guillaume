import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Image} from '../model/Image';
import {Client} from '../model/Client';
// @ts-ignore
import {Notification} from '../../model/Notification';
@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) {
  }

  convert_image(image: Image) {
    return this.http.post<Notification>(`http://172.31.249.9:8181/image/convert`, image);
  }

  suscribe(id: Client) {
    return this.http.post<Client>(`http://172.31.249.9:8181/image/suscribe`, id);
  }
}
