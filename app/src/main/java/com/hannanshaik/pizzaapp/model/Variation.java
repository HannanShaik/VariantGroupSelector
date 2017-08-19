package com.hannanshaik.pizzaapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Variation implements Serializable{
    private String id;
    private String name;
    private long price;
    @SerializedName("default")
    private
    int defaultValue;
    private int inStock;
    private boolean isVariationSelected;

    public boolean isVariationSelected() {
        return isVariationSelected;
    }

    public void setVariationSelected(boolean variationSelected) {
        isVariationSelected = variationSelected;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public int getInStock() {
        return inStock;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Variation{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", defaultValue=").append(defaultValue);
        sb.append(", inStock=").append(inStock);
        sb.append('}');
        return sb.toString();
    }
}
