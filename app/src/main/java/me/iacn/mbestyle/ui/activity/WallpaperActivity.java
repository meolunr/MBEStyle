package me.iacn.mbestyle.ui.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.adapter.WallpaperAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;
import me.iacn.mbestyle.util.StatusBarUtils;

/**
 * Created by iAcn on 2017/5/2
 * Emali iAcn0301@foxmail.com
 */

public class WallpaperActivity extends AppCompatActivity {

    private RecyclerView rvWallpaper;
    private int[] mIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("请选择一张壁纸");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvWallpaper = (RecyclerView) findViewById(R.id.rv_wallpaper);
        rvWallpaper.setLayoutManager(new GridLayoutManager(this, 2));

        mIds = new int[]{
                R.raw.wallpaper_blueberry,
                R.raw.wallpaper_grape,
                R.raw.wallpaper_kiwi,
                R.raw.wallpaper_orange,
                R.raw.wallpaper_pineapple,
                R.raw.wallpaper_strawberry
        };

        WallpaperAdapter adapter = new WallpaperAdapter(mIds, Glide.with(this));
        rvWallpaper.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                File file;

                try {
                    InputStream inputStream = getResources().openRawResource(mIds[position]);
                    byte[] bytes = new byte[inputStream.available()];
                    int len = inputStream.read(bytes);

                    // 权限问题，输出到外置目录
                    file = new File(getExternalFilesDir(""), getResources().getResourceEntryName(mIds[position]));
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(bytes, 0, len);

                    inputStream.close();
                    outputStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    showFailedToast();
                    return;
                }

                Uri uri = Uri.fromFile(file);

                try {
                    WallpaperManager manager = WallpaperManager.getInstance(WallpaperActivity.this);
                    startActivity(manager.getCropAndSetWallpaperIntent(uri));

                } catch (Exception e) {
                    Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                    intent.setDataAndType(uri, "image/*");
                    intent.putExtra("mimeType", "image/*");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent, "设置壁纸"));
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void showFailedToast() {
        Toast.makeText(this, "壁纸设置失败", Toast.LENGTH_SHORT).show();
    }
}