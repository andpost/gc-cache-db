import { Component, OnInit, Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { GeoCache } from './app.geocacheentity';
import { DataService } from './app.dataservice';
import { analyzeAndValidateNgModules } from '@angular/compiler';
declare let L;

@Component({
  providers: [DataService],
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
@Injectable()
export class AppComponent implements OnInit {
  //geocacheListObservable : Observable<GeoCache[]>;
  cacheList: GeoCache[];

  title: string = 'GC Cache DB';

  lat: number = 51.083422;
  lng: number = 13.700582;
  zoom: number = 15;
  latQuery: number = 51.083422;
  lngQuery: number = 13.700582;
  latQueryLast: number;
  lngQueryLast: number;

  map: any;

  shownMarkers = new Array();

  constructor(private dataService: DataService) {
    this.listGeoCaches(this.latQuery, this.lngQuery, 1000);
  }

  ngOnInit() {
    this.map = L.map('map').setView([this.lat, this.lng], this.zoom);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(this.map);

    /*
     * 'this' must get binded to the method, see:
     * https://stackoverflow.com/questions/54939716/angular-7-variable-access-problem-on-leaflet-map-event
     */
    this.map.on('moveend', this.processMoveend.bind(this));
  }

  processMoveend() {
    this.removeMarkers();
    this.listGeoCaches(this.map.getCenter().lat, this.map.getCenter().lng, 1000);
  }

  listGeoCaches(lat: number, lng: number, radius: number) {
    this.dataService.getGeoCacheList(lat, lng, radius).subscribe((data: GeoCache[]) => this.showCaches(data));
  }

  showCaches(cacheList: GeoCache[]) {
    cacheList.forEach(cache => {
      var marker = L.marker([cache.coordinates.coordinates[1], cache.coordinates.coordinates[0]], this.getIcon(cache, true)).addTo(this.map);
      marker.bindPopup(this.getCacheDetailsHtml(cache)).openPopup();
      this.shownMarkers.push(marker);
    });
  }

  removeMarkers() {
    this.shownMarkers.forEach(marker => this.map.removeLayer(marker));
  }

  getCacheDetailsHtml(cache : GeoCache) {
    return "<img src=\"" + this.getIconUrl(cache.type, false) + "\"/><b>"
     + " " + cache.name + "</b><br />" + cache.gcCode;
  }

  getIcon(cache: GeoCache, isMarker: boolean) {
    var icon = {
      icon: L.icon({
        iconSize: [20, 23],
        iconAnchor: [13, 0],
        iconUrl: this.getIconUrl(cache.type, isMarker)
      })
    };

    return icon;
  }

  /**
* Returns the url for an icon for the given cache type.
* 
* old url /images/wpttypes/pins/
* new url is ../map/images/mapicons/
* 
* @param cacheType 
*/
  getIconUrl(cacheType: string, isMarker: boolean) {

    var baseUrl = "assets/images/";

    if (isMarker) {
      baseUrl += "marker_";
    }

    return baseUrl + cacheType.toLowerCase() + ".png";
  }
}
