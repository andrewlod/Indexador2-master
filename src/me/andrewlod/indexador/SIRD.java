package me.andrewlod.indexador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class SIRD {
	
	private static final String FILES_PATH = "src\\me\\andrewlod\\indexador\\files\\";
	private static final String STOPWORDS_FILE = "src\\me\\andrewlod\\indexador\\stopwords\\pt_br.txt";
	private static Scanner in;
	
	public static void main(String[] args) {
		System.out.print("Entre com a sua pesquisa: (aperte apenas ENTER para sair)");
		in = new Scanner(System.in);
		String phrase = in.nextLine();
		
		while(!phrase.equals("")) {
			ArrayList<HashMap<String, Integer>> listWordsFile = new ArrayList<>();
			Diretorio d = new Diretorio(FILES_PATH);
			Documento doc = new Documento("", STOPWORDS_FILE);
			String[] stopwords = Separador.separar(doc.read());
			
			//Pega todos os diretorios, inclusive o primeiro
			ArrayList<Diretorio> dirs = new ArrayList<Diretorio>();
			dirs.add(d);
			dirs.addAll(d.getSubdirs());
			for(Diretorio dd : dirs) {
				System.out.println(dd.getPath());
			}
			//fim


			ArrayList<String> words = Separador.fazerDicionarioString(Separador.separar(phrase));

			for (int i = 0; i < d.getSizeFiles(); i++)
				listWordsFile.add(d.getDictAtIndex(i));

			Dicionario dict = new Dicionario(listWordsFile);
			double[] similaridades = dict.getSimilaridades(words);
			System.out.println(Arrays.toString(similaridades));
			ArrayList<Documento> docs = d.getFiles();
			LinkedHashMap<String, Double> h = listaIndexados(docs, similaridades);
			System.out.println(h.toString());
			
			System.out.print("Entre com a sua pesquisa: (aperte apenas ENTER para sair)");
			in = new Scanner(System.in);
			phrase = in.nextLine();
			
		} 
	}
	
	public static MapStrDouble getMaior(ArrayList<Double> valores, ArrayList<Documento> docs) {
		int index = 0;
		for(int i = 0; i < valores.size(); i++) {
			if(valores.get(i) > valores.get(index)) {
				index = i;
			}
		}
		MapStrDouble h = new MapStrDouble(docs.get(index).getNome(), valores.get(index));
		return h;
	}
	
	public static ArrayList<Double> toArrayList(double[] array){
		ArrayList<Double> convertido = new ArrayList<>();
		for(double o : array) {
			convertido.add(o);
		}
		return convertido;
	}
	
	public static LinkedHashMap<String,Double> listaIndexados(ArrayList<Documento> docs, double[] sims) {
		LinkedHashMap<String,Double> list = new LinkedHashMap<String, Double>();
		ArrayList<Double> simi = toArrayList(sims);
		int s = docs.size();
		for(int i = 0; i < s; i++) {
			MapStrDouble temporario = getMaior(simi, docs);
			list.put(temporario.getString(), temporario.getDouble());
			int index = simi.indexOf(temporario.getDouble());
			simi.remove(index);
			docs.remove(index);
		}
		
		
		return list;
	}
	public boolean insereArqTexto(String path, String nome) throws IOException {
		String fullPath = path + "\\" + nome;
		try {
			FileOutputStream fos = new FileOutputStream(fullPath);
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean removeArqTexto(String path, String nome) {
		String fullPath = path + "\\" + nome;
		File file = new File(fullPath);
		return file.delete();
	}
}
