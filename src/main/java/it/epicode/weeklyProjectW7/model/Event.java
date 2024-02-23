package it.epicode.weeklyProjectW7.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int     id;
    private String  title;
    private String  description;
    private String  location;
    private Date    date;
    @Column(name = "max_spots")
    private int     maxSpots;
    @ManyToMany
    @JoinTable(name = "userlist")
    private List<User> users;

    public Event(String title, String description, String location, Date date, int maxSpots) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.maxSpots = maxSpots;
        this.users = new ArrayList<>();
    }

    public Event(){

    }

    public void addUser(User user) {
        users.add(user);
    }
}
