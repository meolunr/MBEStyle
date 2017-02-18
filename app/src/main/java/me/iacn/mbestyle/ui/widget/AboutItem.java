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
 * Emali iAcn0301@foxmail.com
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
        initView(attrs);

        super(context, attrs, defStyleAttr);
        initView(context);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IconButton);

        ivIcon.setImageResource(array.getResourceId(R.styleable.IconButton_btn_icon, 0));
        tvName.setText(array.getString(R.styleable.IconButton_btn_name));

        array.recycle();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.inflate_about_item, this, true);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvName = (TextView) findViewById(R.id.tv_name);
    }
}