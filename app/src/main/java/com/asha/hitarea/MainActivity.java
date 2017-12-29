package com.asha.hitarea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View button = findViewById(R.id.buttonTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DemoListViewActivity.class);
                startActivity(i);
                // Toast.makeText(MainActivity.this, "button onClick", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.hitarea1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "hitarea1!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
