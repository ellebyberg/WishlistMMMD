package org.example.wishlistmmmd.controller;

import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
import org.example.wishlistmmmd.service.WishService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

@Controller //annotation som fortæller Spring at denne klasse håndterer HTTP-forespørgsler
@RequestMapping("/makemywishcometrue") //annotation Endpoint som fortæller hvilken url / sti at alle forespørgslerne til denne controller skal have for at køre metoderne

public class WishController {

    private final WishService ws;
    public WishController(WishService ws, HttpSession session) {
        this.ws = ws;
        this.session = session;
    }

    /*
    ###########################################
    #           Session attributter           #
    ###########################################
     */
    private HttpSession session;





//    @GetMapping("/welcomePage")
//    public String welcomePage() {
//
//    }
//
    @PostMapping("/loginValidation")
    public String loginValidation(HttpServletRequest request, @RequestParam String username, @RequestParam String password) throws SQLException {
        if (ws.validateLogin(username, password)) { //Brugeren logger ind
            HttpSession session = request.getSession();
            int userID = ws.getUserIDFromDB(username);
            session.setAttribute("userID", userID);
            session.setAttribute("username", username);
            /*
            Data om brugen 'gemmes' på sessionen som ID og username. Dette bruges i placeholder metoder
            for at sikre, at brugeren ikke tilgår andre endpoints, der tilhører andre profiler.
             */

            return "redirect:/makemywishcometrue/"+userID;
        } else {
            //Hvis validateLogin() ikke finder brugeren i DB, redirectes brugeren til login med en fejlbesked,
            //som vises i html view.
            return "redirect:/makemywishcometrue/loginPage?error=true";
        }
    }
    public String redirectUserLoginAttributes(int userID) {
        Integer sessionUserID = (Integer) session.getAttribute("userID");

        if (sessionUserID == null) {
            //Hvis brugeren ikke er logget ind ligger der ikke et ID gemt på session,
            // hvorfor brugeren derfor promptes til at logge ind
            return "redirect:/makemywishcometrue/loginPage";
        }
        else if(!sessionUserID.equals(userID)) {
            /*
            Brugeren prøver at tilgå en anden brugers data.
            De bliver redirected til deres egen side, hvis de er logget ind.
             */
            return "redirect:/makemywishcometrue/"+sessionUserID;
        }
        return null;
    }

    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false)String error, Model model) {
        if (error != null) { //Tjekker på error=true i loginValidation endpoint. Hvis den bliver givet videre må det konstateres, at error ikke er null, og der foreligger en fejl.
            model.addAttribute("errorMessage", "An error has occurred. Please try again.");
        }
        return "login";
    }
    @PostMapping("/saveAccount")
    public String saveNewAccountToDB(@RequestParam String name, @RequestParam Date birthdate, @RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) throws SQLException {

        UserProfile up = new UserProfile(name,birthdate, username, password);

        if (ws.isUsernameAvailable(username)) {
            ws.addUserToDB(up);
            int userIdFromDB = ws.getUserIDFromDB(username);
            up.setUserID(userIdFromDB);
            return "redirect:/makemywishcometrue/"+up.getUserID();
        } else {
            //En anden måde at vise fejlbeskeder på(redirectAttributes). Dette er godt til ting,
            //som fejlbeskeder, der ikke skal persistere.
            redirectAttributes.addFlashAttribute("invalidUserNameErr", "The username is unavailable. Please try again using a different username.");
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
        String redirect = redirectUserLoginAttributes(userID);
        if (redirect != null) {
            return redirect;
        }
        /*
        redirectUserLoginAttributes() returnerer en string(html/endpoint. Hvis if-blokken i denne metode
        ikke opfyldes, så returnerer metoden null og vi fortsætter /{userID}s metodeflow.
        Se redirectUserLoginAttributes() dokumentation i koden.
         */

        List<WishList> listOfWishLists = ws.showListOfWishLists(userID);
        UserProfile up = ws.getUserData(userID);
        model.addAttribute("listOfWishLists", listOfWishLists);
        model.addAttribute("UserProfile",up);
        return "wishListView";

    }

    @GetMapping("/{userID}/{wishListID}")
    public String showSpecificWishList(@PathVariable int wishListID, @PathVariable int userID, Model model) {
        String redirect = redirectUserLoginAttributes(userID); //Hvis alt går som det skal, vil denne metode smide String null tilbage.
        Integer sessionUserID = (Integer) session.getAttribute("userID");
        if (redirect != null && !ws.doesUserOwnWishlist(wishListID, sessionUserID)) {
            return redirect;
        }
        System.out.println(ws.doesUserOwnWishlist(wishListID, sessionUserID));
        System.out.println(userID);
        System.out.println(sessionUserID);

        List<Wish> listOfWishes = ws.showListOfWishes(wishListID);
        String wishListName = ws.getWishListNameFromID(wishListID);
        model.addAttribute("wishListName", wishListName);
        model.addAttribute("listOfWishes", listOfWishes);
        model.addAttribute("userID",userID);
        model.addAttribute("wishListID",wishListID);
        return "wishView";
    }

    @GetMapping("/{userID}/createWishlist")
    public String createWishList(@PathVariable int userID, Model model) {
        model.addAttribute("userID", userID);//userID tilsættes som varibel, da den skal bruges i HTML formularen
        return "createWishList";
    }

    @PostMapping("/saveWishList")
    public String saveWishlist(@RequestParam String listName, @RequestParam Date expireDate, @RequestParam int userID) {
        ws.createWishList(listName, expireDate, userID);
        return "redirect:/makemywishcometrue/"+userID;
    }

    @PostMapping("/deleteWishList/{userID}/{wishListID}")
    public String deleteWishList(@PathVariable int userID, @PathVariable int wishListID) {
        ws.deleteWishList(wishListID);
        return "redirect:/makemywishcometrue/" +userID;
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

    @PostMapping("/deleteWish/{userID}/{wishID}")
    public String deleteWish(@PathVariable int userID, @PathVariable int wishID) {
        ws.deleteWish(wishID);
        return "redirect:/makemywishcometrue/"+ userID;

    }

    @GetMapping("/homeView")
    public String showHomeView() {
        return "homeView";
    }

}
