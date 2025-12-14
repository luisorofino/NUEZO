package ast.lineaP.buclesP;

import ast.lineaP.Instruccion;


public abstract class Bucles extends Instruccion{

	public abstract String toString();

	public boolean isBlock(){
		return true;
	}

}	