package com.openclassrooms.entrevoisins.service;


import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {

        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /*
    Get the position of a neighbour of the list.
     */
    @Override
    public Neighbour getNeighbourByPosition(int position) {
        return neighbours.get(position);
    }


    /*
    Get the favorites list
     */
    @Override
    public List<Neighbour> getNeighboursFavorites() {
        List<Neighbour> favorites = new ArrayList<>();
        for (Neighbour neighbour : neighbours) {
            if (neighbour.isFavorite(neighbour)) {
                if (favorites.contains(neighbour)) {

                } else {
                    favorites.add(neighbour);
                }

            }
        }
        return favorites;
    }

    /*
    Get the position from Neighbours list to Favorite list
     */
    @Override
    public void setNeighboursFavorite(int position, Neighbour neighbour) {
        neighbours.set(position, neighbour);
    }


}



