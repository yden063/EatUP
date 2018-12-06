package com.hfad.eatup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hfad.eatup.Model.Event;
import com.hfad.eatup.Model.User;
import com.hfad.eatup.api.EventHelper;
import com.hfad.eatup.api.UserHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private User currentUser;
    private boolean enableCreateBtn;

    @BindView(R.id.eventNameText)
    EditText eventName;

    @BindView(R.id.eventDate)
    EditText eventDate;

    @BindView(R.id.eventTime)
    EditText eventTime;

    @BindView(R.id.eventAddressText)
    EditText eventAddressText;

    @BindView(R.id.eventCityText)
    EditText eventCityText;

    @BindView(R.id.eventMaxParticipants)
    EditText eventMaxParticipants;

    @BindView(R.id.eventDescriptionText)
    EditText eventDescriptionText;

    @BindView(R.id.eventCreateBtn)
    Button eventCreateBtn;

    private OnFragmentInteractionListener mListener;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFragment newInstance(String param1, String param2) {
        CreateEventFragment fragment = new CreateEventFragment();
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

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        ButterKnife.bind(this, view);

        this.getCurrentUserFromFirestore();
        eventCreateBtn.setEnabled(false);
        enableCreateBtn = false;

        createListenerForEachTextView();

        return view;
    }

    private void createListenerForEachTextView() {
        // EventName
        eventName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAllTextView();
                eventCreateBtn.setEnabled(enableCreateBtn);
            }
        });
        eventDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAllTextView();
                eventCreateBtn.setEnabled(enableCreateBtn);
            }
        });
        eventTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAllTextView();
                eventCreateBtn.setEnabled(enableCreateBtn);
            }
        });
        eventAddressText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAllTextView();
                eventCreateBtn.setEnabled(enableCreateBtn);
            }
        });
        eventCityText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAllTextView();
                eventCreateBtn.setEnabled(enableCreateBtn);
            }
        });
        eventMaxParticipants.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAllTextView();
                eventCreateBtn.setEnabled(enableCreateBtn);
            }
        });
        eventDescriptionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAllTextView();
                eventCreateBtn.setEnabled(enableCreateBtn);
            }
        });


    }

    private void checkAllTextView() {
        if (!eventName.getText().toString().isEmpty() &&
                !eventDate.getText().toString().isEmpty() &&
                !eventTime.getText().toString().isEmpty() &&
                !eventAddressText.getText().toString().isEmpty() &&
                !eventCityText.getText().toString().isEmpty() &&
                !eventMaxParticipants.getText().toString().isEmpty() &&
                !eventDescriptionText.getText().toString().isEmpty()) {
            enableCreateBtn = true;
        } else
            enableCreateBtn = false;
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    @OnClick(R.id.eventCreateBtn)
    public void createEvent() {

        String title = this.eventName.getText().toString();
        String dateString = this.eventDate.getText().toString();
        String time = this.eventTime.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFinale = null;


        try {
            dateFinale = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFinale);

            String[] timeSplit = time.split(":");
            int hours = Integer.parseInt(timeSplit[0]);
            int minutes = Integer.parseInt(timeSplit[1]);

            calendar.add(Calendar.HOUR_OF_DAY, hours);
            calendar.add(Calendar.MINUTE, minutes);

            dateFinale = calendar.getTime(); // Update of the finale date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String address = this.eventAddressText.getText().toString();
        String city = this.eventCityText.getText().toString();
        String description = this.eventDescriptionText.getText().toString();
        int maxPeople = Integer.parseInt(this.eventMaxParticipants.getText().toString());

        EventHelper.createEvent(title, address, city, dateFinale, maxPeople, description, this.currentUser.getUid());

        getActivity().getSupportFragmentManager().beginTransaction().remove(this);
    }

    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(((MainActivity)getActivity()).getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(User.class);
            }
        });
    }
}
