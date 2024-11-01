package org.example.wishlistmmmd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WishController {

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
