package com.example.m3;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This class represents the success screen activity.
 * @author tripproberts
 *
 */

public class SuccessScreenActivity extends Activity {
    
	/**
	 * The view accounts button.
	 */
	
    Button viewAccountsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        addListenerOnCreateButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * Add listener to create button.
     */

    public void addListenerOnCreateButton() {

        final Context context = this;

        viewAccountsButton = (Button) findViewById(R.id.viewAccountsButton);

        viewAccountsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent oldIntent = getIntent();
                String authTokenString = "auth_token";
                String authToken = oldIntent.getStringExtra(authTokenString);
                Intent intent = new Intent(context, AccountsOverviewActivity.class);
                intent.putExtra(authTokenString, authToken);
                startActivity(intent);  

            }

        });

    }

}
