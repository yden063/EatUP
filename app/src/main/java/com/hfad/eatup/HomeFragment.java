package com.hfad.eatup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hfad.eatup.Model.Event;
import com.hfad.eatup.api.EventHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ListEventAdapter.Listener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.createEventBtn)
    Button createEventbtn;

    @BindView(R.id.searchEventBtn)
    Button searchEventBtn;

    @BindView(R.id.list_next_event)
    RecyclerView nextEventView;

    @BindView(R.id.list_next_event_progress_bar)
    ProgressBar progressBar;

    LinearLayoutManager linearLayoutManager;


    private OnFragmentInteractionListener mListener;
    private ListEventAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        init();
        getYourNextEvent();

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
      /*  if (context instanceof OnFragmentInteractionListener) {
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

    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();

        adapter.stopListening();
    }

    private void init(){
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        nextEventView.setLayoutManager(linearLayoutManager);
    }

    @OnClick(R.id.createEventBtn)
    public void onClickCreateEvent(){
        //Toast.makeText(getActivity(), getString(R.string.succesfull_save), Toast.LENGTH_LONG).show();
        ((MainActivity)getActivity()).showCreateEventFragment();
    }

    @OnClick(R.id.searchEventBtn)
    public void onClickSearchEvent(){
        ((MainActivity)getActivity()).showSearchEventFragment();
    }

    @OnClick(R.id.click_next_event)
    public void onClickNextEvent(){
        ((MainActivity)getActivity()).showListEventFragment();
    }

    @Override
    public void onDataChanged() {
        this.progressBar.setVisibility(View.GONE);
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

    public void getYourNextEvent (){


        String uid = getCurrentUser().getUid();

        Query query = EventHelper.getYourNextEvent(uid);

        FirestoreRecyclerOptions<Event> event = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();

        this.adapter = new ListEventAdapter(event,this);
        this.nextEventView.setAdapter(adapter);

    }

    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    private String nextEvtText(List<Event> ev){
        String nextEvent = "No new event planned \n:(";

        if (ev!=null&& ev.size()!=0){
            Event e = ev.get(0);
            DateFormat dateFormat = new SimpleDateFormat("dd, MMM, HH:mm",Locale.CANADA);

            nextEvent = ""+e.getTitle()+"\nIn "+e.getAddress()+"\n"+e.getCity()+"\n"+"The "+dateFormat.format(e.getDate());
        }

        return nextEvent;
    }


}
