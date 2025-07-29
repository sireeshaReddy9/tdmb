package com.mservices.tdmb.controller;

import com.mservices.tdmb.model.Movie;
import com.mservices.tdmb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService=movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> postMovie(@RequestBody Movie movie){
        Movie createdMovie=movieService.createMovie(movie);
        return ResponseEntity.ok(createdMovie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id){
        Movie movie=movieService.fetchMovie(id);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public void updateMovie(@PathVariable Long id,@RequestBody Movie movie){
        movieService.update(id,movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id){
        movieService.delete(id);
    }
}
