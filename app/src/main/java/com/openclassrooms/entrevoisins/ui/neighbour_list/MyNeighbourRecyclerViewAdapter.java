package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder>  {

    private final List<Neighbour> mNeighbours;
    private Context mContext;
    private boolean isFavoriteTab;


    public MyNeighbourRecyclerViewAdapter(Context context, List<Neighbour> items, boolean isFavoriteTab) {
        mNeighbours = items;
        mContext = context;
        this.isFavoriteTab = isFavoriteTab;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        /*
        Removing delete icon if favorites is displayed
        else delete icon is enabled
         */
        if(isFavoriteTab == false) {
            holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));

                    Toast.makeText(mContext,  neighbour.getName() + " supprimé(e) !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.mDeleteButton.setVisibility(View.INVISIBLE);
            holder.mDeleteButton.setEnabled(false);
        }


        /* Click on item "NeighbourFragment"
        Get the neighbour position
        Get the tab selected
        Start ProfileNeighbourActivity
         */
        holder.mNeighbourItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileNeighbourActivity.class);
                intent.putExtra("POSITION", position);
                intent.putExtra("FRAGMENT_TAB", isFavoriteTab ? "FAVORITES" : "ALL");
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {

        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_neighbour)
        public ConstraintLayout mNeighbourItem;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
