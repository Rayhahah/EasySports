package com.rayhahah.easysports.module.forum.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by a on 2017/5/16.
 */

public class ForumApiFactory {





    public static HashMap<String, String> getRequsetMap() throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        map.put("client", "Android");
        map.put("night", "0");
            map.put("token", URLEncoder.encode("", "UTF-8"));
        return map;
    }
}
