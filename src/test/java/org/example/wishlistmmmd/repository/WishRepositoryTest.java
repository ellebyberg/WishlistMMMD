package org.example.wishlistmmmd.repository;

import org.example.wishlistmmmd.model.WishList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WishRepositoryTest {


private WishRepository repository;
private ConnectionManager connectionManager;
private String url = "jdbc:mysql://localhost:3306/wishlistdb";
private String user = "TeamMMMD";
private String password = "PassTeamMMMD";


    @BeforeEach
    void setUp() {
        connectionManager = ConnectionManager.getInstance(url,user,password);
        repository = new WishRepository(connectionManager);
    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void testShowListOfWishLists() {
        //Arrange er lavet i setUp

        //Act
        List<WishList> testListe = repository.showListOfWishLists(3);

        int actualPostsInList = testListe.size();
        int expectedPostsInList = 3;

        //Assert
        Assertions.assertEquals(expectedPostsInList,actualPostsInList);
    }

    @Test
    void testGetUserIDFromDB() {

        //Arrange er lavet i setUp

        //Act
        int expectedUserID = 1;
        int actualUserID = repository.getUserIDFromDB("asmith");

        //Assert
        Assertions.assertEquals(expectedUserID, actualUserID);
    }
}