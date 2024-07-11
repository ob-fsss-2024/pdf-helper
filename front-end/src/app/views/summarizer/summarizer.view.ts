import { Component, inject, Input, OnChanges, SimpleChanges } from "@angular/core";
import { Router } from "@angular/router";
import { isDefined } from "src/app/helpers/common.helpers";
import { HttpClient } from "@angular/common/http";
import { PDF_BASE_URL } from "../../app.config";
import { SumPdfData } from "src/app/types/sumPdfData";
import { ResponseMode } from "src/app/types/responseMode";

@Component({
    templateUrl: './summarizer.view.html',
    styleUrls: ['./summarizer.view.less'],
})
export class SummarizerView {
    @Input()
    id: string;

    private http = inject(HttpClient);
    private router = inject(Router)

    pdfdata: SumPdfData = { pdf: new FormData(), 
        mode: null,
        wordLimit: 0 };

    fileChange(event){
        const pdfFiles = event.target.files;
        // console.log(pdfFile)
         const formdata: FormData = new FormData();
         formdata.append('file', pdfFiles[0]);
        this.pdfdata.pdf= formdata
        console.log(this.pdfdata.pdf)
    }


    postPdf(): void {
        console.log("manja")
        console.log(this.pdfdata.mode as ResponseMode)
        console.log(this.pdfdata.wordLimit)
        const queryParams = '?mode='+this.pdfdata.mode
        +'&wordLimit='+this.pdfdata.wordLimit;
        this.http.post<any>(
            PDF_BASE_URL+"/summary"+queryParams,
            this.pdfdata.pdf
        ).subscribe(
            pdfdata => console.log(pdfdata)
        );
    }

    saveChanges() {
        // if (isDefined(this.pdfdata.id)) {
        //     this.http.put<ResPdfData>(
        //         `${PDF_BASE_URL}/${this.pdfdata.id}`,
        //         this.pdfdata
        //     ).subscribe(pdfdata => this.pdfdata = pdfdata);
        // } else {
        //     this.http.post<ResPdfData>(
        //         PDF_BASE_URL,
        //         this.pdfdata
        //     ).subscribe(pdfdata => this.router.navigateByUrl(`/pdfdata/${pdfdata.id}`));
        // }
    }

    deletepdfdata() {
    //     if (isDefined(this.pdfdata.id) && window.confirm("Are you sure you want to delete this pdfdata?")) {
    //         this.http.delete<void>(
    //             `${PDF_BASE_URL}/${this.pdfdata.id}`,
    //         ).subscribe(() => this.router.navigateByUrl('/'));
    //     } else {
    //         this.router.navigateByUrl('/');
    //     }
    // }
    }
}
