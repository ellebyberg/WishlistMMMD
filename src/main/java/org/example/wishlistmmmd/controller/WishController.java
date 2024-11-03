package org.example.wishlistmmmd.controller;

import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
import org.example.wishlistmmmd.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
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
    @GetMapping("/profilehomepage/{userID}")
    public String userProfileHomePage(@PathVariable int userID, Model model) {
        List<WishList> listOfWishLists = ws.showListOfWishLists(userID);
        model.addAttribute("listOfWishLists", listOfWishLists);
        return "wishListView";

    }

    @GetMapping("/profilehomepage/{userID}/{wishListID}")
    public String userProfileHomePage(@PathVariable int wishListID, @PathVariable int userID, Model model) {
        List<Wish> listOfWishes = ws.showListOfWishes(wishListID);
        model.addAttribute("listOfWishes", listOfWishes);
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
