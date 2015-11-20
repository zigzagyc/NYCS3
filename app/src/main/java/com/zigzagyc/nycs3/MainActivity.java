package com.zigzagyc.nycs3;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "nycsmail@yahoo.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            WebView myWebView = (WebView) rootView.findViewById(R.id.webview);
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            //loads the WebView completely zoomed out
            myWebView.getSettings().setLoadWithOverviewMode(true);

            //true makes the Webview have a normal viewport such as a normal desktop browser
            //when false the webview will have a viewport constrained to it's own dimensions
            myWebView.getSettings().setUseWideViewPort(true);

            //override the web client to open all links in the same webview
            myWebView.setWebViewClient(new MyWebViewClient());
            myWebView.setWebChromeClient(new MyWebChromeClient());

            //Injects the supplied Java object into this WebView. The object is injected into the
            //JavaScript context of the main frame, using the supplied name. This allows the
            //Java object's public methods to be accessed from JavaScript.
            //myWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");

            //load the home page URL
            switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    myWebView.loadUrl("http://www.newtownyardleychineseschool.org");
                    break;
                case 2:
                    myWebView.loadUrl("http://www.newtownyardleychineseschool.org/calendar.html");
                    break;
                case 3:
                    myWebView.loadUrl("http://www.newtownyardleychineseschool.org/contact.html");
                    break;
                default:
                    myWebView.loadUrl("http://www.newtownyardleychineseschool.org");
            }
            return rootView;
        }

        //customize your web view client to open links from your own site in the
        //same web view otherwise just open the default browser activity with the URL
        private class MyWebViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().equals("demo.mysamplecode.com")) {
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        }

        private class MyWebChromeClient extends WebChromeClient {

            //display alert message in Web View
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        //        Log.d(LOG_TAG, message);
                new AlertDialog.Builder(view.getContext())
                        .setMessage(message).setCancelable(true).show();
                result.confirm();
                return true;
            }

        }

        public class JavaScriptInterface {
            Context mContext;

            // Instantiate the interface and set the context
            JavaScriptInterface(Context c) {
                mContext = c;
            }

            //using Javascript to call the finish activity
            public void closeMyActivity() {
          //      finish();
            }

        }

        //Web view has record of all pages visited so you can go back and forth
        //just override the back button to go back in history if there is page
        //available for display
        //@Override
        //public boolean onKeyDown(int keyCode, KeyEvent event) {
        //    if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
        //        myWebView.goBack();
        //        return true;
        //    }
        //    return super.onKeyDown(keyCode, event);
        //}
    }
}
