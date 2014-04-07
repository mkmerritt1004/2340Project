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
 * This class represents a registration activity.
 * @author tripproberts
 *
 */

public class RegistrationActivity extends Activity {
    
	/**
	 * This is the register button.
	 */
	
    Button registerButton;
    
    /**
     * This is the edit box for the name.
     */
    
    EditText name;
    
    /**
     * This is the edit box for the email.
     */
    
    EditText email;
    
    /**
     * This is the edit box for the password.
     */
    
    EditText password;
    
    /**
     * This is the edit box for the password confirmation.
     */
    
    EditText passwordConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration, menu);
        return true;
    }

    /**
     * This updates a text view with a string.
     * @param newStr The new string
     */
    
    public void updateTextView(String newStr) {
        TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setText(newStr);
    }
    
    /**
     * Adds a listener to the register button.
     */
    
    public void addListenerOnButton() {
         
        final Context context = this;
 
        registerButton = (Button) findViewById(R.id.depositButton);
 
        registerButton.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                name   = (EditText) findViewById(R.id.editText1);
                email   = (EditText) findViewById(R.id.editText2);
                password   = (EditText) findViewById(R.id.editText3);
                passwordConfirmation   = (EditText) findViewById(R.id.editText4);
                String nameStr = name.getText().toString();
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                String passwordConfirmationStr = passwordConfirmation.getText().toString();

                try {
                    String[] inputs = {nameStr, emailStr, passwordStr, passwordConfirmationStr};
                    HttpResponse response = new DatabaseInterface().registerUser(inputs);
                    if ( response.getStatusLine().getStatusCode() == 201 ) {
                        try {
                            String stringResponse = EntityUtils.toString(response.getEntity());
                            String[] splitResponse = stringResponse.split(":");
                            String authToken = splitResponse[3].substring(1, splitResponse[3].length() - 2);
                            Intent intent = new Intent(context, SuccessScreenActivity.class);
                            intent.putExtra("auth_token", authToken);
                            startActivity(intent); 
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            updateTextView(EntityUtils.toString(response.getEntity()));
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
 
    }
}
