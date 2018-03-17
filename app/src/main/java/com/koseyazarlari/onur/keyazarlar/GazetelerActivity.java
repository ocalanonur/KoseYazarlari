package com.koseyazarlari.onur.keyazarlar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GazetelerActivity extends AppCompatActivity {

    private String[] gazeteler =
            {"SÖZCÜ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gazete_listesi_layout);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ListView gazetelerListView = (ListView) findViewById(R.id.gazetelerListView);

        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>
                (this, R.layout.gazete, R.id.gazeteAdiTextView, gazeteler);

        gazetelerListView.setAdapter(veriAdaptoru);

        gazetelerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch( position )
                {
                    case 0:  Intent newActivity = new Intent(GazetelerActivity.this, YazarlarActivity.class);
                        startActivity(newActivity);
                        break;
                }
            }
        });
    }
}
