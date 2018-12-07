package com.hfad.eatup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hfad.eatup.Model.Event;
import com.hfad.eatup.api.EventHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Boolean isCreator = false;
    private Boolean isParticipant = false;
    private String uidEvent;
    private List<String>  list= new ArrayList<>();

    @BindView(R.id.eventNameText)
    TextView eventNameText;
    @BindView(R.id.eventDateText)
    TextView eventDateText;
    @BindView(R.id.eventAddressText)
    TextView eventAddressText;
    @BindView(R.id.eventCityText)
    TextView eventCityText;
    @BindView(R.id.eventDescriptionText)
    TextView eventDescriptionText;
    @BindView(R.id.eventMaxParticipantsText)
    TextView eventMaxParticipantsText;
    @BindView(R.id.eventCancelOrDeleteBtn)
    Button eventCancelOrDeleteBtn;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailEventFragment newInstance(String param1, String param2) {
        DetailEventFragment fragment = new DetailEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_event, container, false);
        ButterKnife.bind(this,view);
        getActivity().setTitle("Event detail");
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    ((MainActivity)getActivity()).showListEventFragment();
                    return true;
                }
                return false;
            }
        });

        updateUIWhenCreating();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void updateUIWhenCreating() {

        if(mParam1 != null){
            EventHelper.getEvent(mParam1).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Event event = documentSnapshot.toObject(Event.class);

                    if (event.getUidCreator().equals(getCurrentUser().getUid())){

                        eventCancelOrDeleteBtn.setText("Cancel Event");
                        isCreator = true;
                        uidEvent = event.getUuid();


                    }
                    else if(event.getListppl().indexOf(getCurrentUser().getUid()) == -1){
                        eventCancelOrDeleteBtn.setText("Subscribe Event");
                        list = event.getListppl();
                        uidEvent = event.getUuid();
                        list.add(getCurrentUser().getUid());
                    }
                    else{

                        eventCancelOrDeleteBtn.setText("Unsubscribe");
                        isParticipant = true;
                        uidEvent = event.getUuid();
                        list = event.getListppl();
                        list.remove(list.indexOf(getCurrentUser().getUid()));
                    }
                    eventCityText.setText(event.getCity());
                    eventAddressText.setText(event.getAddress());
                    eventDescriptionText.setText(event.getDescription());
                    eventDateText.setText(event.getDate().toString());
                    eventMaxParticipantsText.setText(String.valueOf(event.getMaxppl()));
                    eventNameText.setText(event.getTitle());
                }
            });


        }
    }

    private void subscribeEvent (){
        EventHelper.updateList(list,uidEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                eventCancelOrDeleteBtn.setClickable(false);
            }
        });
    }

    private void unSubscribeEvent (){
        EventHelper.updateList(list,uidEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                eventCancelOrDeleteBtn.setClickable(false);
            }
        });
    }

    private void deleteEvent (){
        EventHelper.deleteEvent(uidEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                eventCancelOrDeleteBtn.setClickable(false);
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @OnClick(R.id.eventCancelOrDeleteBtn)
    public void onCLickButton(){

        if(isCreator)
            deleteEvent();
        else if(isParticipant)
            unSubscribeEvent();
        else
            subscribeEvent();
    }

    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }


}
