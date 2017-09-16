package me.iacn.mbestyle.presenter;

import android.content.pm.ApplicationInfo;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
 * Email i@iacn.me
 */

public class IconShowPresenter {

    private IconShowFragment mView;

    public IconShowPresenter(IconShowFragment view) {
        mView = view;
    }

    public Disposable getAllIcons() {
        return Observable.create(new ObservableOnSubscribe<IconBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<IconBean> e) throws Exception {
                XmlResourceParser xml = mView.getResources().getXml(R.xml.drawable);

                while (xml.getEventType() != XmlResourceParser.END_DOCUMENT) {
                    if (xml.getEventType() == XmlPullParser.START_TAG) {
                        if (xml.getName().startsWith("item")) {
                            IconBean bean = new IconBean();

                            String iconName = xml.getAttributeValue(null, "drawable");
                            bean.id = mView.getResources().getIdentifier(
                                    iconName, "drawable", BuildConfig.APPLICATION_ID);
                            bean.name = iconName;

                            e.onNext(bean);
                        }
                    }
                    xml.next();
                }
                e.onComplete();
            }
        }).toList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<IconBean>>() {
                    @Override
                    public void accept(List<IconBean> list) throws Exception {
                        mView.onLoadData(list);
                    }
                });
    }

    public Disposable getAdaptedIcons() {
        return Observable.create(new ObservableOnSubscribe<IconBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<IconBean> e) throws Exception {
                XmlResourceParser xml = mView.getResources().getXml(R.xml.appfilter);

                while (xml.getEventType() != XmlResourceParser.END_DOCUMENT) {
                    if (xml.getEventType() == XmlPullParser.START_TAG) {
                        if (xml.getName().startsWith("item")) {
                            IconBean bean = new IconBean();

                            String component = xml.getAttributeValue(null, "component");
                            if (!component.startsWith(":")) {
                                // 跳过 Component 系统图标模糊匹配
                                bean.iconPkgName = findPackageName(component);
                            }
                            String iconName = xml.getAttributeValue(null, "drawable");
                            bean.id = mView.getResources().getIdentifier(
                                    iconName, "drawable", BuildConfig.APPLICATION_ID);
                            bean.name = iconName;

                            checkCodeError(bean);

                            e.onNext(bean);
                        }
                    }
                    xml.next();
                }
                e.onComplete();
            }
        }).toList().map(new Function<List<IconBean>, List<IconBean>>() {
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
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<IconBean>>() {
                    @Override
                    public void accept(@NonNull List<IconBean> list) throws Exception {
                        mView.onLoadData(list);
                    }
                });
    }

    public Disposable getWhatsNewIcons() {
        return Observable.fromArray(mView.getResources().getStringArray(R.array.whatsnew))
                .map(new Function<String, IconBean>() {
                    @Override
                    public IconBean apply(@NonNull String s) throws Exception {
                        IconBean bean = new IconBean();
                        bean.id = mView.getResources().getIdentifier(s, "drawable", BuildConfig.APPLICATION_ID);
                        bean.name = s;

                        return bean;
                    }
                }).toList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<IconBean>>() {
                    @Override
                    public void accept(List<IconBean> list) throws Exception {
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

    /**
     * Q：为什么有这个看似没用的逻辑？
     * A：因为每个图标的 xml 编写是十分繁杂的，难免出错
     * 所以在这里检查下（Ps.不好意思麻烦设计师，就只好麻烦自己啦~）
     */
    private void checkCodeError(IconBean bean) {
        if (bean.id == 0) {
            throw new RuntimeException("Icon resource id can't be zero." + " Icon = " + bean.name);
        }
    }
}