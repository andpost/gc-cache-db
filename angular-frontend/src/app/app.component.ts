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
  public selectedGeoCacheData = "";

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

    this.dataService.getGeoCacheList(this.latQuery, this.lngQuery, radius).subscribe(resultList => this.geoCacheList = resultList);
 }

 processMarkerClick(geoCache) {
  this.dataService.getGeoCacheData(geoCache.href).subscribe(res => this.setSelectionMarkerData(res));
 }

 setSelectionMarkerData(selectedGeoCache) {
  this.selectedGeoCacheData = selectedGeoCache.gcCode + " - " + selectedGeoCache.name + "<br>"
    + "D" + selectedGeoCache.difficulty + " / T" + selectedGeoCache.terrain;
 }

/**
 * Returns the url for an icon for the given cache type.
 * 
 * old url https://www.geocaching.com/images/wpttypes/pins/
 * new url is https://www.geocaching.com/map/images/mapicons/
 * 
 * @param cacheType 
 */
 getIconUrl(cacheType : string) {

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
