package ast.lineaP.funcionP;

import ast.tiposP.Tipo;
import ast.*;
import ast.tiposP.TipoArray;
import ast.expresionP.ZNum;

public class Argumento extends ASTNode{
	private Tipo type;
	private String name;
	private int ref;//1 por ref, 0 no ref

	public Argumento(Tipo type, String name, int ref){
		this.type = type;
		this.name = name;
		this.ref = ref;
		
	}

	public String toString(){
		if (ref == 0){
			return "(" + type.toString() + " " + name + ")";
		}
		else{
			return "(" + type.toString() + " & " + name + ")";
		}
		
	}

	public int getTamArray(){
		ZNum tam = ((TipoArray)this.type).getTam().get(0);
		return tam.getZ();
	}

	@Override
	public NodeKind nodeKind(){
		return NodeKind.ARGUMENTO;
	}

	@Override
	public void binding(){
		ASTNode node = Programa.searchIdLastFun(name);
		if (node == null){
			type.binding();
			
			Programa.insertar(name, this);
			
		}
		else{
			System.out.println("ERROR: identificador ARG" + name + " no se puede utilizar en " + this);
			Programa.setFin();
		}
	}
	public void calcularDirRelativa(){
		Programa.codigo.println("\ti32.const " + delta);
        Programa.codigo.println("\tlocal.get $localsStart");
        Programa.codigo.println("\ti32.add");
		if(ref==1) Programa.codigo.println("\ti32.load");
	}
	@Override
	public void checkType(){
		this.type = type.reduceTipo();
	}

	public Tipo getTipo(){
		return this.type;
	}

	public int getRef(){
		return this.ref;
	}

	public int getTam(){
		if (ref == 0){
			return type.getTamanyoTipo();
		}
		return 4;
	}
}