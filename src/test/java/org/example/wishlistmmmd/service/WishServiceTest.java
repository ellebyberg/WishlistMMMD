package org.example.wishlistmmmd.service;

import jakarta.servlet.http.HttpSession;
import org.example.wishlistmmmd.model.Wish;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class WishServiceTest {
    @InjectMocks
    WishService wishService;

    @Mock
    private HttpSession session;
    @Mock
    private

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void redirectUserLoginAttributesNotLoggedIn() {
        //Arrange
        when(session.getAttribute("userID")).thenReturn(null);

        //Act
        String result = wishService.redirectUserLoginAttributes(session, 2);


        //Assert
        assertEquals("redirect:/makemywishcometrue/loginPage", result);
    }
}