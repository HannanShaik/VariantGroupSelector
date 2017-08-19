package com.hannanshaik.pizzaapp.model;

import com.google.gson.annotations.SerializedName;

public class Exclusion {

    @SerializedName("group_id")
    private String groupId;
    @SerializedName("variation_id")
    private String variationId;

    public String getGroupId() {
        return groupId;
    }

    public String getVariationId() {
        return variationId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Exclusion{");
        sb.append("groupId='").append(groupId).append('\'');
        sb.append(", variationId='").append(variationId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
