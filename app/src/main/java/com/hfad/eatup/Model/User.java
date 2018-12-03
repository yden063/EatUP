package com.hfad.eatup.Model;
import android.support.annotation.Nullable;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String uid;
    private String username;
    private GeoPoint localisation;
    private Boolean isOnline;
    @Nullable
    private String urlPicture;
    private String job;
    private String food;
    private String topics;
    private int rating;
    private int nbratings;
    private List<Event> createdEvent,partipatingEvent;
    private String phone;

    public User() { }

    public User(String uid, String username, @Nullable String urlPicture, GeoPoint localisation, String phone) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.localisation = localisation ;
        this.isOnline = false;
        createdEvent = new ArrayList<Event>();
        partipatingEvent = new ArrayList<Event>();
        this.phone = phone;
    }

    public void rateUser(int mark){
        rating = (rating*nbratings+mark)/nbratings+1;
        nbratings++;
    }

    public void createEvent(Event event){
        createdEvent.add(event);
    }

    public void joinEvent(Event event){
        partipatingEvent.add(event);
    }

    public Event getNextEvent(){
        List<Event> listNextEvents = new ArrayList<>();
        listNextEvents.addAll(createdEvent);
        listNextEvents.addAll(partipatingEvent);
        Event nextEvent=null;

        if (listNextEvents.size()!=0){
            nextEvent=listNextEvents.get(0);

            for (int i=0;i<listNextEvents.size();i++) {

                if(listNextEvents.get(i).getDate().before(nextEvent.getDate())){
                    nextEvent=listNextEvents.get(i);
                }
            }
        }
        return nextEvent;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    @Nullable public String  getUrlPicture() { return urlPicture; }
    public GeoPoint getLocalisation() {
        return localisation;
    }
    public Boolean getIsOnline(){return isOnline;}

    public String getJob() {
        return job;
    }

    public String getFood() {
        return food;
    }

    public String getTopics() {
        return topics;
    }

    public int getRating() {
        return rating;
    }

    public int getNbratings() {
        return nbratings;
    }

    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(@Nullable String urlPicture) { this.urlPicture = urlPicture; }
    public void setLocalisation(GeoPoint localisation) {
        this.localisation = localisation;
    }
    public void setIsOnlinee(Boolean isOnline) { this.isOnline = isOnline; }

    public void setJob(String job) {
        this.job = job;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

}