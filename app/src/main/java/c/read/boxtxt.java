package c.read;

/**
 * Created by Vidyadheesha D N on 16-10-2016.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class boxtxt extends Activity {
    GridView gv;
    List<String> ITEM_LIST;
    List<Integer> IMG_LIST;
    //ArrayAdapter<String> arrayadapter;
    //ArrayAdapter<Integer []> arrayadapte;



    public ArrayList<String>  arrayList=new ArrayList<String>();
    Set<String> set = new HashSet<String>();
    Set<String> rset = new HashSet<String>();


    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
    public static Integer [] prgmImages={R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};

    public static String [] name;
    public static Integer [] img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridimg);



        Bundle bundle=getIntent().getExtras();
        String stff=bundle.getString("stuff");

        //listview = (ListView)findViewById(R.id.listView);
        SharedPreferences prefs=this.getSharedPreferences("yourPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=prefs.edit();


        rset=prefs.getStringSet("yourKey",null);
        arrayList=new ArrayList<String>(rset);
        arrayList.add(arrayList.size(),stff);

        set.addAll(arrayList);
        edit.putStringSet("yourKey", set);
        edit.apply();

        //set = prefs.getStringSet("yourKey", null);
        arrayList=new ArrayList<String>(set);

        //prefs.edit().remove("yourKey").commit();

        for (int j = arrayList.size()-1; j >= 0; j--) {
            if(arrayList.get(j)=="")
                arrayList.remove(j);
        }




        ITEM_LIST = new ArrayList<String>(Arrays.asList(prgmNameList));
        IMG_LIST = new ArrayList<Integer>(Arrays.asList(prgmImages));

//        ITEM_LIST.add(ITEM_LIST.size(),"NEW");
//        ITEM_LIST.add(ITEM_LIST.size(),"NEW2");
//        IMG_LIST.add(IMG_LIST.size(),R.drawable.profile);
//        IMG_LIST.add(IMG_LIST.size(),R.drawable.v);


        //arrayadapter.notifyDataSetChanged();
        //arrayadapte.notifyDataSetChanged();


        gv=(GridView) findViewById(R.id.gridView1);
        gv.setAdapter(new CustomAdapter(this, arrayList,IMG_LIST));


    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

}