PullDownListView
================
![](https://raw.githubusercontent.com/guojunyi/PullDownListView/master/screenshot/1.gif)
## Sample Application
<a href="https://raw.githubusercontent.com/guojunyi/PullDownListView/master/apk/PullDownListView.apk" target="_blank" title="Download From Google Play">Click to Download the simple apk</a>

## Usage
``` java
<com.pulldownlistview.PullDownListView
        android:id="@+id/pullDownListView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#424242"
        >

        <RelativeLayout
            android:id="@+id/layoutHeader"
            android:layout_width="match_parent"
            android:layout_height="80dp"
            android:layout_alignParentTop="true" >

            <com.yourCustomView..../>
            
        </RelativeLayout>

        <RelativeLayout
            android:id="@+id/layoutFooter"
            android:layout_width="match_parent"
            android:layout_height="80dp"
            android:layout_alignParentBottom="true" >

            <com.yourCustomView..../>
            
        </RelativeLayout>
</com.pulldownlistview.PullDownListView>
```

``` java
pullDownListView.setOnPullHeightChangeListener(new OnPullHeightChangeListener(){

			@Override
			public void onTopHeightChange(int headerHeight, int pullHeight) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onBottomHeightChange(int footerHeight, int pullHeight) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onRefreshing(boolean isTop) {
				// TODO Auto-generated method stub
				
			}
			
		});
```


