package com.example.csparmaps.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csparmaps.R;
import com.example.csparmaps.model.APiResponse;
import com.example.csparmaps.model.Success;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ActivityProfile extends AppCompatActivity implements ProfileAdapter.ClickListener {

    Button maps;
    RecyclerView listView;
    APiResponse successList;
    ProfileAdapter profileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        maps = findViewById(R.id.maps);
        listView = findViewById(R.id.listView);
        Intent i = getIntent();
        if (getIntent().getExtras() != null) {
            successList = (APiResponse) i.getSerializableExtra("data");
        }
        profileAdapter = new ProfileAdapter(this);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(profileAdapter);
        profileAdapter.setOnClickListener(this);

        profileAdapter.setList(successList.getSuccess());
        maps.setOnClickListener(view -> {
            super.onBackPressed();
        });
    }

    private void showBottomSheet(Success tag) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottomsheet_profile);

        TextView name = bottomSheetDialog.findViewById(R.id.name);
        TextView email = bottomSheetDialog.findViewById(R.id.email);
        TextView date = bottomSheetDialog.findViewById(R.id.date);
        String nameS = "", emailS = "", dateS = "";
        if (tag.getName().toString() != null && !tag.getName().toString().isEmpty())
            nameS = tag.getName().toString();
        if (tag.getEmail().toString() != null && !tag.getEmail().toString().isEmpty())
            emailS = tag.getEmail().toString();
        if (tag.getCreatedAt() != null && !tag.getCreatedAt().isEmpty())
            dateS = tag.getCreatedAt().toString();

        name.setText(nameS);
        email.setText(emailS);
        date.setText(dateS);

        bottomSheetDialog.show();

    }

    @Override
    public void onClickViewOrder(int position, Success data) {
        showBottomSheet(data);

    }


}
