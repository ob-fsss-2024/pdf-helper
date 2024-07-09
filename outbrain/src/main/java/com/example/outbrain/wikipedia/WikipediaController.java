package com.example.outbrain.wikipedia;

import com.example.outbrain.wikipedia.dto.WikipediaData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("wikipedia")
public class WikipediaController {
  private final WikipediaService wikipediaService;

  public WikipediaController(WikipediaService wikipediaService) {
    this.wikipediaService = wikipediaService;
  }

  @GetMapping("/title/{title}")
  public List<WikipediaData> findByTitle(@PathVariable("title") final String title) {
    return wikipediaService.findByTitleRepository(title);
  }

  @GetMapping("/title2/{title}")
  public List<WikipediaData> findByTitleCustom(@PathVariable("title") final String title) {
    return wikipediaService.findByTitleCustom(title);
  }

  @GetMapping("/title3/{title}")
  public List<WikipediaData> findByTitleClient(@PathVariable("title") final String title) {
    return wikipediaService.findByTitleClient(title);
  }

  @GetMapping("/title4/{title}")
  public List<WikipediaData> findByTitleOperations(@PathVariable("title") final String title) {
    return wikipediaService.findByTitleOperations(title);
  }
}
