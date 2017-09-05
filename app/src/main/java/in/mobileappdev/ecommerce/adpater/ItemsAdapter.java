package in.mobileappdev.ecommerce.adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import in.mobileappdev.ecommerce.ItemDetailsActivity;
import in.mobileappdev.ecommerce.R;
import in.mobileappdev.ecommerce.model.Item;
import in.mobileappdev.ecommerce.utils.Utils;

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
        holder.txtItemName.setText(item.getName());
        holder.txtItemDeesc.setText(item.getDescription());

        holder.txtItemCost.setText("Rs. "+ Utils.getDiscountedPrice(item.getPrice(), item.getDiscount()));
        holder.txtItemQty.setText(""+item.getQuantity()+" items left!");

        Glide.with(context).load(item.getUrl()).into(holder.thumbnail);
        Log.d(TAG, "ID "+item.getId());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String itemJSONString = gson.toJson(item);
                Intent details = new Intent(context, ItemDetailsActivity.class);
                details.putExtra("item", itemJSONString);
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
        ImageView thumbnail;
        public ItemsViewHolder(View itemView) {
            super(itemView);
            parent = (RelativeLayout) itemView.findViewById(R.id.item_parent);
            txtItemName = (TextView) itemView.findViewById(R.id.txt_item_name);
            txtItemDeesc = (TextView) itemView.findViewById(R.id.txt_item_desc);
            txtItemCost = (TextView) itemView.findViewById(R.id.txt_item_cost);
            txtItemQty = (TextView) itemView.findViewById(R.id.txt_item_qty);
            thumbnail = (ImageView) itemView.findViewById(R.id.item_icon);
        }
    }
}
