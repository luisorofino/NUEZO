package ast.expresionP;

import ast.lineaP.KindAsig;

import java.util.List;

public class ExpPar extends Expresion{
	private Expresion e;
	
	public ExpPar(Expresion e){
		this.e = e;
	}

	public String toString(){
		return "(" + e.toString() + ")";
	}

	@Override
	public void binding(){
		e.binding();
	}

	@Override
	public boolean eAsig(){
		return e.eAsig();
	}

	@Override
	public void checkType(){
		e.checkType();
		setTipo(e.getTipo());
	}

	public void generaCodigo(){
		e.generaCodigo();
	}

	public void calcularDirRelativa(){
		e.calcularDirRelativa();
	}

}

