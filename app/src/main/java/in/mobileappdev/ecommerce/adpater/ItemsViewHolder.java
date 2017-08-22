package in.mobileappdev.ecommerce.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.mobileappdev.ecommerce.R;

/**
 * Created by Techjini on 8/22/2017.
 */

class ItemsViewHolder extends RecyclerView.ViewHolder{

    TextView txtItemName;
    public ItemsViewHolder(View itemView) {
        super(itemView);

        itemView = (TextView) itemView.findViewById(R.id.add_item);
    }

    //TextView, Button, ImageView
}
