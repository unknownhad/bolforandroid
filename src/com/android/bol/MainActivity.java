package com.android.bol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	ImageView iv;
	Button iCapture;
	EditText filename;
	static Bitmap bmp, pic;
	static int cameraData = 0;
	private static final int PICK_FROM_GALLERY = 1;
	//static Uri uriSavedImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initialize();
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.background);
	}

	private void initialize() {
		iv = (ImageView) findViewById(R.id.ivReturnedPic);
		iCapture = (Button) findViewById(R.id.bTakePic);
		iCapture.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bTakePic:
			Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, cameraData);
		//	i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			bmp = (Bitmap) data.getExtras().get("data");
			iv.setImageBitmap(bmp);
		}

		if (requestCode == PICK_FROM_GALLERY) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				pic = extras.getParcelable("data");
				iv.setImageBitmap(pic);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater look = getMenuInflater();
		look.inflate(R.menu.cool_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		
		case R.id.setWall:
			try {
				getApplicationContext().setWallpaper(bmp);
				Toast.makeText(MainActivity.this, "Your Wallpaper is saved", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case R.id.saveSD:
			Intent save = new Intent("com.android.Bol.SAVETOSD");
			startActivity(save);
			break;

		case R.id.imageList:
			Intent iImage = new Intent("com.android.Bol.IMAGELIST");
			startActivity(iImage);
			break;

		case R.id.importImage:
			Intent intent = new Intent();
			// call android default gallery
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			try {
				intent.putExtra("return-data", true);
				startActivityForResult(Intent.createChooser(intent, "Complete action using"),PICK_FROM_GALLERY);
			} catch (ActivityNotFoundException e) {
				e.printStackTrace();
			}
			break;

		case R.id.aboutUs:
			Intent about = new Intent("com.android.Bol.ABOUT");
			startActivity(about);
			break;

		case R.id.exit:
			finish();
			break;
		}
		return false;
	}
}