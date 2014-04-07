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
 * Activity class that represents account creation.
 * 
 * @author Tripp Roberts
 * @version 1.0
 *
 */
public class AccountCreationActivity extends Activity {
    
    /**
    * Button registerButton.
    */
    Button registerButton;
    
    /**
    * Button backButton.
    */
    Button backButton;
    
    /**
     * EditText name.
     */
    EditText name;
    
    /**
     * EditText accountName.
     */
    EditText accountName;
    
    /**
     * EditText balance.
     */
    EditText balance;
    
    /**
     * EditText interestRate.
     */
    EditText interestRate;
    
    /**
     * String authToken.
     */
    String authToken;
    
    /**
     * String of the word auth_token.
     */
    String authTokenString;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent oldIntent = getIntent();
        authTokenString = "auth_token";
        authToken = oldIntent.getStringExtra(authTokenString);
        setContentView(R.layout.activity_account_creation);
        addListenerOnCreateButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_creation, menu);
        return true;
    }
    
    /**
     * update text views.
     * 
     * @param newStr new string for update
     */
    public void updateTextView(String newStr) {
        TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setText(newStr);
    }
    
    
    /**
     * Adds button listener on button.
     */
    public void addListenerOnCreateButton() {

        final Context context = this;

        registerButton = (Button) findViewById(R.id.depositButton);
        
        backButton = (Button) findViewById(R.id.backButton);

        registerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                name   = (EditText) findViewById(R.id.editText1);
                accountName   = (EditText) findViewById(R.id.editText2);
                balance   = (EditText) findViewById(R.id.editText3);
                interestRate   = (EditText) findViewById(R.id.editText4);
                String nameStr = name.getText().toString();
                String accountNameStr = accountName.getText().toString();
                String balanceDouble = balance.getText().toString();
                String interestRateDouble = interestRate.getText().toString();

                try {
                    String[] inputs = {nameStr, accountNameStr, balanceDouble, interestRateDouble, authToken};
                    HttpResponse response = new DatabaseInterface().createAccount(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4]);
                    if ( response.getStatusLine().getStatusCode() == 201 ) {
                        Intent intent = new Intent(context, AccountsOverviewActivity.class);
                        intent.putExtra(authTokenString, authToken);
                        startActivity(intent); 
                    }
                    else {
                        try {
                            updateTextView(EntityUtils.toString(response.getEntity()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                }

            }

        });
        
        backButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, AccountsOverviewActivity.class);
                intent.putExtra(authTokenString, authToken);
                startActivity(intent);
            }
        });
    }

}
