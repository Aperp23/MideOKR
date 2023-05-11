package com.example.mideokr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TareasTrabajadoresAdapter extends RecyclerView.Adapter<TareasTrabajadoresAdapter.ViewHolder> {

        // Initialize variable
        ArrayList<String> arrayList;
        ItemClickListener itemClickListener;
        int selectedPosition = -1;
        OnRadioButtonSelectedListener listener;

    public void setListener(OnRadioButtonSelectedListener listener) {
        this.listener = listener;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    // Create constructor
public TareasTrabajadoresAdapter(ArrayList<String> arrayList,
        ItemClickListener itemClickListener)
        {
        this.arrayList = arrayList;
        this.itemClickListener = itemClickListener;
        }

@NonNull
@Override
public ViewHolder
        onCreateViewHolder(@NonNull ViewGroup parent,
        int viewType)
        {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_sprints_view, parent,
        false);
        // Pass holder view
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Set text on radio button
        holder.radioButton.setText(arrayList.get(position));
        holder.radioButton.setChecked(position == selectedPosition);

        // set listener on radio button
            holder.radioButton.setOnClickListener(new View.OnClickListener() {
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

@Override public long getItemId(int position)
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

public class ViewHolder
        extends RecyclerView.ViewHolder {
    // Initialize variable
    RadioButton radioButton;

    public ViewHolder(@NonNull View itemView)
    {
        super(itemView);

        // Assign variable
        radioButton
                = itemView.findViewById(R.id.radio_button);
    }
}
}

/*class GFG {
    public static void main(String[] args)
    {
        System.out.println("GFG!");
    }
}*/