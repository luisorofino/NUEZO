package ast.lineaP;

import ast.expresionP.Expresion;
import ast.tiposP.TipoPuntero;
import ast.Programa;

public class Delete extends Instruccion{

    private Expresion exp;

    public Delete(Expresion exp){
        this.exp = exp;

    }
    public String toString(){
        return "(DELETE (" + exp.toString() + "))"; 

    }

    @Override
    public void binding(){
        exp.binding();
    }

    @Override
	public void checkType(){
		exp.checkType();
		if (!(exp.getTipo() instanceof TipoPuntero)){
			System.out.println("ERROR mal tipado DELETE " + this); 
            Programa.setFin();
		}
	}
}