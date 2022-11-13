package com.example.ispitniprojekat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CocktailArrayAdapter extends ArrayAdapter<Drink.Cocktail> {

    private final Context context; //U kom okruzenju se izvrsava adapter(gde se adaptiraju podaci)
    private final ArrayList<Drink.Cocktail> cocktails;

    public CocktailArrayAdapter(@NonNull Context context, ArrayList<Drink.Cocktail> cocktails) {
        super(context, R.layout.list_cocktail, cocktails);
        this.context = context;
        this.cocktails = cocktails;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //stvara pogled na odredjeni layout file

        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.list_cocktail, parent, false);
        ImageView cocktailImage = rowView.findViewById(R.id.cocktailImage);
        TextView labelCocktailName = rowView.findViewById(R.id.labelCocktailName);

        String strDrinkThumb = cocktails.get(position).getStrDrinkThumb(); //uzima el na toj poziciji, radi se dok lista ima el

        AsyncTask<String, Void, Bitmap> getCocktailImage = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true); // ako se konekcija koristi za input, odnosno za unos onda se kaze na true, a ako je samo output onda je false
                    con.connect();
                    InputStream inputStream = con.getInputStream();
                    Bitmap image = BitmapFactory.decodeStream(inputStream); //tip promenljive koji omogucava smestanje slike
                    inputStream.close();
                    return image;
                } catch (IOException e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                cocktailImage.setImageBitmap(bitmap);
            }
        };

        getCocktailImage.execute(strDrinkThumb);

        labelCocktailName.setText(cocktails.get(position).getStrDrink());

        return rowView;

    }
}
