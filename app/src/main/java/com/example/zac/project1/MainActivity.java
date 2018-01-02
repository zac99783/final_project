package com.example.zac.project1;

import com.bumptech.glide.Glide;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    Button map,route ,price, service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.tenor);
        Glide.with(this).load(R.drawable.tenor).into(imageView);



        map = (Button) findViewById(R.id.mapbusstsaion);
        route = (Button) findViewById(R.id.searchroute);
        service = (Button) findViewById(R.id.directionservice);
        price = (Button) findViewById(R.id.ticketprice);



        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, image_1.class);
                startActivityForResult(intent1, 0);
            }
        });


        map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, MapBusStation.class);
                startActivityForResult(intent1, 0);
            }
        });



        route.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, SearchRoute.class);
                startActivityForResult(intent1, 0);
            }
        });



        service.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, DirectionService.class);
                startActivityForResult(intent1, 0);
            }
        });


        price.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, TicketPrice.class);
                startActivityForResult(intent1, 0);
            }
        });


    }

}
