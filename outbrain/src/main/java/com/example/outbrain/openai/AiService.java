package com.example.outbrain.openai;

import com.example.outbrain.openai.client.dto.DocumentSummary;
import com.example.outbrain.openai.client.dto.ResourceData;
import com.example.outbrain.openai.client.dto.ResponseMode;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;


@Service
public class AiService {
    private final AzureOpenAiChatModel chatModel;
    private final Logger logger = LoggerFactory.getLogger(AiService.class.getName());
    private final Counter summaryGenCounter;
    private final Counter keywordGenCounter;
    private final Timer summaryGenTimer;
    private final Timer keywordGenTimer;

    public AiService(AzureOpenAiChatModel chatModel, final MeterRegistry metricsRegistry) {
        this.chatModel = chatModel;
        this.summaryGenCounter = metricsRegistry.counter("summary_gen_counter");
        this.keywordGenCounter = metricsRegistry.counter("keywords_gen_counter");
        this.summaryGenTimer = metricsRegistry.timer("summary_gen_timer");
        this.keywordGenTimer = metricsRegistry.timer("keywords_gen_timer");
    }

    public DocumentSummary genSummary(String text, ResponseMode mode, int wordLimit) {
        final long startTime = System.currentTimeMillis();
        String modeDescription =
                (mode == ResponseMode.detailed) ? (modeDescription = "Try to be detailed as possible. " +
                        "Make sure you include every topic from the text.")
                : (modeDescription = "Try to be as concise and short as possible, preserving key topics.");
        if(text.length() > 5) {
            String response = chatModel.call("Make a summary of the following text." + modeDescription
                    + "Do not add special characters (eg. line breaks). Keep it below "+wordLimit+ "word limit. Text: "+ text);
            summaryGenCounter.increment();
            summaryGenTimer.record(Duration.ofMillis(System.currentTimeMillis() - startTime));
            logger.info("Successfully obtained a {} summary of the text.", mode);

            return new DocumentSummary(response);
        }else {
            return new DocumentSummary("The pdf does not contain enough text to make a detailed summary.");
        }
    }

    public ResourceData getKeywords(String text) {
        if(text.length() > 5) {
            String response = chatModel.call("Retreive up to 10 keywords " +
                    "from the following text. Separate the keywords by commas. Text: " + text);
            List<String> keywords = List.of(response.split(", "));
            keywordGenCounter.increment();
            logger.info("Successfully obtained keywords for given text.");
            return new ResourceData(keywords);
        }else {
            return new ResourceData(Collections.singletonList("The pdf does not contain enough text to find keywords."));
        }
    }

}
