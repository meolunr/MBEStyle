package me.iacn.mbestyle.presenter;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.iacn.mbestyle.BuildConfig;
import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.IconBean;
import me.iacn.mbestyle.ui.fragment.BaseIconFragment;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class IconShowPresenter {

    private BaseIconFragment mView;

    public IconShowPresenter(BaseIconFragment view) {
        mView = view;
    }

    public void getAllIcons() {
        Flowable.create(new FlowableOnSubscribe<List<IconBean>>() {
            @Override
            public void subscribe(FlowableEmitter<List<IconBean>> flowableEmitter) throws Exception {
                XmlResourceParser xml = mView.getResources().getXml(R.xml.drawable);
                List<IconBean> icons = new ArrayList<>();

                while (xml.getEventType() != XmlResourceParser.END_DOCUMENT) {
                    if (xml.getEventType() == XmlPullParser.START_TAG) {
                        if (xml.getName().startsWith("item")) {
                            IconBean bean = new IconBean();

                            String iconName = xml.getAttributeValue(null, "drawable");
                            bean.id = mView.getResources().getIdentifier(
                                    iconName, "drawable", BuildConfig.APPLICATION_ID);
                            bean.name = iconName;

                            icons.add(bean);
                        }
                    }
                    xml.next();
                }

                flowableEmitter.onNext(icons);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<IconBean>>() {
                    @Override
                    public void accept(@NonNull List<IconBean> iconBeen) throws Exception {
                        mView.showIcons(iconBeen);
                    }
                });
    }

    public void getAdaptedIcons() {
        Flowable.create(new FlowableOnSubscribe<List<IconBean>>() {
            @Override
            public void subscribe(FlowableEmitter<List<IconBean>> flowableEmitter) throws Exception {
                XmlResourceParser xml = mView.getResources().getXml(R.xml.appfilter);
                List<IconBean> icons = new ArrayList<>();

                while (xml.getEventType() != XmlResourceParser.END_DOCUMENT) {
                    if (xml.getEventType() == XmlPullParser.START_TAG) {
                        if (xml.getName().startsWith("item")) {
                            IconBean bean = new IconBean();

                            String component = xml.getAttributeValue(null, "component");
                            String iconPkgName = findPackageName(component);

                            System.out.println(iconPkgName);
                        }
                    }
                    xml.next();
                }

                flowableEmitter.onNext(icons);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribe(new Consumer<List<IconBean>>() {
                    @Override
                    public void accept(@NonNull List<IconBean> list) throws Exception {

                    }
                });
    }

    private String findPackageName(String component) {
        try {
            return component.split("/")[0].split("\\{")[1];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}