package com.example.guiautfpr;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import com.example.guiautfpr.R;
import com.example.guiautfpr.R.id;
import com.example.guiautfpr.R.layout;
import com.orochi.guiautfpr.persistence.DatabaseOperations;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.widget.AdapterView.OnItemClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PainelActivity extends ActionBarActivity implements PanelSlideListener,OnItemClickListener    {
	  private final int RELATORIOS = 0;
	  private final int ONIBUS = 1;
	//  private final int SOBRE = 2;
	  private final int LOGOFF = 2;
	  private SlidingPaneLayout mSlidingLayout;  
	  private ListView mList;  
	  Date hoje = null; // Sei l�

		private Context ctx = this;

	  int hora_dia = DadosAulas.horaDia(hoje);
	  int dia_semana = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-2; // Pegar o dia da semana pra usar na matriz
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_painel);
		//Referente ao menu
		mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);  
		 mSlidingLayout.setPanelSlideListener(this);
			    
			    String[] opcoes = new String[] {   
			    "Relatórios",
			    "Horário dos Ônibus",
			    "Logoff"};
	
			 mList = (ListView) findViewById(R.id.left_pane);
			    mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opcoes));
			    mList.setOnItemClickListener(this);
		  // Fim do que se refere ao menu
			    
				final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
				proxima();
//			    String msg = "";
//			    for(int i=0;i<DadosAulas.aulas.length;i++){
//			    	msg += DadosAulas.aulas[i][0] + "\n";
//			    }
//			    builder1.setMessage(msg);
//		        
//		        AlertDialog alert12 = builder1.create();
//		        alert12.show();

	}


	void proxima()
	{
	    TextView blocoAula = (TextView) findViewById(R.id.blocoAula);
	    TextView faixaHorario = (TextView) findViewById(R.id.faixaHorario);
	    TextView materia  = (TextView) findViewById(R.id.materia);
	    TextView nomeAluno  = (TextView) findViewById(R.id.nomeAluno);
	    TextView data = (TextView) findViewById(R.id.data);
	    
	    String materiaAtual = "";
	    String salaAtual = "";
	    String faixaHr = "";
		 if(dia_semana < 0) dia_semana = 5;
		 
	if(DadosAulas.aulas[hora_dia][dia_semana].equals("&nbsp;")){ // Se não tiver aula nesse horário
	    while(DadosAulas.aulas[hora_dia][dia_semana].equals("&nbsp;")){
	        hora_dia++;
	    }
	    materiaAtual = DadosAulas.aulas[hora_dia][dia_semana].split("/")[0];
	    salaAtual = DadosAulas.aulas[hora_dia][dia_semana].split("/")[1];
		faixaHr = DadosAulas.faixaHoraria(hora_dia);
	}
	else { // Se tiver aula...
		 materiaAtual = DadosAulas.aulas[hora_dia][dia_semana].split("/")[0];
		 salaAtual = DadosAulas.aulas[hora_dia][dia_semana].split("/")[1];
		 faixaHr = DadosAulas.faixaHoraria(hora_dia);
	}

	String[] weekdays = new DateFormatSymbols().getWeekdays();

	// Seta os valores para os TextView
    nomeAluno.setText("Logado como: " + DadosAluno.getNome());
	blocoAula.setText("" + salaAtual);
	materia.setText("" + materiaAtual);
	faixaHorario.setText("" + faixaHr);
	data.setText("" + weekdays[dia_semana+2] );
	}
	
	
	void setaValores()
	{
	    TextView blocoAula = (TextView) findViewById(R.id.blocoAula);
	    TextView faixaHorario = (TextView) findViewById(R.id.faixaHorario);
	    TextView materia  = (TextView) findViewById(R.id.materia);
	    TextView nomeAluno  = (TextView) findViewById(R.id.nomeAluno);
	    TextView data = (TextView) findViewById(R.id.data);
	    
	    String materiaAtual = "";
	    String salaAtual = "";
	   
		String faixaHr = DadosAulas.faixaHoraria(hora_dia);
		 if(dia_semana < 0) dia_semana = 5;
		 
	if( DadosAulas.aulas[hora_dia][dia_semana].equals("&nbsp;")){ // Se n�o tiver aula nesse hor�rio
		materiaAtual = "N/A";
		salaAtual = "N/A";
	}
	else { // Se tiver aula...
		 materiaAtual = DadosAulas.aulas[hora_dia][dia_semana].split("/")[0];
		 salaAtual = DadosAulas.aulas[hora_dia][dia_semana].split("/")[1];
	}

	String[] weekdays = new DateFormatSymbols().getWeekdays();

	// Seta os valores para os TextView
    nomeAluno.setText("Logado como: " + DadosAluno.getNome());
	blocoAula.setText("" + salaAtual);
	materia.setText("" + materiaAtual);
	faixaHorario.setText("" + faixaHr);
	data.setText("" + weekdays[dia_semana+2] );
	}
	
	
	public void anteriorAula (View v){
		if(hora_dia > 0 )
		{
		hora_dia--;
		
		}
		else if(hora_dia == 0 )
		{

		if(dia_semana > 0) { dia_semana--;	hora_dia = 16;}
		
		}
		setaValores();
	}
	public void proximaAula (View v){
		if(hora_dia < 16 )
		{
		 hora_dia++;
		}
		else if (hora_dia >= 16)
		{
		if(dia_semana < 5){dia_semana++;hora_dia = 0;}
		}
		setaValores();
	} 
	
public boolean onKeyDown(int keycode, KeyEvent e) {
    switch(keycode) {
        case KeyEvent.KEYCODE_MENU:
        	  if (mSlidingLayout.isOpen()){  
        		    mSlidingLayout.closePane();  
        		  } else {  
        		    mSlidingLayout.openPane();  
        		  }  
            return true;
    }

    return super.onKeyDown(keycode, e);
}
int vTest = 0;

public void onItemClick(AdapterView<?> adapterView,   
  View view, int position, long id) {  
	if(vTest == 1){
		
	AlertDialog alertDialog = new AlertDialog.Builder(this).create(); //AlertDialog

	if(position == RELATORIOS){	
     	startActivity(new Intent(PainelActivity.this, MenuRelatorioActivity.class));
	}
	if(position == ONIBUS){	
     	startActivity(new Intent(PainelActivity.this, OnibusActivity.class));
	}
//	else if(position == SOBRE){	
//     	startActivity(new Intent(PainelActivity.this, SobreActivity.class));
//	} VIET CARMA
	else if(position == LOGOFF){
		DatabaseOperations dbo = new DatabaseOperations(ctx);
        dbo.deleteLoginInformation(dbo);
     	startActivity(new Intent(PainelActivity.this, LoginActivity.class));
    	finish();
	}

	 mSlidingLayout.closePane();  
	}
}  

public void onPanelClosed(View arg0) {  
  // TODO Ao fechar o painel  
	vTest = 0;
}  

public void onPanelOpened(View arg0) {  
  // TODO Ao abrir o painel  
	vTest = 1;
}  

public void onPanelSlide(View arg0, float arg1) {  
  // TODO Enquanto o painel desliza  
}  
}
