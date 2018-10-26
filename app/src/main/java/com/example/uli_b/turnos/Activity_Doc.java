package com.example.uli_b.turnos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class Activity_Doc extends AppCompatActivity {

    ImageButton ib_traslate, ib_notary, ib_tax, ib_acords, ib_powers, ib_permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__doc);

        ib_traslate = (ImageButton)findViewById(R.id.traslate);
        ib_notary = (ImageButton)findViewById(R.id.notary);
        ib_tax = (ImageButton)findViewById(R.id.tax);
        ib_acords = (ImageButton)findViewById(R.id.acords);
        ib_powers = (ImageButton)findViewById(R.id.powers);
        ib_permission = (ImageButton)findViewById(R.id.permission);

        ib_powers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_acords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ib_powers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
