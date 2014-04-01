package com.example.m3;

import java.io.IOException;

import java.util.concurrent.ExecutionException;
import org.apache.http.HttpResponse;

import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * This class represents the withdrawal activity.
 * @author tripproberts
 *
 */

public class WithdrawalActivity extends Activity {
	
	/**
	 * String value of the auth token.
	 */
	
    private String authToken;
    
	/**
	 * String value of the account id.
	 */
    
    private String accountId;
    
    /**
     * The withdrawal button. 
     */
    
    private Button withdrawalButton;
    
    /**
     * The edit text for the reason.
     */
    
    private EditText reason;
    
    /**
     * The spinner for the category.
     */
    
    private Spinner category;
    
    /**
     * The date picker for the effective date.
     */
    
    private DatePicker effectiveDate;
    
    /**
     * The edit text for the amount.
     */
    
    private EditText amount;
    
    /**
     * String value for the word auth_token.
     */
    
    private String authTokenString;
    
    /**
     * String value for the word account_id.
     */
    
    private String accountIdString;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        effectiveDate   = (DatePicker) findViewById(R.id.startDatePicker);
        effectiveDate.setCalendarViewShown(false);
        Intent oldIntent = getIntent();
        authToken = oldIntent.getStringExtra(authTokenString);
        accountId = oldIntent.getStringExtra(accountIdString);
        addListenerOnCreateButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.withdrawal, menu);
        return true;
    }
    
    /**
     * Update the text view with errors.
     * @param newStr The errors to display
     */
    
    public void updateTextView(String newStr) {
        TextView textView = (TextView) findViewById(R.id.errors);
        textView.setText(newStr);
    }
    
    /**
     * Adds listener to withdrawal button.
     */
    
    public void addListenerOnCreateButton() {
         
        final Context context = this;
 
        withdrawalButton = (Button) findViewById(R.id.withdrawalButton);
 
        withdrawalButton.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                reason   = (EditText) findViewById(R.id.reason_input);
                category   = (Spinner) findViewById(R.id.spinner1);
                amount   = (EditText) findViewById(R.id.amount_input);
                String reasonStr = reason.getText().toString();
                String categoryStr = category.getSelectedItem().toString();
                int day = effectiveDate.getDayOfMonth();
                int month = effectiveDate.getMonth() + 1;
                int year = effectiveDate.getYear();
                String dateDelimeter = "/";
                String date = day + dateDelimeter + month + dateDelimeter + year;
                String amountStr = amount.getText().toString();

                try {
                    HttpResponse response = new DatabaseInterface().withdraw(reasonStr, categoryStr, date, amountStr, accountId, authToken);
                    if ( response.getStatusLine().getStatusCode() == 201 ) {
                        Intent intent = new Intent(context, TransPageActivity.class);
                        intent.putExtra(authTokenString, authToken);
                        intent.putExtra(accountIdString, accountId);
                        startActivity(intent); 
                    } else {
                        updateTextView(EntityUtils.toString(response.getEntity()));
                    }
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                
            }
 
        });
    
    }

}
