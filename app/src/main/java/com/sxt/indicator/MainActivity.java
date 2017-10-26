package com.sxt.indicator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sxt.indicator.spinner.CustomSpinner;

public class MainActivity extends AppCompatActivity {

    private CustomSpinner customSpinner;
    private TextView tvSearch;
    private View titleRoot;
    private View titleRoot2;
    private View ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        customSpinner = (CustomSpinner) findViewById(R.id.spinnerCustom);

        tvSearch = findViewById(R.id.tvSearch);
        titleRoot = findViewById(R.id.title_layoutRoot);

        titleRoot2 = findViewById(R.id.title_2_layout);
        ivBack = findViewById(R.id.ivBack);


        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation(titleRoot, 800);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


//        EditText editText = findViewById(R.id.et);
//        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        //可以输入小数
//        editText.setKeyListener(new DigitsKeyListener(true, false));

//        String digits = "1234567890";
//        editText.setKeyListener(DigitsKeyListener.getInstance(digits));
//        editText.setKeyListener(new NumberKeyListener() {
//
//            @Override
//            public int getInputType() {
//                return android.text.InputType.TYPE_CLASS_PHONE;
//            }
//
//            @NonNull
//            @Override
//            protected char[] getAcceptedChars() {
//                return new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
//            }
//
//        });

//        customSpinner.post(new Runnable() {
//            @Override
//            public void run() {
//                customSpinner.initPopubWindow(MainActivity.this);
//                List<String> list = new ArrayList<>();
//                list.add("北京");
//                list.add("上海");
//                list.add("深圳");
//                list.add("广州");
//                list.add("广州");
//                list.add("广州");
//                list.add("广州");
//                list.add("广州");
//                customSpinner.setListViewData(list);
//            }
//        });
//
//        String[] titles = {"北京", "上海", "深圳", "广州"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        spinner.post(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void run() {
//                spinner.setDropDownVerticalOffset(spinner.getMeasuredHeight() + 10);
//            }
//        });
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    @SuppressLint("ObjectAnimatorBinding")
    public void startAnimation(View targetView, long duration) {

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(targetView, "ScaleX", 1, 0.8f);
       //TODO  ObjectAnimator translateY = ObjectAnimator.ofFloat(targetView, "TranslateY", );
        set.setDuration(duration).playTogether(scaleX);
        set.start();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0.5f).setDuration(duration);
        ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) titleRoot.getLayoutParams();
                lp.topMargin -= 1;
                titleRoot.setLayoutParams(lp);
//                tvSearch.setEnabled(false);
            }
        };
        valueAnimator.addUpdateListener(mUpdateListener);
        valueAnimator.start();
    }

}
