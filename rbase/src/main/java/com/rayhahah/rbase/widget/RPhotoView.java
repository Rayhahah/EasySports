package com.rayhahah.rbase.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by a on 2017/6/22.
 */

public class RPhotoView extends PhotoView {

    public RPhotoView(Context context) {
        super(context);
    }

    public RPhotoView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public RPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);

    }
}
