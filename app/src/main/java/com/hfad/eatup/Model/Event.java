package com.hfad.eatup.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
    private String uidCreator;
    private String title;
    private String address;
    private String city;
    private Date date;
    private int maxppl;
    private String description;
    private User creator;
    private List<User> listppl;


//    public Event( String title, String address, String city, Date date, int maxppl, String description, User creator) {
//        this.title = title;
//        this.address = address;
//        this.city = city;
//        this.date = date;
//        this.maxppl = maxppl;
//        this.description = description;
//        this.creator = creator;
//        listppl = new ArrayList<User>();
//    }
    public Event(){};

    public Event( String title, String address, String city, Date date, int maxppl, String description, String uidCreator) {
        this.title = title;
        this.address = address;
        this.city = city;
        this.date = date;
        this.maxppl = maxppl;
        this.description = description;
        this.uidCreator = uidCreator;
        listppl = new ArrayList<User>();
    }



    public boolean addParticipant(User participant){
        if (listppl.size()==maxppl) {
            return false;
        }else{
            listppl.add(participant);
            return true;
        }
    }

    public void delParticipant(User participant){
        if(listppl.contains(participant)){
            listppl.remove(participant);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public Date getDate() {
        return date;
    }

    public int getMaxppl() {
        return maxppl;
    }

    public String getDescription() {
        return description;
    }

    public User getCreator() {
        return creator;
    }

    public List<User> getListppl() {
        return listppl;
    }

    public String getUidCreator() {
        return uidCreator;
    }

    public void setUidCreator(String uidCreator) {
        this.uidCreator = uidCreator;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMaxppl(int maxppl) {
        this.maxppl = maxppl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setListppl(List<User> listppl) {
        this.listppl = listppl;
    }
}
