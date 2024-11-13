package org.example.wishlistmmmd.service;

import jakarta.servlet.http.HttpSession;
import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
import org.example.wishlistmmmd.repository.DatabaseConfig;
import org.example.wishlistmmmd.repository.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
class WishServiceTest {
    @MockBean
    private WishRepository wishRepository;
    @MockBean
    private DatabaseConfig databaseConfig;
    /*
    Mocker databaseconfig for at "berolige" Spring. Kan ikke få tests til at køre eller passe uden denne mockbean.
    Formentlig pga., hvordan vi har sat vores Connection objekter op i forbindelse med vores Singleton-klasse.
     */
    @Autowired
    WishService wishService;
    @Mock
    private HttpSession session; //Vi mocker session for at isolere testkoden uden at bruge et servermiljø. Tests er hurtigere på denne måde.

    @BeforeEach
    void setUp() {
    }

    @Test
    void doesUserOwnWishlist_ShouldReturnTrue_WhenWishlistExists() {
        /*
        Se evt. metodelogik i WishRepository getWishListByWlIdAndUserId().
         */
        // Arrange
        int wishlistID = 1;
        int userID = 1;
        WishList wishlist = new WishList("Wishlist Name", Date.valueOf("2024-12-31"), wishlistID);


        when(wishRepository.getWishListByWlIdAndUserId(wishlistID, userID)).thenReturn(wishlist);

        // Act
        boolean result = wishService.doesUserOwnWishlist(wishlistID, userID);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void doesUserOwnWishList_ShouldReturnFalse_WishListDoesNotExist() {
        /*
        Se evt. metodelogik i WishRepository getWishListByWlIdAndUserId().
         */
        int wishlistID = 1;
        int userID = 1;
        when(wishRepository.getWishListByWlIdAndUserId(wishlistID, userID)).thenReturn(null);

        //Act
        boolean result = wishService.doesUserOwnWishlist(wishlistID, userID);

        //Assert
        assertFalse(result);
    }


    @Test
    void redirectUserLoginAttributesNotLoggedIn() {
        /*
        Hvis sessionUserID er null har brugeren prøvet at tilgå et endpoint udenom loginprocessen.
        Vi tester derfor om brugeren bliver redirected til loginpage.
        Hvis session == null => redirect loginpage.
         */
        //Arrange
        when(session.getAttribute("userID")).thenReturn(null);
        int userID = 2;

        //Act
        String result = wishService.redirectUserLoginAttributes(session, userID);

        //Assert
        assertEquals("redirect:/makemywishcometrue/loginPage", result);
    }

    @Test
    void redirectUserLoginAttributesWrongSessionID() {
        /*
        Hvis brugeren er logget ind som bruger 2(sessionUserID), men prøver at tilgå et endpoint,
        som tilhører bruger 3(userID) tester vi for, om de bliver redirected til deres egen homepage.
         */
        //Arrange
        int sessionUserID = 2;
        int UserID = 3;
        when(session.getAttribute("userID")).thenReturn(sessionUserID);
        //Act
        String result = wishService.redirectUserLoginAttributes(session, UserID);

        //Assert
        assertEquals("redirect:/makemywishcometrue/" + sessionUserID, result);
    }

    @Test
    void redirectUserLoginAttributesLoggedIn() {
        /*
        Hvis brugeren er logged in følger det af metodelogikken, at der skal returneres null for ikke
        at redirecte brugeren til et nyt endpoint. UserID og sessionUserID er ens, hvorfor vi ikke træder
        ind i nogle if-blokke af den egentlige metode. Hvis metoden returnerer null, er der ikke nogen
        redirect if-blok i endpoints. Se evt. /userID og /userID/wishlistID endpoints i WishController.
         */
        //Arrange
        int userID = 2;
        int sessionUserID = 2;
        when(session.getAttribute("userID")).thenReturn(sessionUserID);

        //Act
        String result = wishService.redirectUserLoginAttributes(session, userID);

        //Assert
        assertEquals(null, result);
    }

}