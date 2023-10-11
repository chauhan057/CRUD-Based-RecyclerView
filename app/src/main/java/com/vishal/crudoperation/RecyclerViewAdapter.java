package com.vishal.crudoperation;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    ArrayList<ModelClass> list;
    Context context;

    public RecyclerViewAdapter(ArrayList<ModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelClass Item = list.get(position);
        holder.tvResult.setText(Item.getTitle());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvResult;
    Button  updateBtn, deleteBtn;
    RecyclerView recyclerView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvResult = itemView.findViewById(R.id.tvTitle);
        updateBtn = itemView.findViewById(R.id.updateData);
        deleteBtn = itemView.findViewById(R.id.deleteData);
        recyclerView =itemView.findViewById(R.id.rvTitle);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    deleteItem(position);
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });




    }
    public void showPopup() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_layout);

        EditText editText = dialog.findViewById(R.id.editText);
        Button confirmButton = dialog.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = editText.getText().toString();

                 updateItemWithNewText(getAdapterPosition(), newText);

                dialog.dismiss();
            }
        });
        dialog.show();
    }


}

    public void deleteItem(int position) {
        Toast.makeText(context, ""+list.get(position).title, Toast.LENGTH_SHORT).show();

        list.remove(position);
        notifyItemRemoved(position);
    }

    private void updateItemWithNewText(int position, String newText) {
        ModelClass item = list.get(position);
        item.setTitle(newText);
        notifyItemChanged(position);
    }

}
