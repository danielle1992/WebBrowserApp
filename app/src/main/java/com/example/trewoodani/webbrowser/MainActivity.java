package com.example.trewoodani.webbrowser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {



    private List<Bookmark> bookmarkList = new ArrayList<Bookmark>();
    private List<History> historyList = new ArrayList<>();
    private ListView list;
    private ListView history;
    private ListViewAdapter listViewAdapter;
    private HistoryListViewAdapter historyListViewAdapter;
    private EditText edittext;
    private WebView myWebView;
    private String url;
    private String bookmarkTitle;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = (ListView)findViewById(R.id.bookmark_list);
        history = (ListView)findViewById(R.id.history_list);
        listViewAdapter = new ListViewAdapter(this, bookmarkList);
        historyListViewAdapter = new HistoryListViewAdapter(this, historyList);
        loadSharedPreferencesLogList();
        setWebBrowserView();
    }

    private void setWebBrowserView(){
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Web Browser");

        Button button = (Button)findViewById(R.id.button);
        edittext = (EditText)findViewById(R.id.editText);


        //might need to remove this
        /*if(!url.startsWith("www.")&& !url.startsWith("http://") && !url.startsWith("https://")){
            url = "www."+url;
        }
        if(!url.startsWith("http://") && !url.startsWith("https://")){
            url = "https://"+url;
        }*/

        myWebView = (WebView)findViewById(R.id.webView);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("https://www.google.com");

        ImageButton bookmark = (ImageButton)findViewById(R.id.bookmarkButton);
        //if (! edittext.contains("https://")){
          //  bookmark.setVisibility(View.INVISIBLE);
        //}
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBookmark();
            }
        });
        //go to website when user clicks "go" button
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                url = edittext.getText().toString();
                myWebView.loadUrl(url);
                addHistory();
            }
        });
    }

    private void setBookmarkView() {
        setContentView(R.layout.bookmark_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Bookmarks");

        list = (ListView)findViewById(R.id.bookmark_list);
        list.setAdapter(listViewAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setWebBrowserView();
                Bookmark item = bookmarkList.get(position);
                String url = item.getUrl();
                //String title = item.getTitle();
                myWebView.loadUrl(url);
            }
        });
        Button backBtn = (Button) findViewById(R.id.button2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWebBrowserView();
            }
        });
    }

    private void setHistoryView() {
        setContentView(R.layout.history_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("History");

        history = (ListView)findViewById(R.id.history_list);
        history.setAdapter(historyListViewAdapter);
        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setWebBrowserView();
                History item = historyList.get(position);
                String url = item.getCurrentUrl();
                myWebView.loadUrl(url);
            }
        });
        Button backBtn = (Button) findViewById(R.id.button2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWebBrowserView();
            }
        });
    }

    public void addBookmark(){

        final EditText taskEditText = new EditText(this);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Name:")
                .setMessage("Set a title for this bookmark")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookmarkTitle = String.valueOf(taskEditText.getText().toString());
                        String url = edittext.getText().toString();
                        bookmarkList.add(new Bookmark(bookmarkTitle,url));
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void addHistory() {
        date = new SimpleDateFormat("hh:mm:ss - E d.MMM.Y", Locale.getDefault()).format(new Date());
        String url = edittext.getText().toString();
        historyList.add(new History(date, url));

        saveSharedPreferencesLogList(historyList);
    }

    public void saveSharedPreferencesLogList(List<History> historyList) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());

        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(historyList);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }

    public List<History> loadSharedPreferencesLogList() {
        List<History> historyList = new ArrayList<History>();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            historyList = new ArrayList<History>();
        } else {
            Type type = new TypeToken<List<History>>() {
            }.getType();
            historyList = gson.fromJson(json, type);
        }
        return historyList;
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

        if (id == R.id.action_settings) {
            return true;
        }

        else if (id == R.id.action_bookmarks){
            //action
             setBookmarkView();
            return true;
        }

        else if (id == R.id.action_history){
            //action
             setHistoryView();
            return true;
        }

        else if (id == R.id.action_exit){
            //action
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
