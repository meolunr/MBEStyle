package me.iacn.mbestyle.ui.fragment;

import android.app.Fragment;
import android.content.res.XmlResourceParser;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.adapter.IconTabAdapter;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class IconFragment extends BaseFragment {

    private TabLayout mTab;
    private ViewPager mViewPager;

    @Override
    protected int getInflateView() {
        return R.layout.fragment_icon;
    }

    @Override
    protected void findView() {
        mTab = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        List<Fragment> fragments = new ArrayList();
        fragments.add(new IconAdaptedFragment());
        fragments.add(new IconAllFragment());

        String[] titles = new String[]{
                "已适配",
                String.format(Locale.getDefault(), "全部(%d)", getIconTotal())};

        mTab.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new IconTabAdapter(getFragmentManager(), fragments, titles));
    }

    private int getIconTotal() {
        XmlResourceParser xml = getResources().getXml(R.xml.drawable);
        int total = 0;

        try {
            while (xml.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xml.getEventType() == XmlPullParser.START_TAG) {
                    if (xml.getName().startsWith("item")) {
                        total++;
                    }
                }

                xml.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}