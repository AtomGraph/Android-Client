package com.atomgraph.client.android;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.atomgraph.client.android.model.Container;

import org.w3c.dom.Document;

import java.net.URI;

public class DocumentResponseListener implements Response.Listener<Document>
{

    private final ListView listView;
    private final Context appContext;

    public DocumentResponseListener(ListView listView, Context appContext)
    {
        this.listView = listView;
        this.appContext = appContext;
    }

    @Override
    public void onResponse(Document response) {
        Container current = new Container(URI.create(response.getDocumentURI()), response);
        ListAdapter adapter = new NodeListAdapter(current, getApplicationContext());
        getListView().setAdapter(adapter);
    }

    public ListView getListView()
    {
        return listView;
    }

    public Context getApplicationContext()
    {
        return appContext;
    }

}
