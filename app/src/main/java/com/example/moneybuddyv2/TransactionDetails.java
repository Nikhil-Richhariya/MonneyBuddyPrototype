package com.example.moneybuddyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TransactionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        // Retrieve transaction details from the Intent

        Intent intent = getIntent();

        // Get transaction details from the intent
        double transactionAmount = intent.getDoubleExtra("TRANSACTION_AMOUNT", 0.0);
        String transactionDate = intent.getStringExtra("TRANSACTION_DATE");
        String transactionNote = intent.getStringExtra("TRANSACTION_NOTE");
        int transactionCategory = intent.getIntExtra("TRANSACTION_CATEGORY", 1);

        // Display transaction details in TextViews
        TextView amountTextView = findViewById(R.id.amountTextView);
        amountTextView.setText("Amount : "+Double.toString(transactionAmount));

        TextView dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText("Date : "+transactionDate);

        TextView categoryTextView = findViewById(R.id.textViewC);
        String cat = "";
        //categories
        //1 Not Mentioned
        //2 Academic
        //3 Entertainment
        //4 Rent
        //5 Shopping
        switch (transactionCategory) {
            case 1: cat = "Not Mentioned Category";
                break;
            case 2: cat = "Academic";
                break;
            case 3: cat = "Entertainment";
                break;
            case 4: cat = "Rent";
                break;
            case 5: cat = "Shopping";
                break;
        }
        categoryTextView.setText(cat);

        TextView noteTextView = findViewById(R.id.noteTextView);
        noteTextView.setText("Note: " + transactionNote);

        // Add other TextViews or UI elements for additional transaction details

    }
}