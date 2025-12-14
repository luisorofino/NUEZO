package ast.expresionP;

import ast.Programa;
import ast.lineaP.KindAsig;
import ast.tiposP.Tipo;
import ast.tiposP.TipoPuntero;
import ast.expresionP.ZNum;
import ast.expresionP.Variable;

public class ExpUnaria extends Expresion {
	private Expresion e;
	private KindAsig kind;


	public ExpUnaria(Expresion e, KindAsig kind){
		this.e = e;
		this.kind = kind;
	}

	@Override
	public String toString(){
		return "(" + kind.toString() + " (" + e.toString() + "))";
	}

	@Override
	public void binding(){
		e.binding();
	}

	@Override
	public boolean eAsig(){
		if(kind == KindAsig.NEGACION)
			return false;
		return e.eAsig();
	}

	@Override
	public void checkType(){
		e.checkType();
		Tipo t = e.getTipo();

		switch(kind){				
			case NEGACION:
				if ((t.equals("STATE"))) setTipo(t);
				else{
					System.out.println("ERROR: fallo en tipo EXPUNARIA(negacion) " + this);
					Programa.setFin();
				}
				break;
			case AMPERSAND:
				if(e instanceof Variable) setTipo(ZNum.Tipo());
				else{
					System.out.println("ERROR: fallo en tipo EXPUNARIA(referencia) " + this);
					Programa.setFin();
				}
				break;
			case BARRABAJA:
				if(t instanceof TipoPuntero) setTipo(((TipoPuntero) t).getTipoApuntado());
				else{
					System.out.println("ERROR: fallo en tipo EXPUNARIA(puntero) " + this);
					Programa.setFin();
				}
				break;
			default:
				break;
			
		}
	}

	public void generaCodigo(){
		e.generaCodigo();
		switch (kind) {
	        case NEGACION:
				e.generaCodigo();
				Programa.codigo.println("\ti32.load");
          		Programa.codigo.println("\ti32.eqz");
				Programa.codigo.println("\tif (result i32)");
				Programa.codigo.println("\ti32.const 1");
				Programa.codigo.println("\telse");
				Programa.codigo.println("\ti32.const 0");
				Programa.codigo.println("\tend");
         		break;
			case AMPERSAND:
				e.calcularDirRelativa();
				break;
			case BARRABAJA:
				e.generaCodigo();
				Programa.codigo.println("\t" + this.getTipo().convertWasm() + ".load");
				break;
			default:
		}
	}

	public void calcularDirRelativa(){
		switch (kind) {
	        case NEGACION:
				e.calcularDirRelativa();
         		break;
			case AMPERSAND:
				e.calcularDirRelativa();
				break;
			case BARRABAJA:
				e.generaCodigo();
				break;
			default:
		}
	}

}