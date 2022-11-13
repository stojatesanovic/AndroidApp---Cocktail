package com.example.ispitniprojekat;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DrinkFragment extends Fragment { //deo aplikacije, koji se naknadno poziva i moze biti ponovo iskoriscen, mora biti deo neke
                                              // aktivnosti ili drugog fragmenta, ne moze da stoji sam
    ArrayList<Drink.Cocktail> cocktails;
    ArrayAdapter<Drink.Cocktail> adapter;     //generise pogled svakog mogudjeg objekta
    ListView labelDrinkList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);      //stanje koje se cuva u memoriji za odredjenu komponentu
        init();
    } //nakon izvrsavanja kreiranja pogleda/fragmenta

    @Nullable
    @Override                                                                                  //Inflate
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, //Sluzi za popunjavanje aktivnosti drugim pogledima
                             Bundle savedInstanceState) { //stanje koje se cuva u memoriji za odredjenu komponentu
        return inflater.inflate(R.layout.fragment_drink, container, false);
    } //u trenutku kada treba da se kreira view

    @SuppressLint("HandlerLeak")
    private void init() {
        Api.getJSON("https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=Tequila", new ReadDataHandler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String jsonResponse = getJson();
                try {
                    JSONObject object = new JSONObject(jsonResponse);
                    cocktails = Drink.parseJSONObject(object).getDrinks();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new CocktailArrayAdapter(requireContext(), cocktails);
                labelDrinkList = requireView().findViewById(R.id.labelDrinkList);
                labelDrinkList.setAdapter(adapter);
            }
        });
    }
}