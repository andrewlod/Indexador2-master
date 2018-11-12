package me.andrewlod.indexador;

public class MapStrDouble {
	private String texto;
	private double doub;
	
	public MapStrDouble(String _texto, double _doub) {
		texto = _texto;
		doub = _doub;
	}
	
	public String getString() {
		return texto;
	}
	
	public double getDouble() {
		return doub;
	}
	
}
