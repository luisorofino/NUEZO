package ast.tiposP;

import ast.ASTNode;
import ast.NodeKind;


public abstract class Tipo extends ASTNode{

	public abstract String toString();
	
	@Override
	public NodeKind nodeKind(){
		return NodeKind.TIPO;
	}

	public void checkType(){
		
	}
	
	@Override
    public boolean equals(Object o){
        if (this == null || o == null){
            return false;
        }
        if (o.toString().equals(this.toString())){
            return true;
        }
        return false;
    }

	public abstract void binding();

	public Tipo reduceTipo(){
		return this;
	}

	public abstract int getTamanyoTipo();

	public String convertWasm(){
		if (this.equals(new TipoBasico(KindTipo.RNUM))){
			return "f32";
		}
		return "i32";
	}

	public Tipo getTipoPrimario(){
		return this;
	}

}