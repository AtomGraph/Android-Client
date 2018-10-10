package com.atomgraph.client.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.atomgraph.client.android.http.DocumentRequest;
import com.atomgraph.client.android.http.MySingleton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = findViewById(R.id.list_view);

        String url = "https://linkeddatahub.com:4443/docs/";
        try {
            DocumentRequest request = new DocumentRequest(url, new DocumentResponseListener(listView, getApplicationContext()), null);
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

        // https://www.tutlane.com/tutorial/android/android-xml-parsing-using-dom-parser

//        Document doc = null;
//        try
//        {
//            InputStream is = getAssets().open("docs.rdf");
//            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//            builderFactory.setNamespaceAware(true);
//            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
//            doc = docBuilder.parse(is);
//        }
//        catch (IOException | SAXException | ParserConfigurationException e)
//        {
//            e.printStackTrace();
//        }

//        Container current = new Container(URI.create(url), doc);
//        ListAdapter adapter = new NodeListAdapter(current, getApplicationContext());
//        listView.setAdapter(adapter);
        //listView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//        listView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,
//                                    long id) {
//                Intent intent = new Intent(MainActivity.this, SendMessage.class);
//                String message = "abc";
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);
//            }
//        });

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
