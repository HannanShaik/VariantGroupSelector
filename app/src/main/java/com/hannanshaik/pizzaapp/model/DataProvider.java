package com.hannanshaik.pizzaapp.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProvider {

    private Context context;
    private ArrayList<VariantGroup> variantGroups;
    private List<List<Exclusion>> exclusions;

    public DataProvider(Context context) {
        this.context = context;
    }

    public void init(){
        JsonParser jsonParser = new JsonParser();
        try {
            JsonObject variants = jsonParser.parse(getJsonData("data.json")).getAsJsonObject();
            variants = variants.getAsJsonObject("variants");

            JsonArray variantGroupsJson = variants.getAsJsonArray("variant_groups");
            Type listType = new TypeToken<List<VariantGroup>>(){}.getType();
            variantGroups = new Gson().fromJson(variantGroupsJson, listType);

            JsonArray exclusionList = variants.getAsJsonArray("exclude_list");
            listType = new TypeToken<List<List<Exclusion>>>(){}.getType();
            exclusions = new Gson().fromJson(exclusionList, listType);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VariantGroup> getVariantGroups(){
        return this.variantGroups;
    }

    public VariantGroup getNextVariantGroup(int index, String selectedGroupId, String selectedVariationId) {
        VariantGroup variantGroup = null;
        try {
            List<VariantGroup> variantGroups = deepCopy(this.variantGroups);
            variantGroup = variantGroups.get(index);

            if(selectedGroupId != null && selectedVariationId!=null){
                for(List combination: exclusions){
                    Exclusion one = (Exclusion) combination.get(0);
                    if(one.getGroupId().equals(selectedGroupId) && one.getVariationId().equals(selectedVariationId)){
                        Exclusion two = (Exclusion) combination.get(1);
                        List<Variation> variations = variantGroup.getVariations();

                        Iterator<Variation> iterator = variations.iterator();

                        while (iterator.hasNext()) {
                            Variation variation = iterator.next();

                            if(variation.getId().equals(two.getVariationId())) {
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return variantGroup;
    }

    private String getJsonData(String filename) throws IOException {
        InputStream inputStream = context.getAssets().open(filename);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        return (new String(buffer, "UTF-8"));
    }

    private ArrayList<VariantGroup> deepCopy(ArrayList<VariantGroup> obj)throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        ByteArrayInputStream bins = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream oins = new ObjectInputStream(bins);
        ArrayList<VariantGroup> ret =  (ArrayList<VariantGroup>)oins.readObject();
        oins.close();
        return ret;
    }

}
