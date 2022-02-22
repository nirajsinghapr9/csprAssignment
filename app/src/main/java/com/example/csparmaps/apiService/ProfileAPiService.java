package com.example.csparmaps.apiService;

import android.content.Context;

import com.example.csparmaps.model.APiResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfileAPiService {

    private static ProfileAPiService onBoardingAPIService = null;
    private static RetrofitService apiService;
    private static ProfileAPiService.CallBackData callBackData;

    public static ProfileAPiService getInstance(Context context, ProfileAPiService.CallBackData callBackData) {
        if (onBoardingAPIService == null) {
            synchronized (ProfileAPiService.class) {
                if (onBoardingAPIService == null) {
                    onBoardingAPIService = new ProfileAPiService();
                    apiService = RetrofitApiClient.getClient(context).create(RetrofitService.class);
                    ProfileAPiService.callBackData = callBackData;
                    return onBoardingAPIService;
                }
            }
        }
        return onBoardingAPIService;
    }


    public boolean getLocation() {

        apiService.getProfile("https://aryupay.com/hrportal/public/tracking/viewreport")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<APiResponse>() {

                    @Override
                    public void onNext(APiResponse data) {
                        callBackData.onResponse(data);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callBackData.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return true;
    }

    public interface CallBackData {
        void onResponse(APiResponse data);

        void onFailure(Throwable e);

    }
}
