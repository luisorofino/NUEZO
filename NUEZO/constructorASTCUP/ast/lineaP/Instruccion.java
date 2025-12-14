package ast.lineaP;

import ast.ASTNode;
import ast.NodeKind;

public abstract class Instruccion extends ASTNode{

	public abstract String toString();
	
	@Override
	public NodeKind nodeKind(){
		return NodeKind.INSTRUCCION;
	}

	public void checkType(){}

	public void binding(){}

	public void generaCodigo(){
	}

}