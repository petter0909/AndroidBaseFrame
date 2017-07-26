package com.third.party.library.views.PulleyView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.third.party.library.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dell on 2016/12/16.
 */
public class TypeAdapter extends BaseAdapter {


    private Context context;
    private List<String> list = new ArrayList<String>();

    public TypeAdapter(List<String> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size() ;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i) ;
    }

    @Override
    public long getItemId(int i) {
        return i ;
    }

    public String indexOf(Object o) {
        return (String) o;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (null == convertView) {
            convertView =  View.inflate(context, R.layout.type_item,null);
        } else {

        }
        TextView tv = (TextView)convertView.findViewById(R.id.tv);
        tv.setText(list.get(position));

        return convertView;
    }
}
