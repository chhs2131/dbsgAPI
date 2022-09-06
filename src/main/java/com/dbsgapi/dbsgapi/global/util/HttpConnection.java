package com.dbsgapi.dbsgapi.global.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class HttpConnection {
    public String get(String apiUrl) throws Exception {
        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public String get(String apiUrl, Map<String,String> header) throws Exception {
        StringBuffer result = new StringBuffer();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // header값 추가
            Iterator<String> keys = header.keySet().iterator();
            while(keys.hasNext()) {
                String key = keys.next();
                urlConnection.setRequestProperty(key, header.get(key) );
            }

            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
