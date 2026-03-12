package com.example.movieapi.service;

import com.example.movieapi.entity.Movie;
import com.example.movieapi.exception.ResourceNotFoundException;
import com.example.movieapi.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;
    private Long movieId;

    @BeforeEach
    void setUp() {
        movieId = 1L;
        movie = Movie.builder()
                .id(movieId)
                .title("Inception")
                .director("Christopher Nolan")
                .releaseYear(2010)
                .genre("Sci-Fi")
                .rating(9)
                .build();
    }

    @Test
    void getAllMovies_ShouldReturnPageOfMovies() {
        Page<Movie> page = new PageImpl<>(List.of(movie));
        when(movieRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Movie> result = movieService.getAllMovies(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Inception", result.getContent().get(0).getTitle());
    }

    @Test
    void getMovieById_WhenExists_ShouldReturnMovie() {
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(movieId);

        assertNotNull(result);
        assertEquals(movieId, result.getId());
    }

    @Test
    void getMovieById_WhenNotExists_ShouldThrowException() {
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.getMovieById(movieId));
    }

    @Test
    void createMovie_ShouldReturnSavedMovie() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie result = movieService.createMovie(movie);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
    }

    @Test
    void updateMovie_WhenExists_ShouldReturnUpdatedMovie() {
        Movie updatedDetails = Movie.builder()
                .title("Inception 2")
                .director("Christopher Nolan")
                .releaseYear(2025)
                .genre("Sci-Fi")
                .rating(8)
                .build();

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie result = movieService.updateMovie(movieId, updatedDetails);

        assertNotNull(result);
        verify(movieRepository).save(movie);
    }

    @Test
    void deleteMovie_WhenExists_ShouldDeleteMovie() {
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        movieService.deleteMovie(movieId);

        verify(movieRepository).delete(movie);
    }
}
