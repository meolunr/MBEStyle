package me.iacn.mbestyle.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;

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

        int[] ids = new int[]{
                R.drawable.wallpaper_blueberry,
                R.drawable.wallpaper_grape,
                R.drawable.wallpaper_kiwi,
                R.drawable.wallpaper_orange,
                R.drawable.wallpaper_pineapple,
                R.drawable.wallpaper_strawberry
        };

        WallpaperAdapter adapter = new WallpaperAdapter(ids, Glide.with(this));
        rvWallpaper.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                System.out.println("ItemClick");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}