package com.atomgraph.client.android.http;

import android.support.annotation.Nullable;
import android.util.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DocumentRequest extends Request<Document>
{

    private static final String ACCEPT = "Accept"; // no header constants in Volley?
    private static final String RDF_XML_MEDIA_TYPE = "application/rdf+xml";

    private final Response.Listener<Document> listener;
    private final DocumentBuilderFactory builderFactory;
    private final Map<String, String> headers = new HashMap<>();

    public DocumentRequest(String url, Response.Listener<Document> listener, @Nullable Response.ErrorListener errorListener) throws AuthFailureError {
        super(Method.GET, url, errorListener);
        this.listener = listener;
        this.builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        setShouldCache(false); // disable request caching
        headers.put(ACCEPT, RDF_XML_MEDIA_TYPE); // request RDF/XML media type
    }

    @Override
    public Map<String, String> getHeaders()
    {
        return headers;
    }

    @Override
    protected Response<Document> parseNetworkResponse(NetworkResponse response) {
        try {
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new ByteArrayInputStream(response.data));
            doc.setDocumentURI(getUrl());
            return Response.success(doc, HttpHeaderParser.parseCacheHeaders(response));
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
