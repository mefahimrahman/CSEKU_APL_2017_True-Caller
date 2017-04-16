package com.example.nightfury.cse_ku_apl_2017_true_caller;

/**
 * Created by Night Fury on 4/16/2017.
 */

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
public class Background extends AsyncTask<String ,Void, String > {

    Context ctx;
    String Name,Number;

    public Background(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String ... params) {
        String input = "http://127.0.0.1/Connection.php";

        Name = params[0];
        Number = params[1];
        String sx = "";
        for(int i=0;i<Number.length();++i)
        {
            if(Number.charAt(i)!='+' && Number.charAt(i)!=' ' && Number.charAt(i)!='-' && Number.charAt(i)!='%')
            {
                sx += Number.charAt(i);
            }
        }
        try {


            URL url = new URL(input);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

            String data = URLEncoder.encode("NAME", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8") +"&" +
                    URLEncoder.encode("NUMBER", "UTF-8") + "=" + URLEncoder.encode(sx, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();

            InputStream IS = httpURLConnection.getInputStream();
            IS.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void...values)
    {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String  result)
    {
        //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }

}
