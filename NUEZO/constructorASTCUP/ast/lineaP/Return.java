package ast.lineaP;

import ast.expresionP.Expresion;
import ast.Programa;
import ast.tiposP.TipoBasico;

public class Return extends Instruccion{
	private Expresion exp;
	
	public Return(){
	}

	public Return(Expresion exp){
		this.exp = exp;
	}

	public String toString(){
		if (exp == null){
			return "(RETURN)";
		}
		return "(RETURN (" + exp.toString() + "))";
	}

	@Override
	public void binding(){
		if(exp!=null) exp.binding();
	}

	@Override 
	public void checkType(){
		Funcion fun = (Funcion) this.getLink();
		if(exp != null){
			exp.checkType();
			if(!(fun.getTipo().equals(exp.getTipo()))){
				System.out.println("ERROR en tipo RETURN " + this);
				Programa.setFin();
			}
		}
		else if(!fun.getTipo().equals("SILENT")){
            System.out.println("ERROR en tipo RETURN " + this);
            Programa.setFin();
        }
	}

	@Override
	public void generaCodigo() {
		if (exp == null) {
			Programa.codigo.println("\tglobal.get $SP");
			Programa.codigo.println("\ti32.const 4");
			Programa.codigo.println("\ti32.sub");
			Programa.codigo.println("\tcall $freeStack");
			Programa.codigo.println("\treturn");
			return;
		}
		// return con expresión

		// Calcular dirección donde guardar el valor de retorno: SP - 4
		Programa.codigo.println("\tglobal.get $SP");
		Programa.codigo.println("\ti32.const 4");
		Programa.codigo.println("\ti32.sub");
		Programa.codigo.println("\tlocal.set $retAddr");

		if (exp.getTipo() instanceof TipoBasico) {
			// Guardar el valor en retAddr
			Programa.codigo.println("\tlocal.get $retAddr");
			// Evaluar la expresión y dejar el valor en la pila
			exp.generaCodigo();
			Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");
		} else {
			// Tipo compuesto: calcular dirección origen (src) y copiar
			exp.calcularDirRelativa(); // asume que deja dirección en local $src
			Programa.codigo.println("\tlocal.get $src");
			Programa.codigo.println("\tlocal.get $retAddr");
			Programa.codigo.println("\ti32.const " + (exp.getTipo().getTamanyoTipo() / 4));
			Programa.codigo.println("\tcall $copyn");
		}
		// Liberar el stack
		Programa.codigo.println("\tglobal.get $SP");
		Programa.codigo.println("\ti32.const 4");
		Programa.codigo.println("\ti32.sub");
		Programa.codigo.println("\tcall $freeStack");

		// Retornar
		Programa.codigo.println("\treturn");
	}
}