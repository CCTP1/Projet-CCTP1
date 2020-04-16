package com.example.projet_cctp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAtbash =  findViewById(R.id.btnAtbash);
        btnAtbash.setOnClickListener(this);
        Button btnCesar =  findViewById(R.id.btnCesar);
        btnCesar.setOnClickListener(this);
        Button btnVigenere = findViewById(R.id.btnVigenere);
        btnVigenere.setOnClickListener(this);
        Button btnHomophone = findViewById(R.id.btnHomophone);
        btnHomophone.setOnClickListener(this);
        Button btnPlayfair = findViewById(R.id.btnPlayfair);
        btnPlayfair.setOnClickListener(this);
        Button btnDES = findViewById(R.id.btnDES);
        btnDES.setOnClickListener(this);
        Button btnHill = findViewById(R.id.btnHill);
        btnHill.setOnClickListener(this);


    }
    @Override
    public void onClick(View v){
        if (v.getId() == R.id.btnAtbash)
            startActivity(new Intent(this, AtbashActivity.class));
        if (v.getId() == R.id.btnCesar)
            startActivity(new Intent(this, CesarActivity.class));
        if (v.getId() == R.id.btnVigenere)
            startActivity(new Intent(this, VigenereActivity.class));
        if (v.getId() == R.id.btnHomophone)
            startActivity(new Intent(this, HomophoneActivity.class));
        if (v.getId() == R.id.btnPlayfair)
            startActivity(new Intent(this, PlayfairActivity.class));
        if (v.getId() == R.id.btnDES)
            startActivity(new Intent(this, DES.class));
        if (v.getId() == R.id.btnHill)
            startActivity(new Intent(this, HillActivity.class));

    }
}
