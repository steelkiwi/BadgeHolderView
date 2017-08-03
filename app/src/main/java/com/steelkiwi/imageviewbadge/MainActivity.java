package com.steelkiwi.imageviewbadge;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.steelkiwi.library.view.BadgeHolderLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BadgeHolderLayout v = (BadgeHolderLayout) findViewById(R.id.view);
        v.setCount(10);

        Button increment = (Button) findViewById(R.id.increment);
        Button decrement = (Button) findViewById(R.id.decrement);
        Button count = (Button) findViewById(R.id.count);

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(v.getCount() > 10) {
                    v.setBadgeBackground(ContextCompat.getColor(MainActivity.this, R.color.color4));
                }
                v.increment();
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.decrement();
            }
        });

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Count - " + v.getCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
