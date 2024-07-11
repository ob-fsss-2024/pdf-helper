package com.example.outbrain.pdf;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
public class PdfController {
    private final PDFService pdfService;

    public PdfController(PDFService pdfService) {
        this.pdfService = pdfService;
    }

    public String convertPDF(@RequestParam("file") MultipartFile file) throws IOException {
        return pdfService.convertPDF(file);
    }
}