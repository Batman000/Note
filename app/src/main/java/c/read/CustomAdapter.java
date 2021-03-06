package c.read;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends BaseAdapter{

    List<String> result;
    Context context;
    List<Integer> imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context c, List<String> prgmNameList, List<Integer> prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=c;
        imageId= prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.programlist, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

        holder.tv.setText(result.get(position));
        holder.img.setImageResource(imageId.get(position));

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
               // Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
                TextView listText = (TextView) view.findViewById(R.id.textView1);
                String text = listText.getText().toString();
                Intent intent = new Intent(context, shownote.class);
                intent.putExtra("selected-item", text);
                context.startActivity(intent);
            }
        });

        return rowView;
    }

}