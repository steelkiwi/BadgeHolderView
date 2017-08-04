package com.steelkiwi.imageviewbadge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.steelkiwi.library.view.BadgeHolderLayout;

public class MainActivity extends AppCompatActivity {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BadgeHolderLayout v = (BadgeHolderLayout) findViewById(R.id.view);

        Button increment = (Button) findViewById(R.id.increment);
        Button decrement = (Button) findViewById(R.id.decrement);
        final Button count1 = (Button) findViewById(R.id.count);

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count += 10;
                v.setCountWithAnimation(count);
//                v.increment();
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count -= 10;
                v.setCountWithAnimation(count);
//                v.decrement();
            }
        });

        count1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Count - " + v.getCount(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
