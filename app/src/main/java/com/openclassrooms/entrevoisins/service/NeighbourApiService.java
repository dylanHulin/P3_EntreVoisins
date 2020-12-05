package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Get position of neighbour
     */
    Neighbour getNeighbourByPosition(int position);

    /**
     * Get all my favorites
     */
    List<Neighbour> getNeighboursFavorites();
    /**
     * Get the position from neigbours to favorites
     */
    void setNeighboursFavorite(int position, Neighbour neighbour);
}
