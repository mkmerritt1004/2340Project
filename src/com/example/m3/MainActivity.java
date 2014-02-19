package com.example.m3;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;



public class MainActivity extends Activity {
	
	Button loginButton;
	EditText userEdit;
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
    public void updateTextView(String newStr) {

        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText(newStr);
    }
    
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		loginButton = (Button) findViewById(R.id.loginButton);
 
		loginButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				userEdit   = (EditText)findViewById(R.id.editText1);
				passEdit   = (EditText)findViewById(R.id.editText2);
				String userEditStr = userEdit.getText().toString();
				String passEditStr = passEdit.getText().toString();

				new CheckLoginTask(context).execute(userEditStr, passEditStr);
				
			}
 
		});
    
	}
	
	class CheckLoginTask extends AsyncTask<String, Void, Boolean> {

		private Context context;
		
		private CheckLoginTask(Context context) {
		    this.context = context.getApplicationContext();
		}

	    protected Boolean doInBackground(String... inputs) {
	    	HttpURLConnection urlConnection = null;
	        try {
				disableConnectionReuseIfNecessary();
				String email = inputs[0];
				String password = inputs[1];
				String url = "http://intense-garden-9893.herokuapp.com/api/users/sign_in.json";
	        	urlConnection = (HttpURLConnection) new URL(url).openConnection();
				urlConnection.setDoOutput(true); //Triggers POST
				urlConnection.setRequestProperty("Content-Type", "application/json");
				urlConnection.setRequestProperty("Accept", "application/json");
				urlConnection.setRequestMethod("POST");
								
				JSONObject user = new JSONObject();
				JSONObject params = new JSONObject();
			    try {
					params.put("email",email);
				    params.put("password",password);
				    user.put("user", params);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    byte[] outputBytes = user.toString().getBytes("UTF8");
			    
				OutputStream os = urlConnection.getOutputStream();
				os.write(outputBytes);
				os.close();
				TextView textView = (TextView) findViewById(R.id.textView1);
				return urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	    }

	    protected void onPostExecute(Boolean result) {
			if ( result ){
				Intent intent = new Intent(context, SuccessScreenActivity.class);
				startActivity(intent); 
			}
			else{
				updateTextView("Incorrect Username or Password. Try Again.");
			}
	    }
	    
		/**
		 * required in order to prevent issues in earlier Android version.
		 */
		@SuppressWarnings("deprecation")
		private void disableConnectionReuseIfNecessary() {
		    // see HttpURLConnection API doc
		    if (Integer.parseInt(Build.VERSION.SDK) 
		            < Build.VERSION_CODES.FROYO) {
		        System.setProperty("http.keepAlive", "false");
		    }
		}
	}
}
