package ast.expresionP;

import ast.ASTNode;
import ast.NodeKind;


public abstract class Expresion extends ASTNode {

	public abstract String toString();

	@Override
	public NodeKind nodeKind(){
		return NodeKind.EXPRESION;
	}

	public abstract void binding();

	public abstract void checkType();

	public boolean eAsig(){
		return true;
	}

	public void generaCodigo(){

	}

	public void calcularDirRelativa(){

	}

}