package com.example.moneybuddyv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TakeTransaction extends AppCompatActivity {

    EditText amount ;
    CalendarView calendarView ;
    Spinner spinnerCategory;
    EditText note;
    long selectedDateInMillis;

    private String formatDate(long dateInMillis) {
        // Create a SimpleDateFormat object with your desired date pattern
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Convert the dateInMillis to a Date object
        Date date = new Date(dateInMillis);

        // Format the Date object to a String
        return dateFormat.format(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_transaction);

        // Get a reference to the spinner
        spinnerCategory = findViewById(R.id.spinnerCategory);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.category_options, // Use the array defined in your strings.xml
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerCategory.setAdapter(adapter);

        calendarView = findViewById(R.id.date);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Convert the selected date to milliseconds
                Calendar selectedCalendar = new GregorianCalendar(year, month, dayOfMonth);
                selectedDateInMillis = selectedCalendar.getTimeInMillis();
            }
        });
    }


    public void addToDataBase(View v) {

        Log.d("myAPP", "add to database funct called " );



        // Retrieve the selected date from CalendarView


        String tempDate = formatDate(selectedDateInMillis);

        Log.d("myAPP", "addToDataBase: selected date is " + tempDate);

        //add the data to the transaction table
        amount = findViewById(R.id.amount);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        note = findViewById(R.id.note);

        //arranging values for the constructor

        //amount
        String amountString = amount.getText().toString();
        double amt;
        if (!amountString.isEmpty())
            amt = Double.parseDouble(amountString);
        else
            amt = 0.0;
        Log.d("myAPP", "addToDataBase: amnt is " + amt);

        //date
//        calendarView = findViewById(R.id.date);
//        long dateInMillis = calendarView.getDate();
//        String tempDate = formatDate(dateInMillis);


        int cat = 1;
        //categories
        //1 Not Mentioned
        //2 Academic
        //3 Entertainment
        //4 Rent
        //5 Shopping

        String tempCat = spinnerCategory.getSelectedItem().toString();


        if(tempCat.equals("Academic"))
            cat = 2;
        else if (tempCat.equals("Entertainment"))
            cat = 3;
        else if (tempCat.equals("Rent"))
            cat = 4;
        else
            cat = 5;

        String noted = note.getText().toString();

        //creating the object of .transaction
        transaction t = new transaction(amt,tempDate,cat,noted);


        //adding the transaction to the database
        DBhandler db = new DBhandler(this, "Transactions", null, 1);
        db.addTran(t);

        Toast.makeText(this, "Transaction Added "+tempDate, Toast.LENGTH_SHORT).show();


        //change the intent to the main activity
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
