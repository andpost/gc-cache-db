import { Component } from '@angular/core';
import { LatLngLiteral } from '@agm/core';
import { log } from 'util';
import {DataService} from './app.dataservice';

@Component({
  providers: [DataService],
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title: string = 'GC Cache DB';

  lat: number = 51.084083;
  lng: number = 13.720567;
  zoom: number = 15;
  latQuery: number = 51.08408;
  lngQuery: number = 13.720567;
  latQueryLast: number;
  lngQueryLast: number;

  public geoCacheList = [];

  constructor(private dataService: DataService) {

    this.loadData();
  }

  processCenterChange(coordinates : LatLngLiteral) {
	  this.latQuery = coordinates.lat;
    this.lngQuery = coordinates.lng;
  }

  processZoomChange(newZoom : number) {
    if (this.zoom != newZoom) {
      this.zoom = newZoom;

      this.loadData();
    }
  }

  doQuery() {
    // only reload from server if things changed
    if (this.latQueryLast != this.latQuery && this.lngQueryLast != this.latQuery) {
      this.latQueryLast = this.latQuery;
      this.lngQueryLast = this.lngQuery;

      this.loadData();
    }
  }

  loadData() {
    //var featureCollection;

    var radius = 10000;

    if (this.zoom >= 15) {
      radius = 1000;
    } else if (this.zoom <= 10) {
      radius = 100000;
    }

    this.dataService.getGeoCacheData(this.latQuery, this.lngQuery, radius).subscribe(resultList => this.geoCacheList = resultList);
 }

 getIconUrl(cacheType : string) {

  if (cacheType == "TRADITIONAL" || cacheType == "Traditional Cache") {
    return "https://www.geocaching.com/images/WptTypes/2.gif";
  } else if (cacheType == "MULTI" || cacheType == "Multi-cache") {
    return "https://www.geocaching.com/images/WptTypes/3.gif";
  } else if (cacheType == "MYSTERY" || cacheType == "Unknown Cache") {
    return "https://www.geocaching.com/images/WptTypes/8.gif";
  } else if (cacheType == "EARTHCACHE" || cacheType == "Earthcache") {
    return "https://www.geocaching.com/images/WptTypes/earthcache.gif";
  } else if (cacheType == "LETTERBOX" || cacheType == "Letterbox Hybrid") {
    return "https://www.geocaching.com/images/WptTypes/5.gif";
  } else if (cacheType == "EVENT" || cacheType == "Event Cache") {
    return "https://www.geocaching.com/images/WptTypes/6.gif";
  } else if (cacheType == "CITO" || cacheType == "Cache In Trash Out Event") {
    return "https://www.geocaching.com/images/WptTypes/13.gif";
  } else if (cacheType == "MEGA_EVENT" || cacheType == "Mega-Event Cache") {
    return "https://www.geocaching.com/images/WptTypes/mega.gif";
  } else if (cacheType == "GIGA_EVENT" || cacheType == "Giga-Event Cache") {
    return "https://www.geocaching.com/images/WptTypes/giga.gif";
  } else if (cacheType == "WHERIGO" || cacheType == "Wherigo Cache") {
    return "https://www.geocaching.com/images/WptTypes/1858.gif";
  } else if (cacheType == "GPS_ADVENTURE" || cacheType == "GPS Adventures Exhibit") {
    return "https://www.geocaching.com/images/WptTypes/1304.gif";
  } else if (cacheType == "VIRTUAL" || cacheType == "Virtual Cache") {
    return "https://www.geocaching.com/images/WptTypes/4.gif";
  } else if (cacheType == "WEBCAM" || cacheType == "Webcam Cache") {
    return "https://www.geocaching.com/images/WptTypes/11.gif";
  } else if (cacheType == "LNF_EVENT" || cacheType == "Lost and Found Event Cache") {
    return "https://www.geocaching.com/images/WptTypes/10Years_32.gif";
  } else if (cacheType == "LAB") {
    return "https://www.geocaching.com/images/WptTypes/labs.png";
  } else if (cacheType == "APE") {
    return "https://www.geocaching.com/images/WptTypes/ape_32.gif";
  } else if (cacheType == "HQ") {
    return "https://www.geocaching.com/images/WptTypes/HQ_32.gif";
  } else if (cacheType == "REVERSE") {
    return "https://www.geocaching.com/images/WptTypes/12.gif";
  }
  return "http://maps.google.com/mapfiles/ms/micons/red.png";
}
}
