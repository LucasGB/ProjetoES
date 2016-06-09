package com.example.guiautfpr;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuRelatorioActivity extends ActionBarActivity {
	int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		
		setContentView(R.layout.activity_menu_relatorio);
		
		int nMaterias = DadosAulas.numMaterias();
		final Button[] botao = new Button[nMaterias];

		for( i = 0; i < nMaterias; i++ ){
		botao[i] = new Button(this);
		botao[i].setText(DadosAulas.materias[i][1]);
			botao[i].setTextColor(Color.parseColor("#ffffff"));
		botao[i].setId(i);
		LinearLayout layout = (LinearLayout) findViewById(R.id.body);
		layout.addView(botao[i]);
		
		botao[i].setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(MenuRelatorioActivity.this, RelatorioActivity.class);
				
		 		intent.putExtra("codigo", v.getId());
		     	startActivity(intent);

				}
			});
		}
	}

}
