package com.example.sinki.bai55_albumslideshow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView imgHinh;
    CheckBox chkAuto;
    ImageButton btnPrevious,btnNext;

    int currentPosition = -1;
    ArrayList<String>album;
    ImageTask task;

    Timer timer = null;
    TimerTask timerTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXemHinhKeTiep();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyXemHinhDangTruoc();
            }
        });

        chkAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkAuto.isChecked())
                {
                    btnPrevious.setEnabled(false);
                    btnNext.setEnabled(false);
                    xuLyAutoShow();
                }
                else
                {
                    btnPrevious.setEnabled(true);
                    btnNext.setEnabled(true);
                    if(timer!=null)
                        timer.cancel();
                }
            }
        });
    }

    private void xuLyAutoShow() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            currentPosition++;
                            if(currentPosition == album.size())
                                currentPosition = 0;
                            ImageTask task = new ImageTask();
                            task.execute(album.get(currentPosition));
                        }
                        catch (Exception ex)
                        {
                            Log.e("loi",ex.toString());
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,0,5000);
    }

    private void xuLyXemHinhDangTruoc() {
        currentPosition--;
        if(currentPosition == -1)
            currentPosition = album.size()-1;

        task = new ImageTask();
        task.execute(album.get(currentPosition));
    }

    private void xuLyXemHinhKeTiep() {
        currentPosition++;
        if(currentPosition == (album.size()))
            currentPosition = 0;

        task = new ImageTask();
        task.execute(album.get(currentPosition));
    }

    private void addControls() {
        imgHinh = (ImageView) findViewById(R.id.imgHinh);
        chkAuto = (CheckBox) findViewById(R.id.chkAuto);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnNext = (ImageButton) findViewById(R.id.btnNext);

        album = new ArrayList<>();
        album.add("http://topanhdep.net/wp-content/uploads/2015/12/anh-girl-xinh-gai-dep-98-13.jpg");
        album.add("http://anhnendep.net/wp-content/uploads/2016/03/anh-girl-xinh-hot-girl-han-quoc-05.jpg");
        album.add("https://hinhanhdephd.com/wp-content/uploads/2017/06/anh-girl-xinh-de-thuong-nhat-nam-2017-10.jpg");
        album.add("http://tophinhanhdep.net/wp-content/uploads/2016/01/avatar-girl-xinh-10.jpg");
        album.add("https://tapchianhdep.com/wp-content/uploads/2015/03/hinh-anh-hot-girl-xinh-nhu-bup-be-19.jpg");
        album.add("https://depdrama.com/wp-content/uploads/2015/06/Girl-xinh-dai-hoc-thai-nguyen-5.jpg");
        album.add("http://thuthuattienich.com/wp-content/uploads/2013/06/girl-xinh-8.jpg");
        album.add("http://afamilycdn.com/fRhOWcbaG01Vd2ydvKbOwEYcba/Image/2016/06/khong-thua-gi-han-quoc-thai-lan-lao-cung-co-day-hot-girl-xinh-dep-va-sang-chanh_20160608111105045.jpg");
        album.add("http://sohanews.sohacdn.com/thumb_w/660/2016/photo-11-1481096538752.jpg");
        album.add("http://www.cherryradio.com.au/images/stories/articles/Resized/1976_news_575a56c3ec6e5_292x353.jpg");
        album.add("http://images.tapchianhdep.net/15-12tuyen-chon-hinh-anh-gai-xinh-dep-khong-cuong-noi4.jpg");

        currentPosition = 0;

        task = new ImageTask();
        task.execute(album.get(currentPosition));
    }

    private class ImageTask extends AsyncTask<String,Void,Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgHinh.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try
            {
                String link = strings[0];
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(link).getContent());
                return bitmap;
            }
            catch (Exception ex)
            {
                Log.e("loi",ex.toString());
            }
            return null;
        }
    }
}
