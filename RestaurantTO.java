package com.webmob.Restaurant_Yab.model;

import java.io.Serializable;

/**
 * Created by Ali on 24/01/2018.
 */
public class RestaurantTO implements Serializable {
    private String id;
    private String name;
    private String address;
    private String tel;
    private String pic;

    public RestaurantTO() {

    }

    public RestaurantTO(String id, String name, String address, String tel, String pic) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


}
