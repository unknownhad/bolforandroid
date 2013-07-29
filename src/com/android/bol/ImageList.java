package com.android.bol;

import pl.polidea.coverflow.testingactivity.CoverFlowTestingActivity;
import android.app.Activity;
import android.os.Bundle;

public class ImageList extends Activity{
	
	CoverFlowTestingActivity flow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(CoverFlowTestingActivity.BIND_ADJUST_WITH_ACTIVITY);
	}

}
