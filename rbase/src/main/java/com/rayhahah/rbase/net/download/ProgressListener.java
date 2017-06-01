package com.rayhahah.rbase.net.download;

/**
 * Created by a on 2017/5/15.
 */

public interface ProgressListener {

    void onProgressChange(long progress, long total, boolean done);
}
