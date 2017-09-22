package com.rayhahah.easysports.net;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.mine.bean.RResponse;
import com.rayhahah.rbase.net.UploadFileRequestBody;
import com.rayhahah.rbase.net.download.ProgressListener;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Created by a on 2017/5/15.
 */

public class ApiFactory {

    public static Observable<RResponse> commitCrashMessage(int userId, HashMap<String, String> infos) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(ApiStore.class)
                .commitCrashMessage(userId, infos)
                .compose(RxSchedulers.<RResponse>ioMain());
    }

    public static Observable<RResponse> uploadCover(String username, String password, File file, String fileName, ProgressListener progressListener) {
        UploadFileRequestBody requestBody = new UploadFileRequestBody(file, progressListener);
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload_file", fileName, requestBody);
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(ApiStore.class)
                .uploadFile(part, username, password)
                .compose(RxSchedulers.<RResponse>ioMain());
    }


}
