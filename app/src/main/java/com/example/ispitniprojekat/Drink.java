package com.example.ispitniprojekat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Drink {
    private ArrayList<Cocktail> drinks;

    public Drink() {

    }

    public ArrayList<Cocktail> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<Cocktail> drinks) {
        this.drinks = drinks;
    }

    public static Drink parseJSONObject(JSONObject object) throws JSONException {
        Drink drinks = new Drink();

        if(object.has("drinks")){
           ArrayList<Cocktail> cocktails = new ArrayList<>();
           for(int i = 0; i < object.getJSONArray("drinks").length(); i++) {
               cocktails.add(new Cocktail(
                       object.getJSONArray("drinks").getJSONObject(i).getString("strDrink"),
                       object.getJSONArray("drinks").getJSONObject(i).getString("strDrinkThumb"),
                       object.getJSONArray("drinks").getJSONObject(i).getString("idDrink")
                       ));
           }
           drinks.setDrinks(cocktails);
        }

        return drinks;
    }

    public static class Cocktail {
        private String strDrink, strDrinkThumb, idDrink;

        public Cocktail(String strDrink, String strDrinkThumb, String idDrink) {
            this.strDrink = strDrink;
            this.strDrinkThumb = strDrinkThumb;
            this.idDrink = idDrink;
        }

        public Cocktail() {

        }

        public String getStrDrink() {
            return strDrink;
        }

        public void setStrDrink(String strDrink) {
            this.strDrink = strDrink;
        }

        public String getStrDrinkThumb() {
            return strDrinkThumb;
        }

        public void setStrDrinkThumb(String strDrinkThumb) {
            this.strDrinkThumb = strDrinkThumb;
        }

        public String getIdDrink() {
            return idDrink;
        }

        public void setIdDrink(String idDrink) {
            this.idDrink = idDrink;
        }
    }

}


