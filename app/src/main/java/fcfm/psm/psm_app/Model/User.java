package fcfm.psm.psm_app.Model;

import java.util.List;

/**
 * Created by evera on 11/7/2016.
 */

public class User {
    private int id;
    //Nombre completo del usuario
    private String username;
    //Eventos a los que el usuario va a asistir
    private List<Event> userEvents;

    public User() {
    }

    public User(int id, String username, List<Event> userEvents) {
        this.id = id;
        this.username = username;
        this.userEvents = userEvents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Event> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(List<Event> userEvents) {
        this.userEvents = userEvents;
    }
}
