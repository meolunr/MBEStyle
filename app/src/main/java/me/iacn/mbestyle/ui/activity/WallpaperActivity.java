package me.iacn.mbestyle.ui.activity;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.adapter.WallpaperAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;
import me.iacn.mbestyle.util.GlideUtils;
import me.iacn.mbestyle.util.StatusBarUtils;

/**
 * Created by iAcn on 2017/5/2
 * Email i@iacn.me
 */

public class WallpaperActivity extends AppCompatActivity {

    private int[] mIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("请选择一张壁纸");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView rvWallpaper = findViewById(R.id.rv_wallpaper);
        rvWallpaper.setLayoutManager(new GridLayoutManager(this, 2));

        mIds = new int[]{
                R.raw.wallpaper_blueberry,
                R.raw.wallpaper_grape,
                R.raw.wallpaper_kiwi,
                R.raw.wallpaper_orange,
                R.raw.wallpaper_pineapple,
                R.raw.wallpaper_strawberry
        };

        WallpaperAdapter adapter = new WallpaperAdapter(mIds, GlideUtils.with(this));
        rvWallpaper.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        setWallpaper(mIds[position]);
                    }
                } else {
                    setWallpaper(mIds[position]);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void showFailedToast() {
        Toast.makeText(this, "壁纸设置失败", Toast.LENGTH_SHORT).show();
    }

    private void setWallpaper(@RawRes int rawId) {
        File imageFile;

        try {
            InputStream inputStream = getResources().openRawResource(rawId);
            byte[] bytes = new byte[inputStream.available()];
            int len = inputStream.read(bytes);

            // 权限问题，输出到外置目录
            imageFile = new File(getExternalFilesDir(""), getResources().getResourceEntryName(rawId));
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            outputStream.write(bytes, 0, len);

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            showFailedToast();
            return;
        }

        Uri uri = getImageContentUri(imageFile);

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

    private Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}