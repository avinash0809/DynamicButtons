package avinash.dynamicbuttons.Activities.Activites;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import avinash.dynamicbuttons.Activities.Interfaces.Clickables;
import avinash.dynamicbuttons.R;

/**
 * Created by avinash on 5/4/18.
 */

public class DynamicUI  {

    Context context;
    JSONArray jsonArray;
    ViewGroup rootView;
    Clickables clickables;


    public DynamicUI(Context context, JSONArray jsonArray, ViewGroup rootview, Clickables clickables) {
        this.context = context;
        this.jsonArray = jsonArray;
        this.rootView = rootview;
        this.clickables = clickables;
    }


    public void render() {

        LinearLayout parentView;
        parentView = getMeLinearLayout();
        parentView.setOrientation(LinearLayout.VERTICAL);

        if (jsonArray.length() > 0) {
            for (int index = 0; index < jsonArray.length(); index++) {

                JSONObject uiObject = jsonArray.optJSONObject(index);
                String type = uiObject.optString("type");
                String titleText = uiObject.optString("titletext");
                String buttonText = uiObject.optString("buttontext");

                final Button button = getMeButton(buttonText);
                button.setBackgroundColor(Color.parseColor("#FF303F9F"));
                button.setTextColor(Color.WHITE);
                button.setTag(titleText);

                final TextView titleView = getMeTextView(titleText);
                final LinearLayout buttonContainerLayout = getMeLinearLayout();
                buttonContainerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.border));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = ((Button) view).getTag().toString();
                        clickables.onClick(title);

                    }
                });

                switch (type) {

                    case "1":
                        buttonContainerLayout.setOrientation(LinearLayout.VERTICAL);
                        buttonContainerLayout.addView(titleView);
                        buttonContainerLayout.addView(button);
                        break;

                    case "2":
                        buttonContainerLayout.setOrientation(LinearLayout.HORIZONTAL);
                        buttonContainerLayout.addView(titleView);
                        buttonContainerLayout.addView(button);
                        break;

                    case "3":
                        buttonContainerLayout.setOrientation(LinearLayout.HORIZONTAL);
                        buttonContainerLayout.addView(button);
                        buttonContainerLayout.addView(titleView);
                        break;

                }
                parentView.addView(buttonContainerLayout);
            }
        }
        rootView.addView(parentView);
    }

    private Button getMeButton(String title) {
        LinearLayout.LayoutParams params = getMeParams();
        params.setMargins(20,20,20,20);
        params.gravity = Gravity.CENTER;
        Button button = new Button(context);
        button.setText(title);
        button.setPadding(20,20,20,20);
        button.setLayoutParams(params);
        return button;
    }

    private TextView getMeTextView(String text) {
        LinearLayout.LayoutParams params = getMeParams();
        params.setMargins(20,20,20,20);
        TextView textView = new TextView(context);
        textView.setText(text);
        params.gravity = Gravity.CENTER;
        textView.setPadding(20,20,20,20);
        textView.setLayoutParams(params);
        return  textView;
    }

    private LinearLayout getMeLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = getMeParams();
        layoutParams.setMargins(40,40,40,40);
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }

    private LinearLayout.LayoutParams getMeParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        return params;
    }
}
