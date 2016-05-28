package com.example.guiautfpr;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Application;

abstract public class DadosAluno extends Application {
	Conexao conexao = new Conexao();
	static private String nome;
	static private String RA;
	static private String senha;
	static private String curso;
	static private String codCurso;
	static private String tpcurcodnr;  // Que porra s�o essas?
	static private String alcuordemnr; //
	static private String situacao;
	static private String periodo;

	static public String getNome() {
		return nome;
	}

	static public String getRA() {
		return RA;
	}

	static public String getCurso() {
		return curso;
	}

	static public String getSituacao() {
		return situacao;
	}
	static public String getCodCurso() {
		return codCurso;
	}
	static public String getTpcurcodnr() {
		return tpcurcodnr;
	}
	static public String getAlcuordemnr() {
		return alcuordemnr;
	}
	static public String getSenha() {
		return senha;
	}
	static public void setSenha(String pass) {
		senha = pass;
	}
	
	public static void dadosAluno(String html) {
	
		String html_tabela = "";
		if(html.indexOf("Aluno:") != -1){
		html_tabela = html.substring(html.indexOf("Aluno:"));
		html_tabela = html_tabela.substring(html_tabela.indexOf("</b>"),
				html_tabela.indexOf("</font>"));
		html_tabela = html_tabela.replace("</b>", "");
		String[] dados = html_tabela.split("-");
		dados[0] = dados[0].replace(" ", "");
		RA = dados[0];
		nome = dados[1];

//		html_tabela = html.substring(html.indexOf("Curso:"));
//		html_tabela = html_tabela.substring(html_tabela.indexOf("</strong>"),
//		html_tabela.indexOf("-"));
//		html_tabela = html_tabela.replace("</strong>", "");
//		html_tabela = html_tabela.replaceFirst(" ", "");
//		curso = html_tabela;

//		html_tabela = html.substring(html.indexOf("Situa"));
//		html_tabela = html_tabela.substring(html_tabela.indexOf("</strong>"),
//				html_tabela.indexOf("</font>"));
//		html_tabela = html_tabela.replace("</strong>", "");
//		html_tabela = html_tabela.replaceFirst(" ", "");
//		situacao = html_tabela;
		
		html_tabela = html.substring(html.indexOf("curscodnr:"));
		html_tabela = html_tabela.substring(html_tabela.indexOf(" "), html_tabela.indexOf(","));
		html_tabela = html_tabela.replaceFirst(" ", "");
		codCurso = html_tabela;
		
		html_tabela = html.substring(html.indexOf("tpcurcodnr:"));
		html_tabela = html_tabela.substring(html_tabela.indexOf(" "), html_tabela.indexOf(","));
		html_tabela = html_tabela.replaceFirst(" ", "");
		tpcurcodnr = html_tabela;
		
		html_tabela = html.substring(html.indexOf("alcuordemnr:"));
		html_tabela = html_tabela.substring(html_tabela.indexOf(" "), html_tabela.indexOf("}"));
		html_tabela = html_tabela.replaceFirst(" ", "");
		alcuordemnr = html_tabela;		
	
		}else{
			RA = "------";
			nome = "------";
			curso = "------";
			situacao = "------";
			codCurso = "------";
			tpcurcodnr = "------";
			alcuordemnr = "------";	
		}
	}
	
}