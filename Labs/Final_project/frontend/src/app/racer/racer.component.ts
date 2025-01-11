import { Component } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface RacerDTO {
  firstName: string;
  lastName: string;
  age: number;
}
export interface Racer {
  age: number;
  firstName: string;
  id: number;
  lastName: string;
}
interface Team {
  name: string | null;
}
interface RacerForUpdate {
  age: number | null;
  firstName: string | null;
  team: Team;
  lastName: string | null;
}

@Component({
  selector: 'app-racer',
  imports: [
    FormsModule,
    HttpClientModule,
    CommonModule
  ],
  templateUrl: './racer.component.html',
  styleUrl: './racer.component.css'
})
export class RacerComponent {

  errorMessage: string = '';
  successMessage: string = '';
  racer: RacerDTO = {firstName: '', lastName: '', age: -1};
  filterByFirstName: boolean = false;
  firstNameFilter: string = '';
  racers: Racer[] = [];
  operation: string = 'create';
  idToDelete: number = -1;
  forUpdate: RacerForUpdate = {age: null, firstName: null, lastName: null, team: {name: null}};
  idToUpdate: number = -1;

  constructor(private http: HttpClient) {}

  createForm() {
    const url = `http://localhost:1337/racer`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.post<Racer>(url, this.racer, { headers, withCredentials: true }).subscribe(
      (response: Racer) => {
        // Handle successful response
        this.errorMessage = '';
        this.successMessage = 'Successfully created racer with id: ' + response.id;
      },
      (error: HttpErrorResponse) => {
        // Handle error
        this.successMessage = '';
        this.errorMessage = this.getErrorsFromResponse(error.error);
      },
      (complete: void) => {
        this.racer = {firstName: '', lastName: '', age: -1};
      }
    );
  }

  deleteForm() {
    const url = `http://localhost:1337/racer/${this.idToDelete}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.delete<void>(url, { headers, withCredentials: true }).subscribe(
      (response: void) => {
        // Handle successful response
        this.errorMessage = '';
        this.successMessage = "Successfully executed delete operation";
      },
      (error: HttpErrorResponse) => {
        // Handle error
        this.successMessage = '';
        this.errorMessage = this.getErrorsFromResponse(error.error);
      },
      (complete: void) => {
        this.idToDelete = -1;
      }
    );
  }

  updateForm() {
    const url = `http://localhost:1337/racer/${this.idToUpdate}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    let objectToSend;

    // This way we come across optional field limitation
    if (this.forUpdate.team.name === null) {
      objectToSend = {age: this.forUpdate.age, firstName: this.forUpdate.firstName, lastName: this.forUpdate.lastName};
    } else {
      objectToSend = this.forUpdate;
    }

    this.http.patch<void>(url, objectToSend, { headers, withCredentials: true }).subscribe(
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
        this.forUpdate = {age: null, firstName: null, lastName: null, team: {name: null}};
        this.idToUpdate = -1;
      }
    );
  }

  infoForm() {
    let url;

    if (this.filterByFirstName) {
      url = `http://localhost:1337/racer?firstName=${this.firstNameFilter}`;
    } else {
      url = `http://localhost:1337/racer`;
    }

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.get<Racer[]>(url, { headers, withCredentials: true }).subscribe(
      (response: Racer[]) => {
        // Handle successful response
        this.racers = response;
      },
      (error: HttpErrorResponse) => {},
      (complete: void) => {
        this.firstNameFilter = '';
        this.filterByFirstName = false;
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
