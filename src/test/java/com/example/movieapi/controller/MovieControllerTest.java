package com.example.movieapi.controller;

import com.example.movieapi.entity.Movie;
import com.example.movieapi.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@SuppressWarnings("null")
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getAllMovies_ShouldReturnPageOfMovies() throws Exception {
        Page<Movie> page = new PageImpl<>(List.of(movie));
        when(movieService.getAllMovies(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/movies")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Inception"));
    }

    @Test
    void getMovieById_ShouldReturnMovie() throws Exception {
        when(movieService.getMovieById(movieId)).thenReturn(movie);

        mockMvc.perform(get("/movies/{id}", movieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    void createMovie_ShouldReturnCreatedMovie() throws Exception {
        when(movieService.createMovie(any(Movie.class))).thenReturn(movie);

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    void createMovie_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        Movie invalidMovie = Movie.builder().build(); // Missing title

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidMovie)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void updateMovie_ShouldReturnUpdatedMovie() throws Exception {
        when(movieService.updateMovie(eq(movieId), any(Movie.class))).thenReturn(movie);

        mockMvc.perform(put("/movies/{id}", movieId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    void deleteMovie_ShouldReturnNoContent() throws Exception {
        doNothing().when(movieService).deleteMovie(movieId);

        mockMvc.perform(delete("/movies/{id}", movieId))
                .andExpect(status().isNoContent());
    }
}
