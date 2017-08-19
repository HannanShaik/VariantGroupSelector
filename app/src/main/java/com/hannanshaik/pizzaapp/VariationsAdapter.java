package com.hannanshaik.pizzaapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannanshaik.pizzaapp.model.Variation;

import java.util.List;

/**
 * Created by shaikatif on 18/8/17.
 */

public class VariationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Variation> variations;
    private VariationSelection variantSelection;
    private int position;

    public VariationsAdapter(int position, List<Variation> variationsDataModels, VariationSelection variantSelection) {
        this.variations = variationsDataModels;
        this.variantSelection = variantSelection;
        this.position = position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VariationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_variation, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        VariationHolder variationHolder = (VariationHolder) holder;
        Context context = variationHolder.itemView.getContext();
        final Variation variation = variations.get(position);
        variationHolder.tvVariationName.setText(variation.getName());
        variationHolder.tvVariationInStock.setText(context.getString(R.string.in_stock, variation.getInStock()));
        variationHolder.tvVariationPrice.setText(context.getString(R.string.price, variation.getPrice()));
        if (variation.isVariationSelected()) {
            variationHolder.llVariation.setBackgroundColor(ContextCompat.getColor(variationHolder.itemView.getContext(), android.R.color.darker_gray));
        } else {
            variationHolder.llVariation.setBackgroundColor(ContextCompat.getColor(variationHolder.itemView.getContext(), android.R.color.white));
        }
        variationHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                variantSelection.onVariationSelected(VariationsAdapter.this.position, variation);
            }
        });
    }


    @Override
    public int getItemCount() {
        return variations.size();
    }


    private class VariationHolder extends RecyclerView.ViewHolder {
        private LinearLayout llVariation;
        private TextView tvVariationName;
        private TextView tvVariationInStock;
        private TextView tvVariationPrice;

        VariationHolder(View view) {
            super(view);
            llVariation = view.findViewById(R.id.ll_variation);
            tvVariationName = view.findViewById(R.id.tv_variation_name);
            tvVariationInStock = view.findViewById(R.id.tv_variation_in_stock);
            tvVariationPrice = view.findViewById(R.id.tv_variation_price);
        }
    }

    interface VariationSelection {
        void onVariationSelected(int position, Variation variation);
    }
}
