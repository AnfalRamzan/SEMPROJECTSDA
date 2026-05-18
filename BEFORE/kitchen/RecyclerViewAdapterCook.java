/**
 * This adapter is used to show the chefs and their orders in
 * main screen of kitchen module (Head Chef)
* */

package com.dineout.code.kitchen;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dineout.R;
import com.dineout.code.kitchen.models.Chef;

import java.util.ArrayList;

public class RecyclerViewAdapterCook extends RecyclerView.Adapter<RecyclerViewAdapterCook.ViewHolder>
{
    private ArrayList<Chef> mChefs = new ArrayList<>();
    private Context mContext;
    public static ArrayList<RecyclerViewAdapterOrdersOfCook> adapters = new ArrayList<>();

    public RecyclerViewAdapterCook(Context context, ArrayList<Chef> chefs)
    {
        this.mChefs = chefs;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kitchen_layout_cooks_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.name.setText(mChefs.get(position).getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        final RecyclerView recyclerView = holder.mRecyclerView;
        recyclerView.setLayoutManager(layoutManager);
//adapter depends heavily on:
        RecyclerViewAdapterOrdersOfCook adapter2 =
    new RecyclerViewAdapterOrdersOfCook(
        mContext,
        mChefs.get(position).getChefQueue(),
        position);

recyclerView.setAdapter(adapter2);

    @Override
    public int getItemCount()
    {
        return mChefs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView imageView;
        RecyclerView mRecyclerView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageView=itemView.findViewById(R.id.imageView);
            mRecyclerView=itemView.findViewById(R.id.recyclerview5);
        }
    }
}

@Override
public void onBindViewHolder(
    final ViewHolder holder,
    final int position)
{
    // Setting chef name
    holder.name.setText(
        mChefs.get(position).getName());

    // Creating Layout Manager(DUPLICATE)
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(
            mContext,
            LinearLayoutManager.VERTICAL,
            false);

    // Finding RecyclerView
    final RecyclerView recyclerView =
        holder.mRecyclerView;

    // Setting Layout Manager
    recyclerView.setLayoutManager(layoutManager);

    // Creating Adapter
    RecyclerViewAdapterOrdersOfCook adapter2 =
        new RecyclerViewAdapterOrdersOfCook(
            mContext,
            mChefs.get(position).getChefQueue(),
            position);

    // Adding Adapter
    adapters.add(adapter2);

    // Setting Adapter
    recyclerView.setAdapter(
        adapters.get(adapters.size()-1));

    // Visibility Handling
    if(!mChefs.get(position).isPresent()){

        holder.name.setVisibility(View.GONE);

        holder.imageView.setVisibility(View.GONE);

        holder.mRecyclerView.setVisibility(
            View.GONE);

    }
    else{

        holder.name.setVisibility(View.VISIBLE);

        holder.imageView.setVisibility(View.VISIBLE);

        holder.mRecyclerView.setVisibility(
            View.VISIBLE);
    }
}
//Adapter Classes
if(employee.getType().equals("Chef")){
    // Chef UI
}
// Status Check in Adapter chnging data
if(mData.get(position).getStatus()
        == MainActivity.WAITING)
{
    holder.button.setVisibility(
            View.VISIBLE);
}