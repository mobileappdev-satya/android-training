package in.mobileappdev.ecommerce.listner;

import android.graphics.Bitmap;

/**
 * Created by Techjini on 8/31/2017.
 */

public interface OnFetchItemsListner {
    public void onDownloadStarted();
    public void onSuccess(String bitmapImage);
    public void onFailure();
}
