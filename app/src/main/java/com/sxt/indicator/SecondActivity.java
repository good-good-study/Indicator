package com.sxt.indicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by izhaohu on 2017/10/27.
 */
public class SecondActivity extends AppCompatActivity {
    private TextView mSearchBGTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acivity_second_layout);
        mSearchBGTxt = (TextView) findViewById(R.id.tv_search_bg);
        mSearchBGTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                int location[] = new int[2];
                mSearchBGTxt.getLocationOnScreen(location);
                intent.putExtra("x", location[0]);
                intent.putExtra("y", location[1]);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}
