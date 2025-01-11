import { Component } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { Racer } from '../racer/racer.component'

interface TeamDTO {
  name: string;
}
interface Team {
  name: string;
  racers: Racer[];
}
class RacerDTO {
  constructor(public id: number) {};
}
interface TeamForUpdate {
  racers: RacerDTO[] | null;
}

@Component({
  selector: 'app-team',
  imports: [
    FormsModule,
    HttpClientModule,
    CommonModule
  ],
  templateUrl: './team.component.html',
  styleUrl: './team.component.css'
})
export class TeamComponent {
  errorMessage: string = '';
  successMessage: string = '';
  team: TeamDTO = {name: ''};
  filterByName: boolean = false;
  nameFilter: string = '';
  teams: Team[] = [];
  operation: string = 'create';
  nameToDelete: string = '';
  forUpdate: TeamForUpdate = {racers: null};
  racerIdsNotParsed: string = '';
  nameToUpdate: string = '';
  racerIdToBeAdded: number = -1;
  racerIdToBeDeleted: number = -1;
  teamNameToBeAddedRacer: string = '';
  teamNameToBeDeletedRacer: string = '';

  constructor(private http: HttpClient) {}

  createForm() {
    const url = `http://localhost:1337/team`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.post<Team>(url, this.team, { headers, withCredentials: true }).subscribe(
      (response: Team) => {
        // Handle successful response
        this.errorMessage = '';
        this.successMessage = 'Successfully created team with name: ' + response.name;
      },
      (error: HttpErrorResponse) => {
        // Handle error
        this.successMessage = '';
        this.errorMessage = this.getErrorsFromResponse(error.error);
      },
      (complete: void) => {
        this.team = {name: ''};
      }
    );
  }

  deleteForm() {
    const url = `http://localhost:1337/team/${this.nameToDelete}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.delete<void>(url, { headers, withCredentials: true }).subscribe(
      (response: void) => {
        // Handle successful response
        this.errorMessage = '';
        this.successMessage = "Successfully executed delete operation"
      },
      (error: HttpErrorResponse) => {
        // Handle error
        this.successMessage = '';
        this.errorMessage = this.getErrorsFromResponse(error.error);
      },
      (complete: void) => {
        this.nameToDelete = '';
      }
    );
  }

  updateForm() {
    const url = `http://localhost:1337/team/${this.nameToUpdate}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.forUpdate.racers = this.racerIdsNotParsed
      ? this.racerIdsNotParsed.split(',').map(id => new RacerDTO(parseInt(id.trim(), 10))) // Parse IDs into numbers
      : [];

    this.http.patch<void>(url, this.forUpdate, { headers, withCredentials: true }).subscribe(
      (response: void) => {
        // Handle successful response
        this.errorMessage = '';
        this.successMessage = "Successfully executed update operation";
      },
      (error: HttpErrorResponse) => {
        // Handle error
        this.successMessage = '';
        this.errorMessage = this.getErrorsFromResponse(error.error);
      },
      (complete: void) => {
        this.forUpdate = {racers: null};
        this.racerIdsNotParsed = '';
        this.nameToUpdate = '';
      }
    );
  }

  addRacerForm() {
    const url = `http://localhost:1337/team/${this.teamNameToBeAddedRacer}/racers/${this.racerIdToBeAdded}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.post<void>(url, {}, { headers, withCredentials: true }).subscribe(
      (response: void) => {
        // Handle successful response
        this.errorMessage = '';
        this.successMessage = "Successfully executed update operation";
      },
      (error: HttpErrorResponse) => {
        // Handle error
        this.successMessage = '';
        this.errorMessage = this.getErrorsFromResponse(error.error);
      },
      (complete: void) => {
        this.racerIdToBeAdded = -1;
        this.teamNameToBeAddedRacer = '';
      }
    );
  }

  deleteRacerForm() {
    const url = `http://localhost:1337/team/${this.teamNameToBeDeletedRacer}/racers/${this.racerIdToBeDeleted}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.delete<void>(url).subscribe(
      (response: void) => {
        // Handle successful response
        this.errorMessage = '';
        this.successMessage = "Successfully executed update operation";
      },
      (error: HttpErrorResponse) => {
        // Handle error
        this.successMessage = '';
        this.errorMessage = this.getErrorsFromResponse(error.error);
      },
      (complete: void) => {
        this.racerIdToBeDeleted = -1;
        this.teamNameToBeDeletedRacer = '';
      }
    );
  }

  infoForm() {
    let url;

    if (this.filterByName) {
      url = `http://localhost:1337/team?name=${this.nameFilter}`;
    } else {
      url = `http://localhost:1337/team`;
    }

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.get<Team[]>(url, { headers, withCredentials: true }).subscribe(
      (response: Team[]) => {
        // Handle successful response
        this.teams = response;
      },
      (error: HttpErrorResponse) => {},
      (complete: void) => {
        this.nameFilter = '';
        this.filterByName = false;
      }
    );
  }

  private getErrorsFromResponse(errors: any): string {
    let errorMessage = '';

    Object.keys(errors).forEach(field => {
      errorMessage += `${field}: ${errors[field]} <br>`;
    });

    return errorMessage;
  }
}
