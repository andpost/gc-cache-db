import {Http} from '@angular/http';
import {Injectable} from '@angular/core';
import 'rxjs/add/operator/map'; // add map function to observable

@Injectable()
export class DataService {

    constructor(private http: Http) {
    }

    getCache(href: string) {
      return this.http.get(href + '?expand=details').map(res => res.json());
    }

    getGeoCacheList(lat: number, lng: number, radius: number) {
      return this.http.get('http://localhost:8080/cachedb/caches?lat=' + lat + '&lon=' + lng + '&radius=' + radius)
           .map(res => res.json());
    }

    getGeoCacheData(href: string) {
      return this.http.get(href + '?expand=details').map(res => res.json());
    }
}