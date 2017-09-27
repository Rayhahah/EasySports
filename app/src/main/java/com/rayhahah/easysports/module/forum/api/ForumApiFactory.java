package com.rayhahah.easysports.module.forum.api;

import android.text.TextUtils;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.forum.bean.DetailListData;
import com.rayhahah.easysports.module.forum.bean.ForumDetailInfoData;
import com.rayhahah.easysports.module.forum.bean.ForumsData;
import com.rayhahah.easysports.net.ApiClient;
import com.rayhahah.easysports.utils.HuPuHelper;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by a on 2017/5/16.
 */

public class ForumApiFactory {

    public static Observable<ForumsData> getAllForums() throws UnsupportedEncodingException {
        Map<String, String> params = HuPuHelper.getRequsetMap();
        String sign = HuPuHelper.getRequestSign(params);
        return ApiClient.get(C.BaseURL.HUPU_FORUM_SERVER)
                .create(ForumService.class)
                .getAllForums(sign, params)
                .compose(RxSchedulers.<ForumsData>ioMain());
    }

    /**
     * 获取论坛帖子列表
     *
     * @param fid      论坛id，通过getForums接口获取
     * @param lastTid  最后一篇帖子的id
     * @param limit    分页大小
     * @param lastTamp 时间戳
     * @param type     加载类型  1 按发帖时间排序  2 按回帖时间排序
     */
    public static Observable<DetailListData> getForumInfoList(String fid, String lastTid, String type) {
        Map<String, String> params = HuPuHelper.getRequsetMap();
        params.put("fid", fid);
        params.put("lastTid", lastTid);
        params.put("limit", String.valueOf(C.FORUM.LIMIT));
        params.put("isHome", "1");
        params.put("stamp", C.FORUM.LAST_TAMP);
        params.put("password", "0");
        params.put("special", "0");
        params.put("type", type);
        String sign = HuPuHelper.getRequestSign(params);

        return ApiClient.get(C.BaseURL.HUPU_FORUM_SERVER)
                .create(ForumService.class)
                .getForumInfosList(sign, params)
                .compose(RxSchedulers.<DetailListData>ioMain());
    }

    /**
     * 获取帖子详情
     *
     * @param tid  帖子id
     * @param fid  论坛id
     * @param page 页数
     * @param pid  回复id
     */
    public static Observable<ForumDetailInfoData> getThreadInfo(String tid, String fid, int page, String pid) {
        Map<String, String> params = HuPuHelper.getRequsetMap();
        if (!TextUtils.isEmpty(tid)) {
            params.put("tid", tid);
        }
        if (!TextUtils.isEmpty(fid)) {
            params.put("fid", fid);
        }
        params.put("page", page + "");
        if (!TextUtils.isEmpty(pid)) {
            params.put("pid", pid);
        }
        params.put("nopic", "0");
        String sign = HuPuHelper.getRequestSign(params);

        return ApiClient.get(C.BaseURL.HUPU_FORUM_SERVER)
                .create(ForumService.class)
                .getThreadInfo(sign, params)
                .compose(RxSchedulers.<ForumDetailInfoData>ioMain());
    }
}
