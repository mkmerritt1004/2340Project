package com.example.m3;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
	
	class CheckLoginTask extends AsyncTask<String, Void, HttpResponse> {

		private Context context;
		private HttpResponse response;
		
		private CheckLoginTask(Context context) {
		    this.context = context.getApplicationContext();
		}

	    protected HttpResponse doInBackground(String... inputs) {
	    	HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://intense-garden-9893.herokuapp.com/api/users/sign_in.json");

	        try {
	            // Add your data
	            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
	            params.add(new BasicNameValuePair("[user][email]", inputs[0]));
	            params.add(new BasicNameValuePair("[user][password]", inputs[1]));
	            httppost.setEntity(new UrlEncodedFormEntity(params));

	            // Execute HTTP Post Request
	            response = httpclient.execute(httppost);
	            
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	            System.out.println("CPE"+e);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            System.out.println("IOE"+e);
	        }
	        return response;
	    }

	    protected void onPostExecute(HttpResponse response) {
			if ( response.getStatusLine().getStatusCode() == 200 ){
				try {
					String stringResponse = EntityUtils.toString(response.getEntity());
					String[] splitResponse = stringResponse.split(":");
					String auth_token = splitResponse[2].substring(1,splitResponse[2].length() - 9);
					Intent intent = new Intent(context, SuccessScreenActivity.class);
					intent.putExtra("auth_token", auth_token);
					startActivity(intent); 
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else{
				updateTextView("Incorrect Username or Password. Try Again.");
			}
	    }
	}

}
