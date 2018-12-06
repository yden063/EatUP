package com.hfad.eatup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hfad.eatup.Model.Event;
import com.hfad.eatup.api.EventHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchEventFragment extends Fragment implements ListEventAdapter.Listener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Event> events = new ArrayList<Event>();

    private OnFragmentInteractionListener mListener;
    private ListEventAdapter adapter;

    @BindView(R.id.searchBtn)
    Button searchBtn;

    @BindView(R.id.eventDateTxt)
    EditText eventDateTxt;

    @BindView(R.id.eventCityTxt)
    EditText eventCityTxt;

    @BindView(R.id.eventListResult)
    RecyclerView eventListResult;
    LinearLayoutManager linearLayoutManager;

    @BindView(R.id.eventProgressBar)
    ProgressBar progressBar;



    public SearchEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchEventFragment newInstance(String param1, String param2) {
        SearchEventFragment fragment = new SearchEventFragment();
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

        View view = inflater.inflate(R.layout.fragment_search_event, container, false);
        ButterKnife.bind(this, view);

        init();
        Query query = EventHelper.querryBuilder();
        getEventList(query);

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



    @OnClick(R.id.searchBtn)
    public void onClickSearchBtn(){
        String city = this.eventCityTxt.getText().toString();
        String date = this.eventDateTxt.getText().toString();

        Query query = EventHelper.querryBuilder();
        Log.i("Search with param:","City: "+city+" Date: "+date);

        events.clear();


        if(!city.isEmpty()){
            query = EventHelper.getEventByCity(query,city);
        }

        if(!date.isEmpty()){
            query = EventHelper.getEventByDate(query,date);
        }

        // Update the list view.
        getEventList(query);


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

    private void getEventList(Query query) {

        FirestoreRecyclerOptions<Event> event = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query, Event.class)
                .build();

        adapter = new ListEventAdapter(event,this);
        eventListResult.setAdapter(adapter);
    }

    private void init(){
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        eventListResult.setLayoutManager(linearLayoutManager);
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
}
