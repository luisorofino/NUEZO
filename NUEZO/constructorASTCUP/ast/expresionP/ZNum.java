package ast.expresionP;

import ast.tiposP.Tipo;
import ast.tiposP.TipoBasico;
import ast.tiposP.KindTipo;
import ast.Programa;

public class ZNum extends Constante{
	private int n;
	
	public ZNum(String z){
		this.n = Integer.parseInt(z);
	}
	
	public String toString(){
		return "" + n;
	}

	public int getZ(){
		return n;
	}

	public static Tipo Tipo(){
		return new TipoBasico(KindTipo.ZNUM);
	}

	public Tipo getTipo(){
		return new TipoBasico(KindTipo.ZNUM);
	}

	@Override
	public void checkType(){
		setTipo(ZNum.Tipo());
	}

	@Override
    public boolean equals(Object o){
		return this.toString().equals(o.toString()); 
	}

	public void generaCodigo(){
		Programa.codigo.println("\ti32.const " + n);
	}
}	