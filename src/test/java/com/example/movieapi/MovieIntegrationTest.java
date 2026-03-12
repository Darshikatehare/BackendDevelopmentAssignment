package com.example.movieapi;

import com.example.movieapi.entity.Movie;
import com.example.movieapi.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("null")
class MovieIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    void testCrudOperations() throws Exception {
        // 1. Create a movie
        Movie movie = Movie.builder()
                .title("The Matrix")
                .director("Wachowskis")
                .releaseYear(1999)
                .genre("Sci-Fi")
                .rating(9)
                .build();

        String responseJson = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("The Matrix"))
                .andReturn().getResponse().getContentAsString();

        Movie createdMovie = objectMapper.readValue(responseJson, Movie.class);

        // 2. Get movie by ID
        mockMvc.perform(get("/movies/{id}", createdMovie.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Matrix"));

        // 3. Get all movies (Pagination)
        mockMvc.perform(get("/movies?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value("The Matrix"));

        // 4. Update the movie
        createdMovie.setRating(10);
        mockMvc.perform(put("/movies/{id}", createdMovie.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdMovie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(10));

        // 5. Delete the movie
        mockMvc.perform(delete("/movies/{id}", createdMovie.getId()))
                .andExpect(status().isNoContent());

        // Verify deletion
        assertThat(movieRepository.findById(createdMovie.getId())).isEmpty();
    }
}
