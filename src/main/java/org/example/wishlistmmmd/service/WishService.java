package org.example.wishlistmmmd.service;

import org.example.wishlistmmmd.model.UserProfile;
import org.example.wishlistmmmd.model.Wish;
import org.example.wishlistmmmd.model.WishList;
import org.example.wishlistmmmd.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private final WishRepository wr;

    public WishService(WishRepository wr) {
        this.wr = wr;
    }

    public List<WishList> showListOfWishLists(int userID) {
        return wr.showListOfWishLists(userID);
    }

    public List<Wish> showListOfWishes(int wishListID) {
        return wr.showWishesInSpecificWishList(wishListID);
    }

    public UserProfile getUserData(int userID) {
        return wr.getUserData(userID);
    }

}
