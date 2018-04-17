package com.example.administrator.findanimal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Random;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

        private static final String TAG_LOG = GameActivity.class.getSimpleName();
        private static final int MY_PERMISSIONS_REQUEST_WRITE_LOCAL_STORAGE = 1210;
        //public boolean right;

        private TextView text;
        Random random=new Random();
        final int stringNum = random.nextInt(20);
        final int idNum = random.nextInt(3);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.game);

            // Connect the Button and set the onClick listener
            listFilesInAssets();
            copyAssetsFileIntoSdCard();

            //avoid images repeat
            HashSet<Integer> integerHashSet=new HashSet<Integer>();
            integerHashSet.add(stringNum);

            int[] imagesid = {R.id.imageView,R.id.imageView2,R.id.imageView3,R.id.imageView4};
            String[] filenames = {"png/bear/512w/bearArtboard 1512.png","png/bird/512w/birdArtboard 1512.png","png/cat/512w/catArtboard 1512.png",
                    "png/elephant/512w/elephantArtboard 1512.png", "png/fish/512w/fishArtboard 1512.png","png/flower/512w/flowerArtboard 1512.png",
                    "png/giraffe/512w/giraffeArtboard 1512.png","png/honey/512w/honeyArtboard 1512.png","png/house/512w/houseArtboard 1512.png",
                    "png/hypo/512w/hypoArtboard 1512.png","png/kangaroo/512w/kangarooArtboard 1512.png","png/leo/512w/leoArtboard 1512.png",
                    "png/lion/512w/lionArtboard 1512.png","png/pig/512w/pigArtboard 1512.png","png/rhino/512w/rhinoArtboard 1512.png",
                    "png/round pixel bg/512w/round_pxArtboard 1512.png", "png/smile_great/512w/smileyArtboard 1512.png","png/sun/512w/sunArtboard 1512.png",
                    "png/tiger/512w/tigerArtboard 1512.png","png/wolf/512w/wolfArtboard 1512.png"};


            String[] textcontent = {"bear","bird","cat","elephant","fish","flower","giraffe","honey","house", "hypo","kangaroo",
                    "leo","lion","pig","rhino","round","smile","sun","tiger","wolf"};//,"like","skull_go"

            readImageFromAssets(imagesid[idNum],filenames[stringNum]);
            text = (TextView) findViewById(R.id.textView);
            text.setText(textcontent[stringNum]);

            for(int i=0;i<4;i++){
                //avoid answer-images repeat
                if(i == idNum) i++;
                int tempNum = random.nextInt(20);
                if(!integerHashSet.contains(tempNum)) integerHashSet.add(tempNum);
                else tempNum--;
                readImageFromAssets(imagesid[i],filenames[tempNum]);
            }



        }

        /**
         * Lists the files that are in the assets folder and open its content
         */

        private void listFilesInAssets() {
            String[] fileNames;
            try {
                fileNames = getAssets().list("");
            /*for (int i = 0; i < fileNames.length; i++) {
                Log.d(TAG_LOG, "listFilesInAssets(): " + i + " - " + fileNames[i]);
                accessAssetsTwo(fileNames[i]);
            }*/
            } catch (IOException e) {
                Log.e(TAG_LOG, "listFilesInAssets() : " + e.toString());
            }


        }

        /**
         * Copies the files from the Assets to the the SD card
         */

        private void getAssetAppFolder() {
            String[] fileNames;
            try {
                fileNames = getAssets().list("");
                for (int i = 0; i < fileNames.length; i++) {
                    try {
                        InputStream inputStream = getAssets().open(fileNames[i]);
                        copyToDisk(fileNames[i], inputStream);
                    } catch (IOException e) {
                        Log.e(TAG_LOG, "getAssetAppFolder() / copyToDisk: " + e.toString());
                    }

                }
            } catch (IOException e) {
                Log.e(TAG_LOG, "getAssetAppFolder() : " + e.toString());
            }


        }

        /**
         * Creates a file in the SdCard based on the inputStream provided
         *
         * @param filename    the file name as the copied file
         * @param inputStream for creating the new file
         * @throws IOException
         */
        public void copyToDisk(String filename, InputStream inputStream) throws IOException {
            int size;
            byte[] buffer = new byte[2048];

            //The location for saving the file
            File sdCard = Environment.getExternalStorageDirectory();
            File directoryDestination = new File(sdCard.getAbsolutePath() + "/backupAssets");

            if (!directoryDestination.exists() || !directoryDestination.isDirectory()) {
                directoryDestination.mkdirs();
            }


            FileOutputStream fileOutputStream = new FileOutputStream(directoryDestination + "/" + filename);
            BufferedOutputStream bufferOut = new BufferedOutputStream(fileOutputStream, buffer.length);

            while ((size = inputStream.read(buffer, 0, buffer.length)) != -1) {
                bufferOut.write(buffer, 0, size);
            }
            bufferOut.flush();
            bufferOut.close();
            inputStream.close();
            fileOutputStream.close();

        }

        /**
         * Insures permissions are granted
         */
        private void copyAssetsFileIntoSdCard() {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_LOCAL_STORAGE);

                    // MY_PERMISSIONS_REQUEST is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
                /* Copy the files that are in the Assets into the SD card */
                try {
                    getAssetAppFolder();
                } catch (Exception e) {
                    Log.e(TAG_LOG, "getAssetAppFolder()");
                }

                /* END: Copy the files that are in the Assets into the SD card */

            }
        }

        /**
         * Opens the image feilong.jpeg from the assets and use it in a view
         */
        private void readImageFromAssets(int id,String filename) {
            InputStream inputStream;
            try {
                inputStream = getAssets().open(filename);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ((ImageView) findViewById(id)).setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    @Override
    public void onClick(View v) {
           /* switch (v.getId()){
                case  R.id.imageView:
                    if(v.getId() == idNum)
                        Toast.makeText(this,"you win",Toast.LENGTH_SHORT).show();//right = true;
                    else Toast.makeText(this,"you lose",Toast.LENGTH_SHORT).show();//right = false;
                    break;
                case  R.id.imageView2:
                    if(v.getId() == idNum)
                        Toast.makeText(this,"you win",Toast.LENGTH_SHORT).show();//right = true;
                    else Toast.makeText(this,"you lose",Toast.LENGTH_SHORT).show();//right = false;
                    break;
                case  R.id.imageView3:
                    if(v.getId() == idNum)
                        Toast.makeText(this,"you win",Toast.LENGTH_SHORT).show();//right = true;
                    else Toast.makeText(this,"you lose",Toast.LENGTH_SHORT).show();//right = false;
                    break;
                case  R.id.imageView4:
                    if(v.getId() == idNum)
                        Toast.makeText(this,"you win",Toast.LENGTH_SHORT).show();//right = true;
                    else Toast.makeText(this,"you lose",Toast.LENGTH_SHORT).show();//right = false;
                    break;

            }*/
        }
/*
    /**  * 从Assets中读取图片   * @param activity  * @param fileName  * @return
    public static Bitmap getImageFromAssetsFile(Activity activity, String fileName) {
        Bitmap image = null;
        AssetManager am = activity.getResources().getAssets();
        try  {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**  * 获取目录下的所有文件的文件名  * @param context  * @return
    public static String[] get_img_list(Context context) {
        String[] list_image = null;
       try {
           //得到assets/build_img/目录下的所有文件的文件名，以便后面打开操作时使用
           return list_image = context.getAssets().list("processedimages");
           } catch (IOException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
            }
       return list_image; }
       */
}
