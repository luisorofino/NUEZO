package ast.tiposP;

import ast.expresionP.ZNum;

import java.util.ArrayList;

public class TipoBlock extends Tipo{
	
	private ArrayList<Tipo> tipos;

	public TipoBlock (ArrayList<Tipo> tipos){
		this.tipos = tipos;
	}

	@Override
	public String toString(){
		return "BLOCK " + tipos.toString();
	}

	@Override
	public void binding(){
		for(Tipo t : tipos) t.binding();
	}

	@Override
	public void checkType(){
		for(Tipo t : tipos) t.checkType();
	}

	public Tipo reduceTipo(){
		ArrayList<Tipo> aux = new ArrayList<Tipo>();
		for(Tipo t : tipos) aux.add(t.reduceTipo());
		tipos = aux;
		return this;
	}

	@Override
	public int getTamanyoTipo(){
		return 0;//da igual pq tipoblock es un tipo auxiliar
	}
}