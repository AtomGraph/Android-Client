package com.atomgraph.client.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;

import com.atomgraph.client.android.model.Container;

public class NodeListAdapter extends BaseAdapter implements ListAdapter
{

    private final Container container;
    private final Context context;

    public NodeListAdapter(Container container, Context context)
    {
        this.container = container;
        this.context = context;
    }

    public Container getContainer()
    {
        return container;
    }

    @Override
    public int getCount()
    {
        return getContainer().getChildren().size();
    }

    @Override
    public Object getItem(int position)
    {
        return getContainer().getChildren().get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return getContainer().getChildren().get(position).getURI().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Button button = new Button(getContext());
        button.setText(getContainer().getChildren().get(position).getTitle());
        return button;
    }

    public Context getContext()
    {
        return context;
    }

}
