package com.openclassrooms.entrevoisins.ui.neighbour_list;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ProfileNeighbourActivity extends AppCompatActivity {


    //----------XML Referencing----------//
    @BindView(R.id.nom_user)
    public TextView userNom;
    @BindView(R.id.avatar_profile)
    public ImageView avatar;
    @BindView(R.id.nom_user_cardview)
    public TextView userNomCardview;
    @BindView(R.id.address)
    public TextView address;
    @BindView(R.id.telephone)
    public TextView telephone;
    @BindView(R.id.social_network)
    public TextView socialNetwork;
    @BindView(R.id.description)
    public TextView description;
    @BindView(R.id.add_favorite)
    public FloatingActionButton addFavorites;

    //----------Init Variables----------//

    private int position;
    private NeighbourApiService mApiService;
    private Neighbour mNeighbourSelected;
    public boolean isFavorite = false;
    private String favTab;

    //----------Init Buttons----------//

    private ImageButton mBackButton;

    //----------Logic----------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_neighbour);
        ButterKnife.bind(this);

        mApiService = DI.getNeighbourApiService();

        Intent intent = getIntent();
        position = intent.getIntExtra("POSITION", 0);

        //mNeighbourSelected = mApiService.getNeighbourByPosition(position);
        favTab = intent.getStringExtra("FRAGMENT_TAB");

        if (favTab.equals("ALL")) {
            mNeighbourSelected = mApiService.getNeighbourByPosition(position);
        } else {
            mNeighbourSelected = mApiService.getNeighboursFavorites().get(position);
        }

        if (mNeighbourSelected.isFavorite(mNeighbourSelected)) {
            addFavorites.setImageResource(R.drawable.ic_star_yellow_24dp);
            Log.d("FAVORIS", "Image set = jaune");
        }

        userNom.setText(mNeighbourSelected.getName());
        userNomCardview.setText(mNeighbourSelected.getName());
        Glide.with(this)
                .load(mNeighbourSelected.getAvatarUrl())
                .into(avatar);
        address.setText(mNeighbourSelected.getAddress());
        telephone.setText(mNeighbourSelected.getPhoneNumber());
        description.setText(mNeighbourSelected.getAboutMe());
        socialNetwork.setText("www.facebook.fr/"+ mNeighbourSelected.getName().toLowerCase());


        mBackButton = (ImageButton)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backMainActivity = new Intent(getApplicationContext(), ListNeighbourActivity.class);
                startActivity(backMainActivity);
            }
        });

        /*
        Adding or removing a favorite depending of the click.
         */
        addFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //List<Neighbour> neighbours = mApiService.getNeighbours("ALL");

                //int index = (int) (mNeighbourSelected.getId() - 1);
                Log.d("INDEX", "ID Voisin séléctioné - 1");
                if (mNeighbourSelected.isFavorite(mNeighbourSelected)) {
                    mNeighbourSelected.setFavorite(0);
                    Log.d("ETAT", "Voisin set favoris to 0");
                    mApiService.setNeighboursFavorite(position, mNeighbourSelected);
                    //neighbours.set(index, mNeighbourSelected);
                    Log.d("INDEX", "Set Index to voison séléctioné");
                    addFavorites.setImageResource(R.drawable.ic_star_white_24dp);
                    Log.d("SUPPRESSION", "Click sur supprimer un favoris");
                } else {
                    mNeighbourSelected.setFavorite(1);
                    mApiService.setNeighboursFavorite(position, mNeighbourSelected);
                   // neighbours.set(index, mNeighbourSelected);
                    addFavorites.setImageResource(R.drawable.ic_star_yellow_24dp);
                    Log.d("AJOUT", "Click sur ajouter un voisin");
                }
            }
        });

    }



}
