package com.example.ecom.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecom.Interfaces.ItemClickListener;
import com.example.ecom.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName, txtProductDescription, txtProductCost, txtProductCategory, txtProductDiscount, txtProductColor, txtProductSize;
    public ImageView productImage;
    public ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = (ImageView) itemView.findViewById(R.id.product_image_layout);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name_layout);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description_layout);
        txtProductCost = (TextView) itemView.findViewById(R.id.product_price_layout);
        /*txtProductCategory = (TextView) itemView.findViewById(R.id.productCategory);
        txtProductDiscount = (TextView) itemView.findViewById(R.id.productDiscount);
        txtProductColor = (TextView) itemView.findViewById(R.id.productColor);
        txtProductSize = (TextView) itemView.findViewById(R.id.productSize);*/
    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);
    }
}
