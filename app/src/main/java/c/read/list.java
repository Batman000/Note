package c.read;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class list extends Activity {
    ListView listview;
    Button add;
//Arrays.asList("Buenos Aires", "Córdoba", "La Plata")
    public ArrayList<String>  arrayList=new ArrayList<String>();
    ArrayList<String> sample=new ArrayList<String>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));
    public ArrayAdapter<String> adapter;
    Set<String> set = new HashSet<String>();
    Set<String> rset = new HashSet<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readlist);

        //add=(Button)findViewById(R.id.add);

        Bundle bundle=getIntent().getExtras();
        String stff=bundle.getString("stuff");

        listview = (ListView)findViewById(R.id.listView);
        SharedPreferences prefs=this.getSharedPreferences("yourPrefsKey",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=prefs.edit();

        //if(prefs.getStringSet("yourKey", null)!=null);

        rset=prefs.getStringSet("yourKey",null);
        arrayList=new ArrayList<String>(rset);
        arrayList.add(stff);

        set.addAll(arrayList);
        edit.putStringSet("yourKey", set);
        edit.apply();

        set = prefs.getStringSet("yourKey", null);
        arrayList=new ArrayList<String>(set);

        //prefs.edit().remove("yourKey").commit();

        adapter = new ArrayAdapter<String>(this, R.layout.list_view_row, R.id.listText,arrayList);
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new ListClickHandler());


}

    public void onBackPressed() {
        //Include the code here
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
        return;
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    public class ListClickHandler implements OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            TextView listText = (TextView) view.findViewById(R.id.listText);
            String text = listText.getText().toString();
            Intent intent = new Intent(list.this, shownote.class);
            intent.putExtra("selected-item", text);
            startActivity(intent);

        }

    }

}

