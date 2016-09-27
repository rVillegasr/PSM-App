package fcfm.psm.psm_app.Model;

import android.graphics.Bitmap;

/**
 * Created by RVR_ on 25/09/2016.
 */
public class Event {
    private String eName;
    private String eDescription;
    private Bitmap eImg;
    private Bitmap eCover;

    public Event(String eName, Bitmap eImg, Bitmap eCover) {
        this.eName = eName;
        this.eImg = eImg;
        this.eCover = eCover;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public Bitmap geteImg() {
        return eImg;
    }

    public void seteImg(Bitmap eImg) {
        this.eImg = eImg;
    }

    public Bitmap geteCover() {
        return eCover;
    }

    public void seteCover(Bitmap eCover) {
        this.eCover = eCover;
    }
}
