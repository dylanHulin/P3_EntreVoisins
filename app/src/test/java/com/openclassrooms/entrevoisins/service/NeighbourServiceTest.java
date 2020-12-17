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
    private final int POSITION = 0;
    private List<Neighbour> favoriteNeighbours;

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

    // CHECK
    @Test
    public void getNeighboursFavorites() {
        service.getNeighboursFavorites().clear();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        for (Neighbour neighbour : service.getNeighbours()) {
            if (!neighbour.isFavorite(neighbour)) {
                neighbour.setFavorite(1);
            }
        }
        service.getNeighboursFavorites().addAll(expectedNeighbours);
        favoriteNeighbours = service.getNeighboursFavorites();
        assertThat(favoriteNeighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }


    @Test
    public void setNFavoriteWithSuccess() {
        Neighbour favoriteToTest = service.getNeighbourByPosition(POSITION);
        favoriteToTest.setFavorite(1);
        service.setNeighboursFavorite(POSITION, favoriteToTest);
        assertTrue(service.getNeighboursFavorites().contains(favoriteToTest));
    }
    @Test
    public void removeFavoriteWithSuccess(){
        Neighbour favoriteToTest = service.getNeighbourByPosition(POSITION);
        favoriteToTest.setFavorite(1);
        List<Neighbour> listFavorites = service.getNeighboursFavorites();
        listFavorites.clear();
        assertEquals(service.getNeighbours().get(0), favoriteToTest);
        favoriteToTest.setFavorite(0);
        assertFalse(service.getNeighboursFavorites().contains(favoriteToTest));
    }

    @Test
    public void getNeighboursByPosition() {
        Neighbour neighbourToTest = service.getNeighbourByPosition(POSITION);
        assertEquals(service.getNeighbours().get(0), neighbourToTest);
    }

}
