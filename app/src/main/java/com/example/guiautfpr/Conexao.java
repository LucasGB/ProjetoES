package com.example.guiautfpr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;
import android.content.Context;
import android.util.Log;


public class Conexao extends Application {
    
    	public void logoffUTFPR(){
    	HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://utfws.utfpr.edu.br/aluno03/sistema/mpmenu.pcLogout");
        try {
			HttpResponse response = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
    }


String homeHTML(String login, String senha){
	String html = getHTML(login,senha, "http://utfws.utfpr.edu.br/aluno03/sistema/mpmenu.inicio");
	return html;
}

String aulasHTML(String login, String senha, String codCurso, String alcuordemnr){
String html = getHTML(login,senha,"http://utfws.utfpr.edu.br/aluno03/sistema/mpconfirmacaomatricula.pcTelaAluno?p_pesscodnr=" + login.substring(0,login.length()-1) + "&p_curscodnr="+ codCurso + "&p_alcuordemnr=" + alcuordemnr);	
return html;
}

String materiasHTML(String login, String senha, String codCurso, String alcuordemnr){
String html = getHTML(login,senha,"http://utfws.utfpr.edu.br/aluno03/sistema/mpboletim.inicioAluno?p_pesscodnr=" + login.substring(0,login.length()-1) + "&p_curscodnr="+ codCurso + "&p_alcuordemnr=" + alcuordemnr);	
return html;
}


String getHTML(String login, String senha, String link){

	HttpClient client = new DefaultHttpClient();
	String url = link;
	HttpGet request = new HttpGet(url);
	request.setHeader("Charset", "ISO-8859-1");
	request.addHeader(BasicScheme.authenticate(
			 new UsernamePasswordCredentials(login, senha),
			 "ISO-8859-1", false));
	HttpResponse response = null;
	try {
		response = client.execute(request);
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "";
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "SEMINTERNET";
	}
	InputStream in = null;
	try {
		in = response.getEntity().getContent();
	} catch (IllegalStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "";
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "";
	}
	
	BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("ISO-8859-1")));
	StringBuilder str = new StringBuilder();
	String line = null;
	int cont = 0;
	try {
		while((line = reader.readLine()) != null)
		{
		    str.append(line);
		    cont++;
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "";
	}
	try {
		in.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return "";
	}

	if(cont < 8){return "";}

	logoffUTFPR();
	return str.toString();
}



}
	
