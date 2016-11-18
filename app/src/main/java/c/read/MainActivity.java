package c.read;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    EditText textmsg;
    TextView title;
    static final int READ_BLOCK_SIZE = 100;
    String Textname;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textmsg=(EditText)findViewById(R.id.Content);
        textmsg.setSelection(textmsg.getText().length());

        title=(EditText)findViewById(R.id.Title);

        Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),boxtxt.class);
                Bundle bu=new Bundle();
                bu.putString("stuff", "");
                intent.putExtras(bu);
                startActivity(intent);
            }
        });

    }

    public void WriteBtn(View v) {
        // add-write text into file
        try { Textname=title.getText().toString();
            FileOutputStream fileout=openFileOutput(Textname, MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(textmsg.getText().toString());
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

            Intent in = new Intent(this, boxtxt.class);
            Bundle bundle=new Bundle();
            bundle.putString("stuff",Textname);
            in.putExtras(bundle);
            startActivity(in);





        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    }











