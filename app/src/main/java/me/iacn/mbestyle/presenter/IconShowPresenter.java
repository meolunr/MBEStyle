package me.iacn.mbestyle.presenter;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    private PackageManager mPkgManager;

    public IconShowPresenter(IconShowFragment view) {
        mView = view;
        mPkgManager = mView.getActivity().getPackageManager();
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

                            checkCodeError(bean);
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
                            if (!component.startsWith(":")) {
                                // 跳过 Component 系统图标模糊匹配
                                bean.iconPkgName = findPackageName(component);
                            }
                            String iconName = xml.getAttributeValue(null, "drawable");
                            bean.id = mView.getResources().getIdentifier(
                                    iconName, "drawable", BuildConfig.APPLICATION_ID);
                            bean.name = iconName;

                            checkCodeError(bean);
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
                        Map<String, String> appPkgNames = getAppPkgNames();

                        List<IconBean> newList = new ArrayList<>();
                        Set<Integer> tempSet = new HashSet<>();

                        for (IconBean bean : list) {
                            int drawableId = bean.id;

                            // 排除重复图标
                            if (appPkgNames.keySet().contains(bean.iconPkgName) && !tempSet.contains(drawableId)) {
                                bean.name = appPkgNames.get(bean.iconPkgName);
                                newList.add(bean);
                                tempSet.add(drawableId);
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

    private Map<String, String> getAppPkgNames() {
        List<ApplicationInfo> apps = PackageUtils.getAllApp(mView.getActivity());
        Map<String, String> map = new HashMap<>();

        for (ApplicationInfo info : apps) {
            map.put(info.packageName, info.loadLabel(mPkgManager).toString());
        }

        return map;
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