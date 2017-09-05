package in.mobileappdev.ecommerce.listner;

import java.io.InputStream;

/**
 * Created by Techjini on 9/2/2017.
 */

public interface ECommerceAsycTaskListner {

    public void onDownloadStarted();
    public void onSuccess(InputStream bitmapImage);
    public void onFailure();
}
