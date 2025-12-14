package ast.lineaP;

import ast.tiposP.Tipo;
import ast.*;

public class Alias extends Declaracion{
	private Tipo tipo;
	private String id;
	public Alias(Tipo tipo, String id){
		this.tipo = tipo;
		this.id = id;
	}

	public String toString(){
		return "(RENAME " + tipo.toString() + " AS " + id + ")";
	}

	public void binding() {
        ASTNode nodo = Programa.searchId(id);
        if (nodo != null) {
 			System.out.println("ERROR: este identificador ya esta usado " + id);	
			Programa.setFin();	
       	} else {
            Programa.insertar(id, this);
	    	tipo.binding();
        }
    }

	public Tipo getTipo(){
		return tipo.reduceTipo();
	}
}