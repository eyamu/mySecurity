package com.bit15_21.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bit15_21.mysecurity.R;

import java.util.ArrayList;

/**
 * Created by eyamu on 5/5/15.
 */
public class DisplayHelper extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> id;
    private ArrayList<String> firstName;
    private ArrayList<String> lastName;
    private ArrayList<String> phone;


    public DisplayHelper(Context c, ArrayList<String> id,ArrayList<String> fName, ArrayList<String> phone) {
        this.mContext = c;

        this.id = id;
        this.firstName = fName;
        this.phone = phone;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return id.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int pos, View child, ViewGroup parent) {
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.list_cell, null);
            mHolder = new Holder();
            mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
            mHolder.txt_fName = (TextView) child.findViewById(R.id.txt_fName);
            mHolder.txt_lName = (TextView) child.findViewById(R.id.txt_lName);
            mHolder.txt_phone = (TextView) child.findViewById(R.id.txt_phone);
            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        mHolder.txt_id.setText(id.get(pos));
        mHolder.txt_fName.setText(firstName.get(pos));
        mHolder.txt_lName.setText(lastName.get(pos));
        mHolder.txt_phone.setText(phone.get(pos));

        return child;
    }

    public class Holder {
        TextView txt_id;
        TextView txt_lName;
        TextView txt_fName;
        TextView txt_phone;
    }

}
