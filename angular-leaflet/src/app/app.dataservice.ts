import {Injectable} from '@angular/core';
import { HttpClient } from  "@angular/common/http";
//import { Observable } from  "rxjs/Observable";
//import 'rxjs/add/operator/map'; // add map function to observable
import { GeoCache } from "./app.geocacheentity";


@Injectable()
export class DataService {

    //geocacheListObservable : Observable<GeoCache[]>;

    configUrl = 'assets/config.json';

    constructor(private http: HttpClient) {
    }

    getCache(href: string) {
      return this.http.get<GeoCache>(href + '?expand=details');
    }

    getGeoCacheList(lat: number, lng: number, radius: number) {
      return this.http.get<GeoCache[]>('http://localhost:8080/cachedb/caches?lat=' + lat + '&lon=' + lng + '&radius=' + radius);
    }

    getGeoCacheData(href: string) {
      return this.http.get<GeoCache>(href + '?expand=details');
    }
}