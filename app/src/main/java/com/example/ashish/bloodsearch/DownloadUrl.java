package com.example.ashish.bloodsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUrl {
    public String readUrl(String myurl) throws IOException {

        String data="";
        InputStream inputStream=null;
        HttpURLConnection urlConnection=null;

        try {

            URL url=new URL(myurl);
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.connect();
            inputStream=urlConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer=new StringBuffer();
            String line="";
            while((line = bufferedReader.readLine())!=null) {
                stringBuffer.append(line);
            }
            data=stringBuffer.toString();
            bufferedReader.close();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
