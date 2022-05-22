package com.example.classbsqlite;

import static com.android.volley.Response.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class add_friend extends AppCompatActivity {
    private EditText editNama,editTelpon;
    private Button simpanBtn;
    String nm,tlp;
    int success;

    private  static  String url_insert = "http://10.0.2.2/umyTI/tambahtm.php";
    private  static final String TAG = add_friend.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        editNama = findViewById(R.id.edNama);
        editTelpon = findViewById(R.id.edTelpon);
        simpanBtn = findViewById(R.id.btnSimpan);

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpanData();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
                }

        });

    }
    public void SimpanData()
    {
        if (editNama.getText().toString().equals("") ||
                editTelpon.getText().toString().equals("")) {
            Toast.makeText(add_friend.this, "Semua harus diisi data", Toast.LENGTH_SHORT).show();
        }
        else
        {
            nm = editNama.getText().toString();
            tlp = editTelpon.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            StringRequest strReq = new StringRequest(Request.Method.POST, url_insert, new Response.Listener<String>(){
                @Override
                public void onResponse(String response){
                    Log.d(TAG,"Response : "+response.toString());
            try {
                JSONObject jobj = new  JSONObject(response);
                success = jobj.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Toast.makeText(add_friend.this, "Sukses simpan data", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(add_friend.this, "gagal", Toast.LENGTH_SHORT).show();
                }
                }catch(JSONException e) {
                e.printStackTrace();
            }}
                },new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse (VolleyError error){
                    Log.e(TAG, "Error : " + error.getMessage());
                    Toast.makeText(add_friend.this, "Gagal simpan data", Toast.LENGTH_SHORT).show();
                }
                })

                {
                    @Override
                    protected Map<String, String> getParams () {
                    Map<String, String> params = new HashMap<>();
                    params.put("nama", nm);
                    params.put("telpon", tlp);
                    return params;
                }
                };
                requestQueue.add(strReq);
            }
        }
    }


