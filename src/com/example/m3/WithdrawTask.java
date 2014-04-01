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
 * This class represents the withdraw task.
 * @author tripproberts
 *
 */

class WithdrawTask extends AsyncTask<String, Void, HttpResponse> {

	/**
	 * The http response.
	 */
	
    private HttpResponse response;
	
    @Override
    protected HttpResponse doInBackground(String... inputs) {
    	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(inputs[0] + "/withdrawals?authentication_token=" + inputs[6]);

        try {
            // Add your data
            List<NameValuePair> params = new ArrayList<NameValuePair>(5);
            params.add(new BasicNameValuePair("[withdrawal][reason]", inputs[1]));
            params.add(new BasicNameValuePair("[withdrawal][category]", inputs[2]));
            params.add(new BasicNameValuePair("[withdrawal][effective_date]", inputs[3]));
            params.add(new BasicNameValuePair("[withdrawal][amount]", inputs[4]));
            params.add(new BasicNameValuePair("[withdrawal][account_id]", inputs[5]));
            httppost.setEntity(new UrlEncodedFormEntity(params));
            // Execute HTTP Post Request
            response = httpclient.execute(httppost);
            
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            System.out.println("CPE" + e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("IOE" + e);
        }
        return response;
    }
}
