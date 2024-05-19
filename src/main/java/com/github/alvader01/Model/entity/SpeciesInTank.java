package com.github.alvader01.Model.entity;

import java.util.Objects;

public class SpeciesInTank {
    private int fishtankId;
    private int speciesId;

    public SpeciesInTank(int fishtankId, int speciesId) {
        this.fishtankId = fishtankId;
        this.speciesId = speciesId;
    }
    public SpeciesInTank(){

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeciesInTank that = (SpeciesInTank) o;
        return fishtankId == that.fishtankId && speciesId == that.speciesId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fishtankId, speciesId);
    }

    @Override
    public String toString() {
        return "SpeciesInTank{" +
                "fishtankId=" + fishtankId +
                ", speciesId=" + speciesId +
                '}';
    }
}
