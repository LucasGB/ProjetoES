package com.example.guiautfpr;

import com.example.guiautfpr.LoginActivity.Logar;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SobreActivity extends ActionBarActivity {
	 String msg ="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_sobre);
		Button enviarOpiniao = (Button) findViewById(R.id.enviarOpiniao);
		
		enviarOpiniao.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 AlertDialog.Builder builder1 = new AlertDialog.Builder(SobreActivity.this);

				 builder1.setTitle("Enviar opinião");
				final EditText input = new EditText(SobreActivity.this);
				builder1.setView(input);
								
				builder1.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					   msg = input.getText().toString();
					   Log.d("TESTE", msg);
					}
					});

				builder1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    // Canceled.
					  }
					});

				AlertDialog alert11 = builder1.create();
				alert11.show();
			}
	});


	}
	

	}
	

