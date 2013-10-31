package com.example.javamailapi;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	EditText user;
	EditText pass;
	EditText subject;
	EditText body;
	EditText recipient;

	Button send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = (EditText) findViewById(R.id.username);
		pass = (EditText) findViewById(R.id.password);
		subject = (EditText) findViewById(R.id.subject);
		body = (EditText) findViewById(R.id.body);
		recipient = (EditText) findViewById(R.id.recipient);

		send = (Button) findViewById(R.id.send);
		send.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.send) {
			String[] recp = { recipient.getText().toString() };
			SendEmailAsyncTask email = new SendEmailAsyncTask();
			email.m = new Mail(user.getText().toString(), pass.getText()
					.toString());

			/*
			 * try { email.m.addAttachment(
			 * "storage/sdcard/Pictures/CameraAPIDemo/CameraDemo_20131030093049.jpg"
			 * ); } catch (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			email.m.set_from(user.getText().toString());
			email.m.setBody(body.getText().toString());
			email.m.set_to(recp);
			email.m.set_subject(subject.getText().toString());

			email.execute();
		}
	}

}

class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
	Mail m;

	public SendEmailAsyncTask() {
		if (BuildConfig.DEBUG)
			Log.v(SendEmailAsyncTask.class.getName(), "SendEmailAsyncTask()");

	}

	@Override
	protected Boolean doInBackground(Void... params) {
		if (BuildConfig.DEBUG)
			Log.v(SendEmailAsyncTask.class.getName(), "doInBackground()");
		try {
			m.send();
			return true;
		} catch (AuthenticationFailedException e) {
			Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			Log.e(SendEmailAsyncTask.class.getName(), m.get_to() + "failed");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
