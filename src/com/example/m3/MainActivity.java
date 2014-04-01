package com.example.m3;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;

/**
 * This class represents the activity for logging in.
 *
 */

public class MainActivity extends Activity {
    
    /**
     * Login button.
     */
    
    Button loginButton;
    
    /**
     * Edit box containing username.
     */
    
    EditText userEdit;
    
    /**
     * Edit box containing password.
     */
    
    EditText passEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * Updates a specific text view on the activity.
     * @param newStr New string to be added.
     */
    
    public void updateTextView(String newStr) {

        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText(newStr);
    }
    
    /**
     * Adds listener to login button.
     */
    
    public void addListenerOnButton() {
         
        final Context context = this;
 
        loginButton = (Button) findViewById(R.id.loginButton);
 
        loginButton.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                userEdit   = (EditText) findViewById(R.id.editText1);
                passEdit   = (EditText) findViewById(R.id.editText2);
                String userEditStr = userEdit.getText().toString();
                String passEditStr = passEdit.getText().toString();

                try {
                    HttpResponse response = new DatabaseInterface().login(userEditStr, passEditStr);
                    if ( response.getStatusLine().getStatusCode() == 200 ) {
                        String stringResponse = EntityUtils.toString(response.getEntity());
                        JSONObject json = new JSONObject(stringResponse);
                        String authTokenStr = "auth_token";
                        String authToken = json.getString(authTokenStr);
                        Intent intent = new Intent(context, SuccessScreenActivity.class);
                        intent.putExtra(authTokenStr, authToken);
                        startActivity(intent); 
                    }
                    else {
                        updateTextView("Incorrect Username or Password. Try Again.");
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                } catch (org.apache.http.ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                
            }
 
        });
    
    }

}
