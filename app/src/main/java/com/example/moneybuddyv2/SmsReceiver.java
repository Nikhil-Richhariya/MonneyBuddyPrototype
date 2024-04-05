package com.example.moneybuddyv2;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            for (Object pdu : pdus) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                String messageBody = smsMessage.getMessageBody();

                // Check if the message indicates a debit transaction
                if (messageBody.toLowerCase().contains("debited by")) {
                    // Extract amount
                    double amount = extractAmount(messageBody);

                    // Get the current date
                    String currentDate = getCurrentDate();

                    // Create a Transaction object
                    transaction tran = new transaction(amount, currentDate);

                    Log.d("SMSLOG","Message received : "+messageBody);

                    // Call a method to add the transaction to the database or perform other actions
                    addToDatabase(context, tran);
                }
            }
        }
    }

    private static double extractAmount(String messageBody) {
        // Define the pattern to match "Rs" followed by a decimal number
        Pattern pattern = Pattern.compile("Rs(\\d+(\\.\\d+)?)");

        // Create a matcher with the input string
        Matcher matcher = pattern.matcher(messageBody);

        // Check if the pattern is found
        if (matcher.find()) {
            // Extract the matched decimal number
            String amountString = matcher.group(1);

            // Parse the extracted string to a double
            return Double.parseDouble(amountString);
        } else {
            // Return a default value or throw an exception based on your requirements
            return 0.0; // Default value, change as needed
        }
    }

    private String getCurrentDate() {
        // Get the current date in a specific format (e.g., "dd-MM-yyyy HH:mm:ss")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private void addToDatabase(Context context, transaction t) {
        // Use the application context to avoid potential memory leaks
        DBhandler db = new DBhandler(context.getApplicationContext(), "Transactions", null, 1);
        db.addTran(t);
    }
}


