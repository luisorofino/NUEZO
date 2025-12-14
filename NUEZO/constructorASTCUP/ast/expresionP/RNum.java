package ast.expresionP;

import java.util.Arrays;
import java.util.List;

import ast.tiposP.Tipo;
import ast.tiposP.TipoBasico;
import ast.tiposP.KindTipo;
import ast.Programa;

public class RNum extends Constante{
	private float r;
	
	public RNum(String num){
		List<String> iden = Arrays.asList(num.split("\\."));
		String aux = iden.get(0) + "." + iden.get(1);
		this.r = Float.parseFloat(aux);
	}
	
	public String toString(){
		return "" + r;
	}

	public static Tipo Tipo(){
		return new TipoBasico(KindTipo.RNUM);
	}

	public Tipo getTipo(){
		return new TipoBasico(KindTipo.RNUM);
	}

	@Override
	public void checkType(){
		setTipo(RNum.Tipo());
	}

	public void generaCodigo(){
		Programa.codigo.println("\tf32.const " + r);
	}
}	