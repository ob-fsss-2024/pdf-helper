import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { AboutView } from './views/about/about.view';
import { HomeView } from './views/home/home.view'
import { ResourceFinderView } from './views/resource-finder/r-finder.view';
import { SummarizerView } from './views/summarizer/summarizer.view';


@NgModule({
    declarations: [
        AppComponent,
        SummarizerView,
        ResourceFinderView,
        HomeView,
        AboutView,
        HeaderComponent,
        FooterComponent
    ],
    bootstrap: [AppComponent], imports: 
        [BrowserModule,
        AppRoutingModule,
        FormsModule], 
        providers: [provideHttpClient(withInterceptorsFromDi())]
})
export class AppModule { }
