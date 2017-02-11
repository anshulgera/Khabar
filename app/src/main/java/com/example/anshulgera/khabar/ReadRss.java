package com.example.anshulgera.khabar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by AnshulGera on 14-07-2016.
 */
public class ReadRss extends AsyncTask<Void, Void, Void> {
    //Context for dialog to be shown before fetching news item
    Context dialog;
    String address=MainActivity.url;
    ProgressDialog progressDialog;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    URL url;
    public ReadRss(Context dialog,RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
        this.dialog = dialog;
        progressDialog = new ProgressDialog(dialog);
        progressDialog.setMessage("Loading News Items...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();//show dialog before fetching data
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        MyAdapter adapter=new MyAdapter(dialog,feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(dialog));
        recyclerView.addItemDecoration(new VerticalSpace(50));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(Fetchdata());

        return null;
    }

    private void ProcessXml(Document data) {
        if (data != null) {
            feedItems=new ArrayList<>();//dynamic array of feeditems

            Element root = data.getDocumentElement();//store root element

            Node channel = root.getChildNodes().item(1);//channel-first child of root tag-storing it in Node object

            NodeList items = channel.getChildNodes();//stores all child of channel element
            for (int i = 0; i < items.getLength(); i++) {
                Node currentchild = items.item(i);

                if (currentchild.getNodeName().equalsIgnoreCase("item")) {

                    FeedItem item=new FeedItem();//creating new feeditem for every new item

                    NodeList itemchilds = currentchild.getChildNodes();//store child of current item in list


                    //another loop to go through all child of item
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node current = itemchilds.item(j);

                        if (current.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(current.getTextContent());
                        }
                        else if(current.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(current.getTextContent());
                        }
                        else if(current.getNodeName().equalsIgnoreCase("media:content")){
                            String url = current.getAttributes().item(0).getTextContent();
                            item.setMediaUrl(url);
                        }

                    }
                    feedItems.add(item);


                }
            }
        }
    }

    public Document Fetchdata() {
        try {
            //passing address of feed in url
            url = new URL(address);
            //surround it with try catch
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //give connection to object of inputstream
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //parsing inputstream to document to return xml doc
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            //general exception
            e.printStackTrace();
            return null;
        }
    }
}
