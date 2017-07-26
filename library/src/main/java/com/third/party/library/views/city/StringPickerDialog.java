package com.third.party.library.views.city;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

import com.third.party.library.R;

import java.util.ArrayList;
import java.util.List;

public class StringPickerDialog extends Dialog {

    private final static int DEFAULT_ITEMS = 5;
    private final static int UPDATE_CITY_WHEEL = 11;
    private final static int UPDATE_COUNTY_WHEEL = 12;

    private Activity mContext;

    private ArrayList<String> objectList;
    AbstractWheelTextAdapter objectWheelAdapter;
    WheelView objectWheel;

    public static interface OnStringPickedListener {
        public void onPicked(String string, int currentItem);

    }

    private OnStringPickedListener mlistener;
    public StringPickerDialog(Activity context, final List<String> object, OnStringPickedListener listener) {
        super(context);
        mlistener = listener;
        mContext = context;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        getWindow().setWindowAnimations(R.style.AnimBottom);
        View rootView = getLayoutInflater().inflate(R.layout.dialog_string_picker, null);
        int screenWidth = mContext.getWindowManager().getDefaultDisplay().getWidth();
        LayoutParams params = new LayoutParams(screenWidth, LayoutParams.MATCH_PARENT);
        super.setContentView(rootView, params);
        objectList = new ArrayList<>(object);

        initViews();

        View done = findViewById(R.id.btnSubmit);
        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mlistener != null) {
                    String obj = object.size() > 0 ? object.get(objectWheel.getCurrentItem()) : null;
                    mlistener.onPicked(obj,objectWheel.getCurrentItem());
                }
                dismiss();
            }
        });

        View cancel = findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    private void initViews() {

        objectWheel = (WheelView) findViewById(R.id.stringWheel);

        objectWheelAdapter = new AbstractWheelTextAdapter(mContext, R.layout.wheel_text) {

            @Override
            public int getItemsCount() {

                return objectList.size();
            }

            @Override
            protected CharSequence getItemText(int index) {

                return objectList.get(index).toString();
            }
        };

        if (objectWheel != null) {
            objectWheel.setViewAdapter(objectWheelAdapter);
            objectWheel.setCyclic(false);
            objectWheel.setVisibleItems(DEFAULT_ITEMS);
        }


        OnWheelClickedListener clickListener = new OnWheelClickedListener() {

            @Override
            public void onItemClicked(WheelView wheel, int itemIndex) {
                if (itemIndex != wheel.getCurrentItem()) {
                    wheel.setCurrentItem(itemIndex, true, 500);
                }
            }
        };

        if (objectWheel != null) {
            objectWheel.addClickingListener(clickListener);
        }


    }

}
