import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutView } from './views/about/about.view';
import { HomeView } from './views/home/home.view';
import { NoteView } from './views/note/note.view';

const routes: Routes = [
    { path: 'note/:id', component: NoteView },
    { path: 'about', component: AboutView },
    { path: '', component: HomeView }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { bindToComponentInputs: true })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
