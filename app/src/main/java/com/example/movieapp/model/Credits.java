/**
 * @file Credits.java
 * @brief This is a pojo class, which will hold list of casts
 * @author Nicolas Escobar Cruz
 * @date 29/05/2019
 */
package com.example.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Credits {

    @SerializedName("cast")
    private List<Cast> cast;

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }
}
