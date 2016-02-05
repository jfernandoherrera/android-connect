package com.example.techventures.tucitaconnect.activities.account.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.activities.venue.VenueActivity;
import com.example.techventures.tucitaconnect.model.domain.venue.Venue;
import java.util.List;


public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.ViewHolder> {

    List<Venue> items;
    String reviewBy;
    String users;
    Typeface typeface;


    public VenuesAdapter(List<Venue> venues, String reviewBy, String users, Typeface typeface) {

        super();

        this.typeface = typeface;

        this.reviewBy = reviewBy;

        this.users = users;

        items = venues;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_venue, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }

    private String reviews(int reviews){

        String textReviewsDone = reviewBy + " " + reviews + " " + users;

        return textReviewsDone;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Venue venue = items.get(position);

        String venueName = venue.getName();

        holder.name.setText(venueName);

        holder.setAddress(venue.getAddress());

        holder.location.setText(venue.getCity().formattedLocation());

        holder.textViewReviews.setText(reviews(venue.getReviews()));

        holder.venueImage.setImageBitmap(venue.getPicture());

        float rating = (float)venue.getRating();

        if(rating != 0) {

            holder.ratingBar.setRating(rating);

        } else{

            holder.ratingBar.setVisibility(View.GONE);

            holder.textViewReviews.setVisibility(View.GONE);

        }

        holder.venueIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                VenueActivity.goToVenue(view.getContext(), venue.getObjectId());

            }

        });
    }

    @Override
    public int getItemCount() {

        return items.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;

        protected String address;

        protected TextView location;

        protected RatingBar ratingBar;

        protected TextView textViewReviews;

        protected ImageView venueImage;

        private CardView venueIcon;

        protected String from;

        public void setAddress(String address) {

            this.address = address;
        }

        public ViewHolder(final View itemView) {

            super(itemView);

            location = (TextView) itemView.findViewById(R.id.textLocation);

            name = (TextView) itemView.findViewById(R.id.textName);

            textViewReviews = (TextView) itemView.findViewById(R.id.textViewReviews);

            venueIcon = (CardView) itemView.findViewById(R.id.card_view);

            venueImage = (ImageView) itemView.findViewById(R.id.imageSearchVenue);

            ratingBar = (RatingBar) itemView.findViewById(R.id.searchRatingBar);

            setupStars();

            location.setTypeface(typeface);

            name.setTypeface(typeface);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {


                venueIcon.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            venueIcon.setCardElevation(0);


                        } else if (event.getAction() != MotionEvent.ACTION_MOVE) {

                            venueIcon.setCardElevation(6);

                        }

                        return false;
                    }
                });

            }

        }

        private void setupStars(){

            final int yellowColor = Color.argb(255, 251, 197, 70);

            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();

            stars.getDrawable(2).setColorFilter(yellowColor, PorterDuff.Mode.SRC_ATOP);

            Drawable progress = ratingBar.getProgressDrawable();

            DrawableCompat.setTint(progress, yellowColor);

        }

    }

}


