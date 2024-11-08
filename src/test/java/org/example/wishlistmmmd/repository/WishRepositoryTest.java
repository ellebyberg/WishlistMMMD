package org.example.wishlistmmmd.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private ConnectionManager connectionManager;


    @BeforeEach
    public void setUp() {
        //vi nulstiller ConnectionManager for at sikre, at det er test-konfigurationen der anvendes
        ConnectionManager.resetInstance();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testDatabaseConnection() throws Exception {
        Connection connection = connectionManager.getConn();
        assertNotNull(connection,"Forbindelse til H2-testdatabasen burde ikke være null");
    }

    @Test
    void isUsernameAvailable() {
        boolean actual = wishRepository.isUsernameAvailable("NoUserNameYet");
        boolean expected = true;
        assertEquals(expected,actual);
    }

    @Test
    void getUserIDFromDB() {
        int actual = wishRepository.getUserIDFromDB("newUserNameAsmith");
        assertEquals(1,actual);

    }

    @Test
    void getUserData() {
        String actualUserName = wishRepository.getUserData(3).getUsername();
        String expectedUserName = "cliu";
        assertEquals(expectedUserName,actualUserName);
    }

    @Test
    void showListOfWishLists() {
        int expectedNumberOfLists = 2;
        int actualNumberOfLists = wishRepository.showListOfWishLists(3).size();
        assertEquals(expectedNumberOfLists,actualNumberOfLists);

    }

    @Test
    void checkExpiredList() {
        int expectedNumberOfLists = 1;
        wishRepository.checkExpiredList();
        int actualNumberOfLists = wishRepository.showListOfWishLists(2).size();
        assertEquals(expectedNumberOfLists,actualNumberOfLists);

    }


}