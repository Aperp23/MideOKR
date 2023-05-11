package com.example.mideokr;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TareasSprintAdapter  extends RecyclerView.Adapter<TareasSprintAdapter.ViewHolder>{

    ArrayList<String> arrayList;
    int selectedPosition = -1;
    OnRadioButtonSelectedListener listener;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public TareasSprintAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TareasSprintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sprints_view, parent,
                        false);
        // Pass holder view
        return new TareasTrabajadoresAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasTrabajadoresAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        {
            // Set text on radio button
            holder.checkbox.setText(arrayList.get(position));
            holder.checkbox.setChecked(position == selectedPosition);

            // set listener on radio button
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    System.out.println(position+1);
                    System.out.println(selectedPosition);

                    if (position==position) {
                        selectedPosition = position;
                        notifyDataSetChanged();
                        Toast.makeText(v.getContext(), arrayList.get(selectedPosition), Toast.LENGTH_SHORT).show();
                    }
                }
            });;
        }

    }


    @Override protected long getItemId(int position)
    {
        // pass position
        return position;
    }

    @Override public int getItemViewType(int position)
    {
        // pass position
        return position;
    }

    @Override public int getItemCount()
    {
        // pass total list size
        return arrayList.size();
    }





}
