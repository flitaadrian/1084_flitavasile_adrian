package com.example.adrianflita.networking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpManager implements Callable<String> {

    private URL url;
    private HttpURLConnection connection;


    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    private final String urlAddress;

    public HttpManager(String urlAddress){
        this.urlAddress = urlAddress;
    }

    @Override
    public String call() throws Exception {
        try{

            url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder result = new StringBuilder();
            String linie;
            while ((linie = bufferedReader.readLine())!=null){
                result.append(linie);
            }

            return result.toString();

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            connection.disconnect();
        }
        return null;
    }
}