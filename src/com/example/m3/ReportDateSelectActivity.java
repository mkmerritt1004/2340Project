package com.example.m3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

/**
 * This class represents the date select activity for reports.
 * @author tripproberts
 *
 */

public class ReportDateSelectActivity extends Activity {
    
	/**
	 * The string representation of the word auth token.
	 */
	
    private String authToken;
    
    /**
     * This is the submit button.
     */
    
    private Button submitButton;
    
    /**
     * This is the back button.
     */
    
    private Button backButton;
    
    /**
     * Date picker for the start date.
     */
    
    private DatePicker startDatePicker;
    
    /**
     * Date picker for the end date.
     */
    
    private DatePicker endDatePicker;
    
    /**
     * String value of start date.
     */
    
    private String startDate;
    
    /**
     * String value of end date.
     */
    
    private String endDate;
    
    /**
     * String value of auth token.
     */
    
    private String authTokenString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authTokenString = "auth_token";
        setContentView(R.layout.activity_report_date_select);
        loadElements();        
        addListenersOnButtons();
    }
    

    /**
     * Loads elements from screen.
     */
    
    @SuppressLint("NewApi")
    private void loadElements() {
        Intent oldIntent = getIntent();
        authToken = oldIntent.getStringExtra(authTokenString);
        submitButton = (Button) findViewById(R.id.submitButton);
        backButton = (Button) findViewById(R.id.backButton);
        startDatePicker = (DatePicker) findViewById(R.id.startDatePicker);
        startDatePicker.setCalendarViewShown(false);
        endDatePicker = (DatePicker) findViewById(R.id.endDatePicker);
        endDatePicker.setCalendarViewShown(false);
    }
    
    /**
     * Adds a listener to the view report button.
     */
    
    public void addListenersOnButtons() {
        final Context context = this;


        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	String dateFormat = "%02d/%02d/%d";
                Intent intent = new Intent(context, SpendingReportActivity.class);
                startDate = String.format(dateFormat, startDatePicker.getMonth() + 1, 
                        startDatePicker.getDayOfMonth(), startDatePicker.getYear());
                endDate = String.format(dateFormat, endDatePicker.getMonth() + 1,
                         endDatePicker.getDayOfMonth(), endDatePicker.getYear());
                intent.putExtra(authTokenString, authToken);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, AccountsOverviewActivity.class);
                intent.putExtra(authTokenString, authToken);
                startActivity(intent);
            }
        });
    }
    
    
    
}
