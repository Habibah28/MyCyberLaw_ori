package com.mycyberlaw;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Habibah on 11-Oct-16.
 */
public class AdapterMsg extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    List<DataMsg> data= Collections.emptyList();
    DataMsg current;
    int currentPos=0;

    public AdapterMsg(Context context, List<DataMsg> results) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.data = results;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.card_view_row, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        DataMsg current=data.get(position);
        myHolder.textViewSubject.setText(current.subject);
        myHolder.textViewMsg.setText(current.msg);
        myHolder.textViewName.setText(current.name + ", "+current.record);    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView textViewSubject;
        TextView textViewMsg;
        TextView textViewName;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textViewSubject = (TextView) itemView.findViewById(R.id.textViewSubject);
            textViewMsg = (TextView) itemView.findViewById(R.id.textViewMsg);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        }

    }
}
