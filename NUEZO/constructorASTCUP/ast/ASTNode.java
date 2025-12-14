package ast;

import ast.tiposP.Tipo;

public abstract class ASTNode {
	public abstract String toString();
	public abstract NodeKind nodeKind();

	protected Tipo tipo;
	protected ASTNode link;

	public int delta;

	public Tipo getTipo(){
		return tipo;
	}

	public void setTipo(Tipo tipo){
		this.tipo = tipo;
	}	

	public ASTNode getLink(){
		return this.link;
	}

	public void setLink(ASTNode l){
		this.link = l;
	}

	public abstract void binding();
	public abstract void checkType();

	public void setDelta(){
    	delta = Programa.pila.getDelta();
        int tam = tipo.getTamanyoTipo();
   	   	Programa.pila.updateDelta(tam);
   	}

    public int getDelta() {
        return delta;
    }

	public void setPos(){
	}
	public void setPos(int deltaFun) {
		delta = deltaFun;
	}

	public int getTamanyo(){
		if (tipo != null){
			return tipo.getTamanyoTipo();
		}
		return 0;
	}

	public void getMemory(){

	}

	public int maxMemory(){
		return 0;
	} 

	public boolean isBlock(){
		return false;
	}

	public boolean getGlobal(){
		return false;
	}

	public void calcularDirRelativa(){
	}

	public void generaCodigo(){}
}