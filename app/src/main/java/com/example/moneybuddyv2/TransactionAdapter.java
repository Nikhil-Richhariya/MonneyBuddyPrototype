package com.example.moneybuddyv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TransactionAdapter extends ArrayAdapter<transaction> {
//    public TransactionAdapter(@NonNull Context context, int resource, @NonNull transaction[] objects) {
//        super(context, resource, objects);
//    }

    public TransactionAdapter(Context context, List<transaction> transactions) {
        super(context, 0, transactions);
    }

    @NonNull

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        transaction transaction = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_item, parent, false);
        }

        // Populate the views in your custom layout (transaction_item.xml) with data from the Transaction object
        // For example:
        TextView amountTextView = convertView.findViewById(R.id.amountTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);

        amountTextView.setText(String.valueOf(transaction.getAmount()));
        dateTextView.setText(transaction.getDate());

        return convertView;
    }
}
