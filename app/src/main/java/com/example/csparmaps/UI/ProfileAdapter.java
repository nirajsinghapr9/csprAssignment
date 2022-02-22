package com.example.csparmaps.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csparmaps.R;
import com.example.csparmaps.model.Success;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {
    private Context context;
    private List<Success> data;
    private ProfileAdapter.ClickListener clickListener;



    public ProfileAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickListener(ProfileAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setList(List<Success> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProfileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ProfileAdapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Success list = data.get(position);
        holder.name.setText(data.get(position).getName());

        holder.layItem.setOnClickListener(v -> {
            if (clickListener != null)
                clickListener.onClickViewOrder(position, list);
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.name)
        TextView name;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.layItem)
        RelativeLayout layItem;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ClickListener {
        void onClickViewOrder(int position, Success data);

    }

}
