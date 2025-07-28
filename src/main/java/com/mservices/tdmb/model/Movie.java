package com.mservices.tdmb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue
    public long id;


    public String name;

    public String director;

    @ElementCollection
    public List<String> actors= new ArrayList<>();

    @Version
    private Integer version;
}
