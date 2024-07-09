package com.example.outbrain.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static org.apache.pdfbox.Loader.loadPDF;

@Service
public class PDFService {

    public String convertPDF(String pathToPdf) {
        //pathToPdf = "/Users/lovro/Downloads/EP_Spring_2024__EB045EP_report_en.pdf";
        File file = new File(pathToPdf);
        PDDocument document = null;
        String text = null;
        try {
            document = loadPDF(file);
            System.out.println("PDF loaded");
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return text;
    }
}
