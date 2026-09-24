package com.ledzedev.gemini_demo.model;

import java.util.List;

public record MovieRecommendation(
        String title,
        int releaseYear,
        String director,
        List<String> genres,
        String briefSummary
) {
}
