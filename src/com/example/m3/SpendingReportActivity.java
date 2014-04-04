package com.example.m3;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class represents the spending report activity.
 * @author tripproberts
 *
 */

public class SpendingReportActivity extends Activity {

	/**
	 * This is the back button.
	 */
	
    private Button backButton;
    
    /**
     * String value of the start date.
     */
    
    private String startDate;
    
    /**
     * The text view for the start date.
     */
    
    private TextView startDateTextView;
    
    /**
     * String value of end date.
     */
    
    private String endDate;
    
    /**
     * The text view for the end date.
     */
    
    private TextView endDateTextView;
    
    /**
     * String value of food sum.
     */
    
    private String foodSum;
    
    /**
     * Text view for food sum.
     */
    
    private TextView foodSumTextView;
    
    /**
     * String value of rent sum.
     */
    
    private String rentSum;
    
    /**
     * Text view for the rent sum.
     */
    
    private TextView rentSumTextView;
    
    /**
     * String value of entertainment sum.
     */
    
    private String entertainmentSum;
    
    /**
     * Text view for the entertainment sum.
     */
    
    private TextView entertainmentSumTextView;
    
    /**
     * String value of clothing sum.
     */
    
    private String clothingSum;
    
    /**
     * Text view for the clothing sum.
     */
    
    private TextView clothingSumTextView;
    
    /**
     * String value for other sum.
     */
    
    private String otherSum;
    
    /**
     * Text view for other sum.
     */
    
    private TextView otherSumTextView;
    
    /**
     * String value for total.
     */
    
    private String total;
    
    /**
     * Text view for total.
     */
    
    private TextView totalTextView;
    
    /**
     * String value for auth token.
     */
    
    private String authToken;
    
    /**
     * String value of the word auth_token.
     */
    
    private String authTokenString;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_report);
        authTokenString = "auth_token";
        loadElements();
        startDateTextView.setText(startDate);
        endDateTextView.setText(endDate);
        try {
            HttpResponse response = new DatabaseInterface().getCategorySums(startDate, endDate, authToken);
            if ( response.getStatusLine().getStatusCode() == 200 ) {
                try {
                    String stringResponse = EntityUtils.toString(response.getEntity());
                    JSONObject json = new JSONObject(stringResponse);
                    foodSum = json.getString("food");
                    entertainmentSum = json.getString("entertainment");
                    clothingSum = json.getString("clothing");
                    rentSum = json.getString("rent");
                    otherSum = json.getString("other");
                    total = json.getString("total");
                    updateSums();
                    
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                updateView(EntityUtils.toString(response.getEntity()));
            }
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        addListenerToButton();
    }
    
    /**
     * Get elements from page.
     */
    
    private void loadElements() {
        Intent oldIntent = getIntent();
        authToken = oldIntent.getStringExtra(authTokenString);
        startDate = oldIntent.getStringExtra("startDate");
        startDateTextView = (TextView) findViewById(R.id.startDate);
        endDate = oldIntent.getStringExtra("endDate");
        endDateTextView = (TextView) findViewById(R.id.endDate);
        foodSumTextView = (TextView) findViewById(R.id.food_sum);
        rentSumTextView = (TextView) findViewById(R.id.rent_sum);
        clothingSumTextView = (TextView) findViewById(R.id.clothing_sum);
        otherSumTextView = (TextView) findViewById(R.id.other_sum);
        entertainmentSumTextView = (TextView) findViewById(R.id.entertainment_sum);
        totalTextView = (TextView) findViewById(R.id.total);
    }
    
    /**
     * Add listener to back button.
     */
    
    public void addListenerToButton() {
        
        final Context context = this;
        backButton = (Button) findViewById(R.id.backButton);
        
        backButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ReportDateSelectActivity.class);
                intent.putExtra(authTokenString, authToken);
                startActivity(intent);
            }
        });
    }
    
    /**
     * Update sums on screen.
     */
    
    private void updateSums() {
        foodSumTextView.setText(foodSum);
        rentSumTextView.setText(rentSum);
        clothingSumTextView.setText(clothingSum);
        entertainmentSumTextView.setText(entertainmentSum);
        otherSumTextView.setText(otherSum);
        totalTextView.setText(total);
    }
    
    /**
     * Update the view with errors.
     * @param errors list of errors
     */
    
    private void updateView(String errors) {
        TextView errorsTextView = (TextView) findViewById(R.id.errors);
        errorsTextView.setText(errors);
    }
    
    
    
}
