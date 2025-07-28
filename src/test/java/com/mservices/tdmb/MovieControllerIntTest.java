package com.mservices.tdmb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mservices.tdmb.Repo.MovieRepository;
import com.mservices.tdmb.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MovieRepository movieRepository;

    @BeforeEach
    void cleanUp(){
        movieRepository.deleteAllInBatch();
    }


    @Test
    void giveMovie_whenCreateMovie_thenReturnSaveMovie() throws Exception{

        Movie movie=new Movie();
        movie.setName("RRR");
        movie.setDirector("Raj");
        movie.setActors(List.of("NTR","RC","Alia"));

        var response=mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(notNullValue())))
                .andExpect(jsonPath("$.name",is(movie.getName())))
                .andExpect(jsonPath("$.director",is(movie.director)))
                .andExpect(jsonPath("$.actors",is(movie.getActors())));
    }

    @Test
    void giveMovieId_whenFetchMovie_thenReturnMovie() throws Exception{
        Movie movie=new Movie();
        movie.setName("RRR");
        movie.setDirector("Raj");
        movie.setActors(List.of("NTR","RC","Alia"));
       Movie savedmovie= movieRepository.save(movie);

       var response =mockMvc.perform(get("/"+savedmovie.getId()));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is((int)savedmovie.getId())))
                .andExpect(jsonPath("$.name",is(movie.getName())))
                .andExpect(jsonPath("$.director",is(movie.getDirector())))
                .andExpect(jsonPath("$.actors",is(movie.getActors())));

    }

    @Test
    void givenSavedMovie_whenUpdateMove_thenMovieUpdaeInDb()throws Exception{
        Movie movie=new Movie();
        movie.setName("RRR");
        movie.setDirector("Raj");
        movie.setActors(List.of("NTR","RC","Alia"));
        Movie savedmovie= movieRepository.save(movie);
        Long id=savedmovie.getId();

//        update
        savedmovie.setActors(List.of("ntr","RC"));

        var response=mockMvc.perform(put("/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

//        verify updated movie
        response.andDo(print())
                .andExpect(status().isOk());
        var fetchResponse=mockMvc.perform(get("/"+id));

        fetchResponse.andDo(print())
                .andExpect(jsonPath("$.name",is(savedmovie.getName())))
                .andExpect(jsonPath("$.director",is(savedmovie.getDirector())))
                .andExpect(jsonPath("$.actors",is(savedmovie.getActors())));

    }


    @Test
    void getMovie_whenDeleteRequest_thenRemoveMovieFromDb()throws Exception{

        Movie movie=new Movie();
        movie.setName("RRR");
        movie.setDirector("Raj");
        movie.setActors(List.of("NTR","RC","Alia"));
        Movie savedmovie= movieRepository.save(movie);
        Long id=savedmovie.getId();

        mockMvc.perform(delete("/"+id))
                .andDo(print())
                .andExpect(status().isOk());
        assertFalse(movieRepository.findById(id).isPresent());


    }
}
