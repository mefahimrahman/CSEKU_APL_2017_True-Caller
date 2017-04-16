package com.example.nightfury.cse_ku_apl_2017_true_caller;

/**
 * Created by Night Fury on 4/16/2017.
 */

//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//
///**
// * Created by John Player on 4/11/2017.
// */
//
//public class SearchNumberToDatabase extends AsyncTask<String, Void, String>{
//    String Number;
//    @Override
//    protected void onPreExecute()
//    {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//        Log.w("here "," vvv");
//        String input = "http://127.0.0.1/CallerNumber.php";
//        Number = params[0];
//        Log.w("BackGround   ", Number);
//        try {
//            URL url = new URL(input);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setDoInput(true);
//            OutputStreamWriterm OS = httpURLConnection.getOutputStream();
//            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
//
//            String data = URLEncoder.encode("CallerNumber", "UTF-8") + "=" + URLEncoder.encode(Number, "UTF-8");
//
//            bufferedWriter.write(data);
//            bufferedWriter.flush();
//            bufferedWriter.close();
//            OS.close();
//
//            InputStream inputStream = httpURLConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
//            String response = "";
//            String line = "";
//            while((line = bufferedReader.readLine()) != null)
//            {
//                response += line;
//            }
//
//            bufferedReader.close();
//            httpURLConnection.disconnect();
//            if(response == null)
//            {
//                return "unknown";
//            }
//            else {
//                return response;
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    protected void onProgressUpdate(Void...values)
//    {
//        super.onProgressUpdate(values);
//    }
//
//    @Override
//    protected void onPostExecute(String  result)
//    {
//       // Toast.makeText(this,result,Toast.LENGTH_LONG).show();
//    }
//}
