import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { RacerComponent } from './racer/racer.component';
import { TrackComponent } from './track/track.component';
import { TeamComponent } from './team/team.component';
import { EventComponent } from './event/event.component';

export const routes: Routes = [
  { path: '', redirectTo: 'app/home', pathMatch: 'full'},
  { path: 'app/home', component: HomeComponent, data: {title: 'Home'}},
  { path: 'app/racer', component: RacerComponent, data: {title: 'Racer management'}},
  { path: 'app/track', component: TrackComponent, data: {title: 'Track management'}},
  { path: 'app/team', component: TeamComponent, data: {title: 'Team management'}},
  { path: 'app/event', component: EventComponent, data: {title: 'Event management'}},
  { path: '**', component: NotfoundComponent, data: {title: '404'} }  // Routing for not defined routes
];

@NgModule({
imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
