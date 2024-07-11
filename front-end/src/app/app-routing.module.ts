import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutView } from './views/about/about.view';
import { HomeView } from './views/home/home.view';
import { ResourceFinderView } from './views/resource-finder/r-finder.view';
import { SummarizerView } from './views/summarizer/summarizer.view';

const routes: Routes = [
    { path: 'resourcefinder', component: ResourceFinderView },
    { path: 'summarizer', component: SummarizerView },
    { path: 'about', component: AboutView },
    { path: '', component: HomeView }
];

@NgModule({
    imports: [RouterModule.forRoot(routes, { bindToComponentInputs: true })],
    exports: [RouterModule]
})
export class AppRoutingModule { }
