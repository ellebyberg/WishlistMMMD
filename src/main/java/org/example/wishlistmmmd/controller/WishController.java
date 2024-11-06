package org.example.wishlistmmmd.controller;

import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
import org.example.wishlistmmmd.service.WishService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.time.LocalDate;

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
    @PostMapping("/loginValidation")
    public String loginValidation(HttpServletRequest request, @RequestParam String username, @RequestParam String password) throws SQLException {
        if (ws.validateLogin(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedIn", true);
            int userID = ws.getUserIDFromDB(username);

            //TODO: Der skal laves et if-tjek på følgende controller metoder for at sikre, at brugeren er logget ind.
            /*
            Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");

                if (loggedIn == null || !loggedIn) {
                    return "redirect:/login";
                }
             */
//            return "redirect:/makemywishcometrue/{userid}"; //TODO: Erstat med userProfileHomePage, når denne er færdig. Redirect fører ingen vegne i øjeblikket.
            return "redirect:/makemywishcometrue/"+userID;
        } else {
            return "redirect:/loginPage?error=true";
        }
    }
    public String checkLoginStatus(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        if (loggedIn == null || !loggedIn) {
            return "redirect:/login";
        } else {
            return "redirect:/home";
        }
    }

    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false)String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "An error has occurred. Please try again.");
        } //TODO virker ikke lige nu
        return "login";
    }
    @PostMapping("/saveAccount")
    public String saveNewAccountToDB(@RequestParam String name, @RequestParam Date birthdate, @RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) throws SQLException {

        UserProfile up = new UserProfile(name,birthdate, username, password);
        up.setUserID(ws.getUserIDFromDB(username)); // TODO: Lav en metode, der laver et lookup i SQL for at rette userID til, hvad det måtte være i DB.

        if (ws.isUsernameAvailable(username)) {
            ws.addUserToDB(up);
            return "redirect:/makemywishcometrue/"+up.getUserID();
        } else {
            redirectAttributes.addFlashAttribute("invalidUserNameErr", "The username is unavailable. Please try again using a different username.");
//            return "redirect:/loginPage"; //TODO: Beslutte hvilken html der giver mest mening at redirecte til. Husk at flytte fejlbesked, hvis andet end status quo.
            return "redirect:/makemywishcometrue/createAccountPage";
        }
    }
    @GetMapping("/createAccountPage")
    public String showCreateAccountPage() {
        return "createAccount";
    }
    @GetMapping("/showResetPasswordPage")
    public String showResetPasswordPage() {
        return "resetPassword";
    }
    @PostMapping("/resetPassword")
    public String resetPasswordAction(@RequestParam String password, @RequestParam String username, RedirectAttributes redirectAttributes) throws SQLException {
        if (!ws.isUsernameAvailable(username)) {
            ws.resetPassword(password, username);
            return "redirect:/makemywishcometrue/loginPage";
        } else {
            redirectAttributes.addFlashAttribute("PasswordErr93","Something went wrong, please try again.");
            return "redirect:/showResetPasswordPage";
        }
    }
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

    @GetMapping("/{userID}/createWishlist")
    public String createWishList(@PathVariable int userID, Model model) {
        model.addAttribute("userID", userID);//userID tilsættes som varibel, da den skal bruges i HTML formularen
        System.out.println("Her køres createWishList Endpointet, den der henter formularen");
        return "createWishList";
    }

    @PostMapping("/saveWishList")
    public String saveWishlist(@RequestParam String listName, @RequestParam Date expireDate, @RequestParam int userID) {
        ws.createWishList(listName, expireDate, userID);
        return "redirect:/makemywishcometrue/"+userID;
    }

//    @GetMapping("/addWish")
//    public String addWish() {
//
//    }
//
//    @PostMapping("/saveWish")
//    public String saveWish() {
//        //return redirectSaveWish
//    }

    @PostMapping("/deleteWish/{userid}/{wishid}")
    public String deleteWish(@PathVariable int userid, @PathVariable int wishid) {
        ws.deleteWish(wishid);
        return "redirect:/makemywishcometrue/"+userid ;

    }

}
