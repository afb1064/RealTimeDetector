package com.example.furkan.realtimedetector;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference newRef = database.getReference("Time");
    TextView Temperature;
    TextView Humidity;
    TextView warning;
    ImageView status;
    final DatabaseReference a = myRef.child("DHT11").child("Temperature"); //Real Time DB References
    final DatabaseReference b = myRef.child("DHT11").child("Humidity");
    final DatabaseReference g = myRef.child("MQ2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Temperature = findViewById(R.id.temp); //XML
        Humidity = findViewById(R.id.hum);
        warning = findViewById(R.id.warning);
        status = findViewById(R.id.status);

        final ProgressBar tpbar = findViewById(R.id.tpbar);
        final ProgressBar hpbar = findViewById(R.id.hpbar);
        final ProgressBar gpbar = findViewById(R.id.gpbar);
        final LinearLayout bgLayout = findViewById(R.id.bg);


        b.addValueEventListener(new ValueEventListener() { // Humidity Listener
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float value = dataSnapshot.getValue(Float.class);
                //String data = dataSnapshot.getValue(String.class);
                hpbar.setVisibility(View.GONE);
                Humidity.setVisibility(View.VISIBLE);
                Humidity.setText("%" + Float.toString(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        a.addValueEventListener(new ValueEventListener() { // Temperature listener
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float value = dataSnapshot.getValue(Float.class);
                tpbar.setVisibility(View.GONE);
                Temperature.setVisibility(View.VISIBLE);
                Temperature.setText(Float.toString(value) + "°C");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ValueEventListener valueEventListener = g.addValueEventListener(new ValueEventListener() { //Gas listener
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int value = dataSnapshot.getValue(Integer.class);
                gpbar.setVisibility(View.GONE);
                warning.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);

                if (value == 0) { //Gas Measured
                    bgLayout.setBackground(getResources().getDrawable(R.drawable.bgred, null));
                    status.setImageResource(R.drawable.warning);
                    warning.setTextColor(Color.parseColor("#d50000"));
                    warning.setText("Toxic Gas Measured!");

                }


                else { //
                    bgLayout.setBackground(getResources().getDrawable(R.drawable.bg, null));
                    status.setImageResource(R.drawable.happiness);
                    warning.setTextColor(Color.parseColor("#008900"));
                    warning.setText("No Toxic Gas Measured.");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        FirebaseMessaging.getInstance().subscribeToTopic("warning").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) { //Notification Topic
                        String msg = "OK";
                        if (!task.isSuccessful()) {
                            msg = "FAIL";
                        }
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
