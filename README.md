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

## License

    Copyright 2014 trinea.cn

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

