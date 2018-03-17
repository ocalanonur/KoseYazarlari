package com.koseyazarlari.onur.keyazarlar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class YazarlarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazarlar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ListView yazarlarListView = (ListView)findViewById(R.id.yazarlarListView);
        new MyTask(YazarlarActivity.this, yazarlarListView).execute();

        yazarlarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = new Intent(YazarlarActivity.this, KoseYazisiActivity.class);

                KoseYazisi dataModel = (KoseYazisi) parent.getItemAtPosition(position);
                Bundle b = new Bundle();
                b.putString("link", dataModel.link); //Your id
                newActivity.putExtras(b); //Put your id to your next Intent
                startActivity(newActivity);
            }
        });
    }
}

class MyTask extends AsyncTask<Void, Void, String> {

    private Activity activity;
    ListView listView;
    ArrayList<KoseYazisi> koseYazilariListesi;
    KoseYazisiAdapter adapter;

    private ProgressDialog dialog;


    public MyTask(Activity activity, ListView listView) {
        this.activity = activity;
        this.listView = listView;
    }
    @Override
    protected String doInBackground(Void... params) {
        String title ="";
        Document doc;
        try {
            doc = Jsoup.connect("http://www.sozcu.com.tr/kategori/yazarlar/").timeout(10000).method(Connection.Method.GET).get();
            Elements tumKoseYazilari = doc.select("div.cas-inner");

            koseYazilariListesi = new ArrayList<KoseYazisi>();
            KoseYazisi k = new KoseYazisi();

            for (Element koseYazisi: tumKoseYazilari) {
                k = new KoseYazisi();
                k.link = koseYazisi.select("a[href]").attr("href");
                k.yazarAdi = koseYazisi.select("div.columnist-name").first().text();
                k.tarih = koseYazisi.select("span.date").first().text();
                k.koseYazisiAdi = koseYazisi.select("div.c-text").first().text();
                koseYazilariListesi.add(k);
            }




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
        //if you had a ui element, you could display the title
        adapter = new KoseYazisiAdapter(activity, R.layout.yazar, koseYazilariListesi);
        this.listView.setAdapter(this.adapter);
    }
}

 class KoseYazisiAdapter extends ArrayAdapter<KoseYazisi>{

    Context context;
    int layoutResourceId;
    ArrayList<KoseYazisi> data = null;

    public KoseYazisiAdapter(Context context, int layoutResourceId, ArrayList<KoseYazisi> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        KoseYazisiHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new KoseYazisiHolder();
            holder.yaziLink = (TextView)row.findViewById(R.id.yaziTextView);
            holder.yazarAdi = (TextView)row.findViewById(R.id.yazarAdiTextView);
            holder.tarih = (TextView)row.findViewById(R.id.tarihTextView);
            holder.koseYazisiBaslik = (TextView)row.findViewById(R.id.koseYazisiBaslikTextView);

            row.setTag(holder);
        }
        else
        {
            holder = (KoseYazisiHolder)row.getTag();
        }

        KoseYazisi koseYazisi = data.get(position);
        holder.yazarAdi.setText(koseYazisi.yazarAdi);
        holder.tarih.setText(koseYazisi.tarih);
        holder.koseYazisiBaslik.setText(koseYazisi.koseYazisiAdi);
        holder.yaziLink.setText(koseYazisi.link);

        return row;
    }

    static class KoseYazisiHolder
    {
        TextView yaziLink;
        TextView yazarAdi;
        TextView tarih;
        TextView koseYazisiBaslik;
    }
}
