package com.example.nightfury.cse_ku_apl_2017_true_caller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.R.*;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    EditText et;
    ImageButton im1, im2;
    public static String tst = "yes";
    Database db;
    public final static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this);

        tv = (TextView) findViewById(R.id.contacts);
        et = (EditText) findViewById(R.id.input);
        im1 = (ImageButton) findViewById(R.id.search);

        //use cursor to keep any type of data
        //take all mobile contacts database in cursor

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.
                Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {

            //get name and number from cursor using column index

            String name = phones.getString(phones.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            String phoneNumber = phones.getString(phones.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER));

            // Inserting data to local database

            db.insertData(name, phoneNumber);

        }
        phones.close();

        // Checking Network Connection and send data to central Database

        if (isNetworkAvaliable(MainActivity.this)) {
            Cursor res = db.showAllData();
            if (res.getCount() != 0) {
                while (res.moveToNext()) {
                    Background bcground = new Background(this);
                    bcground.execute(res.getString(1), res.getString(2));
                    tv.append("Name: " + res.getString(1) + "\n");
                    tv.append("Number: " + res.getString(2) + "\n\n");
                }
            }

            // Empty the local database after uploading the data

            db.delete_local_database();
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                return;
            }
        }
    }

    public void CallerNumber(String number) {
        // SearchNumberToDatabase searchNumberToDatabase = new SearchNumberToDatabase(this,number);
        Log.w("MAIN CLASS", number);
        final String Number = number;
        // final String res;
        //searchNumberToDatabase.execute();
        //new SearchNumberToDatabase().execute(number);
        // new SearchNumberToDatabase().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            String Num =null;
            @Override
            protected void onPreExecute ()
            {
                Log.e("onpre","yes");
                super.onPreExecute();
            }
            @Override
            protected String doInBackground (Void...params){
                String sx = "";
                for(int i=0;i<Number.length();++i)
                {
                    if(Number.charAt(i)!='+' && Number.charAt(i)!=' ' && Number.charAt(i)!='-' && Number.charAt(i)!='%')
                    {
                        sx += Number.charAt(i);
                    }
                }
                Log.w("here ", " vvv");
                String num = null;
                try
                {
                    num = URLEncoder.encode(sx,"UTF-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                //Number = params[0];
                Log.w("BackGround   ", num);
                try {
                    Log.w("try","trying");
                    URL url = new URL("http://192.168.43.2/CallerNumber.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                    String data = URLEncoder.encode("CallerNumber", "UTF-8") + "=" + URLEncoder.encode(num, "UTF-8");
                    Log.e("data",data);
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String response = "";
                    String line = "";
                    if(bufferedReader.readLine()==null)
                    {
                        Log.d("val","null");
                    }
                    else
                    {
                        Log.e("val","not null");
                    }
                    String fres="";
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.d("true",line);
                        response += line;
                        if(line.length() > fres.length())
                        {
                            fres = line;
                        }
                    }
                    Log.d("after","that");
                    bufferedReader.close();
                    httpURLConnection.disconnect();
                    if (response == null) {
                        return "unknown";
                    } else {
                        return fres;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate (Void...values)
            {
                Log.e("onupdate","yes");
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute (String result)
            {
                Log.w("onres",result);
                tst = result;
                //fu(result);
                //final String res = result;
                //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        } ;
        // xxx(number);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //Toast.makeText(this, tst, Toast.LENGTH_LONG).show();

        // fu();
    }
    public void fu()
    {
        // String pk = res;
        Toast.makeText(MainActivity.this, tst, Toast.LENGTH_LONG).show();
    }
    public void xxx(String number) {
        Log.w("here ", " vvv");
        String input = "http://192.168.43.2/CallerNumber.php";
        String Number = number;
        Log.w("BackGround   ", number);
        try {
            URL url = new URL(input);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

            String data = URLEncoder.encode("CallerNumber", "UTF-8") + "=" + URLEncoder.encode(Number, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }

            bufferedReader.close();
            httpURLConnection.disconnect();
            if (response == null) {
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, response, Toast.LENGTH_LONG).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search(View view) {

    }

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }
}
