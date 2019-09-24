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
    this.markerDelAgain();
    this.listGeoCaches(this.map.getCenter().lat, this.map.getCenter().lng, 1000);
  }

  listGeoCaches(lat: number, lng: number, radius: number) {
    this.dataService.getGeoCacheList(lat, lng, radius).subscribe((data: GeoCache[]) => this.showCaches(data));
  }

  showCaches(cacheList: GeoCache[]) {
    cacheList.forEach(cache => {
      var marker = L.marker([cache.coordinates.coordinates[1], cache.coordinates.coordinates[0]], this.getIcon(cache)).addTo(this.map);
      marker.bindPopup(this.getCacheDetailsHtml(cache)).openPopup();
      this.shownMarkers.push(marker);
    });
  }

  markerDelAgain() {
    this.shownMarkers.forEach(marker => this.map.removeLayer(marker));
  }

  getCacheDetailsHtml(cache : GeoCache) {
    return "<img src=\"" + this.getIconUrl(cache.type) + "\"/><b>" + cache.gcCode + " " + cache.name + "</b>";
  }

  getIcon(cache: GeoCache) {
    var icon = {
      icon: L.icon({
        iconSize: [20, 23],
        iconAnchor: [13, 0],
        iconUrl: this.getIconUrl(cache.type)
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
  getIconUrl(cacheType: string) {

    var baseUrl = "assets/images/";

    if (cacheType == "TRADITIONAL" || cacheType == "Traditional Cache") {
      return baseUrl + "pin_tradi.png"; // 2
    } else if (cacheType == "MULTI" || cacheType == "Multi-cache") {
      return baseUrl + "pin_multi.png"; // 3
    } else if (cacheType == "MYSTERY" || cacheType == "Unknown Cache") {
      return baseUrl + "pin_mystery.png"; // 8
    } else if (cacheType == "EARTHCACHE" || cacheType == "Earthcache") {
      return baseUrl + "pin_ec.png"; // 137
    } else if (cacheType == "LETTERBOX" || cacheType == "Letterbox Hybrid") {
      return baseUrl + "pin_letterbox.png"; // 5
    } else if (cacheType == "EVENT" || cacheType == "Event Cache") {
      return baseUrl + "pin_event.png"; // 6
    } else if (cacheType == "CITO" || cacheType == "Cache In Trash Out Event") {
      return baseUrl + "pin_cito.png"; // 13
    } else if (cacheType == "MEGA_EVENT" || cacheType == "Mega-Event Cache") {
      return baseUrl + "pin_mega.png"; // 453
    } else if (cacheType == "GIGA_EVENT" || cacheType == "Giga-Event Cache") {
      return baseUrl + "pin_giga.png"; // 7005
    } else if (cacheType == "WHERIGO" || cacheType == "Wherigo Cache") {
      return baseUrl + "pin_wig.png"; // 1858
    } else if (cacheType == "GPS_ADVENTURE" || cacheType == "GPS Adventures Exhibit") {
      return baseUrl + "pin_maze.png"; // 1304
    } else if (cacheType == "VIRTUAL" || cacheType == "Virtual Cache") {
      return baseUrl + "pin_virtual.png"; // 4
    } else if (cacheType == "WEBCAM" || cacheType == "Webcam Cache") {
      return baseUrl + "pin_virtual.png"; // 11
    } else if (cacheType == "LNF_EVENT" || cacheType == "Lost and Found Event Cache") {
      return baseUrl + "pin_lnf.png"; // 3653
    } else if (cacheType == "LAB") {
      return baseUrl + "pin_labs.png"; // ?
    } else if (cacheType == "APE") {
      return baseUrl + "pin_ape.png"; // 9
    } else if (cacheType == "HQ") {
      return baseUrl + "pin_hq.png"; // 3773
    } else if (cacheType == "REVERSE") {
      return baseUrl + "pin_locationless_reverse.png"; // 12
    } else if (cacheType == "BLOCK_PARTY") {
      return baseUrl + "pin_blockparty.png"; // 4738
    }
    return "http://maps.google.com/mapfiles/ms/micons/red.png";
  }
}
