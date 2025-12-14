package ast.expresionP;

import ast.Programa;
import ast.lineaP.KindAsig;
import ast.lineaP.Block;
import ast.tiposP.Tipo;

import java.util.List;

public class ExpAccesoBlock extends Expresion {
	private Expresion e;
	private String id;
	private KindAsig kind;


	public ExpAccesoBlock(Expresion e, String id){
		this.e = e;
		this.id = id;
	}

	@Override
	public String toString(){
		return e.toString() + "." + id ; 
	}

	@Override
	public void binding(){
		e.binding();
	}

	@Override
	public boolean eAsig(){
		return e.eAsig();
	}

	@Override
	public void checkType(){
		e.checkType();
		Tipo t = e.getTipo().reduceTipo();
				
		if (t == null){
			System.out.println("ERROR: fallo en ACCESO BLOCK" + this);
			Programa.setFin();
		}
		else{
			Block node = (Block) t.getLink();
			Tipo tipoCampo = node.getTipoCampo(id);
			if (tipoCampo == null){
				System.out.println("ERROR: fallo en ACCESO BLOCK: no existe el campo " + id + " " + this);
				Programa.setFin();
			}	
			else{
				setTipo(tipoCampo.reduceTipo());
			}
		}

	}

	public void generaCodigo(){
        calcularDirRelativa();
        Programa.codigo.println("\t" + e.getTipo().convertWasm() + ".load");
    }

    public void calcularDirRelativa(){
        e.calcularDirRelativa();
        Block b = (Block) e.getTipo().getLink();
        int deltaCampo = b.getDeltaCampo(id);
        Programa.codigo.println("\ti32.const " + deltaCampo);
        Programa.codigo.println("\ti32.add");
    }
}