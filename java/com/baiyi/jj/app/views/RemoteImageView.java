/**
 *
 */
package com.baiyi.jj.app.views;

import com.baiyi.jj.app.imgtools.GlideTool;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author tangkun
 */
public class RemoteImageView extends ImageView {

    private Bitmap bitmap = null;
    private String imgPath = null;

    /**
     * @param context
     */
    public RemoteImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public RemoteImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public void loadImg(Context context, final String converPath) {

        GlideTool.getListImage(context, converPath, RemoteImageView.this);

    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }
}
