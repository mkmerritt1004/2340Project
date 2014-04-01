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
 * Deposit task class.
 * 
 * @author Tripp
 * @version 1.0
 */
class DepositTask extends AsyncTask<String, Void, HttpResponse> {

	/**
	 * HttpResponse response instance variable.
	 */
    private HttpResponse response;

    @Override
    protected HttpResponse doInBackground(String... inputs) {
    	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(inputs[0] + "deposits?authentication_token=" + inputs[5]);

        try {
            // Add your data
            List<NameValuePair> params = new ArrayList<NameValuePair>(4);
            params.add(new BasicNameValuePair("[deposit][source]", inputs[1]));
            params.add(new BasicNameValuePair("[deposit][effective_date]", inputs[2]));
            params.add(new BasicNameValuePair("[deposit][amount]", inputs[3]));
            params.add(new BasicNameValuePair("[deposit][account_id]", inputs[4]));
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
