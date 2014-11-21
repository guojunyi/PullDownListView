package com.pulldownlistview;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;

public class PullDownListView extends RelativeLayout implements
		OnScrollListener {
	public static final int MAX_PULL_TOP_HEIGHT = 300; 
	public static final int MAX_PULL_BOTTOM_HEIGHT = 300;
	public static final float REFRESHING_PROGRESS_TOP = 1.2f; 
	public static final float REFRESHING_PROGRESS_BOTTOM = 1.2f;
	private boolean isTop;
	private boolean isBottom;
	private boolean isRefreshing;
	private boolean isAnimation;
	
	RelativeLayout layoutHeader;
	RelativeLayout layoutFooter;
	Handler mHandler = new Handler();
	
	float progressTop;
	float progressBottom;
	
	OnPullHeightChangeListener mOnPullHeightChangeListener;
	
	public void setOnPullHeightChangeListener(OnPullHeightChangeListener listener){
		this.mOnPullHeightChangeListener = listener;
	}

	public PullDownListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public boolean isRefreshing(){
		return this.isRefreshing;
	}
	
	private ListView mListView = new ListView(getContext()) {
		
		int y = 0;

		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			if (isAnimation||isRefreshing) {
				return super.onTouchEvent(ev);
			}
			int currentY = (int) ev.getY();
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				y = (int) ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
			{
				RelativeLayout parent = (RelativeLayout) mListView.getParent();
				int step = (int) (currentY - y) * 3 / 5;
				y = currentY;
				if (isTop && mListView.getTop() >= 0) {
					if (step > 0 && mListView.getTop() <= MAX_PULL_TOP_HEIGHT) {
						if ((mListView.getTop() + step) > MAX_PULL_TOP_HEIGHT) {
							mListView.layout(mListView.getLeft(), MAX_PULL_TOP_HEIGHT,
									mListView.getRight(), parent.getHeight()
											+ MAX_PULL_TOP_HEIGHT);
						} else {
							mListView.layout(mListView.getLeft(),
									mListView.getTop() + step,
									mListView.getRight(), mListView.getBottom()
											+ step);
						}
						MotionEvent event = MotionEvent.obtain(ev);
						ev.setAction(MotionEvent.ACTION_UP);
						
						super.onTouchEvent(ev);
						
						if(null!=mOnPullHeightChangeListener){
							mOnPullHeightChangeListener.onTopHeightChange(layoutHeader.getHeight(), mListView.getTop());
						}
						onProgressTopChange();
					} else if (step < 0 && mListView.getTop() > 0) {
						if ((mListView.getTop() + step) < 0) {
							
							mListView.layout(mListView.getLeft(), 0,
									mListView.getRight(), parent.getHeight());
						} else {
							mListView.layout(mListView.getLeft(),
									mListView.getTop() + step,
									mListView.getRight(), mListView.getBottom()
											+ step);
						}
						if(null!=mOnPullHeightChangeListener){
							mOnPullHeightChangeListener.onTopHeightChange(layoutHeader.getHeight(), mListView.getTop());
						}
						onProgressTopChange();
					} else if (step < 0 && mListView.getTop() == 0) {
						return super.onTouchEvent(ev);
					}

					return true;
				}else if(isBottom&&mListView.getBottom()<=parent.getHeight()){
					step = -step;
					if (step > 0 && mListView.getBottom() >= (parent.getHeight()-MAX_PULL_BOTTOM_HEIGHT)) {
						if ((mListView.getBottom() - step) < (parent.getHeight()-MAX_PULL_BOTTOM_HEIGHT)) {
							mListView.layout(mListView.getLeft(), -MAX_PULL_BOTTOM_HEIGHT,
									mListView.getRight(), parent.getHeight()
											- MAX_PULL_BOTTOM_HEIGHT);
						} else {
							mListView.layout(mListView.getLeft(),
									mListView.getTop() - step,
									mListView.getRight(), mListView.getBottom()
											- step);
						}
						MotionEvent event = MotionEvent.obtain(ev);
						ev.setAction(MotionEvent.ACTION_UP);
						
						super.onTouchEvent(ev);
						
						if(null!=mOnPullHeightChangeListener){
							mOnPullHeightChangeListener.onBottomHeightChange(layoutHeader.getHeight(), parent.getHeight()-mListView.getBottom());
						}
						onProgressBottomChange();
					} else if (step < 0 && mListView.getBottom() < parent.getHeight()) {
						if ((mListView.getBottom() - step) > parent.getHeight()) {
							
							mListView.layout(mListView.getLeft(), 0,
									mListView.getRight(), parent.getHeight());
						} else {
							mListView.layout(mListView.getLeft(),
									mListView.getTop() - step,
									mListView.getRight(), mListView.getBottom()
											- step);
						}
						if(null!=mOnPullHeightChangeListener){
							mOnPullHeightChangeListener.onBottomHeightChange(layoutHeader.getHeight(), parent.getHeight()-mListView.getBottom());
						}
						onProgressBottomChange();
					} else if (step < 0 && mListView.getTop() == 0) {
						return super.onTouchEvent(ev);
					}

					return true;
				}
			}
				
			case MotionEvent.ACTION_UP:
			{
				RelativeLayout parent = (RelativeLayout) mListView.getParent();
				if(mListView.getTop()>0){
					if (progressTop>=REFRESHING_PROGRESS_TOP) {
						isRefreshing = true;
						if(null!=mOnPullHeightChangeListener){
							mOnPullHeightChangeListener.onRefreshing();
						}
						scrollTopTo(layoutHeader.getHeight(),300);
					} else {
						if (mListView.getTop() > 0) {
							scrollTopTo(0,300);
						}
					}
				}else if(mListView.getBottom()<parent.getHeight()){
					if (progressBottom>=REFRESHING_PROGRESS_BOTTOM) {
						isRefreshing = true;
						if(null!=mOnPullHeightChangeListener){
							mOnPullHeightChangeListener.onRefreshing();
						}
						scrollBottomTo(parent.getHeight()-layoutFooter.getHeight(),300);
					} else {
						if (mListView.getBottom() < parent.getHeight()) {
							scrollBottomTo(parent.getHeight(),300);
						}
					}
				}
			}

				break;

			}

			return super.onTouchEvent(ev);
		}

	};

	@Override 
	public void onFinishInflate(){
		mListView.setBackgroundColor(Color.WHITE);
		mListView.setCacheColorHint(Color.TRANSPARENT);
		mListView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		mListView.setOnScrollListener(this);
		this.addView(mListView);
		
		layoutHeader = (RelativeLayout) this.findViewById(R.id.layoutHeader);
		layoutFooter = (RelativeLayout) this.findViewById(R.id.layoutFooter);
		super.onFinishInflate();
	}
	
	
	private void scrollTopTo(final int y,int duration){
		if(isAnimation){
			return;
		}
		
		ValueAnimator animator = ValueAnimator.ofInt(
				mListView.getTop(), y);
		animator.setDuration(duration);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(
					ValueAnimator animation) {
				// TODO Auto-generated method stub
				int frameValue = (Integer) animation
						.getAnimatedValue();
				RelativeLayout parent = (RelativeLayout) mListView
						.getParent();
				mListView.layout(mListView.getLeft(),
						frameValue, mListView.getRight(),
						parent.getHeight());
				if(null!=mOnPullHeightChangeListener){
					mOnPullHeightChangeListener.onTopHeightChange(layoutHeader.getHeight(), frameValue);
				}
				onProgressTopChange();
				if (frameValue == y) {
					isAnimation = false;
				}
			}

		});
		isAnimation = true;
		animator.start();
	}
	
	private void scrollBottomTo(final int y,int duration){
		if(isAnimation){
			return;
		}
		
		ValueAnimator animator = ValueAnimator.ofInt(
				mListView.getBottom(), y);
		animator.setDuration(duration);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(
					ValueAnimator animation) {
				// TODO Auto-generated method stub
				int frameValue = (Integer) animation
						.getAnimatedValue();
				RelativeLayout parent = (RelativeLayout) mListView
						.getParent();
				mListView.layout(mListView.getLeft(),
						frameValue-parent.getHeight(), mListView.getRight(),
						frameValue);
				if(null!=mOnPullHeightChangeListener){
					mOnPullHeightChangeListener.onBottomHeightChange(layoutFooter.getHeight(), parent.getHeight()-frameValue);
				}
				onProgressTopChange();
				if (frameValue == y) {
					isAnimation = false;
				}
			}

		});
		isAnimation = true;
		animator.start();
	}
	
	public ListView getListView(){
		return this.mListView;
	}
	
	public void pullUp(){
		isRefreshing = false;
		if(mListView.getTop()>0){
			this.scrollTopTo(0, 300);
		}else if(mListView.getBottom()<this.getHeight()){
			this.scrollBottomTo(this.getHeight(), 300);
		}
		
		
	}
	
	private void onProgressTopChange(){
		this.progressTop = (float)mListView.getTop()/(float)layoutHeader.getHeight();
	}
	
	private void onProgressBottomChange(){
		this.progressBottom = ((float)this.getHeight()-(float)mListView.getBottom())/(float)layoutFooter.getHeight();
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (mListView.getCount() > 0) {
			if((firstVisibleItem+visibleItemCount)==totalItemCount){
				View lastItem = (View) mListView.getChildAt(visibleItemCount-1);
				if (null != lastItem) {
					
					if(lastItem.getBottom()==mListView.getHeight()){
						Log.e("my",lastItem.getBottom()+"");
						isBottom = true;
					}else{
						isBottom = false;
					}
				}
			}else{
				isBottom = false;
			}
		}else{
			isBottom = false;
		}
		
		
		if (mListView.getCount() > 0) {
			if (firstVisibleItem == 0) {
				View firstItem = mListView.getChildAt(0);
				if (null != firstItem) {
					if (firstItem.getTop() == 0) {
						isTop = true;
					} else {
						isTop = false;
					}
				}
			} else {
				isTop = false;
			}
		} else {
			isTop = true;
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	//listener call back
	public interface OnPullHeightChangeListener{
		public void onTopHeightChange(int headerHeight,int pullHeight);
		public void onBottomHeightChange(int footHeight,int pullHeight);
		public void onRefreshing();
	}
}
