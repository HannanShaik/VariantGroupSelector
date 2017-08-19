package com.hannanshaik.pizzaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hannanshaik.pizzaapp.model.DataProvider;
import com.hannanshaik.pizzaapp.model.VariantGroup;

import java.util.ArrayList;
import java.util.List;

public class VariantSelectionActivity extends AppCompatActivity implements VariantGroupAdapter.VariantGroupItemSelection {

    private List<VariantGroup> variantGroupList;
    private VariantGroupAdapter variantGroupAdapter;
    private DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variant_selection);
        initUI();
        fetchData();
    }

    /**
     * Initialize th UI Elements.
     */
    private void initUI() {
        variantGroupList = new ArrayList<>();
        RecyclerView variantGroupRecyclerView = (RecyclerView) findViewById(R.id.rv_variant_groups);
        variantGroupRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        variantGroupAdapter = new VariantGroupAdapter(variantGroupList, this);
        variantGroupRecyclerView.setAdapter(variantGroupAdapter);
    }


    /**
     * Fetch the variant groups from the Json.
     */
    private void fetchData() {
        dataProvider = new DataProvider(this);
        dataProvider.init();
        variantGroupList = dataProvider.getVariantGroups();
        variantGroupAdapter.updateList(variantGroupList);
    }

    /**
     * Whenever a variation is selected, reset the following variant groups with respective data
     * considering the exclusions.
     * @param index - Index of variant group that got selected.
     * @param selectedGroupId - Selected Variant Group Id
     * @param selectedVariationId - Selected Variation Id under the group.
     */
    @Override
    public void onVariantGroupItemSelection(int index, String selectedGroupId, String selectedVariationId) {
        for(index = index+1; index< variantGroupList.size(); index++){
            VariantGroup next = dataProvider.getNextVariantGroup(index, selectedGroupId, selectedVariationId);
            variantGroupAdapter.updateList(index, next);
        }
    }
}
