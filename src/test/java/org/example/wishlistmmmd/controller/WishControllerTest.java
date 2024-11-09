package org.example.wishlistmmmd.controller;

import org.example.wishlistmmmd.service.WishService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


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


    @Test
    void loginValidation() throws Exception {
        // Arrange: Vi bruger her Mockito til at "mocke" opkald til wishService, dvs. vi fortæller hvad den simulerede
        // version af wishService skal returnere.
        Mockito.when(wishService.validateLogin("validUser", "validPassword")).thenReturn(true);
        Mockito.when(wishService.getUserIDFromDB("validUser")).thenReturn(1);
        // Ovenstående betyder, at hvis controlleren kalder disse metoder med præcist de værdier, vi har angivet, vil de
        // returnere henholdsvis true og 1, uden at den rigtige WishService virkelig gør noget.

        // Act: Simuler en POST-anmodning til Controllerens loginValidation-Endpoint ved at bruge MockMvc.
        mockMvc.perform(MockMvcRequestBuilders.post("/makemywishcometrue/loginValidation")
                        .param("username", "validUser") //disse simulerer hvad anmodningen indeholder
                        .param("password", "validPassword"))

                // Assert: Tjek at vi bliver redirected til det forventede endpoint
                .andExpect(status().is3xxRedirection()) // denne kontrollerer, at serveren svarede med en "3xx" HTTP-statuskode,
                //som er en kode for "redirect" (videresendelse).
                .andExpect(redirectedUrl("/makemywishcometrue/1"));//denne tjekker, om redirect-adressen er /makemywishcometrue/1,
                // hvilket indikerer, at login var succesfuldt, og brugeren blev omdirigeret til sin personlige side.

        //Verify: Bekræft at validateLogin blev kaldt med de rigtige parametre
        //Med verifikation sørger vi for, at controlleren ikke bare reagerer korrekt, men at den også interagerer korrekt med andre systemkomponenter.
        //Uden verifikation kunne vi have en test, der bekræfter, at en controller returnerer en korrekt status (f.eks. en redirect),
        //men vi ville ikke kunne vide, om den faktisk kaldte de nødvendige metoder i wishService. Det er især nyttigt for at sikre,
        // at controlleren ikke bare reagerer korrekt på inddata, men at den også udfører de nødvendige operationer på serviceniveau.
        Mockito.verify(wishService).validateLogin("validUser", "validPassword");
        Mockito.verify(wishService).getUserIDFromDB("validUser");
    }

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