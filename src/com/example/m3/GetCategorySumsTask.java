package com.example.m3;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

/**
 * GetCategorySumsTask class.
 * 
 * @author Tripp
 * @version 1.0
 */
class GetCategorySumsTask extends AsyncTask<String, Void, HttpResponse> {

	/**
	 * HttpResponse response instance variable.
	 */
    private HttpResponse response;

    @Override
    protected HttpResponse doInBackground(String... inputs) {
    	
    	HttpClient httpclient = new DefaultHttpClient();
    	String url = inputs[0] + "/users/categories.json?authentication_token=" 
        		+ inputs[3] + "&start_date=" + inputs[1] + "&end_date=" + inputs[2];
    	Log.d(url, url);
        HttpGet httpget = new HttpGet(url);

        try {
            // Execute HTTP Get Request
            response = httpclient.execute(httpget);
        } catch (ClientProtocolException e) {
            System.out.println("CPE" + e);
        } catch (IOException e) {
            System.out.println("IOE" + e);
        }
        return response;
    }

}
