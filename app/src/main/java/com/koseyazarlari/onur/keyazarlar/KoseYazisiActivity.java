package com.koseyazarlari.onur.keyazarlar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class KoseYazisiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kose_yazisi);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle b = getIntent().getExtras();
        String value = ""; // or other values
        if(b != null)
            value = b.getString("link");

        new KoseYazisiGosterTask(KoseYazisiActivity.this, value).execute();



    }

}

class KoseYazisiGosterTask extends AsyncTask<Void, Void, String> {

    private Activity activity;
    ListView listView;
    String link;

    String koseYazisi = "";

    private ProgressDialog dialog;


    public KoseYazisiGosterTask(Activity activity, String link) {
        this.activity = activity;
        this.link = link;
    }
    @Override
    protected String doInBackground(Void... params) {
        String title ="";
        Document doc;
        try {
            doc = Jsoup.connect(this.link).timeout(10000).method(Connection.Method.GET).get();
            Elements koseHtml = doc.select("div.author-the-content");
            koseYazisi = koseHtml.html();

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    protected void onPostExecute(String result) {
        String mime = "text/html";
        String encoding = "utf-8";

        WebView view = (WebView)activity.findViewById(R.id.koseYazisiWebView);
        view.getSettings().setJavaScriptEnabled(false);
        view.loadDataWithBaseURL(null, koseYazisi, mime, encoding, null);

        WebSettings webSettings = view.getSettings();
        webSettings.setDefaultFontSize(50);
    }
}
