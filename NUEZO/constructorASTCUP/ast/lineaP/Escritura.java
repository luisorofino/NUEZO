package ast.lineaP;

import ast.expresionP.Expresion;
import ast.tiposP.KindTipo;
import ast.Programa;
import ast.tiposP.TipoBasico;

public class Escritura extends Instruccion{
	private Expresion exp;
	private int tipoEscritura; //0 si es zWrite, 1 si rWrite y 2 si sWrite
	
	public Escritura(int tipo, Expresion exp){
		this.exp = exp;
		this.tipoEscritura = tipo;
	}

	public String toString(){
		String s = "";
		if (tipoEscritura == 0){
			s = "ZWRITE";
		}
		else if(tipoEscritura == 1){
			s = "RWRITE";
		}
		else{
			s = "SWRITE";
		}
		return "("+s+" " + exp.toString() + ")";
	}

	@Override
	public void binding(){
		exp.binding();
	}

	@Override
	public void checkType(){
		exp.checkType();
		if(Programa.getFin() != 1){
			if(tipoEscritura==0 && !(exp.getTipo().equals("ZNUM"))){
				System.out.println("ERROR de tipos en la escritura " + this);
				Programa.setFin();
			}
			else if(tipoEscritura==1 && !(exp.getTipo().equals("RNUM"))){
				System.out.println("ERROR de tipos en la escritura " + this);
				Programa.setFin();
			}
			else if(tipoEscritura==2 && !(exp.getTipo().equals("STATE"))){
				System.out.println("ERROR de tipos en la escritura " + this);
				Programa.setFin();
			}
		}
	}

	public void generaCodigo(){
		exp.generaCodigo();
		if(tipoEscritura==1) Programa.codigo.println("\tcall $printf32");
		else Programa.codigo.println("\tcall $printi32");
	}
}