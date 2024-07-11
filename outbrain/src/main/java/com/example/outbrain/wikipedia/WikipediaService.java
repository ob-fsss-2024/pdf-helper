package com.example.outbrain.wikipedia;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.outbrain.wikipedia.dto.WikipediaData;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class WikipediaService {
  private final WikipediaRepository wikipediaRepository;
  private final ElasticsearchClient elasticsearchClient;
  private final ElasticsearchOperations elasticsearchOperations;

  private final Logger logger = LoggerFactory.getLogger(WikipediaService.class.getName());
  private final Counter wikiQueryCounter;
  private final Timer articleFetchingTimer;


  public WikipediaService(final WikipediaRepository wikipediaRepository, final ElasticsearchClient elasticsearchClient, final ElasticsearchOperations elasticsearchOperations, final MeterRegistry metricsRegistry) {
    this.wikipediaRepository = wikipediaRepository;
    this.elasticsearchClient = elasticsearchClient;
    this.elasticsearchOperations = elasticsearchOperations;
    this.wikiQueryCounter = metricsRegistry.counter("wiki_query_counter");
    this.articleFetchingTimer = metricsRegistry.timer("fetching_wiki_articles_timer");
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

  public List<ShortWikiData> findByMultipleTitle(List<String> titles, int limit) {
    final long startTime = System.currentTimeMillis();
    Query query = null;
    List<ShortWikiData> data = new ArrayList<>();
    for (String title : titles) {
      List<ShortWikiData> temp = new ArrayList<>();
      query = new StringQuery("{\"match\":{\"title\":{\"query\":\""+ title + "\"}}}\"");
      List<WikipediaData> output = elasticsearchOperations.search(query, WikipediaData.class, IndexCoordinates.of("enwiki"))
              .stream()
              .map(SearchHit::getContent)
              .collect(Collectors.toList());
      if(output.size()>limit){
        output = output.subList(0,limit);
      }

      for (WikipediaData w: output) {
        wikiQueryCounter.increment();
        temp.add(new ShortWikiData(w.title(), w.text().length() > 500 ? w.text().substring(0,500) : w.text(),
                (w.external_link().size()>3) ? (w.external_link().subList(0,3)) : (w.external_link())));
      }
      logger.info("For the title {} the number of wikipedia article results is {}",title ,output.size());
      data.addAll(temp);
    }
    articleFetchingTimer.record(Duration.ofMillis(System.currentTimeMillis() - startTime));
    //articleFetchingTimer.record(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
    return data;
  }
}

