package ast.expresionP;

import ast.lineaP.Llamada;
import ast.lineaP.KindAsig;
import ast.Programa;

public class ExpLlamada extends Expresion{
	private Llamada call;
	private String id;
	
	public ExpLlamada(Llamada call){
		this.call = call;
		this.id = call.getNombre();
	}

	public String toString(){
		return call.toString();
	}

	@Override
	public void binding(){
		call.binding();
	}

	@Override
	public boolean eAsig(){
		return false;
	}

	@Override
	public void checkType(){	
		call.checkType();
		setTipo(call.getTipo());
	}

	public void calcularDirRelativa(){
		int dir = call.getDelta();
		dir += 4;
		Programa.codigo.println("\ti32.const " + dir);	
	}

	public void generaCodigo() {
		call.setAsigned();
		call.generaCodigo();
	}
}

