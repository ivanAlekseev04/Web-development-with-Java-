import { Component } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface TrackDTO {
  name: string;
  length: number;
}
interface Track {
  name: string;
  length: number;
  id: number;
}
interface TrackForUpdate {
  length: number | null;
  name: string | null;
}

@Component({
  selector: 'app-track',
  imports: [
    FormsModule,
    HttpClientModule,
    CommonModule
  ],
  templateUrl: './track.component.html',
  styleUrl: './track.component.css'
})
export class TrackComponent {
  errorMessage: string = '';
  successMessage: string = '';
  track: TrackDTO = {name: '', length: -1};
  filterByName: boolean = false;
  nameFilter: string = '';
  tracks: Track[] = [];
  operation: string = 'create';
  nameToDelete: string = '';
  forUpdate: TrackForUpdate = {name: null, length: null};
  idToUpdate: number = -1;

  constructor(private http: HttpClient) {}

  createForm() {
    const url = `http://localhost:1337/track`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.post<Track>(url, this.track, { headers, withCredentials: true }).subscribe(
      (response: Track) => {
        // Handle successful response
        this.errorMessage = '';
        this.successMessage = 'Successfully created track with id: ' + response.id;
      },
      (error: HttpErrorResponse) => {
        // Handle error
        this.successMessage = '';
        this.errorMessage = this.getErrorsFromResponse(error.error);
      },
      (complete: void) => {
        this.track = {name: '', length: -1};
      }
    );
  }

  deleteForm() {
    const url = `http://localhost:1337/track/${this.nameToDelete}`;
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
    const url = `http://localhost:1337/track/${this.idToUpdate}`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

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
        this.forUpdate = {name: null, length: null};
        this.idToUpdate = -1;
      }
    );
  }

  infoForm() {
    let url;

    if (this.filterByName) {
      url = `http://localhost:1337/track?name=${this.nameFilter}`;
    } else {
      url = `http://localhost:1337/track`;
    }

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    this.http.get<Track[]>(url, { headers, withCredentials: true }).subscribe(
      (response: Track[]) => {
        // Handle successful response
        this.tracks = response;
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
