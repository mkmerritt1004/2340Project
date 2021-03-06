package com.example.m3;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

/**
 * GetAccountInfoTask class.
 * 
 * @author Tripp
 * @version 1.0
 */
class GetAccountInfoTask extends AsyncTask<String, Void, HttpResponse> {

	/**
	 * HttpResponse reponse instance variable.
	 */
    private HttpResponse response;

    @Override
    protected HttpResponse doInBackground(String... inputs) {
    	
    	HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(inputs[0] + "/accounts/" + inputs[1] + ".json?authentication_token=" + inputs[2]);
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
