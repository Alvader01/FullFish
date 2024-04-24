package com.github.alvader01.Model.dao;

import com.github.alvader01.Model.entity.Species;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SpeciesDAO implements DAO<Species, Integer> {
    @Override
    public Species save(Species entity) {
        return null;
    }

    @Override
    public Species delete(Species entity) throws SQLException {
        return null;
    }

    @Override
    public Species findById(Integer key) {
        return null;
    }

    @Override
    public List<Species> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }


}
