package com.example.uli_b.turnos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class Activity_OtherS extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__other_s);

        ImageButton ib_packet, ib_temporal, ib_vehicule, ib_travel;

        ib_packet = (ImageButton)findViewById(R.id.packet);
        ib_temporal = (ImageButton)findViewById(R.id.temporal);
        ib_travel = (ImageButton)findViewById(R.id.travel);
        ib_vehicule = (ImageButton)findViewById(R.id.vehicule);

        ib_packet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_temporal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_vehicule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
