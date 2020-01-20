package com.a8_bit_tech.www.afterplan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class popup2 extends AppCompatActivity {

    EditText edit2;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup2);

        edit2 = findViewById(R.id.edit_backup);
        b = findViewById(R.id.b_savep2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    void saveData(){
        SharedPreferences sharedPreferences = popup2.this.getSharedPreferences(getString(R.string.data_file), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String num = edit2.getText().toString();
        if(!num.contains("+88"))
            num="+88"+num;
        editor.putString(getString(R.string.backup_number), num);
        editor.commit();

        Intent intent =new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        finish();
    }
}
