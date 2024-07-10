package com.example.outbrain.pdf;

import com.example.outbrain.wikipedia.WikipediaService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.apache.pdfbox.Loader.loadPDF;

@Service
public class PDFService {
    private final Logger logger = LoggerFactory.getLogger(WikipediaService.class.getName());
    private final Counter pdfTransformationCounter;
    private final Timer pdfTransformationTimer;

    public PDFService(final MeterRegistry metricsRegistry) {
        this.pdfTransformationCounter = metricsRegistry.counter("successful_pdf_transformation_counter");;
        this.pdfTransformationTimer = metricsRegistry.timer("transforming_pdf_document_timer");
    }


    public String convertPDF(String pathToPdf) {
        final long startTime = System.currentTimeMillis();
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
            System.out.println("allalalal");
            throw new RuntimeException(e);
        }
        pdfTransformationCounter.increment();
        pdfTransformationTimer.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
        logger.info("The pdf document {} has been successfuly transformed into plain text",file.getName());
        return text;
    }
}
