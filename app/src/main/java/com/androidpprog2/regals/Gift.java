package com.androidpprog2.regals;

import android.widget.ImageView;


import java.util.UUID;

public class Gift {
    private String name;
    private int price; // float ?
    private String link;

    private String user;
    private int  prio;
    private UUID id;
    private String date;
    private ImageView foto;
    private String WishList;
    private int  reserved; // boolean ?

    public ImageView getFoto() {
        return foto;
    }
    //No tinc clar com s'acaba de fer aixo amb el image view , repasar quan fiquem la api
/*
    public void setFoto(ImageView foto) {
        this.foto = foto;
    }

 */

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWishList() {
        return WishList;
    }

    public void setWishList(String wishList) {
        WishList = wishList;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int  reserved) {
        this.reserved = reserved;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
