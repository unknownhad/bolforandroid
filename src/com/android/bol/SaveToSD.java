package com.android.bol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SaveToSD extends Activity implements OnItemSelectedListener,OnClickListener {

	TextView canRead, canWrite;
	boolean canR, canW;
	private String state;
	Spinner spinner;
	String[] paths = { "Pictures", "Downloads", "Music", "DCIM" };
	File path = null, file = null;
	EditText saveFile;
	Button save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.savetosd);

		canRead = (TextView) findViewById(R.id.tvCanRead);
		canWrite = (TextView) findViewById(R.id.tvCanWrite);
		saveFile = (EditText) findViewById(R.id.etSaveAs);
		save = (Button) findViewById(R.id.bSave);
		save.setOnClickListener(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(SaveToSD.this,android.R.layout.simple_spinner_item, paths);
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		checkState();
	}

	private void checkState() {
		// TODO Auto-generated method stub
		state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// Read and Write
			canRead.setText("true");
			canWrite.setText("true");
			canR = canW = true;
		} else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			// Read but can't write
			canRead.setText("true");
			canWrite.setText("false");
			canR = true;
			canW = false;
		} else {
			canWrite.setText("false");
			canWrite.setText("false");
			canR = canW = false;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		int position = spinner.getSelectedItemPosition();
		switch (position) {
		case 0:
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			break;
		case 1:
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			break;
		case 2:
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
			break;
		case 3:
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSave:
			String f = saveFile.getText().toString();
			file = new File(path, f +".png");
			checkState();
			if (canW == canR == true) {
				path.mkdirs();
				try {
					//Uri uri = null;
					InputStream is = getResources().openRawResource(R.drawable.background);//Not Working
					OutputStream os = new FileOutputStream(file);
					//os =  getContentResolver().openOutputStream(uri);
					byte[] data = new byte[is.available()];
					is.read(data);
					os.write(data);
					is.close();
					os.close();
					Toast t = Toast.makeText(SaveToSD.this, "File Saved",Toast.LENGTH_SHORT);
					t.show();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			break;
		}
	}
}
