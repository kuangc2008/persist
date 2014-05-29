package com.example.myapplication.app;

import java.util.Locale;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Scroller;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        View mShadow = null;
        ViewGroup mBottom = null;
        boolean isShowUserInfo = false;
        MainActivity activity = null;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            activity = (MainActivity) getActivity();

            animator = ValueAnimator.ofFloat(0, 1);
            animator.setDuration(DURATION);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    if(mScroller.computeScrollOffset()) {
                        Log.v("kc", "mScroller.getCurrY()" + mScroller.getCurrY());
                        mBottom.scrollTo(0, mScroller.getCurrY());
                    }
                }
            });
            mScroller = new Scroller(getActivity(), null, true);
        }

        ValueAnimator animator = null;
        public static final int DURATION  = 1000;
        private Scroller mScroller = null;
        private int height = 0;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            rootView.findViewById(R.id.user).setOnClickListener(this);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mShadow = view.findViewById(R.id.shadow);
            mBottom = (ViewGroup) view.findViewById(R.id.bottom);

            mBottom.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Log.v("kc", " getBottom10-->" + mBottom.getHeight());
                    height = mBottom.getHeight();
                    mBottom.scrollTo(0, -mBottom.getHeight());
                }
            });
            Log.v("kc", " getBottom-->" + mBottom.getHeight());


            final GestureDetector dec = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    mBottom.scrollBy(0, (int) distanceY);
                    return true;
                }

            });
            mBottom.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            mBottom.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.v("kc", "onTouch-->beggin"  );
                    if(dec.onTouchEvent(event)) {
                        return true;
                    }
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        int scrolly = mBottom.getScrollY();
                        if(scrolly > -height/2) {
                             scrollUp(scrolly);
                        } else {
                            scrollDown(scrolly);
                        }
                    }
                    return false;
                }
            });
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.v("kc", " getBotto3m-->" + mBottom.getHeight());
        }


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.v("kc", "onActivityCreated");

        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onClick(View v) {
            Log.v("kc", "onclick-->" + mBottom.getScrollY());
            int scrolly =  mBottom.getScrollY();
            switch (v.getId()) {
                case R.id.user:
                    if(!isShowUserInfo) {
            //            mBottom.setVisibility(View.VISIBLE);
                        scrollDown(scrolly);
                    } else {

             //           mBottom.setVisibility(View.GONE);
                        scrollUp(scrolly);
                    }
                    isShowUserInfo = !isShowUserInfo;

                    break;

            }
        }

        private void scrollUp(int scrolly) {
            mScroller.startScroll(0, scrolly,0,  0 - scrolly, DURATION);
            animator.start();
            mShadow.setVisibility(View.VISIBLE);
        }

        private void scrollDown(int scrolly) {
            mScroller.startScroll(0, scrolly, 0,  -height - scrolly, DURATION);
            animator.start();

            mShadow.setVisibility(View.GONE);
        }
    }

}
