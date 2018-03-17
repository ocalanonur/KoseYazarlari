package com.koseyazarlari.onur.keyazarlar;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Onur on 30-Aug-17.
 */

public class KoseYazisiArrayAdapter extends ArrayAdapter<KoseYazisi> {

    private final ArrayList<KoseYazisi> data;

    public KoseYazisiArrayAdapter(@NonNull Context context, @LayoutRes int textViewResourceId, ArrayList<KoseYazisi> data) {
        super(context, textViewResourceId);
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.yazar, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        KoseYazisi i = data.get(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView tt = (TextView) v.findViewById(R.id.yazarAdiTextView);
            TextView ttd = (TextView) v.findViewById(R.id.koseYazisiBaslikTextView);
            TextView mt = (TextView) v.findViewById(R.id.tarihTextView);
            //ImageView mtd = (ImageView) v.findViewById(R.id.yazarImageView);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (tt != null){
                tt.setText(i.yazarAdi);
            }
            if (ttd != null){
                ttd.setText(i.koseYazisiAdi);
            }
            if (mt != null){
                mt.setText(i.tarih);
            }
        }

        // the view must be returned to our activity
        return v;

    }


}
