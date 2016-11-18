package c.read;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.MathContext;

import static android.R.attr.data;
import static android.widget.ImageView.ScaleType.FIT_XY;


public class shownote extends Activity {
    TextView textView;
    Bitmap imageBitmap;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int READ_BLOCK_SIZE = 100;
    public final static String APP_PATH_SD_CARD = "/DesiredSubfolderName/";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "thumbnails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        //textView = (TextView) findViewById(R.id.textView);
        Button savenote = (Button) findViewById(R.id.savenote);
        Button cam = (Button) findViewById(R.id.cam);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        RelativeLayout.LayoutParams lp=null;
        Intent intent = getIntent();
        String item = intent.getStringExtra("selected-item");
        //textView.setText("you selected "+item);

        EditText tv = (EditText) findViewById(R.id.editText);
        try {
            FileInputStream fileIn = openFileInput(item);
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
            tv.setText(s);

            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;

            for(int i=1;i<=numoffiles(fullPath);i++) {
                imageBitmap = getThumbnail("desiredFilename"+i+".png");
                lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ImageView mImageView = new ImageView(this);
                // LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                //lp.addRule(RelativeLayout.RIGHT_OF,getThumbnail("desiredFilename"+(i)+".png").getGenerationId());


                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 200, 200, true);
                mImageView.setImageBitmap(imageBitmap);

                mImageView.setLayoutParams(lp);
                //lp.addRule(mImageView);

                layout.addView(mImageView,lp);
            }

//            imageBitmap=getThumbnail("desiredFilename.png");
//
//            ImageView mImageView = new ImageView(this);
//
//            imageBitmap = Bitmap.createScaledBitmap(imageBitmap,500, 500, true);
//            mImageView.setImageBitmap(imageBitmap);
//            RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
//            layout.addView(mImageView);

        } catch (Exception e) {
            e.printStackTrace();
        }


        savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try { //Textname=title.getText().toString();
                    EditText tvx = (EditText) findViewById(R.id.editText);
                    Intent intent = getIntent();
                    String item1 = intent.getStringExtra("selected-item");
                    FileOutputStream fileout = openFileOutput(item1, MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(tvx.getText().toString());
                    outputWriter.close();

                    //display file saved message
                    Toast.makeText(getBaseContext(), "File saved successfully!",
                            Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            saveImageToExternalStorage(imageBitmap);

        }
    }


    public boolean saveImageToExternalStorage(Bitmap image) {
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
        int n;

        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            OutputStream fOut = null;
            n=numoffiles(fullPath);
            File file = new File(fullPath, "desiredFilename"+(n+1)+".png");
            file.createNewFile();
            fOut = new FileOutputStream(file);

            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());


            return true;

        } catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
            return false;
        }
    }


    public Bitmap getThumbnail(String filename) {
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
        Bitmap thumbnail = null;

        try {
            if (isSdReadable() == true) {
                thumbnail = BitmapFactory.decodeFile(fullPath + "/" + filename);
            }
        } catch (Exception e) {
            Log.e("getTstorage", e.getMessage());
        }

        if (thumbnail == null) {
            try {
                File filePath = getFileStreamPath(filename);
                FileInputStream fi = new FileInputStream(filePath);
                thumbnail = BitmapFactory.decodeStream(fi);
            } catch (Exception ex) {
                Log.e("getThumbnorage", ex.getMessage());
            }
        }
        return thumbnail;
    }


    public boolean isSdReadable() {
        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {

            mExternalStorageAvailable = true;
            Log.i("isSdReadable", "External storage card is readable.");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            Log.i("isSdReadable", "External storage card is readable.");
            mExternalStorageAvailable = true;
        } else {

            mExternalStorageAvailable = false;
        }
        return mExternalStorageAvailable;
    }

   int numoffiles(String path){
      File file = new File(path);
      File[] list = file.listFiles();
      int count = 0;
      for (File f : list) {
          String name = f.getName();
          if (name.endsWith(".png") )
              count++;
          System.out.println("170 " + count);
      }
       return count;
   }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.second, menu);
//        return true;
//    }

}