package com.donghh.studentmanagement.ui.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.donghh.studentmanagement.R;
import com.donghh.studentmanagement.entity.Department;

import java.util.List;

class SpinnerSexAdapter extends BaseAdapter {

    private List<String> list;

    private Context context;

    public SpinnerSexAdapter(Context context,
                             List<String> list) {
        this.context = context;
        this.list = list;
        ;
    }

    @Override
    public int getCount() {
        if (this.list == null) {
            return 0;
        }
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
        // return position; (Return position if you need).
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_department_select, parent, false);
        AppCompatTextView label= view.findViewById(R.id.tvDepartmentName);
        label.setText(list.get(position));
        return view;
    }
}