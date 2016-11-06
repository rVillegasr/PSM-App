package fcfm.psm.psm_app.Model;

import android.graphics.Bitmap;

import java.sql.Date;

/**
 * Created by RVR_ on 25/09/2016.
 */
public class Event {
    private String name;
    private String description;
    private Date date;
    private String address;
    private String price;
    private Bitmap img;
    private Bitmap cover;

    public Event(String name, Bitmap img, Bitmap cover) {
        this.name = name;
        this.img = img;
        this.cover = cover;
    }

    public Event(String name, String description, Bitmap img, Bitmap cover) {
        this.name = name;
        this.description = description;
        this.img = img;
        this.cover = cover;
    }

    public Event(String name, String description, Date date, String address, String price, Bitmap img, Bitmap cover) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.address = address;
        this.price = price;
        this.img = img;
        this.cover = cover;
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

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
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
}
