package in.mobileappdev.ecommerce.adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.mobileappdev.ecommerce.ItemDetailsActivity;
import in.mobileappdev.ecommerce.R;
import in.mobileappdev.ecommerce.model.Item;

/**
 * Created by Techjini on 8/22/2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>{

    private static final String TAG = ItemsAdapter.class.getSimpleName();
    private ArrayList<Item> items;
    private Context context;

    public ItemsAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_list, parent, false);
        return new ItemsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        final Item item = items.get(position);
        holder.txtItemName.setText(item.getItemName());
        holder.txtItemDeesc.setText(item.getItemDesc());
        holder.txtItemCost.setText("Rs. "+item.getItemCost());
        holder.txtItemQty.setText(""+item.getItemQuantity()+" items left!");
        Log.d(TAG, "ID "+item.getItemId());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(context, ItemDetailsActivity.class);
                details.putExtra("id", item.getItemId());
                context.startActivity(details);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder{

        TextView txtItemName,txtItemDeesc, txtItemCost, txtItemQty;
        RelativeLayout parent;
        public ItemsViewHolder(View itemView) {
            super(itemView);
            parent = (RelativeLayout) itemView.findViewById(R.id.item_parent);
            txtItemName = (TextView) itemView.findViewById(R.id.txt_item_name);
            txtItemDeesc = (TextView) itemView.findViewById(R.id.txt_item_desc);
            txtItemCost = (TextView) itemView.findViewById(R.id.txt_item_cost);
            txtItemQty = (TextView) itemView.findViewById(R.id.txt_item_qty);
        }
    }
}