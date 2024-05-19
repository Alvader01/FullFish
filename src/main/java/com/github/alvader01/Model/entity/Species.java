package com.github.alvader01.Model.entity;

import java.util.Objects;

public class Species {
    private int id;
    private String name;
    private int dimension;
    private int longevity;

    public Species(int id, String name, int dimension, int longevity) {
        this.id = id;
        this.name = name;
        this.dimension = dimension;
        this.longevity = longevity;
    }

    public Species(String name, int dimension, int longevity) {
        this.name = name;
        this.dimension = dimension;
        this.longevity = longevity;
    }


    public Species() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getLongevity() {
        return longevity;
    }

    public void setLongevity(int longevity) {
        this.longevity = longevity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return id == species.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Species{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dimension=" + dimension +
                ", longevity=" + longevity +
                '}';
    }
}
