package com.example.m3;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

class GetAccountsTask extends AsyncTask<String, Void, HttpResponse> {

	/**
	 * HttpResponse response instance variable.
	 */
    private HttpResponse response;
	
    protected HttpResponse doInBackground(String... inputs) {
    	
    	HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(inputs[0] + "/users/accounts.json?authentication_token=" + inputs[1]);

        try {
            // Execute HTTP Get Request
            response = httpclient.execute(httpget);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            System.out.println("CPE"+e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("IOE"+e);
        }
        return response;
    }
}
