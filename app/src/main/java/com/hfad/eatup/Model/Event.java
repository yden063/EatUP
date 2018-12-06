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
    private List<String> listppl;

    public Event(){}

    public Event( String title, String address, String city, Date date, int maxppl, String description, String uidCreator) {
        this.title = title;
        this.address = address;
        this.city = city;
        this.date = date;
        this.maxppl = maxppl;
        this.description = description;
        this.uidCreator = uidCreator;
        listppl = new ArrayList<>();
        listppl.add(uidCreator);
    }



    public boolean addParticipant(String participantUid){
        if (listppl.size()==maxppl) {
            return false;
        }else{
            listppl.add(participantUid);
            return true;
        }
    }

    public void delParticipant(String participantUid){
        listppl.remove(participantUid);
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

    public List<String> getListppl() {
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

    public void setListppl(List<String> listppl) {
        this.listppl = listppl;
    }
}
