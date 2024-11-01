package org.example.wishlistmmmd.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.wishlistmmmd.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class WishController {
    private WishService ws;
    public WishController() {
        ws = new WishService();
    }

//    @GetMapping("/welcomePage")
//    public String welcomePage() {
//
//    }
//
    @PostMapping("/loginValidation")
    public String loginValidation(HttpServletRequest request, @RequestParam String username, @RequestParam String password) throws SQLException {
        if (ws.validateLogin(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedIn", true);
            return "redirect:/home";
        } else {
            return "redirect:/loginPage?error";
        }
    }
    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false)String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "An error has occurred. Please try again.");
        }
        return "login";
    }
//
//    @GetMapping("/userProfileHomePage")
//    public String userProfileHomePage() {
//
//    }
//
//    @GetMapping("/createWishlist")
//    public String createWishList() {
//
//    }
//
//    @PostMapping("/saveWishlist")
//    public String saveWishlist() {
//        //return redirectUserProfile;
//    }
//
//    @GetMapping("/addWish")
//    public String addWish() {
//
//    }
//
//    @PostMapping("/saveWish")
//    public String saveWish() {
//        //return redirectSaveWish
//    }

}
