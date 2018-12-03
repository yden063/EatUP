package com.hfad.eatup.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hfad.eatup.Model.Event;
import com.hfad.eatup.Model.User;

import java.util.Date;

public class EventHelper {
    private static final String COLLECTION_NAME = "events";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getEventsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---
    public static Task<DocumentReference> createEvent(String title) {
        Event eventToCreate = new Event(title);

    return EventHelper.getEventsCollection()
            .document()
            .collection(COLLECTION_NAME)
            .add(eventToCreate);
    }
}
