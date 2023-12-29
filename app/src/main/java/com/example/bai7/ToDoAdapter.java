package com.example.bai7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoAdapter extends BaseAdapter {
    Context context;
        ArrayList<ToDo> listToDo ;

    public ToDoAdapter(Context context, ArrayList<ToDo> listToDo) {
        this.context = context;
        this.listToDo = listToDo;
    }

    @Override
    public int getCount() {
        return listToDo.size();
    }

    @Override
    public Object getItem(int position) {
        return listToDo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.item_activity,parent, false);
        TextView TvTitle = convertView.findViewById(R.id.txttitle);
        TextView TvContent = convertView.findViewById(R.id.txtcontent);
        TextView TvDate = convertView.findViewById(R.id.txtdate);
        ToDo  toDo = listToDo.get(position);
        TvTitle.setText(toDo.getTitle());
        TvContent.setText(toDo.getContent());
        TvDate.setText(toDo.getDate());

        return convertView;
    }
}
