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

class LoginTask extends AsyncTask<String, Void, HttpResponse> {

	private HttpResponse response;
	

    protected HttpResponse doInBackground(String... inputs) {
    	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(inputs[0] + "/users/sign_in.json");

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

}
