package com.example.m3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

/**
 * This class represents the action to register a user.
 * @author tripproberts
 *
 */

class RegisterTask extends AsyncTask<String, Void, HttpResponse> {

	/**
	 * The response from the server.
	 */
	
    private HttpResponse response;
    
    @Override
    protected HttpResponse doInBackground(String... inputs) {
    	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(inputs[0] + "/users.json");

        try {
            // Add your data
            List<NameValuePair> params = new ArrayList<NameValuePair>(4);
            params.add(new BasicNameValuePair("[user][name]", inputs[1]));
            params.add(new BasicNameValuePair("[user][email]", inputs[2]));
            params.add(new BasicNameValuePair("[user][password]", inputs[3]));
            params.add(new BasicNameValuePair("[user][password_confirmation]", inputs[4]));
            httppost.setEntity(new UrlEncodedFormEntity(params));

            // Execute HTTP Post Request
            response = httpclient.execute(httppost);
            
        } catch (ClientProtocolException e) {
            System.out.println("CPE" + e);
        } catch (IOException e) {
            System.out.println("IOE" + e);
        }
        return response;
    }
}
