package com.example.guiautfpr;

import java.util.concurrent.ExecutionException;

import com.example.guiautfpr.R;
import com.orochi.guiautfpr.persistence.DatabaseOperations;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity implements PanelSlideListener, OnItemClickListener {
		private SlidingPaneLayout mSlidingLayout;
		private ListView mList;
		private final int MIN_DIGITOS_RA = 6;
	  	private final int MIN_DIGITOS_SENHA = 8;
		private Context ctx = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_login);
		Button entrar = (Button) findViewById(R.id.entrar);
		final EditText login = (EditText) findViewById(R.id.login);
		final EditText senha = (EditText) findViewById(R.id.senha);

		//Referente ao menu
		mSlidingLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);  
        mSlidingLayout.setPanelSlideListener(this);
			    
        String[] opcoes = new String[] {
			    "Horário dos Ônibus",
		};
	
		mList = (ListView) findViewById(R.id.left_pane);
		mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opcoes));
		mList.setOnItemClickListener(this);
		// Fim do que se refere ao menu

		DatabaseOperations dop = new DatabaseOperations(ctx);
		Cursor c = dop.getInformation(dop);

        if(c.moveToFirst()){
            if(!(c.isNull(0) && c.isNull(1))){
                login.setText(c.getString(0));
                senha.setText(c.getString(1));
                Logar logar = new Logar();
                logar.execute(login.getText().toString(), senha.getText().toString());
            }
        }


		senha.setOnKeyListener(new OnKeyListener()
		{
		    public boolean onKey(View v, int keyCode, KeyEvent event)
		    {
		        if (event.getAction() == KeyEvent.ACTION_DOWN)
		        {
		            switch (keyCode)
		            {
		                case KeyEvent.KEYCODE_DPAD_CENTER:
		                case KeyEvent.KEYCODE_ENTER:
		    				if(!login.getText().toString().equals("") && !senha.getText().toString().equals("") ){ // Verifica se os campos est�o em branco
		    					if(login.getText().toString().length() >= MIN_DIGITOS_RA){ // Verifica se os digitos do RA est�o em um tamanho ideal
		    						if(senha.getText().toString().length() >= MIN_DIGITOS_SENHA){ // Verifica se os digitos da senha est�o em um tamanho ideal
		    				            Logar logar = new Logar();
		    				            logar.execute(login.getText().toString(), senha.getText().toString());
		    						}
		    						else{ // Login com menos digitos
		    							Toast.makeText(getApplicationContext(), "Sua senha precisa ter no mínimo 8 digitos!", Toast.LENGTH_SHORT).show();
		    						}
		    					}
		    					else{ // Login com menos digitos
		    						Toast.makeText(getApplicationContext(), "Seu RA tem poucos digitos!", Toast.LENGTH_SHORT).show();
		    					}
		    					
		    				}else // Campo em branco
		                	{
		                		Toast.makeText(getApplicationContext(), "Algum campo está em branco!", Toast.LENGTH_SHORT).show();
		                	}
		                    return true;
		                default:
		                    break;
		            }
		        }
		        return false;
		    }
		});
		
		entrar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(!login.getText().toString().equals("") && !senha.getText().toString().equals("") ){ // Verifica se os campos est�o em branco
					if(login.getText().toString().length() >= MIN_DIGITOS_RA){ // Verifica se os digitos do RA est�o em um tamanho ideal
						if(senha.getText().toString().length() >= MIN_DIGITOS_SENHA){ // Verifica se os digitos da senha est�o em um tamanho ideal

							Logar logar = new Logar();
							logar.execute(login.getText().toString(), senha.getText().toString());
						}
						else{ // Login com menos digitos
							Toast.makeText(getApplicationContext(), "Sua senha precisa ter no mínimo 8 digitos!", Toast.LENGTH_SHORT).show();
						}
					}
					else{ // Login com menos digitos
						Toast.makeText(getApplicationContext(), "Seu RA tem poucos digitos!", Toast.LENGTH_SHORT).show();
					}
					
				}else // Campo em branco
            	{
            		Toast.makeText(getApplicationContext(), "Algum campo está em branco!", Toast.LENGTH_SHORT).show();
            	}
			}

		});

	}

	// AsyncTask do inferno pois não pode usar acesso a internet direto na
	// thread principal
	class Logar extends AsyncTask<String, String, String> {

		ProgressDialog dialog;
		AlertDialog.Builder builder1;

		protected void onPreExecute() {
			super.onPreExecute();
			
			dialog = ProgressDialog.show(LoginActivity.this, "Aguarde",
					"Logando...", false, true);
			dialog.setCancelable(false);
			builder1 = new AlertDialog.Builder(LoginActivity.this);

		}

		protected String doInBackground(String... params) {
			Conexao conexao = new Conexao();
			final CheckBox memorizar = (CheckBox) findViewById(R.id.memorizar);
			String login = params[0];
			String senha = params[1];
			String con_utf = conexao.homeHTML(login, senha);
			DadosAluno.setSenha(senha);
			if (!con_utf.equals("") && !con_utf.equals("SEMINTERNET")) {
   				if (memorizar.isChecked()) { // MEMORIZA LOGIN E SENHA
					DatabaseOperations dbo = new DatabaseOperations(ctx);
                    Cursor c = dbo.getInformation(dbo);
                    if(!c.moveToFirst()){
                        dbo.putInformation(dbo, login, senha);
                    } else {
                        dbo.rewriteInformation(dbo, login, senha);
                    }
				}

				DadosAluno.dadosAluno(con_utf);
			    String html = conexao.aulasHTML(DadosAluno.getRA(), DadosAluno.getSenha(), DadosAluno.getCodCurso(), DadosAluno.getAlcuordemnr());
			    DadosAulas.puxaMaterias(html);
			    
			    String html2 = conexao.materiasHTML(DadosAluno.getRA(), DadosAluno.getSenha(), DadosAluno.getCodCurso(), DadosAluno.getAlcuordemnr());
			  DadosAulas.moreMaterias(html2);
			    
				Intent intent = new Intent(LoginActivity.this, PainelActivity.class);
				startActivity(intent);
				dialog.dismiss();
				finish();
				return "NOERROR";
			} 

			return con_utf;
			
		}

		protected void onPostExecute(String con_utf) {
			if(!con_utf.equals("NOERROR")){
				
				String mensagem = "";
				builder1.setTitle("Erro ao acessar UTFPR.");
				if (con_utf.equals("SEMINTERNET")) {
					mensagem = "Falha ao acessar a rede da UTFPR. Talvez voc� esteja sem internet.";
				} else if (con_utf.equals("")) {
					mensagem = "Login ou senha incorretos.\nCorrija os dados e tente novamente.";
				}

				dialog.dismiss();
				builder1.setMessage(mensagem);

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
 if(position == 0){	
     	startActivity(new Intent(LoginActivity.this, OnibusActivity.class));
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