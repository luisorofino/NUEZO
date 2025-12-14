package ast.tiposP;

import ast.Programa;
import ast.ASTNode;
import ast.lineaP.Alias;
import ast.lineaP.Block;

public class TipoNombre extends Tipo{
	private String tipo;

	public TipoNombre (String tipo) {
		this.tipo = tipo;
	}

	public String toString(){
		return tipo;
	}

	@Override
	public void binding(){
		ASTNode node = Programa.searchId(tipo);
		if (node != null){
			this.link = node;
		}
		else{	
			System.out.println("ERROR: TIPO " + tipo + " no declarado");
			Programa.setFin();
		}
	}

	@Override
	public Tipo reduceTipo(){
		if(this.link instanceof Alias)
			return link.getTipo();
		return this;
	}

	@Override
	public void checkType(){
		if(!(this.link instanceof Block)){
			System.out.println("ERROR en TipoNombre " + this);
			Programa.setFin();
		}
		
	}

	@Override
	public int getTamanyoTipo(){
		Block b = (Block) this.link;
        return b.getTamanyo();
	}
}