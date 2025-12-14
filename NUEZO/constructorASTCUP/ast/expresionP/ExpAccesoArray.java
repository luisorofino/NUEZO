package ast.expresionP;

import ast.Programa;
import ast.lineaP.KindAsig;
import ast.tiposP.Tipo;
import ast.tiposP.TipoArray;

public class ExpAccesoArray extends Expresion{
	private Expresion e1;
	private Expresion e2;
	
	public ExpAccesoArray(Expresion e1, Expresion e2){
		this.e1 = e1;
		this.e2 = e2;
	}

	public String toString(){
		return e1.toString() + "[" + e2.toString() + "]";
	}

	@Override
	public void binding(){
		e1.binding();
		e2.binding();
	}

	@Override
	public boolean eAsig(){
		return e1.eAsig();
	}

	@Override
	public void checkType(){
		e1.checkType();
		e2.checkType();
		Tipo t1 = e1.getTipo();
		Tipo t2 = e2.getTipo();
		if((t1 instanceof TipoArray) && t2.equals("ZNUM")) setTipo(((TipoArray)t1).getTipoApuntado());
		else{
			System.out.println("ERROR: fallo en tipo ACCESO ARRAY " + this);
			Programa.setFin();
		}
	}

	public void calcularDirRelativa(){
		TipoArray t = (TipoArray) e1.getTipo();
		ZNum tam = t.getTam().get(0);
		int n = tam.getZ();
		comprobarRango(n);
		e1.calcularDirRelativa();
		int tamtipo = t.getTipoApuntado().getTamanyoTipo();
		Programa.codigo.println("\ti32.const " + tamtipo);
		e2.generaCodigo();
		Programa.codigo.println("\ti32.mul");
		Programa.codigo.println("\ti32.add");
	}

	public void comprobarRango(int tam){
		e2.generaCodigo();
		Programa.codigo.println("\ti32.const " + tam);
		Programa.codigo.println("\ti32.gt_s");
		Programa.codigo.println("\tif");
		Programa.codigo.println("\ti32.const 1");
		Programa.codigo.println("\tcall $exception");
		Programa.codigo.println("\tend");
		e2.generaCodigo();
		Programa.codigo.println("\ti32.const 0");
		Programa.codigo.println("\ti32.lt_s");
		Programa.codigo.println("\tif");
		Programa.codigo.println("\ti32.const 2");
		Programa.codigo.println("\tcall $exception");
		Programa.codigo.println("\tend");

	}

	public void generaCodigo(){
		calcularDirRelativa();
		Programa.codigo.println("\t" + e1.getTipo().getTipoPrimario().convertWasm() + ".load");
	}
}

