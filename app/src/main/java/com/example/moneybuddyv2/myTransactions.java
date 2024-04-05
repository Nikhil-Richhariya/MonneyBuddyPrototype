package com.example.moneybuddyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class myTransactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transactions);
        displayTransactions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTransactions();
    }

    private void displayTransactions() {
        DBhandler db = new DBhandler(this, "Transactions", null, 1);




        // Get the current date in dd-MM-yyyy format
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
//
//        // Extract the month part from the current date
//        String currentMonth = currentDate.substring(3, 5);

        //Entering the total amount spent :
//        double total = db.getTotalExpenseForMonth(currentMonth);
//        String totalAmt = Double.toString(total);


        // Get transactions for the current month
        List<transaction> transactionList = db.getAllTransactions();
//        List<transaction> transactionList = db.getTransactionsForMonth(currentMonth);


        Log.d("mytagList", "Transaction count: " + transactionList.size());


        // Assuming you have a ListView with the id "transactionListView" in your activity_main.xml
        ListView transactionListView = findViewById(R.id.Tlist2);



        transactionListView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected transaction
            transaction selectedTransaction = (transaction) parent.getItemAtPosition(position);

            // Create an Intent to start TransactionDetails activity
            Intent detailsIntent = new Intent(myTransactions.this, TransactionDetails.class);

            // Pass transaction details to TransactionDetails activity
            detailsIntent.putExtra("TRANSACTION_AMOUNT", selectedTransaction.getAmount());
            detailsIntent.putExtra("TRANSACTION_DATE", selectedTransaction.getDate());
            detailsIntent.putExtra("TRANSACTION_NOTE", selectedTransaction.getNote());
            detailsIntent.putExtra("TRANSACTION_CATEGORY", selectedTransaction.getCategory());

            // Start the TransactionDetails activity
            startActivity(detailsIntent);
        });

        // Create a custom adapter and set it on the ListView
        TransactionAdapter adapter = new TransactionAdapter(this, transactionList);
        transactionListView.setAdapter(adapter);
    }
}