package com.github.ryctabo.movies.resources;

import com.github.ryctabo.movies.models.CatalogItem;
import com.github.ryctabo.movies.models.Movie;
import com.github.ryctabo.movies.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 */
@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public Collection<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
//        List<Rating> ratings = Arrays.asList(
//                new Rating("1234", 4),
//                new Rating("5678", 3)
//        );

        final String urlRatings = "http://rating-data-service/ratingsdata/users/" + userId;
        UserRating ratings = restTemplate.getForObject(urlRatings, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
            final String url = "http://movie-info-service/movies/" + rating.getMovieId();
            Movie movie = restTemplate.getForObject(url, Movie.class);
//            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri(url)
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();
            return new CatalogItem(movie.getName(), "A Demo description", rating.getRating());
        }).collect(Collectors.toList());
    }

}
