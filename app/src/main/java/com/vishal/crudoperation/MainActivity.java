package com.vishal.crudoperation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String url ="https://jsonplaceholder.typicode.com/todos";
    RecyclerView rvTitle;
    ImageView ivFloatButton;
    RecyclerViewAdapter adapter;
    ArrayList<ModelClass> dataList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTitle = findViewById(R.id.rvTitle);
        ivFloatButton = findViewById(R.id.ivFloatButton);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        ModelClass item = new ModelClass(title);
                        dataList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
        adapter = new RecyclerViewAdapter(dataList, this);
        rvTitle.setAdapter(adapter);
        rvTitle.setLayoutManager(new LinearLayoutManager(this));

        ivFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemPopup();
            }
        });
    }

    private void showAddItemPopup() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_layout);
        final EditText editText = dialog.findViewById(R.id.editText);
        Button confirmButton = dialog.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = editText.getText().toString();
                if (!newText.isEmpty()) {
                    addItemToTop(newText);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter text", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
    private void addItemToTop(String newText) {
               ModelClass newItem = new ModelClass(newText);
               dataList.add(0, newItem);
                adapter.notifyItemInserted(0);
                rvTitle.scrollToPosition(0);
    }
}
