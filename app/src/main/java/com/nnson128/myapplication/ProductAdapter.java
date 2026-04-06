package com.nnson128.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
        }

        Product product = products.get(position);
        TextView tvName = convertView.findViewById(R.id.textViewProductName);
        TextView tvDetails = convertView.findViewById(R.id.textViewProductDetails);

        tvName.setText("Mã: " + product.getId());
        tvDetails.setText("Tên: " + product.getName() + "\nGiá: " + product.getPrice() + " VNĐ\nLoại: " + product.getCategory());

        return convertView;
    }
}
