import { Component, inject, OnInit } from "@angular/core";
import { Note } from "src/app/types/note";
import { HttpClient } from "@angular/common/http";
import { NOTES_BASE_URL } from "../../app.config";

@Component({
    templateUrl: './home.view.html',
    styleUrls: ['./home.view.less'],
})
export class HomeView implements OnInit {
    private http = inject(HttpClient);

    notes: Note[] = [];

    ngOnInit() {
        this.http.get<Note[]>(NOTES_BASE_URL).subscribe(notes => this.notes = notes);
    }
}
