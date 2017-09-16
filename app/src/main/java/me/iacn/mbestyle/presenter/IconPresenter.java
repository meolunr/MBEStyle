package me.iacn.mbestyle.presenter;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.fragment.IconFragment;

/**
 * Created by iAcn on 2017/2/18
 * Email i@iacn.me
 */

public class IconPresenter {

    private IconFragment mView;

    public IconPresenter(IconFragment view) {
        mView = view;
    }

    public void calcIconTotal() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> flowableEmitter) throws Exception {
                XmlResourceParser xml = mView.getResources().getXml(R.xml.drawable);
                int total = 0;

                while (xml.getEventType() != XmlResourceParser.END_DOCUMENT) {
                    if (xml.getEventType() == XmlPullParser.START_TAG) {
                        if (xml.getName().startsWith("item")) {
                            total++;
                        }
                    }
                    xml.next();
                }

                flowableEmitter.onNext(total);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        mView.setIconTotal(integer);
                    }
                });
    }
}