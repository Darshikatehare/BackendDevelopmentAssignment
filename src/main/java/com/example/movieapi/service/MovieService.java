package com.example.movieapi.service;

import com.example.movieapi.entity.Movie;
import com.example.movieapi.exception.ResourceNotFoundException;
import com.example.movieapi.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class MovieService {

    private final MovieRepository movieRepository;

    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        Movie movie = getMovieById(id);
        
        movie.setTitle(movieDetails.getTitle());
        movie.setDirector(movieDetails.getDirector());
        movie.setReleaseYear(movieDetails.getReleaseYear());
        movie.setGenre(movieDetails.getGenre());
        movie.setRating(movieDetails.getRating());
        
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        Movie movie = getMovieById(id);
        movieRepository.delete(movie);
    }
}
