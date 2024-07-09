package com.example.outbrain.pdf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {
    private final PDFService pdfService;

    public PdfController(PDFService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/convert")
    public String convertPDF(@RequestBody String path){
        return pdfService.convertPDF(path);
    }
}
