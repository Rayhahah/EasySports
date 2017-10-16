package com.rayhahah.rbase.net.download;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;


public class ProgressResponseBody extends ResponseBody {
    private final Response response;
    private BufferedSource bufferedSource;
    private ProgressListener progressListener;

    public ProgressResponseBody(Response response, ProgressListener progressListener) {
        this.progressListener = progressListener;
        this.response = response;
    }

    @Override
    public MediaType contentType() {
        return response.body().contentType();
    }


    @Override
    public long contentLength() {
        return response.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(response.body().source()) {
            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                progressListener.onProgressChange(bytesReaded, contentLength(), false);
                return bytesRead;
            }
        });
    }

}
