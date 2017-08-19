package com.hannanshaik.pizzaapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VariantGroup implements Serializable {

    @SerializedName("group_id")
    private String id;
    private String name;
    private List<Variation> variations;

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VariantGroup{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", variations=").append(variations);
        sb.append('}');
        return sb.toString();
    }
}
