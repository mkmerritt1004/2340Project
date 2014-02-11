package com.example.m3;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		loginButton = (Button) findViewById(R.id.loginButton);
 
		loginButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				userEdit   = (EditText)findViewById(R.id.editText1);
				passEdit   = (EditText)findViewById(R.id.editText2);
				System.out.println(userEdit);
				//if(userEdit.equals("admin") && passEdit.equals("pass123")){
					Intent intent = new Intent(context, SuccessScreenActivity.class);
					startActivity(intent); 
				//}
				//}else{
				//	signal to try again
				//}
			}
 
		});
    
}
}
