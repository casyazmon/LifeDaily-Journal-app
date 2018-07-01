package com.example.kasi.lifedaily.listedit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kasi.lifedaily.R;
import com.example.kasi.lifedaily.db.entity.Dairy;

import com.example.kasi.lifedaily.utils.Util;

import java.util.ArrayList;
import java.util.List;



public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private List<Dairy> mValues;
    private ListContract.OnItemClickListener mOnItemClickListener;

    public PeopleAdapter(ListContract.OnItemClickListener onItemClickListener) {
        mValues = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nameTextView.setText(mValues.get(position).name);
        //holder.phoneTextView.setText(mValues.get(position).phone);

        holder.addressTextView.setText(holder.mItem.address);
        //holder.emailTextView.setText(holder.mItem.email);
        holder.birthdayTextView.setText(Util.formatMin(holder.mItem.birthday));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.clickItem(holder.mItem);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.clickLongItem(holder.mItem);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setValues(List<Dairy> values) {
        mValues = values;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nameTextView;
        /*public final TextView phoneTextView;
        public final TextView emailTextView;*/
        public final TextView birthdayTextView;
        public final TextView addressTextView;
        public Dairy mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
           /* phoneTextView = (TextView) view.findViewById(R.id.phoneTextView);
            emailTextView = (TextView) view.findViewById(R.id.emailTextView);*/
            birthdayTextView = (TextView) view.findViewById(R.id.birthdayTextView);
            addressTextView = (TextView) view.findViewById(R.id.addressTextView);
        }
    }


}
