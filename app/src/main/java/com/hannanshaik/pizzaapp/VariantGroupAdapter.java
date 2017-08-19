package com.hannanshaik.pizzaapp;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannanshaik.pizzaapp.model.VariantGroup;
import com.hannanshaik.pizzaapp.model.Variation;

import java.util.List;

public class VariantGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements VariationsAdapter.VariationSelection {
    private final VariantGroupItemSelection variantGroupItemSelection;
    private List<VariantGroup> variantGroupList;

    VariantGroupAdapter(List<VariantGroup> variantGroups,
                        VariantGroupItemSelection variantGroupItemSelection) {
        this.variantGroupList = variantGroups;
        this.variantGroupItemSelection = variantGroupItemSelection;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VariantsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_variants, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final VariantsHolder variantsHolder = (VariantsHolder) holder;
        final VariantGroup variantGroup = variantGroupList.get(position);
        variantsHolder.tvVariantGroupName.setText(variantGroup.getName());
        variantsHolder.rvVariations.setLayoutManager(new GridLayoutManager(variantsHolder.itemView.getContext(), 3));

        VariationsAdapter variationsAdapter = new VariationsAdapter(holder.getAdapterPosition(), variantGroup.getVariations(), this);
        variantsHolder.rvVariations.setAdapter(variationsAdapter);
    }

    @Override
    public int getItemCount() {
        return variantGroupList.size();
    }

    /**
     * Clear and add all the variantGroups
     * @param variantGroups
     */
    public void updateList(List<VariantGroup> variantGroups) {
        variantGroupList.clear();
        variantGroupList.addAll(variantGroups);
        notifyDataSetChanged();
    }

    /**
     * Remove the older set of variants and add the new one.
     * @param index
     * @param next
     */
    public void updateList(int index, VariantGroup next) {
        variantGroupList.remove(index);
        variantGroupList.add(index, next);
        notifyDataSetChanged();
    }

    /**
     * On selection of a particular variant, highlight the same and call the group adapter
     * to populate and refresh the following groups.
     * @param index
     * @param selectedVariation
     */
    @Override
    public void onVariationSelected(int index, Variation selectedVariation) {
        VariantGroup variantGroup = variantGroupList.get(index);
        for (Variation variation : variantGroup.getVariations()) {
            if (selectedVariation.getId().equals(variation.getId())) {
                variation.setVariationSelected(true);
            } else {
                variation.setVariationSelected(false);
            }
        }

        variantGroupItemSelection.onVariantGroupItemSelection(index, variantGroup.getId(), selectedVariation.getId());
        notifyDataSetChanged();
    }

    private class VariantsHolder extends RecyclerView.ViewHolder {
        private TextView tvVariantGroupName;
        private RecyclerView rvVariations;

        VariantsHolder(View view) {
            super(view);
            tvVariantGroupName = (TextView) view.findViewById(R.id.tv_variant_group_name);
            rvVariations = (RecyclerView) view.findViewById(R.id.rv_variations);
        }
    }

    interface VariantGroupItemSelection {
        void onVariantGroupItemSelection(int index, String selectedGroupId, String selectedVariationId);
    }
}
