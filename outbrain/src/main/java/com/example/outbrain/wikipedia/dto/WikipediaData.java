package com.example.outbrain.wikipedia.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "enwiki", createIndex = false)
public record WikipediaData(
        @Id Long page_id,
        String title,
        String opening_text,
        String auxiliary_text,
        String text,
        String language,
        List<String> ores_articletopics,
        List<String> weighted_tags,
        List<String> category,
        List<String> outgoing_link,
        List<String> external_link,
        List<String> heading,
        String wikibase_item,
        String source_text,
        Integer incoming_links,
        Integer text_bytes,
        Integer namespace,
        String namespace_text,
        Double popularity_score,
        String create_timestamp,
        List<WikipediaRedirect> redirect
) { }
