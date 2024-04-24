package com.github.alvader01.Model.entity;

import com.github.alvader01.Model.Enum.SubGroup;

import java.util.Objects;

public class Species {
    private int id;
    private String name;
    private int dimension;
    private int longevity;
    private SubGroup subGroup;

    public Species(int id, String name, int dimension, int longevity, SubGroup subGroup) {
        this.id = id;
        this.name = name;
        this.dimension = dimension;
        this.longevity = longevity;
        this.subGroup = subGroup;
    }

    public Species() {
        id = 0;
        name = "";
        dimension = 0;
        longevity = 0;
        subGroup = null;
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

    public SubGroup getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(SubGroup subGroup) {
        this.subGroup = subGroup;
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
                ", subGroup=" + subGroup +
                '}';
    }
}
