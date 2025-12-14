package ast.expresionP;

import ast.lineaP.KindAsig;
import ast.tiposP.Tipo;
import ast.tiposP.TipoBasico;
import ast.tiposP.KindTipo;
import ast.Programa;

public class State extends Constante{
	private boolean b;

	public State(KindAsig b){
		if (b == KindAsig.ON){
			this.b = true;
		}
		else{
			this.b = false;
		}
	}

	@Override
	public String toString(){
		if (b){
			return "on";
		}
		return "off";
	}

	public static Tipo Tipo(){
		return new TipoBasico(KindTipo.STATE);
	}

	public Tipo getTipo(){
		return new TipoBasico(KindTipo.STATE);
	}

	@Override
	public void checkType(){
		setTipo(State.Tipo());
	}

	public int getB(){
		if (b){
			return 1;
		}
		return 0;
	}

	public void generaCodigo(){
		Programa.codigo.println("\ti32.const " + getB());
	}
}