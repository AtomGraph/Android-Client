package com.atomgraph.client.android.http;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DocumentRequest extends Request<Document> {

    private final Response.Listener<Document> listener;
    private final DocumentBuilderFactory builderFactory;

    public DocumentRequest(String url, Response.Listener<Document> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
        this.builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
    }

    @Override
    protected Response<Document> parseNetworkResponse(NetworkResponse response) {
        try {
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            return Response.success(docBuilder.parse(new ByteArrayInputStream(response.data)), HttpHeaderParser.parseCacheHeaders(response));
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Document response) {
        getListener().onResponse(response);
    }

    public Response.Listener<Document> getListener()
    {
        return listener;
    }
}
