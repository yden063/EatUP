package com.hfad.eatup.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.hfad.eatup.Model.User;

public class UserHelper {

    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid, String username, String urlPicture, GeoPoint localisation, String phone, String job, String topics, String food) {
        //  Create User object
        User userToCreate = new User(uid, username, urlPicture, localisation, phone, job, topics, food);
        //  Add a new User Document to Firestore
        return UserHelper.getUsersCollection()
                .document(uid) // Setting uID for Document
                .set(userToCreate); // Setting object for Document
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String uid){
        return UserHelper.getUsersCollection().document(uid).get();
    }



    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }
    public static Task<Void> updateJob(String job, String uid){
        return UserHelper.getUsersCollection().document(uid).update("job",job);
    }

    public static Task<Void> updateIsOnline(Boolean online, String uid){
        return UserHelper.getUsersCollection().document(uid).update("isOnline",online);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }

}
