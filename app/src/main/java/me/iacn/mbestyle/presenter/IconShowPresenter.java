package me.iacn.mbestyle.presenter;

import android.content.pm.ApplicationInfo;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.iacn.mbestyle.BuildConfig;
import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.IconBean;
import me.iacn.mbestyle.ui.fragment.IconShowFragment;
import me.iacn.mbestyle.util.PackageUtils;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class IconShowPresenter {

    private IconShowFragment mView;

    public IconShowPresenter(IconShowFragment view) {
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
                    public void accept(@NonNull List<IconBean> list) throws Exception {
                        mView.onLoadData(list);
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
                            bean.iconPkgName = findPackageName(component);

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
                .map(new Function<List<IconBean>, List<IconBean>>() {
                    @Override
                    public List<IconBean> apply(@NonNull List<IconBean> list) throws Exception {
                        Set<String> appPkgNames = getAppPkgNames();

                        List<IconBean> newList = new ArrayList<>();
                        Set<String> tempSet = new HashSet<>();

                        for (IconBean bean : list) {
                            String iconName = bean.name;

                            // 排除重复图标
                            if (appPkgNames.contains(bean.iconPkgName) && !tempSet.contains(iconName)) {
                                newList.add(bean);
                                tempSet.add(iconName);
                            }
                        }

                        return newList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<IconBean>>() {
                    @Override
                    public void accept(@NonNull List<IconBean> list) throws Exception {
                        mView.onLoadData(list);
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

    private Set<String> getAppPkgNames() {
        List<ApplicationInfo> apps = PackageUtils.getAllApp(mView.getActivity());
        Set<String> set = new HashSet<>();

        for (ApplicationInfo info : apps) {
            set.add(info.packageName);
        }

        return set;
    }
}