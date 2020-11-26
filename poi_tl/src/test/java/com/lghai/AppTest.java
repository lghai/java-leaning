package com.lghai;

import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class AppTest
{

    @Test
    public void shouldAnswerWithTrue()
    {
        String path = "A:\\1111.m3u8";
        String dir = "D:\\360WiFi\\";
        String Referer = "https://avgle.com/video/EHCO3OkATOO/%E3%81%8A%E3%82%8C%E3%81%AE%E6%9C%80%E6%84%9B%E3%81%AE%E5%A6%B9%E3%81%8C%E4%B8%AD%E5%B9%B4%E3%82%AA%E3%83%A4%E3%82%B8%E3%81%A8%E3%81%AE%E6%9C%9B%E3%81%BE%E3%81%AA%E3%81%84%E7%B5%90%E5%A9%9A%E3%82%92%E5%BC%B7%E3%81%84%E3%82%89%E3%82%8C%E3%81%9F-%E6%A4%8E%E5%90%8D%E3%81%9D%E3%82%89-miae-056";
        try {
            FileReader reader = new FileReader(new File(path));
            BufferedReader in = new BufferedReader(reader);
            String str = null ;
            int i = 0;
            while ((str = in.readLine())!=null){
                if (str.startsWith("#"))
                    continue;
                i++;
                String fileName = String.format("%04d",i)+".ts";

                HttpURLConnection connection = (HttpURLConnection) new URL(str).openConnection();
//                HttpClient httpclient = HttpClientBuilder.create().build();
//                HttpGet httpGet = new HttpGet(str);
                String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";
//                httpGet.setHeader("User-Agent",userAgent);
//                HttpResponse response = httpclient.execute(httpGet);

                connection.setRequestProperty("User-agent", userAgent);
                connection.setRequestProperty("Referer", Referer);
                connection.setRequestMethod("GET");
                int code = connection.getResponseCode();
                if (code == 200 || code == 206) {
                    InputStream is = connection.getInputStream();
                    System.out.println(str);
                    DataInputStream dataInputStream = new DataInputStream(is);

                    int length =0;
                    byte[] buf = new byte[1024*1024*8];


                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(dir+ fileName));
                    while ((length = dataInputStream.read(buf))!=-1){
                        dataOutputStream.write(buf,0,length);
                    }
                    dataOutputStream.close();
                    dataInputStream.close();

                    is.close();
                }
                connection.disconnect();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void shouldAnswerWithTrue1()
    {
        System.out.println(String.format("%04d",1));
    }
}
