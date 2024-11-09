package org.example.wishlistmmmd.controller;

import org.example.wishlistmmmd.service.WishService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(WishController.class)
//Når vi skal teste i en Controller, så vil vi benytte en Mock (Mockito) for at lave en simuleret version
//af en afhængighed (Mocking), i dette tilfælde WishService. På den måde kan vi "kontrollere" den falske version af
// WishService objektet... hvordan den skal opføre sig, og hvad den skal returnere (Stubbing), uden at køre
// WishServices' egentlige kode.

public class WishControllerTest {

    @Autowired // med denne annotation fortæller vi Spring, at den automatisk skal indsætte (injecte) en instans
    //af denne afhængighed. Dvs vi skal ikke oprette instansen manuelt med new. Spring håndterer instansieringen.
    // og administrationen af objekter, så du slipper for at skabe dem med new.
    private MockMvc mockMvc;

    @MockBean //med denne annotation instruere vi Spring Boot i at oprette en Mock-version af WishSerice, som
    //vi kan manipulere med under testen
    private WishService wishService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void loginValidation() {
    }

    @Test
    void checkLoginStatus() {
    }

    @Test
    void loginPage() {
    }

    @Test
    void saveNewAccountToDB() {
    }

    @Test
    void showCreateAccountPage() {
    }

    @Test
    void showResetPasswordPage() {
    }

    @Test
    void resetPasswordAction() {
    }

    @Test
    void showUserHomePage() {
    }

    @Test
    void showSpecificWishList() {
    }

    @Test
    void createWishList() {
    }

    @Test
    void saveWishlist() {
    }

    @Test
    void deleteWishList() {
    }

    @Test
    void deleteWish() {
    }
}