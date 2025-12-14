package ast.expresionP;

import ast.Programa;
import ast.tiposP.Tipo;
import ast.tiposP.TipoPuntero;

public class IniPuntero extends Expresion{

	private Tipo tipo;
	private Expresion valor;
	
	public IniPuntero(Tipo tipo){
		this.tipo = tipo;
		this.valor = null;
	}

	public IniPuntero(Tipo tipo, Expresion e){
		this.tipo = tipo;
		this.valor = e;
	}

	public String toString(){
		if(valor == null)
			return "NEW " + tipo.toString();
		else return "NEW " + tipo.toString() + " (" + valor.toString() + ")";
	}

	public boolean eAsig(){
		return false;
	}

	@Override
	public void binding(){
		tipo.binding();
		if(valor != null) valor.binding();
	}

	@Override
	public void checkType(){
		Tipo t = tipo.reduceTipo();
		t.checkType();
		if(valor != null) {
			valor.checkType();
			if(!t.equals(valor.getTipo().reduceTipo())){
				System.out.println("ERROR: fallo en tipo INIPUNTERO" + this);
				Programa.setFin();
			}
		}
		setTipo(new TipoPuntero(t));
	}

	public void generaCodigo(){
        Programa.codigo.println("\ti32.const " + tipo.getTamanyoTipo());
        Programa.codigo.println("\tcall $reserveHeap");
        Programa.codigo.println("\tglobal.get $NP");
        if(valor != null){
            if(valor instanceof IniArray){
                valor.generaCodigo();
            }
            else{
                valor.generaCodigo();
                Programa.codigo.println("\t" + valor.getTipo().convertWasm() + ".store");
            }
            Programa.codigo.println("\tglobal.get $NP");
        }
    }

}

