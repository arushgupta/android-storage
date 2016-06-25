package com.arush.android.hellostorage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    int count1;
    static int count2;
    int count3;
    int count4;

    TextView count1TextView;
    TextView count2TextView;
    TextView count3TextView;
    TextView count4TextView;

    TextView textView;

    EditText editText;

    private final String KEY_COUNT3 = "count3";
    private final String KEY_COUNT4 = "count4";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNT3, count3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            count3 = savedInstanceState.getInt(KEY_COUNT3);
        }

        count1TextView = (TextView) findViewById(R.id.count1TextView);
        count2TextView = (TextView) findViewById(R.id.count2tTextView);
        count3TextView = (TextView) findViewById(R.id.count3TextView);
        count4TextView = (TextView) findViewById(R.id.count4TextView);

        textView = (TextView) findViewById(R.id.textView);

        editText = (EditText) findViewById(R.id.editText);

        updateDisplay();

    }

    private void updateDisplay() {
        count1TextView.setText("Count = " + count1);
        count2TextView.setText("Count = " + count2);
        count3TextView.setText("Count = " + count3);
        count4TextView.setText("Count = " + count4);
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

        switch (id) {
            case R.id.action_increment:
                count1++;
                count2++;
                count3++;
                count4++;
                updateDisplay();
                return true;
            case R.id.action_clear:
                count1 = count2 = count3 = count4 = 0;
                updateDisplay();
                return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY_COUNT4, count4);
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        count4 = sp.getInt(KEY_COUNT4, 0);
        updateDisplay();
    }

    public void doResRead(View view) {
        try {
            InputStream is = getResources().openRawResource(R.raw.readme);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            String s = new String(buffer);
            textView.setText(s);
        } catch (IOException e) {
            //e.printStackTrace();
            textView.setText(e.getMessage());
        }
    }

    public void doAssetsRead(View view) {
        try {
            InputStream is = getAssets().open("readme.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            String s = new String(buffer);
            textView.setText(s);
        } catch (IOException e) {
            //e.printStackTrace();
            textView.setText(e.getMessage());
        }
    }

    private final String FILENAME = "myfile.txt";

    public void doWriteInternal(View view) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
            String s = editText.getText().toString();
            byte[] buffer = s.getBytes();
            fos.write(buffer);
            fos.close();

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            textView.setText(e.getMessage());
        } catch (IOException e) {
            //e.printStackTrace();
            textView.setText(e.getMessage());
        }
    }

    public void doReadInternal(View view) {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            String s = new String(buffer);
            textView.setText(s);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            textView.setText(e.getMessage());
        } catch (IOException e) {
            //e.printStackTrace();
            textView.setText(e.getMessage());
        }
    }
}
