package com.example.outbrain.wikipedia;

import com.example.outbrain.wikipedia.dto.WikipediaData;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface WikipediaRepository extends ElasticsearchRepository<WikipediaData, Long> {
  List<WikipediaData> findByTitle(String title);
  @Query("{\"match\": {\"title\": {\"query\": \"?0\"}}}")
  List<WikipediaData> findByTitleCustom(String title);
}
