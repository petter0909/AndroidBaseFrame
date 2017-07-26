package com.third.party.library.views.PulleyView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.third.party.library.R;

import java.util.ArrayList;
import java.util.List;


public class WatchTypeView extends BasePickerView implements View.OnClickListener {

    WheelWatchType wheelTime;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectListener timeSelectListener;



    private List<String> strings = new ArrayList<>();


    public WatchTypeView(Context context, int type) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pickerview_type, contentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----时间转轮
        final View timepickerview = findViewById(R.id.timepicker);
        wheelTime = new WheelWatchType(timepickerview, type);
        strings.clear();
        if (type == 1) {
            strings.add("保密");
            strings.add("男");
            strings.add("女");
        } else {
            strings.add("保密");
            for (int i=0; i<51; i++) {
                strings.add(i+"");
            }
        }
        wheelTime.setPicker(context,strings);

    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (timeSelectListener != null) {
                String string = wheelTime.getTime();
                timeSelectListener.onTimeSelect(string);
            }
            dismiss();
            return;
        }
    }

    public interface OnTimeSelectListener {
        public void onTimeSelect(String string);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }
}
