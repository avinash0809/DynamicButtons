package avinash.dynamicbuttons.Activities.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import avinash.dynamicbuttons.Activities.Interfaces.Clickables;
import avinash.dynamicbuttons.R;

/**
 * Created by avinash on 5/5/18.
 */

public class Home extends AppCompatActivity implements Clickables {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            JSONArray buttonsArray = new JSONArray(readJSONFromAsset("buttons.json"));
            final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                    .findViewById(android.R.id.content)).getChildAt(0);
            DynamicUI dynamicUI = new DynamicUI(this,buttonsArray,viewGroup ,this);
            dynamicUI.render();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onClick(String title) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }

    private String readJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream inputStream = getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
