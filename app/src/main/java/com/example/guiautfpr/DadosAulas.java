package com.example.guiautfpr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;


abstract public class DadosAulas {

	
		 public static String[][] materias; // 0 = codDisciplina // 1 = Nome // 2 = Turma //
		 
		 public static String[][] aulas;
		 
		 public static int numMaterias(){ // retorna o numero de materias matriculadas
		 return materias.length;
		 }
		 
		public static void moreMaterias(String html){
			String html_tabela;
			html_tabela = html.toString().substring(html.toString().indexOf("<table class=\"tbl\" width=\"80%\" >"));
			html_tabela = html_tabela.substring(html_tabela.indexOf("<tr>",html_tabela.indexOf("<tr>")+1));
			html_tabela = html_tabela.substring(html_tabela.indexOf("<tr>",html_tabela.indexOf("<tr>")+1));
			html_tabela = html_tabela.substring(0, html_tabela.indexOf("</table>"));
			String linhas[] = html_tabela.split("(</tr>)");
			
			for(int i=0; i < linhas.length-1;i++){
				linhas[i] = linhas[i].replace("<tr>", "");
				linhas[i] = linhas[i].replace("</tr>", "");
				linhas[i] = linhas[i].replace("<b>", "");
				linhas[i] = linhas[i].replace("</b>", "");
				if(linhas[i].indexOf("<td") > 0){
				linhas[i] = linhas[i].substring(linhas[i].indexOf("<td"));
				Log.d("Teste", linhas[i]);
				}
			}
			
			for(int i=0; i < linhas.length-1 ;i++){
				String[] colunas = linhas[i].split("(</td>)");
		

				for(int j=3; j < colunas.length-2 ;j++){
					colunas[j] = colunas[j].substring(colunas[j].indexOf(">")+1);
					colunas[j] = colunas[j].substring(colunas[j].indexOf(">")+1);
					if(colunas[j].indexOf("<") > 0){
			      colunas[j] = colunas[j].substring(0, colunas[j].indexOf("<"));
					}
				materias[i][j] = colunas[j];
				}
				
			
			}
			
		
		}
		
		public static void puxaMaterias(String html){
			String html_tabela;
			
			if(html.indexOf("div id=\"fsDisciplinas_int\"") > -1){
			html_tabela = html.substring(html.indexOf("div id=\"fsDisciplinas_int\""));
			html_tabela = html_tabela.substring(0, html_tabela.indexOf("</div>"));
			html_tabela = html_tabela.substring(html_tabela.indexOf("<tr class=\"imprime\">", html_tabela.indexOf("<tr class=\"imprime\">")+1 ));
			String[] linhas = html_tabela.split("(</tr>)");

			materias = new String [linhas.length-4][9];
			
			for(int i=0; i < linhas.length - 4 ;i++){
				linhas[i] = linhas[i].substring(linhas[i].indexOf(">")+1);
				String colunas[] = linhas[i].split("</td>");
				
				for(int j=0; j < 3 ;j++){
				if(colunas[j].toLowerCase().contains("<td>")) colunas[j] = colunas[j].replace("<td>", "");
				if(colunas[j].indexOf(">") > 0){ colunas[j] = colunas[j].substring(colunas[j].indexOf(">")+1);}
				materias[i][j] = colunas[j];
				}
			}
		
			}
			 puxaAulas(html);
		}
		
		public static void puxaAulas(String html){
			String html_tabela;
			html_tabela = html.substring(html.indexOf("<div id=\"fshorarios_int\">"));
			html_tabela = html_tabela.substring(0, html_tabela.indexOf("</div>"));
			html_tabela = html_tabela.substring(html_tabela.indexOf("<tr class=\"imprime\">", html_tabela.indexOf("<tr class=\"imprime\">")+1 ));
			html_tabela = html_tabela.substring(0, html_tabela.indexOf("</table>"));
			String linhas[] = html_tabela.split("(</tr>)");
			
			for(int i=0; i < linhas.length ;i++){
				linhas[i] = linhas[i].replace("<tr class=\"imprime\">", "");
				linhas[i] = linhas[i].replace("</tr>", "");
				linhas[i] = linhas[i].substring(linhas[i].indexOf("<td>"));
			}
			aulas = new String [linhas.length][6];
			for(int i=0; i < linhas.length ;i++){
				String[] colunas = linhas[i].split("(</td>)");
				
				for(int j=0; j < colunas.length ;j++){
					colunas[j] = colunas[j].replace("<td>", "");
					colunas[j] = colunas[j].substring(colunas[j].indexOf(">")+1);
					colunas[j] = colunas[j].substring(0, colunas[j].indexOf("<"));
					if(!colunas[j].equals("&nbsp;")){
					colunas[j] = replaceCodAulas(colunas[j].substring(0, colunas[j].indexOf("-"))) + colunas[j].substring(colunas[j].indexOf("/"));
					}
					
					aulas[i][j] = colunas[j];
				}
			}
				
	}
	
		public static String replaceCodAulas(String codigo){
			
			for(int i=0;i<materias.length;i++){
			if(codigo.equals(materias[i][0])){
				codigo = materias[i][1];
				return codigo;
			}
			}
			
			return codigo;
		}

		static int horaDia(Date hoje){
			int hora_dia = 0;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // Pattern pra hora na matriz

			try {
				hoje = format.parse(format.format(new Date()));

			} catch (ParseException e) {e.printStackTrace();}
		
			
			try {
			     if(hoje.after(format.parse("07:30")) && hoje.before(format.parse("08:20")))hora_dia = 0;
			else if(hoje.after(format.parse("08:20")) && hoje.before(format.parse("09:10")))hora_dia = 1;
			else if(hoje.after(format.parse("09:10")) && hoje.before(format.parse("10:00")))hora_dia = 2;
			else if(hoje.after(format.parse("10:00")) && hoje.before(format.parse("11:10")))hora_dia = 3;
			else if(hoje.after(format.parse("11:10")) && hoje.before(format.parse("12:00")))hora_dia = 4;
			else if(hoje.after(format.parse("12:00")) && hoje.before(format.parse("12:50")))hora_dia = 5;
			else if(hoje.after(format.parse("12:50")) && hoje.before(format.parse("13:50")))hora_dia = 6;
			else if(hoje.after(format.parse("13:50")) && hoje.before(format.parse("14:40")))hora_dia = 7;
			else if(hoje.after(format.parse("14:40")) && hoje.before(format.parse("15:50")))hora_dia = 8;
			else if(hoje.after(format.parse("15:50")) && hoje.before(format.parse("16:40")))hora_dia = 9;
			else if(hoje.after(format.parse("16:40")) && hoje.before(format.parse("17:30")))hora_dia = 10;
			else if(hoje.after(format.parse("17:30")) && hoje.before(format.parse("18:20")))hora_dia = 11;
			else if(hoje.after(format.parse("18:20")) && hoje.before(format.parse("19:30")))hora_dia = 12;
			else if(hoje.after(format.parse("19:30")) && hoje.before(format.parse("20:20")))hora_dia = 13;
			else if(hoje.after(format.parse("20:20")) && hoje.before(format.parse("21:10")))hora_dia = 14;
			else if(hoje.after(format.parse("21:10")) && hoje.before(format.parse("22:10")))hora_dia = 15;
			else if(hoje.after(format.parse("22:10")) && hoje.before(format.parse("23:00")))hora_dia = 16;
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return hora_dia;
		}
		
		static String faixaHoraria (int hora_dia){
			String faixaHr = "";
			
					 if(hora_dia == 0) 	faixaHr="07:30 - 08:20";
				else if(hora_dia == 1)  faixaHr="08:20 - 09:10";
				else if(hora_dia == 2)  faixaHr="09:10 - 10:00";
				else if(hora_dia == 3)  faixaHr="10:20 - 11:10";
				else if(hora_dia == 4)  faixaHr="11:10 - 12:00";
				else if(hora_dia == 5)  faixaHr="12:00 - 12:50";
				else if(hora_dia == 6)  faixaHr="13:00 - 13:50";
				else if(hora_dia == 7)  faixaHr="13:50 - 14:40";
				else if(hora_dia == 8)  faixaHr="14:40 - 15:50";
				else if(hora_dia == 9)  faixaHr="15:50 - 16:40";
				else if(hora_dia == 10) faixaHr="16:40 - 17:30";
				else if(hora_dia == 11) faixaHr="17:30 - 18:20";
				else if(hora_dia == 12) faixaHr="18:40 - 19:30";
				else if(hora_dia == 13) faixaHr="19:30 - 20:20";
				else if(hora_dia == 14) faixaHr="20:20 - 21:10";
				else if(hora_dia == 15) faixaHr="21:20 - 22:10";
				else if(hora_dia == 16) faixaHr="22:10 - 23:00";
			 
		return faixaHr;
		}
		
		
}
