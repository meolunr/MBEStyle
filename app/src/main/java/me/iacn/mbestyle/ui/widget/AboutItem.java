package me.iacn.mbestyle.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/2/18
 * Email i@iacn.me
 */

public class AboutItem extends FrameLayout {

    private TextView tvTitle;
    private TextView tvSummary;

    public AboutItem(Context context) {
        this(context, null);
    }

    public AboutItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AboutItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

        if (attrs != null) {
            setAttr(context, attrs);
        }
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.inflate_about_item, this, true);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvSummary = (TextView) findViewById(R.id.tv_summary);
    }

    private void setAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AboutItem);

        tvTitle.setText(array.getString(R.styleable.AboutItem_title));
        tvSummary.setText(array.getString(R.styleable.AboutItem_summary));

        array.recycle();
    }

    public void setSummary(String summary) {
        tvSummary.setText(summary);
    }
}