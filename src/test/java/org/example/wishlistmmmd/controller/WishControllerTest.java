package org.example.wishlistmmmd.controller;

import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.service.WishService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.text.SimpleDateFormat;


@WebMvcTest(WishController.class)
//Når vi skal teste i en Controller, så vil vi benytte en Mock (Mockito) for at lave en simuleret version
//af en afhængighed (Mocking), i dette tilfælde WishService. På den måde kan vi "kontrollere" den falske version af
// WishService objektet... hvordan den skal opføre sig, og hvad den skal returnere (Stubbing), uden at køre
// WishServices' egentlige kode.

public class WishControllerTest {

    @Autowired // med denne annotation fortæller vi Spring, at den automatisk skal indsætte (injecte) en instans
    //af denne afhængighed. Dvs vi skal ikke oprette instansen manuelt med new. Spring håndterer instansieringen.
    private MockMvc mockMvc;

    @MockBean //med denne annotation instruere vi Spring Boot i at oprette en Mock-version af WishService, som
    //vi kan manipulere med under testen
    private WishService wishService;

    // Globale variabler til at genbruge på tværs af tests
    private String username;
    private String password;
    private String name;
    private UserProfile testUser;
    private Date birthDate;
    private int userID;
    private int wishListID;
    private int wishID;
    //private SimpleDateFormat dateFormat;


    @BeforeEach
    public void setup() throws Exception {
        // Laver en testUser med den tomme konstruktør hvorefter variablerne settes og gettes,
        // så de er klar til brug i hver test
        testUser = new UserProfile();

        testUser.setUsername("validUser");
        username = testUser.getUsername();
        testUser.setPassword("pass");
        password = testUser.getPassword();
        testUser.setName("name");
        name = testUser.getName();
        testUser.setUserID(3);
        userID = testUser.getUserID();

        wishListID = 2;
        wishID = 1;

    }


    @Test
    void loginValidation() throws Exception {
        // Arrange: Vi bruger her Mockito til at "mocke" opkald til wishService, dvs. vi fortæller hvad den simulerede
        // version af wishService skal returnere.
        Mockito.when(wishService.validateLogin(username, password)).thenReturn(true);
        Mockito.when(wishService.getUserIDFromDB(username)).thenReturn(userID);
        // Ovenstående betyder, at hvis controlleren kalder disse metoder med præcist de værdier, vi har angivet, vil de
        // returnere henholdsvis true og 3, uden at den rigtige WishService virkelig gør noget.

        // Act: Simuler en POST-anmodning til Controllerens loginValidation-Endpoint ved at bruge MockMvc.
        mockMvc.perform(post("/makemywishcometrue/loginValidation")
                        .param("username", username) //disse simulerer hvad anmodningen indeholder
                        .param("password", password))

                // Assert: Tjek at vi bliver redirected til det forventede endpoint
                .andExpect(status().is3xxRedirection()) // denne kontrollerer, at serveren svarede med en "3xx" HTTP-statuskode,
                //som er en kode for "redirect" (videresendelse).
                .andExpect(redirectedUrl("/makemywishcometrue/3"));//denne tjekker, om redirect-adressen er /makemywishcometrue/1,
        // hvilket indikerer, at login var succesfuldt, og brugeren blev omdirigeret til sin personlige side.

        //Verify: Bekræft at validateLogin blev kaldt med de rigtige parametre
        //Med verifikation sørger vi for, at controlleren ikke bare reagerer korrekt, men at den også interagerer korrekt med andre systemkomponenter.
        //Uden verifikation kunne vi have en test, der bekræfter, at en controller returnerer en korrekt status (f.eks. en redirect),
        //men vi ville ikke kunne vide, om den faktisk kaldte de nødvendige metoder i wishService. Det er især nyttigt for at sikre,
        // at controlleren ikke bare reagerer korrekt på inddata, men at den også udfører de nødvendige operationer på serviceniveau.
        Mockito.verify(wishService).validateLogin(username, password);
        Mockito.verify(wishService).getUserIDFromDB(username);
    }

//    @Test
//    void saveNewAccountToDB() throws Exception {
//
//        //Arrange
//
//        Mockito.when(wishService.isUsernameAvailable(username)).thenReturn(true);
//        Mockito.doNothing().when(wishService).addUserToDB(testUser); //doNothing anvendes da metoden er void
//        Mockito.when(wishService.getUserIDFromDB(username)).thenReturn(3);
//
//        //Act
//        mockMvc.perform(post("/makemywishcometrue/saveAccount")
//                        .param("name", name)
//                        .param("birthdate", "")
//                        .param("username", username)
//                        .param("password",password))
//
//                //Assert
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/makemywishcometrue/3"));
//
//                //Verify
////        Mockito.verify(wishService).isUsernameAvailable(username);
////        Mockito.verify(wishService).addUserToDB(testUser);
//    }


//@Test
//void checkLoginStatus() {
//}
//
//@Test
//void loginPage() {
//}
//

    //
//@Test
//void showCreateAccountPage() {
//}
//
//@Test
//void showResetPasswordPage() {
//}
//
//@Test
//void resetPasswordAction() {
//}
//
    @Test
    void showUserHomePage() {

        //Arrange
        Mockito.when(wishService.showListOfWishLists(userID)).


        //Act


        //Assert


        //Verify


    }
//
//@Test
//void showSpecificWishList() {
//}
//
//@Test
//void createWishList() {
//}
//
//@Test
//void saveWishlist() {
//}
//
//@Test
//void deleteWishList() {
//}
//
//@Test
//void deleteWish() {
//}
//}
}