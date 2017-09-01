package in.mobileappdev.ecommerce.listner;

import android.graphics.Bitmap;

/**
 * Created by Techjini on 8/31/2017.
 */

public interface OnImageDownloadListner {
    public void onDownloadStarted();
    public void onSuccess(Bitmap bitmapImage);
    public void onFailure();
}
