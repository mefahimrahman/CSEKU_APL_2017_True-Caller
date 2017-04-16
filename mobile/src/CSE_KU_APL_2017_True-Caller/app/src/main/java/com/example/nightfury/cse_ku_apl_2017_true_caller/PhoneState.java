package com.example.nightfury.cse_ku_apl_2017_true_caller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
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

/**
 * Created by Night Fury on 4/16/2017.
 */

public class PhoneState extends BroadcastReceiver {

    static String  state, phoneNumber = null;
    MainActivity main;
    public static String tst="hi";
    @Override
    public void onReceive(final Context context, Intent intent) {
        main = new MainActivity();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            state = extras.getString(TelephonyManager.EXTRA_STATE);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.w("MY_DEBUG_TAG", phoneNumber);

                final String Number = phoneNumber;

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
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
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
                        return "Unknown";
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
                        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        //AlertDialog.Builder b= new AlertDialog.Builder(context);
                        //b.setTitle("Caller");
                        //b.setMessage(tst);
                        //AlertDialog al = b.create();
                        //al.show();
                    }
                } ;
                // xxx(number);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //Toast.makeText(context, tst, Toast.LENGTH_LONG).show();
            }
        }
    }


}
