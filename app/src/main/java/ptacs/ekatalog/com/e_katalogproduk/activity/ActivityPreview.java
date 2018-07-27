package ptacs.ekatalog.com.e_katalogproduk.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.helper.Constant;
import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;

public class ActivityPreview extends AppCompatActivity {

    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            URL = bundle.getString(Constant.BUNDLE_FOTO_PRODUK);
            PhotoView Pview = (PhotoView) findViewById(R.id.ReviewIV);

            Glide.with(this)
                    .load(URL)
                    .into(Pview);


        }
    }
}

