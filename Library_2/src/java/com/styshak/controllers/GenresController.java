package com.styshak.controllers;

import com.styshak.beans.Genre;
import com.styshak.db.DataAccess;
import com.styshak.db.MySQLDataAccessImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

@ManagedBean
@ApplicationScoped
@Named(value = "genresController")
public class GenresController implements Serializable{
    
    private DataAccess da = new MySQLDataAccessImpl();
    private List<Genre> genresList = new ArrayList<>();

    public List<Genre> getGenresList() {
        if (genresList.isEmpty()) {
            return da.getGenres();
        } else {
            return genresList;
        }
    }
}
