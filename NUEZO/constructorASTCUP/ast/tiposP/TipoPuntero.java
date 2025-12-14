package ast.tiposP;

import ast.expresionP.Expresion;

import java.util.ArrayList;
import java.util.List;

public class TipoPuntero extends Tipo{
	
	private Tipo tipo;

	public TipoPuntero(Tipo tipo){
		this.tipo = tipo;
	}	

	@Override
	public String toString(){
		return tipo.toString() + " DIR";
	}

	@Override
	public void binding(){
		tipo.binding();
	}

	@Override
	public Tipo reduceTipo(){
		this.tipo = tipo.reduceTipo();
		return this;
	}

	public Tipo getTipoApuntado(){
		return tipo.reduceTipo();
	}

	@Override
	public int getTamanyoTipo(){
		return 4;
	}
}