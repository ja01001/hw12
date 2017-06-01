package com.example.ja010.training601;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Main3Activity extends AppCompatActivity {
    ArrayList<String > list = new ArrayList<>();
    ArrayAdapter ad;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        lv = (ListView)findViewById(R.id.lv);

    }
    public void clcl(View v){
        ad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(ad);
        th.start();
    }
    Handler hd = new Handler();
    Thread th = new Thread(){
        @Override
        public void run() {
            URL url = null;
            try {
                url = new URL("https://news.google.com/news?cf=all&hl=ko&pz=1&ned=kr&topic=m&output=rss");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    final int itemCount = readData(urlConnection.getInputStream());
                    hd.post(new Runnable() {
                        @Override
                        public void run() {
                            ad.notifyDataSetChanged();
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    };

    int readData(InputStream is) throws ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(is);
            int datacount = parseDocument(document);
            return datacount;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private int parseDocument(Document doc) {
        Element docEle = doc.getDocumentElement();
        NodeList nodelist = docEle.getElementsByTagName("item");
        int count = 0;
        if ((nodelist != null) && (nodelist.getLength() > 0)) {
            for (int i = 0; i < nodelist.getLength(); i++) {
                String newsItem = getTagData(nodelist, i);
                if (newsItem != null) {
                    list.add(newsItem);
                    count++;
                }
            }
        }
        return count;
    }
    private String getTagData(NodeList nodelist, int index) {
        String newsItem = null;
        try {
            Element entry = (Element) nodelist.item(index);
            Element title = (Element) entry.getElementsByTagName("title").item(0);
            Element pubDate = (Element) entry.getElementsByTagName("pubDate").item(0);
            String titleValue = null;
            if (title != null) {
                Node firstChild = title.getFirstChild();
                if (firstChild != null) titleValue = firstChild.getNodeValue();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            Date date = new Date();

            newsItem = titleValue + "-" + simpleDateFormat.format(date);
        } catch (DOMException e) {
            e.printStackTrace();
        }
        return newsItem;
    }

}
