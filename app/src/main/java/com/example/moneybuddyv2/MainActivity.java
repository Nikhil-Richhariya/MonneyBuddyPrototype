package com.example.moneybuddyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView t;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t = findViewById(R.id.spentAmount);
        add = findViewById(R.id.button);

        //display the transactions from the database into the list view of this month
        Intent intent2 = getIntent();
        displayTransactions();

    }

    protected void onResume() {
        super.onResume();
        Log.d("myLOG", "onResume called");

        displayTransactions();
    }

    public void addTran(View v) {
        Intent intent = new Intent(this,TakeTransaction.class);
        startActivity(intent);
    }

    private void displayTransactions() {
        DBhandler db = new DBhandler(this, "Transactions", null, 1);




        // Get the current date in dd-MM-yyyy format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());

        // Extract the month part from the current date
        String currentMonth = currentDate.substring(3, 5);

        //Entering the total amount spent :
        double total = db.getTotalExpenseForMonth(currentMonth);
        String totalAmt = Double.toString(total);

        TextView spentAmount = findViewById(R.id.spentAmount);
        spentAmount.setText(totalAmt);

        // Get transactions for the current month
//        List<transaction> transactionList = db.getTransactionsForMonth(currentMonth);
        List<transaction> transactionList = db.getTransactionsForMonth(currentMonth);


        Log.d("mytagList", "Transaction count: " + transactionList.size());


        // Assuming you have a ListView with the id "transactionListView" in your activity_main.xml
        ListView transactionListView = findViewById(R.id.Tlist);



        transactionListView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected transaction
            transaction selectedTransaction = (transaction) parent.getItemAtPosition(position);

            // Create an Intent to start TransactionDetails activity
            Intent detailsIntent = new Intent(MainActivity.this, TransactionDetails.class);

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


    public void allTransAct(View v)
    {
        Intent intent3 = new Intent(this,myTransactions.class);
        startActivity(intent3);
    }



}