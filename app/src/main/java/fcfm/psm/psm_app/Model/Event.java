package fcfm.psm.psm_app.Model;

import java.sql.Date;

/**
 * Modified by Everardo Hernandez on 25/09/2016.
 */

public class Event {
    private int id;
    private String name;
    private String description;
    private Date date;
    private String address;
    private float price;
    private String imgPath;
    private String coverPath;
    private float rating;
    private String category;
    //private int following;

    public Event(int id, String name, String description, Date date, String address, float price, String imgPath, String coverPath, float rating, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.address = address;
        this.price = price;
        this.imgPath = imgPath;
        this.coverPath = coverPath;
        this.rating = rating;
        this.category = category;
    }

    public Event() {


    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
