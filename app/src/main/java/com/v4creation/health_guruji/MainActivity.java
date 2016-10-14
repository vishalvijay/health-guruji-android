package com.v4creation.health_guruji;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.v4creation.health_guruji.utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, HGWebViewClient.HGWebViewCallBacks {

    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NavigationView navigationView;

    private static final String BLOG_URL = "http://www.health-guruji.in";
    private static final String YOUTUBE_URL = "https://www.youtube.com/channel/UCMyBo48vpI8a0ciEElkdUHw";
    private static final String FACEBOOK_PAGE_ID = "549712475222908";
    private static final String FACEBOOK_URL = "https://m.facebook.com/മലയാളി-" + FACEBOOK_PAGE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setBlogAsSeelcted();
    }

    private void setBlogAsSeelcted() {
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        webView = (WebView) findViewById(R.id.wv);
        webView.setWebViewClient(new HGWebViewClient(webView, this));
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        onBlogClick();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_blog) {
            onBlogClick();
        } else if (id == R.id.nav_youtube) {
            onClickYouTube();
        } else if (id == R.id.nav_facebook) {
            onFacebook();
        } else if (id == R.id.nav_share) {
            onClickShare();
        } else if (id == R.id.nav_about) {
            onClickAbout();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onClickAbout() {
        Intent i = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(i);
    }

    private void onClickShare() {
        shareAction(getString(R.string.share_message) + " " + Utils.getAppPlayStoreURL());
    }

    private void onFacebook() {

        try {
            this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + FACEBOOK_PAGE_ID));
            startActivity(intent);
            setBlogAsSeelcted();
        } catch (Exception e) {
            webView.loadUrl(FACEBOOK_URL);
        }
    }

    private void onClickYouTube() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(YOUTUBE_URL));
            startActivity(intent);
            setBlogAsSeelcted();
        } catch (Exception e) {
            webView.loadUrl(YOUTUBE_URL);
        }
    }

    private void onBlogClick() {
        webView.loadUrl(BLOG_URL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                onFabClick();
                break;
        }
    }

    private void onFabClick() {
        shareAction(webView.getUrl());
    }

    @Override
    public void onRefresh() {
        webView.reload();
    }

    @Override
    public void onWebViewStartLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onWebViewStopLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void shareAction(String message) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, getString(R.string.share_using)));
    }
}
