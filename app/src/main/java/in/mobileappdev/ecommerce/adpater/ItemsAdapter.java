package in.mobileappdev.ecommerce.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.mobileappdev.ecommerce.model.Item;

/**
 * Created by Techjini on 8/22/2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder>{

    private ArrayList<Item> items;
    private Context context;

    public ItemsAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //layout inflattion
        return null;
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {

        Item item = items.get(position);
        holder.txtItemName.setText(item.getItemName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
