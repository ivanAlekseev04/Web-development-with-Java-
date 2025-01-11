import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { FormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';

import { HomeComponent } from './home/home.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { RacerComponent } from './racer/racer.component';
import { TrackComponent } from './track/track.component';
import { TeamComponent } from './team/team.component';
import { EventComponent } from './event/event.component';

@NgModule({
declarations: [
  HomeComponent,
  NotfoundComponent,
  RacerComponent,
  TrackComponent,
  TeamComponent,
  EventComponent
],
imports: [
  BrowserModule,
  AppRoutingModule,
  FormsModule,
  FormBuilder,
  FormGroup,
  HttpClientModule,
  RouterModule
],
providers: [],
bootstrap: [AppComponent]
})

export class AppModule { }
