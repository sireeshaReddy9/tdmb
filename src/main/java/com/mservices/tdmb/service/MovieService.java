package com.mservices.tdmb.service;

import com.mservices.tdmb.Repo.MovieRepository;
import com.mservices.tdmb.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository=movieRepository;
    }

    //    create
    public Movie createMovie(Movie movie){
        if(movie==null){
            throw new RuntimeException("Invalid movie");
        }
        else{
            return movieRepository.save(movie);
        }
    }

    //   Read
    public Movie fetchMovie(Long id){
        return  movieRepository.findById(id).orElseThrow(()-> new RuntimeException("Movie not found"));
    }
    //    update
    public void update(Long id,Movie movies){
        if(movies==null || id==null){
            throw new RuntimeException("invalid movie");
        }
        if(movieRepository.existsById(id)){
            Movie movie=movieRepository.getReferenceById(id);
            movie.setDirector(movies.getDirector());
            movie.setName(movies.getName());
            movie.setActors(movies.getActors());
            movieRepository.save(movie);
        }
        else{
            throw new RuntimeException("Movie not found");
        }

    }
    //    Delete
    public void delete(Long id){
        if(movieRepository.existsById(id)){
            movieRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Movie not found");
        }
    }
}
