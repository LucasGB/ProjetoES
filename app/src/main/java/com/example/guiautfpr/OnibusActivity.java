package com.example.guiautfpr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.guiautfpr.R;
import com.example.guiautfpr.R.id;
import com.example.guiautfpr.R.layout;
import com.example.guiautfpr.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class OnibusActivity extends ActionBarActivity {
	Date agora = null;
	String horarios = "";
	String horarios2 = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_onibus);
		
	    TextView utfTerminal = (TextView) findViewById(R.id.hrUTFPRTerminal);
	    TextView terminalUTF = (TextView) findViewById(R.id.hrTerminalUTFPR);
		
		Calendar c = Calendar.getInstance();	
		int dia_semana = c.get(Calendar.DAY_OF_WEEK)-2; // Pegar o dia da semana pra usar na matriz
		
		if(dia_semana != 5 && dia_semana != -1){
			 horarios = "07:00/07:25/09:20/10:20/12:05/13:00/13:50/15:50/17:05/17:40/18:00/18:45/21:25/20:00/22:05/23:05";
			 horarios2 = "06:45/07:20/07:55/09:10/10:10/12:45/13:15/13:40/15:35/17:30/18:30/18:50/19:15/20:10/21:10";
			}else if(dia_semana == 5){// Se for sábado
					 horarios = "07:00/07:25/09:20/10:20/12:05";
					 horarios2 = "06:45/07:15/07:55/09:10/10:10";
			}
			else if(dia_semana == -1){// Se for domingo
				 horarios = "N/A";
				 horarios2 = "N/A";
		}
//		final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
//		   
//	    builder1.setMessage("" + dia_semana);
//        
//       AlertDialog alert12 = builder1.create();
//        alert12.show();

		String[] UTF_Terminal = horarios.split("/");
		String[] Terminal_UTF = horarios2.split("/");

  
		int proximo = 0;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // Pattern pra hora na matriz
		try {
			agora = format.parse(format.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			for(int i = 0;i<UTF_Terminal.length -1;i++){
		     try {
				if(agora.after(format.parse(UTF_Terminal[i])) && agora.before(format.parse(UTF_Terminal[i+1]))){
					proximo = i+1; 
					i=99;
					
				}
				else{
					if(i > UTF_Terminal.length) proximo = 0;

				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		     
			}
		
		utfTerminal.setText(UTF_Terminal[proximo]);
		
		for(int i = 0;i<Terminal_UTF.length -1;i++){
		     try {
				if(agora.after(format.parse(Terminal_UTF[i])) && agora.before(format.parse(Terminal_UTF[i+1]))){
					proximo = i+1; 
					i=99;
					
				}else{
					if(i >= Terminal_UTF.length-2) proximo = 0;

				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		     
			}
		Log.d("Teste", "" + proximo);
		Log.d("Teste", "" + Terminal_UTF.length);

		terminalUTF.setText(Terminal_UTF[proximo]);


		
	}

public void hrUTF_Terminal(View v){
	final AlertDialog.Builder builder1 = new AlertDialog.Builder(OnibusActivity.this);
	
	String[] UTF_Terminal = horarios.split("/");
	String msg = "";
    for(int i=0;i<UTF_Terminal.length;i++){
    	msg += UTF_Terminal[i] + "\n";
    }
    builder1.setMessage(msg);
	builder1.setPositiveButton("OK",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface diag,
						int whichButton) {
				}
			});
	AlertDialog alert11 = builder1.create();
	alert11.show();
}

public void hrTerminal_UTF(View v){
	final AlertDialog.Builder builder1 = new AlertDialog.Builder(OnibusActivity.this);

	String msg = "";
	String[] Terminal_UTF = horarios2.split("/");
    for(int i=0;i<Terminal_UTF.length;i++){
    	msg += Terminal_UTF[i] + "\n";
    }
    builder1.setMessage(msg);
	builder1.setPositiveButton("OK",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface diag,
						int whichButton) {
				}
			});
	AlertDialog alert11 = builder1.create();
	alert11.show();
}

}
