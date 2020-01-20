package com.a8_bit_tech.www.afterplan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class popup1 extends AppCompatActivity {

    EditText edit1;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup1);

        edit1 = findViewById(R.id.edit_phone);
        b = findViewById(R.id.b_savep1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    void saveData(){
        SharedPreferences sharedPreferences = popup1.this.getSharedPreferences(getString(R.string.data_file), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String num = edit1.getText().toString();
        if(!num.contains("+88"))
            num="+88"+num;
        editor.putString(getString(R.string.phone_number), num);
        editor.commit();

        Intent intent =new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        finish();
    }
}
