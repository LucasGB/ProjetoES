package com.example.guiautfpr;


import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;


public class TelaSplash extends ActionBarActivity implements Runnable {

    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
  //    getSupportActionBar().hide();
        setContentView(R.layout.activity_tela_splash);
        
 		Intent intent = new Intent(TelaSplash.this, LoginActivity.class);
     	startActivity(intent);
    	finish();
    	
  //      anima_logo(); // Animação do simbolo

    //    Processos proc = new Processos(this);
    //   proc.execute();
      

    }

//    private void anima_logo(){
//        ImageView logo = (ImageView) findViewById(R.id.simbolo);
//        Animation anim = new TranslateAnimation(0.0f, 0.0f,0.0f, -13.0f);
//        anim.setDuration(800); 
//        anim.setFillAfter(true); 
//        anim.setFillEnabled(true); 
//        anim.setRepeatCount(-1);
//        anim.setRepeatMode(Animation.REVERSE); 
//        logo.startAnimation(anim);
//    }

    public class Processos extends AsyncTask<Void, Void, Void>{
        private Context mContext;

        public Processos(Context context) {
            mContext = context;
        } 


        // É invocado para fazer uma atualização na
        // interface gráfica
        protected void onProgressUpdate(Void...arg0) {
        }

		@Override
		protected Void doInBackground(Void...arg0) {
	        Conexao conexao = new Conexao();
			SharedPreferences settings = getSharedPreferences("DADOS", 0);
			if(settings.getString("login", "") != "" && settings.getString("senha", "") != "" ){  // SE J� HOUVER DADOS CADASTRADOS LOGA AUTOMATICAMENTE
				String user = settings.getString("login", "");
				String pass = settings.getString("senha", "");
				String con_utf =  conexao.homeHTML(user, pass);
	    	 	if(!con_utf.equals("") && !con_utf.equals("SEMINTERNET")){ // SE O LOGIN FOR BEM SUCEDIDO	

	    	 		Intent intent = new Intent(mContext, PainelActivity.class);
	    	 		intent.putExtra("login", user);
	    	 		intent.putExtra("senha", pass);
	    	 		startActivity(intent);
	    	 		
	    	    	finish();
	    	 		
	    	 	}else{//SENÃO, VAI PRA TELA DE LOGIN
	    	 		Intent intent = new Intent(mContext, LoginActivity.class);
	    	 		intent.putExtra("login", user);
	    	 		intent.putExtra("senha", pass);
	    	     	startActivity(intent);
	    	    	finish();
	    	 	}
	    	 	
	    	 	
	    	 	}else{
	    	 		
	    	 		Intent intent = new Intent(mContext, LoginActivity.class);
	    	 		intent.putExtra("login", "");
	    	 		intent.putExtra("senha", "");
	    	     	startActivity(intent);
	    	    	finish();
	    	 	}
	
		
			return null;
		}
		
	    protected void onPostExecute(Void...arg0) {
	 
	    }
	    

          
}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}



}

