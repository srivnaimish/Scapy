package com.riseapp.test;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScannedResults extends AppCompatActivity {

    ArrayList<String> list;
    String type;
    ListView listView;
    Toolbar toolbar;
    RelativeLayout empty,non_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_results);
        list = getIntent().getStringArrayListExtra("str");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        empty= (RelativeLayout) findViewById(R.id.empty_state);
        non_empty= (RelativeLayout) findViewById(R.id.non_empty);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        type = getIntent().getStringExtra("type");
        Set<String> hs = new HashSet<>();
        hs.addAll(list);
        list.clear();
        list.addAll(hs);

        TextView tx = (TextView) findViewById(R.id.result_count);
        if (list.size() == 0) {
           empty.setVisibility(View.VISIBLE);
            non_empty.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.GONE);
            non_empty.setVisibility(View.VISIBLE);
            String s = list.size() + " results found";
            tx.setText(s);
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.row, list);
            listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (type.equalsIgnoreCase("url")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(i)));

                        try {
                            startActivity(intent);
                        } catch (Exception e) {

                            Snackbar snackbar = Snackbar.make(listView, list.get(i) + " is not a Valid Url", 4000);
                            View v = snackbar.getView();
                            v.setBackgroundResource(R.color.colorPrimaryDark);
                            snackbar.show();
                        }

                    } else if (type.equalsIgnoreCase("email")) {
                        if (Patterns.EMAIL_ADDRESS.matcher(list.get(i)).matches()) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                            String addresses[] = {list.get(i)};
                            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                            intent.putExtra(Intent.EXTRA_SUBJECT, "");
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        } else {
                            Snackbar snackbar = Snackbar.make(listView, list.get(i) + " is not a Valid Email Id", 4000);
                            View v = snackbar.getView();
                            v.setBackgroundResource(R.color.colorPrimaryDark);
                            snackbar.show();
                        }
                    } else if (type.equalsIgnoreCase("phone")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + list.get(i)));
                        startActivity(intent);

                    } else if (type.equalsIgnoreCase("search")) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            String query = URLEncoder.encode(list.get(i), "utf-8");
                            String url = "http:/www.google.com/search?q=" + query;
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    view.setBackgroundResource(R.drawable.row);
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Data", list.get(i));
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ScannedResults.this,"Copied to Clipboard",Toast.LENGTH_SHORT).show();

                    return true;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getIntent().removeExtra("str");
        finish();
    }


}
