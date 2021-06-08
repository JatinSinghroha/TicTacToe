package com.jatinsinghroha.tictactoe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class IndianStatesAPIExample extends AppCompatActivity {

    Button fetchBtn;
    PowerSpinnerView indianStatesSpinner;

    List<StateItem> listOfStateItems;
    List<String> listOfStateNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indian_states_apiexample);

        listOfStateItems = new ArrayList<>();
        listOfStateNames = new ArrayList<>();

        fetchBtn = findViewById(R.id.fetchBtn);
        indianStatesSpinner = findViewById(R.id.listOfStatesSpinner);

        fetchBtn.setOnClickListener(v -> {
            listOfStateItems.clear();
            String apiUrl = "https://cdn-api.co-vin.in/api/v2/admin/location/states/";

            RequestQueue requestQueue = Volley.newRequestQueue(IndianStatesAPIExample.this);

            JsonObjectRequest getStates = new JsonObjectRequest(Request.Method.GET, apiUrl, null, response -> {

                try {
                    JSONArray states = response.getJSONArray("states");

                    for(int i = 0; i < states.length(); i++){
                        JSONObject stateObject = (JSONObject) states.get(i);
                        listOfStateItems.add(new StateItem((int) stateObject.get("state_id"), (String) stateObject.get("state_name")));
                    }

                    setItemsInStatesSpinner();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }, error -> {


            });

            requestQueue.add(getStates);

        });

        indianStatesSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<Object>() {
            @Override
            public void onItemSelected(int i, @Nullable Object o, int i1, Object t1) {

                Toast.makeText(IndianStatesAPIExample.this, listOfStateItems.get(i1).getState_id()
                        + " - " + listOfStateItems.get(i1).getState_name(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void setItemsInStatesSpinner() {
        listOfStateNames.clear();
        for(int i = 0; i < listOfStateItems.size(); i++){

            listOfStateNames.add(listOfStateItems.get(i).getState_name());

        }
        indianStatesSpinner.setItems(listOfStateNames);
    }

}