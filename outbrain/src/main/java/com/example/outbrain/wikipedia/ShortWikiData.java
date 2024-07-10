package com.example.outbrain.wikipedia;

import java.util.List;

public record ShortWikiData(
        String title,
        String text,
        List<String> external_links
) {
}
