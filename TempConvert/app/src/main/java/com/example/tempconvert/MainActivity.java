package com.example.tempconvert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    EditText textF, textC;
    ListView listView;
    Button btnConvert;
    ArrayAdapter<String> adapter;
    ArrayList<String> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();

        restoreData(savedInstanceState);
    }

    private void restoreData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            textC.setText(savedInstanceState.getString("textC"));
            textF.setText(savedInstanceState.getString("textF"));

            history = savedInstanceState.getStringArrayList("history");
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textc", textC.getText().toString());
        outState.putString("textf", textF.getText().toString());

        outState.putStringArrayList("history", history);
    }

    private void addEvents() {
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtc = textC.getText().toString();
                String txtf = textF.getText().toString();

                int numc = txtc.length();
                int numf = txtf.length();

                double tc = 0, tf = 0;

                if (numc != 0 || numf != 0) {
                    if (numc != 0 && numf == 0) {
                        tc = Double.parseDouble(txtc);
                        tf = (tc * 9.0 / 5.0) + 32.0;
                        textF.setText(tf + "");
                        history.add(0, String.format("C->F: %s->%s", tc, tf));
                    } else {
                        tf = Double.parseDouble(txtf);
                        tc = (tf - 32.0) * 5.0 / 9.0;
                        history.add(0, String.format("F->C: %s->%s", tf, tc));
                        textC.setText(tc + "");
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });

        textF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textF.setText("");
                textC.setText("");
            }
        });
        textC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textF.setText("");
                textC.setText("");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = history.get(position);
                String[] content = item.split(":");

                String[] title = content[0].split("->");
                String[] tempConvert = content[1].split("->");
                if (title[0].trim().equals("F")) {
                    textF.setText(tempConvert[0].trim());
                    textC.setText(tempConvert[1].trim());
                } else {
                    textC.setText(tempConvert[0].trim());
                    textF.setText(tempConvert[1].trim());
                }
            }
        });
    }

    private void addControls() {
        textC = findViewById(R.id.textC);
        textF = findViewById(R.id.textF);

        listView = findViewById(R.id.listView);

        btnConvert = findViewById(R.id.btnConvert);

        history = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, history);

        listView.setAdapter(adapter);
    }
}