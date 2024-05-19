package com.github.alvader01.Model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FishTank {
    private int id;
    private String name;
    private int capacity;
    private float lengthy;
    private float width;
    private float height;
    private List<Species> speciess;



    public FishTank(int id, String name, int capacity, float lengthy, float width, float height, List<Species> speciess) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.lengthy = lengthy;
        this.width = width;
        this.height = height;
        this.speciess = speciess;

    }
    public FishTank( String name, int capacity, float lengthy, float width, float height) {
        this.name = name;
        this.capacity = capacity;
        this.lengthy = lengthy;
        this.width = width;
        this.height = height;

    }

    public FishTank() {
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getLengthy() {
        return lengthy;
    }

    public void setLengthy(float lengthy) {
        this.lengthy = lengthy;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public List<Species> getSpeciess() {
        return speciess;
    }

    public void setSpeciess(List<Species> speciess) {
        this.speciess = speciess;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FishTank fishtank = (FishTank) o;
        return id == fishtank.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Fishtank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", lengthy=" + lengthy +
                ", width=" + width +
                ", height=" + height +
                ", speciess=" + speciess +
                '}';
    }
}
