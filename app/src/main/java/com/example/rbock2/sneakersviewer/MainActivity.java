package com.example.rbock2.sneakersviewer;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Main activity
 *
 * @author rbock2
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.cover_card_image)
    protected ImageView imageView;

    @Bind(R.id.title_text_view)
    protected TextView title;

    @Bind(R.id.subtitle_text_view)
    protected TextView subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getCoverCards();
    }

    public void getCoverCards() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            final Call<JsonNode> getContentCall = ApiManager.getSneakersService().getContent();
            getContentCall.enqueue(new Callback<JsonNode>() {

                @Override
                public void onResponse(Call<JsonNode> call, Response<JsonNode> response) {
                    if (response.code() == 200) {
                        JsonNode node = response.body();
                        Card coverCard = getCoverCardImage(node);
                        title.setText(coverCard.getTitle());
                        subtitle.setText(coverCard.getSubtitle());

                        if (coverCard.getColorTheme().equals("dark")) {
                            title.setTextColor(Color.BLACK);
                        } else {
                            title.setTextColor(Color.WHITE);
                        }


                        //TODO: FIX URL LOGIC
                        if (coverCard.getPortraitURL().isEmpty()) {
                            Picasso.with(getApplicationContext()).load(coverCard.getSquarishURL()).fit().into(imageView);
                        } else {
                            Picasso.with(getApplicationContext()).load(coverCard.getPortraitURL()).fit().into(imageView);
                        }
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        Toast.makeText(MainActivity.this, "Something bad happened", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonNode> call, Throwable t) {
                    Log.d(TAG, "Something bad happened", t);
                }
            });

        } else {
            Toast.makeText(this, "no internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO: Refactor to use Jackson
    public Card getCoverCardImage(JsonNode node) {
        ArrayNode objectsArray = (ArrayNode) node.at("/objects");
        ObjectNode objectNode = (ObjectNode) objectsArray.get(5);
        ObjectNode publishedContent = (ObjectNode) objectNode.at("/publishedContent");
        ObjectNode properties = (ObjectNode) publishedContent.at("/properties");
        ObjectNode coverCard = (ObjectNode) properties.at("/coverCard");
        ObjectNode coverCardProperties = (ObjectNode) coverCard.at("/properties");

        String title = coverCardProperties.at("/title").asText();
        String subTitle = coverCardProperties.at("/subtitle").asText();
        String squarishUrl = coverCardProperties.at("/squarishURL").asText();
        String portraitURL = coverCardProperties.at("/portraitURL").asText();
        String landscapeURL = coverCardProperties.at("/landscapeURL").asText();
        String colorTheme = coverCardProperties.at("/colorTheme").asText();

        Card card = new Card();
        card.setSquarishURL(squarishUrl);
        card.setTitle(title);
        card.setSubtitle(subTitle);
        card.setColorTheme(colorTheme);
        card.setPortraitURL(portraitURL);
        card.setLandscapeURL(landscapeURL);

        return card;
    }
}
