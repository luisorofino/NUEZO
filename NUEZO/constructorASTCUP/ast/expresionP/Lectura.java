package ast.expresionP;

import ast.expresionP.Expresion;
import ast.expresionP.ZNum;
import ast.expresionP.RNum;
import ast.expresionP.State;
import ast.Programa;

public class Lectura extends Expresion{
	private int tipoLectura; //0 si es z, 1 si r y 2 si s
	
	public Lectura(int tipo){
		this.tipoLectura = tipo;
	}

	public String toString(){
		String s = "";
		switch(tipoLectura){
			case 0:
				s = "ZREAD";	
				break;
			case 1:
				s = "RREAD";
				break;
			case 2:
				s = "SREAD";
				break;
			default:
				break;
		}
		return "("+s+")";
	}

	@Override
	public void binding(){}

	@Override
	public boolean eAsig(){
		return false;
	}

	@Override
	public void checkType(){
		switch(tipoLectura){
			case 0:
				setTipo(ZNum.Tipo());
				break;
			case 1:
				setTipo(RNum.Tipo());
				break;
			case 2:
				setTipo(State.Tipo());
				break;
			default:
				break;
		}

	}

	public void generaCodigo(){
		if(tipoLectura == 1) Programa.codigo.println("\tcall $readf32");
		else Programa.codigo.println("\tcall $readi32");
	}
}