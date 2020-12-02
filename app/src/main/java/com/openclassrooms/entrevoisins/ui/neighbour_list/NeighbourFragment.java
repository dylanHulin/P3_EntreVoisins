package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;


public class NeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private static final String POSITION= "POSITION";
    public boolean isFavoriteTab = false;
    private String targetList = "ALL";


    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     * @param position
     */

    /*
    Getting NeighbourFragment position by Bundle
     */
    public static NeighbourFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        NeighbourFragment mFragment = new NeighbourFragment();
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();

        Log.d("ALL", "Afficher liste ALL");

        //Checking the position of POSITION
        //Return Favorites if true
        if (getArguments() != null) {
           if(getArguments().getInt(POSITION) == 1) {
               isFavoriteTab = true;
               targetList = "FAVORITES";
               Log.d("FAVORIS", "Afficher liste FAVORIS");
           }

       }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (isFavoriteTab) {
            view = inflater.inflate(R.layout.fragment_neighbour_favlist, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        }
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        initList();

        return view;
    }

    /**
     * Init the List of neighbours depending of targetList.
     */
    private void initList() {
        if (targetList.equals("ALL")) {
            mNeighbours = mApiService.getNeighbours();
        } else {
            mNeighbours = mApiService.getNeighboursFavorites();
        }
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(getActivity().getApplicationContext(), mNeighbours, isFavoriteTab));

    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */


    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
            mApiService.deleteNeighbour(event.neighbour);
            initList();

    }


}

