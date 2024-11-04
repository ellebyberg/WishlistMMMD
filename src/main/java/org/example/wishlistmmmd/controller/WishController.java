package org.example.wishlistmmmd.controller;

import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
import org.example.wishlistmmmd.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller //annotation som fortæller Spring at denne klasse håndterer HTTP-forespørgsler
@RequestMapping("/makemywishcometrue") //annotation Endpoint som fortæller hvilken url / sti at alle forespørgslerne til denne controller skal have for at køre metoderne

public class WishController {

    private final WishService ws;

    public WishController(WishService ws) {
        this.ws = ws;
    }

//    @GetMapping("/welcomePage")
//    public String welcomePage() {
//
//    }
//
//    @PostMapping("/login")
//    public String loginValidation() {
//
//    }
//
    @GetMapping("/{userID}")
    public String showUserHomePage(@PathVariable int userID, Model model) {
        List<WishList> listOfWishLists = ws.showListOfWishLists(userID);
        UserProfile up = ws.getUserData(userID);
        model.addAttribute("listOfWishLists", listOfWishLists);
        model.addAttribute("UserProfile",up);
        return "wishListView";

    }

    @GetMapping("/{userID}/{wishListID}")
    public String showSpecificWishList(@PathVariable int wishListID, @PathVariable int userID, Model model) {
        List<Wish> listOfWishes = ws.showListOfWishes(wishListID);
        model.addAttribute("listOfWishes", listOfWishes);
        model.addAttribute("userID",userID);
        model.addAttribute("wishListID",wishListID);
        return "wishView";

    }
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

    @PostMapping("/deleteWish/{wishname}")
    public String deleteWish(@PathVariable String wishName) {
        return "redirect:/wishListView";
        //Når et ønske er fjernet fra listen (og DB), så skal vi komme tilbage til oversigten med den givne ønskeseddel
        //I det view skal der være en delete button ud for hver wish:
        //        <form th:action="@{/delete/{wishName}(wishName=${wish.wishName})}"
        //                      method="post" onsubmit="return confirmDeletion()">
        //                    <button type="submit" class="submitbutton">Slet</button>
        //                </form>
    }

}
