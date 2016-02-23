package com.example.techventures.tucitaconnect.activities.venue.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.techventures.tucitaconnect.R;
import com.example.techventures.tucitaconnect.model.domain.slot.Slot;
import java.util.Calendar;
import java.util.List;

public class SelectSlotsAdapter extends RecyclerView.Adapter<SelectSlotsAdapter.ViewHolder> {

    int duration;
    int amount;
    Calendar initialDate;
    List<Slot> slots;

    public SelectSlotsAdapter(List<Slot> slots, Calendar initialDate){

        this.slots = slots;

        this.duration = slots.get(0).getDurationMinutes();

        this.amount = slots.size();

        this.initialDate = initialDate;

    }

    public void setSlots(List<Slot> slots) {

        this.slots.clear();

        this.slots.addAll(slots);

        notifyDataSetChanged();

    }

    public void setInitialDate(Calendar initialDate) {

        this.initialDate = initialDate;

        notifyDataSetChanged();

    }

    @Override
    public SelectSlotsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_slot, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SelectSlotsAdapter.ViewHolder holder, final int position) {

        if(slots.get(position) != null) {

            if (slots.get(position).isSelected()) {


                holder.textView.setBackgroundResource(R.drawable.border_selected_slot);

            } else {

                holder.textView.setBackgroundResource(R.drawable.border_left_bar);

            }

            holder.textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (!slots.get(position).isSelected()) {

                        slots.get(position).setSelected(true);

                        holder.textView.setBackgroundResource(R.drawable.border_selected_slot);

                    } else {

                        slots.get(position).setSelected(false);

                        holder.textView.setBackgroundResource(R.drawable.border_left_bar);

                    }

                }
            });

            holder.textView.setText(hour(position));

        }

    }

    public List<Slot> getSlots() {

        return slots;

    }

    private String hour(int position){

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, initialDate.get(Calendar.HOUR_OF_DAY));

        calendar.set(Calendar.MINUTE, initialDate.get(Calendar.MINUTE));

        int time = position * duration;

        calendar.add(Calendar.MINUTE, time);

        String hourSlot = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        return hourSlot;

    }

    @Override
    public int getItemCount() {

        return slots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        boolean selected;

        public ViewHolder(final View itemView){

            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.lapses);

            selected = false;

        }
    }
}
