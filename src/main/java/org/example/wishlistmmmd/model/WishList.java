package org.example.wishlistmmmd.model;

import java.time.LocalDate;
import java.util.List;

public class WishList {

    private int wishListID;
    private String listName;
    private LocalDate expireDate;
    private List<Wish> wishesOnTheList;

    public WishList(String listName, LocalDate expireDate) {
        this.listName = listName;
        this.expireDate = expireDate;
    }

    public int getWishListID() {
        return wishListID;
    }

    public void setWishListID(int wishListID) {
        this.wishListID = wishListID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public List<Wish> getWishesOnTheList() {
        return wishesOnTheList;
    }

    public void setWishesOnTheList(List<Wish> wishesOnTheList) {
        this.wishesOnTheList = wishesOnTheList;
    }
}
