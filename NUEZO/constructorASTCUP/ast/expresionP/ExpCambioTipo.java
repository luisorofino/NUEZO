package ast.expresionP;

import ast.Programa;
import ast.lineaP.KindAsig;
import ast.expresionP.RNum;
import ast.expresionP.ZNum;
import ast.tiposP.Tipo;
import ast.Programa;

public class ExpCambioTipo extends Expresion{
	private Expresion e;
	private KindAsig fun;
	
	public ExpCambioTipo(KindAsig fun, Expresion e){
		this.e = e;
		this.fun = fun;
	}

	public String toString(){
		return fun.toString() + "(" + e.toString() + ")";
	}

	@Override
	public void binding(){
		e.binding();
	}

	@Override
	public boolean eAsig(){
		return false;
	}

	@Override
	public void checkType(){
		e.checkType();
		Tipo t = e.getTipo();

		switch(fun){				
			case TOR:
				if ((t.equals("ZNUM"))) setTipo(RNum.Tipo());
				else {
					System.out.println("ERROR: fallo en tipo TOR" + this);
					Programa.setFin();
				}
				break;
			case TOZ:
				if ((t.equals("RNUM"))) setTipo(ZNum.Tipo());
				else {
					System.out.println("ERROR: fallo en tipo TOZ" + this);
					Programa.setFin();
				}
				break;
			default:
		}
	}

	public void generaCodigo(){
		e.generaCodigo();
		switch(fun){
			case TOR:
				Programa.codigo.println("\tf32.convert_i32_s");
				break;
			case TOZ:
				Programa.codigo.println("\ti32.trunc_f32_s");
				break;
			default:
		}
	}

	public void calcularDirRelativa(){}
}

