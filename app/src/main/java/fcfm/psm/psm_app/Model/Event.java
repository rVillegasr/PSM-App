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
    private String price;
    private String imgPath;
    private String coverPath;

    public Event() {

    }

    public Event(int id, String name, String description, Date date, String address, String price, String imgPath, String coverPath) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.address = address;
        this.price = price;
        this.imgPath = imgPath;
        this.coverPath = coverPath;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
}