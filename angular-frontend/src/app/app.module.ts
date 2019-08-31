import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ApplicationRef  } from '@angular/core';
import { HttpModule } from '@angular/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AgmCoreModule } from '@agm/core';
//import { AgmJsMarkerClustererModule } from '@agm/js-marker-clusterer';
import { LAZY_MAPS_API_CONFIG } from '@agm/core/services';
import {GoogleMapsConfig} from './app.googlemapsconfig';


@NgModule({
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    HttpModule,
    AgmCoreModule.forRoot(),
    //AgmJsMarkerClustererModule
  ],
  providers: [{provide: LAZY_MAPS_API_CONFIG, useClass: GoogleMapsConfig}],
  declarations: [
    AppComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }