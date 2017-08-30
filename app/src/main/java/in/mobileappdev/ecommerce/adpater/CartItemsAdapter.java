package in.mobileappdev.ecommerce.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import in.mobileappdev.ecommerce.R;
import in.mobileappdev.ecommerce.listner.CartItemOptionClickListner;
import in.mobileappdev.ecommerce.model.Item;

/**
 * Created by Techjini on 8/30/2017.
 */

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartViewHolder> {

    private ArrayList<Item>  cartItems;
    private Context context;
    private CartItemOptionClickListner cartItemOptionClickListner;

    public CartItemsAdapter(ArrayList<Item> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cart_item_list, parent, false);
        return new CartViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, final int position) {

        Item  currentItem = cartItems.get(position);
        holder.cartItemName.setText(currentItem.getItemName());

        holder.btnCartBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItemOptionClickListner.onBuyNowClicked(position);
            }
        });

        holder.btnCartRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItemOptionClickListner.onRemoveClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    protected class CartViewHolder extends RecyclerView.ViewHolder{

        TextView cartItemName;
        Button btnCartRemove, btnCartBuyNow;
        public CartViewHolder(View itemView) {
            super(itemView);
            cartItemName = (TextView) itemView.findViewById(R.id.cart_item_name);
            btnCartBuyNow = (Button) itemView.findViewById(R.id.buy_now_from_cart);
            btnCartRemove = (Button) itemView.findViewById(R.id.remove_from_cart);
        }
    }


    public void setCartItemOptionClickListner(CartItemOptionClickListner cartItemOptionClickListner){
        this.cartItemOptionClickListner = cartItemOptionClickListner;
    }

}
