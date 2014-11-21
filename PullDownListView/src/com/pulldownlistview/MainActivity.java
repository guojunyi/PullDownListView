package com.pulldownlistview;


import com.pulldownlistview.PullDownListView.OnPullHeightChangeListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity{
	final String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final PullDownListView pullDownListView = (PullDownListView) this.findViewById(R.id.pullDownListView);
		final YProgressView progressViewTop = (YProgressView) this.findViewById(R.id.progressView);
		final EyeView eyeView = (EyeView) this.findViewById(R.id.eyeView);
		String[] adapterData = new String[] {"Afghanistan", "Albania", "Algeria", 
		        "American Samoa", "Andorra", "Angola", "Anguilla", 
		        "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", 
		        "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain", 
		        "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", 
		        "Benin", "Bermuda", "Bhutan", "Bolivia", 
		        "Bosnia and Herzegovina", "Botswana", "Bouvet Island"}; 

        
		pullDownListView.getListView().setAdapter(new ArrayAdapter<String>(this, 
                android.R.layout.simple_list_item_1, adapterData));
		
		
		pullDownListView.setOnPullHeightChangeListener(new OnPullHeightChangeListener(){

			@Override
			public void onTopHeightChange(int headerHeight, int pullHeight) {
				// TODO Auto-generated method stub
				Log.e(TAG,"headerHeight:"+headerHeight+" pullHeight:"+pullHeight);
				
				float progress = (float)pullHeight/(float)headerHeight-0.2f;
				if(progress>1.0f){
					progress = 1.0f;
				}
				
				if(!pullDownListView.isRefreshing()){
					eyeView.setProgress(progress);
				}
			}

			@Override
			public void onBottomHeightChange(int footerHeight, int pullHeight) {
				// TODO Auto-generated method stub
				Log.e(TAG,"footerHeight:"+footerHeight+" pullHeight:"+pullHeight);
				
				float progress = (float)pullHeight/(float)footerHeight-0.2f;
				if(progress>1.0f){
					progress = 1.0f;
				}
				if(!pullDownListView.isRefreshing()){
					progressViewTop.setProgress(progress);
				}
				
			}
			
			@Override
			public void onRefreshing() {
				// TODO Auto-generated method stub
				progressViewTop.startAnimate();
				eyeView.startAnimate();
				new Handler().postDelayed(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						pullDownListView.pullUp();
						progressViewTop.stopAnimate();
						eyeView.stopAnimate();
					}
					
				}, 3000);
			}

			
			
		});
		
		pullDownListView.getListView().setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
}
