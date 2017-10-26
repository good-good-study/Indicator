package com.sxt.indicator.spinner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sxt.indicator.R;

import java.util.List;

/**
 * Created by izhaohu on 2017/10/24.
 */
public class CustomSpinner extends FrameLayout {

    private TextView textView;
    private ImageView raw;
    private ListView listView;
    private PopupWindow popupWindow;
    private List<String> data;

    public CustomSpinner(@NonNull Context context) {
        super(context, null);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            init(getContext());
        }
    }

    @SuppressLint("ResourceAsColor")
    private void init(Context context) {
        //添加TextView 用于显示用户点击选择的条目内容
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(R.color.black);
        textView.setPadding(32, 0, 8, 0);
        textView.setBackgroundResource(R.drawable.item_pressed_false);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        addView(textView);
        FrameLayout.LayoutParams params = (LayoutParams) textView.getLayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);

        //添加右侧的指示箭头
        raw = new ImageView(context);
        raw.setImageResource(R.mipmap.down);
        addView(raw);
        FrameLayout.LayoutParams lpRaw = (LayoutParams) raw.getLayoutParams();
        lpRaw.width = LayoutParams.WRAP_CONTENT;
        lpRaw.height = LayoutParams.WRAP_CONTENT;
        lpRaw.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
        raw.setPadding(8, 0, 32, 0);
        raw.setLayoutParams(lpRaw);
    }

    public CustomSpinner initPopubWindow(Activity activity) {
        popupWindow = new PopupWindow(activity);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item_pressed_false);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        popupWindow.setBackgroundDrawable(drawable);//设置背景
        View contentView = LayoutInflater.from(activity).inflate(R.layout.item_spinner_content_listview, null);
        listView = (ListView) contentView.findViewById(R.id.listView);
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        textView.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                textView.setEnabled(false);
                if (popupWindow != null && !popupWindow.isShowing()) {
//                    startAnimation(raw, 0, 180);
                    raw.setImageResource(R.mipmap.up);
                    textView.setBackgroundResource(R.drawable.item_pressed_true);
                    popupWindow.showAsDropDown(textView, 0, 50, Gravity.START);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (data != null) {
                    textView.setText(data.get(position));
                    if (popupWindow != null) popupWindow.dismiss();
                }
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                textView.setEnabled(true);
//                startAnimation(raw, 180, 0);
                raw.setImageResource(R.mipmap.down);
                textView.setBackgroundResource(R.drawable.item_pressed_false);
            }
        });

        return this;
    }


    public CustomSpinner setListViewData(List<String> data) {
        this.data = data;
        if (data != null && data.size() > 0) {
            textView.setText(data.get(0));
        }
        setAdapter();
        return this;
    }

    private CustomSpinner setAdapter() {
        if (listView != null) {
            listView.setAdapter(new MyAdapter(this.data));
        }
        return this;
    }

    public CustomSpinner setCustomAdapter() {

        return this;
    }


    class MyAdapter extends BaseAdapter {

        private List<String> data;

        public MyAdapter(List<String> data) {
            this.data = data;
        }

        public void refersh(List<String> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, viewGroup, false);
                holder.itemRoot = convertView.findViewById(R.id.item_root);
                holder.itemTv = (TextView) convertView.findViewById(R.id.item_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String str = data.get(position);
            holder.itemTv.setText(str);

            return convertView;
        }
    }


    class ViewHolder {
        public TextView itemTv;
        public View itemRoot;
    }

//    private void startAnimation(View view, float start, float end) {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "RotationX", start, end);
//        animator.setDuration(2000);
//        animator.start();
//    }

}
