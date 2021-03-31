package com.example.expensetracker;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter{

    private ArrayList<Transaction> transactionData;
    private boolean isDeleting;
    private Context parentContext;
    private View.OnClickListener mOnItemClickListener;
    MainActivity mainAct;

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView transName;
        public TextView transAmount;
        public TextView transDate;
        public Button deleteButton;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            transName = itemView.findViewById(R.id.transAmount);
            transAmount = itemView.findViewById(R.id.transName);
            transDate = itemView.findViewById(R.id.transDate);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getTransName() {return transName;}
        public TextView getTransAmount() {return transAmount;}
        public TextView getTransDate() {return transDate;}
        public Button getDeleteButton() {return deleteButton;}
    }

    public TransactionAdapter(ArrayList<Transaction> arrayList, Context context, MainActivity mainActivity) {
        transactionData = arrayList;
        parentContext = context;
        mainAct = mainActivity;
    }

    public void setmOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // this class is required for for RecyclerView.adapter. This method is called for each item in the data set

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false); //to be displayed

        return new TransactionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        TransactionViewHolder tvh = (TransactionViewHolder) holder;

        tvh.getTransName().setText("$" + Float.toString(transactionData.get(position).getAmount()));
        tvh.getTransAmount().setText(transactionData.get(position).getType());
        tvh.getTransDate().setText((DateFormat.format("MM/dd/yyyy", transactionData.get(position).getDate())));

        if (isDeleting) {
            tvh.getDeleteButton().setVisibility(View.VISIBLE);
            tvh.getDeleteButton().setOnClickListener(view -> {
                deleteItem(position);
            });
            mainAct.initCurrentBalance();

        }
        else {
            tvh.getDeleteButton().setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return transactionData.size();
    }

    public void setDelete(boolean b) {
        isDeleting = b;
    }

    private void deleteItem(int position) {
        Transaction transaction = transactionData.get(position);
        TransactionDataSource ds = new TransactionDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteTransaction(transaction.getTransactionID());
            ds.close();
            if (didDelete) {
                transactionData.remove(position);
                notifyDataSetChanged();

            }
            else {
                Toast.makeText(parentContext, "Delete Failed :(", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(parentContext, "Delete Failed! :(", Toast.LENGTH_LONG).show();
        }


    }
}
