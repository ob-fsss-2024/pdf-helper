package com.example.outbrain.wikipedia;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.outbrain.wikipedia.dto.WikipediaData;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WikipediaService {
  private final WikipediaRepository wikipediaRepository;
  private final ElasticsearchClient elasticsearchClient;
  private final ElasticsearchOperations elasticsearchOperations;

  public WikipediaService(final WikipediaRepository wikipediaRepository, final ElasticsearchClient elasticsearchClient, final ElasticsearchOperations elasticsearchOperations) {
    this.wikipediaRepository = wikipediaRepository;
    this.elasticsearchClient = elasticsearchClient;
    this.elasticsearchOperations = elasticsearchOperations;
  }

  public List<WikipediaData> findByTitleRepository(final String title) {
    return wikipediaRepository.findByTitle(title);
  }

  public List<WikipediaData> findByTitleCustom(final String title) {
    return wikipediaRepository.findByTitleCustom(title);
  }

  public List<WikipediaData> findByTitleClient(final String title) {
    try {
      final SearchResponse<WikipediaData> response = elasticsearchClient.search(s -> s
        .index("enwiki")
        .query(q -> q
          .match(t -> t
            .field("title")
            .query(title)
          )
        ), WikipediaData.class);

      return response.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
    } catch (final Exception e) {
      return Collections.emptyList();
    }
  }

  public List<WikipediaData> findByTitleOperations(final String title) {
    final Query query = new StringQuery("{\"match\":{\"title\":{\"query\":\""+ title + "\"}}}\"");

    return elasticsearchOperations.search(query, WikipediaData.class, IndexCoordinates.of("enwiki")).stream().map(SearchHit::getContent).collect(Collectors.toList());
  }

  public List<WikipediaData> findByMultipleTitle(List<String> titles, int limit) {
    Query query = null;
    List<WikipediaData> data = new ArrayList<>();

    for (String title : titles) {
      query = new StringQuery("{\"match\":{\"title\":{\"query\":\""+ title + "\"}}}\"");
      List<WikipediaData> temp = elasticsearchOperations.search(query, WikipediaData.class, IndexCoordinates.of("enwiki"))
              .stream()
              .map(SearchHit::getContent)
              .collect(Collectors.toList());
      if(temp.size()>limit){
        temp = temp.subList(0,limit);
      }
      System.out.println("for title "+title + "len of temp "+temp.size());
      data.addAll(temp);
    }
    return data;
  }
}
