package com.example.newagesafety;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

//made for customizing the adapter
public class ListAdapter extends ArrayAdapter
{
    List list=new ArrayList();
    public ListAdapter(@NonNull  Context context,int resource)
    {
        super(context,resource);
    }
    static  class LayoutHandler
    {
        TextView NAME,NUMBER;
    }

    public void add(Object object)
    {
        super.add(object);
        list.add(object);
    }

    public int getCount()
    {
        return list.size();
    }

    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //return list
    public List getList()
    {
        return list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.customize_adapter, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.NAME = (TextView) row.findViewById(R.id.name);//id from customize_adapter
            layoutHandler.NUMBER = (TextView) row.findViewById(R.id.number);
            row.setTag(layoutHandler);
        } else {
            layoutHandler = (LayoutHandler) row.getTag();
        }

        DataProvider dataProvider = (DataProvider) this.getItem(position);
        layoutHandler.NAME.setText(dataProvider.getName());
        layoutHandler.NUMBER.setText(dataProvider.getNumber());

        return row; //super.getView(position, convertView, parent)

    }

}

