package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getFavoritesWithSuccess() {
        List<Neighbour> favorites = service.getNeighboursFavorites();
        List<Neighbour> expectedFavorites = favorites;
        assertThat(favorites, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedFavorites.toArray()));
    }

    @Test
    public void addFavoritesWithSuccess() {
        Neighbour neighbour = new Neighbour(1, "test", "test", "test", "test", "test", 0);
        neighbour.setFavorite(1);
        service.setNeighboursFavorite(0, neighbour);
        assertTrue(service.getNeighboursFavorites().contains(neighbour));
    }

    @Test
    public void deleteFavoritesWithSuccess() {
        Neighbour favoriteToDelete = service.getNeighbours().get(0);
        List<Neighbour> listeFavorites = service.getNeighboursFavorites();
        listeFavorites.clear();
        listeFavorites.add(favoriteToDelete);
        assertEquals(service.getNeighbours().get(0), favoriteToDelete);
        service.deleteNeighbour(favoriteToDelete);
        assertFalse(service.getNeighboursFavorites().contains(favoriteToDelete));
    }
}
