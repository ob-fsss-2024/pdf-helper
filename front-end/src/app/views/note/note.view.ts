import { Component, inject, Input, OnChanges, SimpleChanges } from "@angular/core";
import { Router } from "@angular/router";
import { isDefined } from "src/app/helpers/common.helpers";
import { Note } from "src/app/types/note";
import { HttpClient } from "@angular/common/http";
import { NOTES_BASE_URL } from "../../app.config";

@Component({
    templateUrl: './note.view.html',
    styleUrls: ['./note.view.less'],
})
export class NoteView implements OnChanges {
    @Input()
    id: string;

    private http = inject(HttpClient);
    private router = inject(Router)

    note: Note = { title: '', text: '' };


    ngOnChanges(changes: SimpleChanges): void {
        if (changes['id'] && isDefined(this.id)) {
            if (this.id !== 'new') {
                this.http.get<Note>(
                    `${NOTES_BASE_URL}/${this.id}`
                ).subscribe(note => this.note = note);
            }
        }
    }

    saveChanges() {
        if (isDefined(this.note.id)) {
            this.http.put<Note>(
                `${NOTES_BASE_URL}/${this.note.id}`,
                this.note
            ).subscribe(note => this.note = note);
        } else {
            this.http.post<Note>(
                NOTES_BASE_URL,
                this.note
            ).subscribe(note => this.router.navigateByUrl(`/note/${note.id}`));
        }
    }

    deleteNote() {
        if (isDefined(this.note.id) && window.confirm("Are you sure you want to delete this note?")) {
            this.http.delete<void>(
                `${NOTES_BASE_URL}/${this.note.id}`,
            ).subscribe(() => this.router.navigateByUrl('/'));
        } else {
            this.router.navigateByUrl('/');
        }
    }
}
