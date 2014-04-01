package com.example.m3;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class represents the transaction page activity.
 * @author tripproberts
 *
 */

public class TransPageActivity extends Activity {
	
	/**
	 * The deposit button.
	 */
	
    Button deposit;
    
    /**
     * The withdrawal button.
     */
    
    Button withdrawal;
    
    /**
     * The back button.
     */
    
    Button back;
    
    /**
     * String value for auth token.
     */
    
    String authToken;
    
    /**
     * String value for account id.
     */
    
    String accountId;
    
    /**
     * String value for the word auth_token.
     */
    
    String authTokenString;
    
    /**
     * String value for the word account_id.
     */
    
    String accountIdString;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_page);
        Intent oldIntent = getIntent();
        authToken = oldIntent.getStringExtra(authTokenString);
        accountId = oldIntent.getStringExtra(accountIdString);
        HttpResponse response;
        try {
            response = new DatabaseInterface().getAccountInfo(authToken, accountId);
            if ( response.getStatusLine().getStatusCode() == 200 ) {
                String stringResponse = EntityUtils.toString(response.getEntity());
                JSONObject json = new JSONObject(stringResponse);
                String balance = json.getString("balance");
                String name = json.getString("name");
                addBalanceToScreen(balance);
                addNameToScreen(name);
            } else {
                addBalanceToScreen(EntityUtils.toString(response.getEntity()));
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
        } catch (JSONException e) {
			// TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trans_page, menu);
        return true;
    }
    
    /**
     * Adds balance to the screen.
     * @param balance The balance
     */
    
    private void addBalanceToScreen(String balance) {
        TextView textView = (TextView) findViewById(R.id.balance_input);
        textView.setText(balance);
    }
    
    /**
     * Adds name to the screen.
     * @param name The name
     */
    
    private void addNameToScreen(String name) {
        TextView textView = (TextView) findViewById(R.id.account_name_input);
        textView.setText(name);
    }
    
    /**
     * Add listeners to deposit, withdrawal, and back buttons.
     */
    
    public void addListenerOnButton() {
         
        final Context context = this;
 
        deposit = (Button) findViewById(R.id.depositButton);
        withdrawal = (Button) findViewById(R.id.withdrawalButton);
        back = (Button) findViewById(R.id.button1);
 
        deposit.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, DepositActivity.class);
                intent.putExtra(authTokenString, authToken);
                intent.putExtra(accountIdString, accountId);
                startActivity(intent);  
            }
 
        });
        withdrawal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, WithdrawalActivity.class);
                intent.putExtra(authTokenString, authToken);
                intent.putExtra(accountIdString, accountId);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0 ) {
                Intent intent = new Intent(context, AccountsOverviewActivity.class);
                intent.putExtra(authTokenString, authToken);
                intent.putExtra(accountIdString, accountId);
                startActivity(intent);
            }
        });

    }
    

}
