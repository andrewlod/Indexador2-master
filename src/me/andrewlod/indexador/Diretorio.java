package me.andrewlod.indexador;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Diretorio {
	private String path;
	private ArrayList<Documento> files;
	private ArrayList<Diretorio> subdirs;
	
	public Diretorio(String _path) {
		this.path = _path;
		File[] filesArray = new Documento("",path).listFiles();
		subdirs = new ArrayList<Diretorio>();
		files = new ArrayList<Documento>();
		for (int i = 0; i < filesArray.length; i++) {
		  if (filesArray[i].isFile()) {
		    files.add(new Documento(filesArray[i].getName(),filesArray[i].getPath()));
		  }else if(filesArray[i].isDirectory()) {
			  subdirs.add(new Diretorio(filesArray[i].getPath()));
		  }
		}
	}
	public ArrayList<Diretorio> getSubdirs(){
		ArrayList<Diretorio> dirs = new ArrayList<Diretorio>();
		for(int i = 0; i < subdirs.size(); i++) {
			Diretorio d = subdirs.get(i);
			dirs.add(d);
			for(int j = 0; j < d.getSubdirs().size(); j++) {
				dirs.add(d.getSubdirs().get(j));
			}
		}
		return dirs;
	}
	public String getPath() {
		return this.path;
	}
	public void showFiles() {
		for(int i = 0; i < files.size(); i++) {
			System.out.println(files.get(i));
		}
	}
	public ArrayList<Documento> getFiles(){
		return files;
	}
	public int getSizeFiles(){
		return files.size();
	}
	public String getFileAndRead(int index) {
		return files.get(index).read();
	}
	public HashMap<String, Integer> getDictAtIndex(int index){
		return files.get(index).getDicionario();
	}
	public HashMap<String, Integer> getDictAtIndex(int index, String[] stopwords){
		return files.get(index).getDicionario(stopwords);
	}

}
