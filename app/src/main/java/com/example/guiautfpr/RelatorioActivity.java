package com.example.guiautfpr;

import android.support.v7.app.ActionBarActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RelatorioActivity extends ActionBarActivity  {
    ListView listView ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_relatorio);
		 // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        Intent intent = getIntent();
        int cod = intent.getIntExtra("codigo", 0);
        Log.d("Teste", "Vai:" + cod);

        String[] values = new String[] { "Nome: " + DadosAulas.materias[cod][1], 
                                         "Código: "  + DadosAulas.materias[cod][0],
                                         "Faltas: " + DadosAulas.materias[cod][5],
                                         "Aulas dadas: " + DadosAulas.materias[cod][7], 
                                         "Sua frequência: " + DadosAulas.materias[cod][8],
                                         "Nota: " + DadosAulas.materias[cod][3], 
                                         "Media parcial: " + DadosAulas.materias[cod][6], 
                                         "Turma: "  + DadosAulas.materias[cod][2], 
                                         "Media da turma: "  + DadosAulas.materias[cod][4] 
                                        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
                
	}


}
