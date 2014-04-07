package com.example.m3;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Deposit activity class.
 * 
 * @author Tripp
 * @version 1.0
 */
public class DepositActivity extends Activity {
    
    /**
     * Authentication token string instance variable.
     */
    private String authToken;
    
    /**
     * String accountID.
     */
    private String accountId;
    
    /**
     * Button depositButton.
     */
    private Button depositButton;
    
    /**
     * EditText source.
     */
    private EditText source;
    
    /**
     * EditText effectiveDate.
     */
    private EditText effectiveDate;
    
    /**
     * EditText amount.
     */
    
    private EditText amount;
    
    /**
     * String of the word auth_token.
     */
    private String authTokenString;
    
    /**
     * String of the word accound_id.
     */
    private String accountIdString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        Intent oldIntent = getIntent();
        authTokenString = "auth_token";
        accountIdString = "account_id";
        authToken = oldIntent.getStringExtra(authTokenString);
        accountId = oldIntent.getStringExtra(accountIdString);
        addListenerOnCreateButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.deposit, menu);
        return true;
    }
    
    /**
     * Update text view method.
     * 
     * @param newStr new string
     */
    public void updateTextView(String newStr) {
        TextView textView = (TextView) findViewById(R.id.errors);
        textView.setText(newStr);
    }
    
    /**
     * Adds button listener on buttons.
     */
    public void addListenerOnCreateButton() {
         
        final Context context = this;
 
        depositButton = (Button) findViewById(R.id.depositButton);
 
        depositButton.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                source   = (EditText) findViewById(R.id.source_input);
                effectiveDate   = (EditText) findViewById(R.id.effective_date_input);
                amount   = (EditText) findViewById(R.id.amount_input);
                String sourceStr = source.getText().toString();
                String effectiveDateStr = effectiveDate.getText().toString();
                String amountStr = amount.getText().toString();
                try {
                    String[] inputs = {sourceStr, effectiveDateStr, amountStr, accountId, authToken};
                    HttpResponse response = new DatabaseInterface().deposit(inputs);
                    if (response.getStatusLine().getStatusCode() == 201 ) {
                        Intent intent = new Intent(context, TransPageActivity.class);
                        intent.putExtra(authTokenString, authToken);
                        intent.putExtra(accountIdString, accountId);
                        startActivity(intent); 
                    } else {
                        updateTextView(EntityUtils.toString(response.getEntity()));
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
 
        });
    
    }

}
