package com.example.moneybuddyv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.moneybuddyv2.transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBhandler extends SQLiteOpenHelper {

    public DBhandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE transactions (sno INTEGER PRIMARY KEY, amount REAL,category INTEGER , date TEXT, note TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String drop = "DROP TABLE IF EXISTS transactions";
        db.execSQL(drop);
        onCreate(db);
    }
//    private String formatDateForDatabase(String inputDate) {
//        try {
//            // Parse the input date string using the current format
//            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//            Date date = inputFormat.parse(inputDate);
//
//            // Format the date using the desired format for the database
//            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//            return outputFormat.format(date);
//        } catch (ParseException e) {
//            // Handle parsing exception if needed
//            e.printStackTrace();
//            return inputDate; // Return the original date in case of an error
//        }
//    }
    public void addTran(transaction t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sno", t.getSno());
        values.put("amount", t.getAmount());
        values.put("date", t.getDate());
        values.put("category", t.getCategory());
        values.put("note", t.getNote());

        long k = db.insert("transactions", null, values);
        Log.d("mytag", Long.toString(k));
        db.close();
    }

    public void getTran(int sno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "transactions",
                new String[]{"sno", "amount", "date", "note"},
                "sno=?",
                new String[]{String.valueOf(sno)},
                null,
                null,
                null
        );

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int amountIndex = cursor.getColumnIndex("amount");
                int dateIndex = cursor.getColumnIndex("date");

                if (amountIndex != -1) {
                    Log.d("mytag", "Amount: " + cursor.getString(amountIndex));
                } else {
                    Log.d("mytag", "Amount column not found");
                }

                if (dateIndex != -1) {
                    Log.d("mytag", "Date: " + cursor.getString(dateIndex));
                } else {
                    Log.d("mytag", "Date column not found");
                }
            } else {
                Log.d("mytag", "No transaction found with sno: " + sno);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public List<transaction> getAllTransactions() {
        List<transaction> transactionList = new ArrayList<>();
        String selectQuery = "SELECT * FROM transactions";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int snoIndex = cursor.getColumnIndex("sno");
                    int amountIndex = cursor.getColumnIndex("amount");
                    int dateIndex = cursor.getColumnIndex("date");
                    int categoryIndex = cursor.getColumnIndex("category");
                    int noteIndex = cursor.getColumnIndex("note");

                    if (snoIndex != -1 && amountIndex != -1 && dateIndex != -1 && categoryIndex != -1 && noteIndex != -1) {
                        int sno = cursor.getInt(snoIndex);
                        double amount = cursor.getDouble(amountIndex);
                        String date = cursor.getString(dateIndex);
                        int category = cursor.getInt(categoryIndex);
                        String note = cursor.getString(noteIndex);

                        transaction t = new transaction(amount, date, category, note);
                        Log.d("sqlCheck","date is "+date);

                        transactionList.add(t);
                    } else {
                        Log.d("mytag", "Column index not found in cursor");
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return transactionList;
    }


    public List<transaction> getTransactionsForMonth(String month) {
        List<transaction> transactionList = new ArrayList<>();
        String selectQuery;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        if (month != null && !month.isEmpty()) {
            // If a specific month is provided, filter by month
            String formattedMonth = String.format("%02d-%04d", Integer.parseInt(month), Calendar.getInstance().get(Calendar.YEAR));
            selectQuery = "SELECT * FROM transactions WHERE date LIKE ?";
            cursor = db.rawQuery(selectQuery, new String[]{"%" + formattedMonth});
        } else {
            // If no specific month is provided, fetch all transactions
            selectQuery = "SELECT * FROM transactions";
            cursor = db.rawQuery(selectQuery, null);
        }

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int snoIndex = cursor.getColumnIndex("sno");
                    int amountIndex = cursor.getColumnIndex("amount");
                    int dateIndex = cursor.getColumnIndex("date");
                    int categoryIndex = cursor.getColumnIndex("category");
                    int noteIndex = cursor.getColumnIndex("note");

                    if (snoIndex != -1 && amountIndex != -1 && dateIndex != -1 && categoryIndex != -1 && noteIndex != -1) {
                        int sno = cursor.getInt(snoIndex);
                        double amount = cursor.getDouble(amountIndex);
                        String date = cursor.getString(dateIndex);
                        int category = cursor.getInt(categoryIndex);
                        String note = cursor.getString(noteIndex);

                        transaction t = new transaction(amount, date, category, note);
                        transactionList.add(t);
                    } else {
                        Log.d("mytag", "Column index not found in cursor");
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return transactionList;
    }




    public double getTotalExpenseForMonth(String month) {
        double totalExpense = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        // Format the month as MM-yyyy
        String formattedMonth = String.format("%02d-%04d", Integer.parseInt(month), Calendar.getInstance().get(Calendar.YEAR));
        Log.d("monthE",formattedMonth);
        // SQL query to get the total expense for the specified month
        String selectQuery = "SELECT SUM(amount) as totalExpense FROM transactions WHERE date LIKE ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + formattedMonth});

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int totalExpenseIndex = cursor.getColumnIndex("totalExpense");
                if (totalExpenseIndex != -1) {
                    totalExpense = cursor.getDouble(totalExpenseIndex);
                    Log.d("mytag", "Total Expense for month " + month + ": " + totalExpense);
                } else {
                    Log.d("mytag", "Total Expense column not found");
                }
            } else {
                Log.d("mytag", "No transactions found for the specified month");
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return totalExpense;
    }








}
