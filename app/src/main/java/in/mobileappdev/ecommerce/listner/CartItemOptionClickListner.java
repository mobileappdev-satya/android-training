package in.mobileappdev.ecommerce.listner;

import android.view.View;

/**
 * Created by Techjini on 8/30/2017.
 */

public interface CartItemOptionClickListner {
   //public void onCartOptionClick(int type);

    public void onBuyNowClicked(int position);
    public void onRemoveClicked(int position);
}
