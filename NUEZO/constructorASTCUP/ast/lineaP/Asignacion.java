package ast.lineaP;

import ast.Programa;
import ast.expresionP.Expresion;
import ast.expresionP.ExpBinaria;
import ast.tiposP.*;
import ast.expresionP.IniArray;
import ast.expresionP.IniBlock;
import ast.tiposP.TipoBasico;

public class Asignacion extends Declaracion{
	private TipoAsig asig;
	private Expresion e2;
	private Expresion e1;

	public Asignacion(Expresion e1, TipoAsig asig, Expresion e2){
		this.e1 = e1;
		this.e2 = e2;
		this.asig = asig;
	}

	public String toString(){
		return "( "+asig.toString()+" (" + e1.toString() + ", "+ e2.toString()+"))";
	}

	@Override
	public void binding(){
		e1.binding();
		if(!e1.eAsig()){//si no es del tipo asignacion o no es modificable (es define)
			System.out.println("ERROR: expresion no asignable " + this);
			Programa.setFin();
		}
		e2.binding();
		switch(asig){
			case MASPREF: 
				e2 = new ExpBinaria(e1,e2,KindAsig.MAS);
				break;
			case MENOSPREF: 
				e2 = new ExpBinaria(e1,e2,KindAsig.MENOS);
				break;
			case PORPREF: 
				e2 = new ExpBinaria(e1,e2,KindAsig.POR);
				break;
			case DIVPREF: 
				e2 = new ExpBinaria(e1,e2,KindAsig.DIV);
				break;
			case DIVENTPREF: 
				e2 = new ExpBinaria(e1,e2,KindAsig.DIVENT);
				break;
			case MODULPREF: 
				e2 = new ExpBinaria(e1,e2,KindAsig.MODUL);
				break;
			default: break;
		
		}
		this.asig = TipoAsig.ASIG;
	}

	@Override
	public void checkType(){
		e1.checkType();
		e2.checkType();
		if(Programa.getFin()!=1){
			Tipo t1 = e1.getTipo();
			Tipo t2 = e2.getTipo();
			if(t1 == null || t2 == null){
				System.out.println("ERROR: mal tipada la declaracion " + this);
				Programa.setFin();
				return;
			}
			if(t1 instanceof TipoNombre){ // es un block
				Tipo aux = t1.getLink().getTipo();
				if(!aux.equals(t2) && (!(t2 instanceof TipoNombre) || !aux.equals(t2.getLink().getTipo()))){
					System.out.println("ERROR: mal tipada la declaracion " + this);
					Programa.setFin();
				}
			}
			else if(t1 instanceof TipoPuntero){
				if(!(t2 instanceof TipoPuntero || t2.equals("ZNUM"))){
					System.out.println("ERROR: mal tipada la declaracion " + this);
					Programa.setFin();
				}
			}
			else if(!t1.equals(t2) && !(t1.equals("RNUM") && t2.equals("ZNUM"))){
				System.out.println("ERROR mal tipada la asignacion " + this);
				Programa.setFin();
			}
			
			setTipo(t1);
		}
	}

	private boolean esNum(Expresion e){
		return (e.getTipo().equals("RNUM") || e.getTipo().equals("ZNUM"));
	}

	public void generaCodigo(){
		e1.calcularDirRelativa();
		if(!e2.eAsig()){
			e2.generaCodigo();
			if(!(e2 instanceof IniArray || e2 instanceof IniBlock)){
				if(e1.getTipo().equals("RNUM")&& e2.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				Programa.codigo.println("\t" + e1.getTipo().convertWasm() + ".store");
			}
		}
		else {
			Programa.codigo.println("\tlocal.set $dest");

			// Dirección origen: donde está el valor de la variable existente
			e2.calcularDirRelativa(); // deja dirección origen arriba de la pila
			Programa.codigo.println("\tlocal.set $src");

			if (e2.getTipo() instanceof TipoBasico) {
				// Copia simple: load de origen, store en destino
				Programa.codigo.println("\tlocal.get $dest");

				// Dirección de origen y carga del valor
				Programa.codigo.println("\tlocal.get $src");
				Programa.codigo.println("\t" + e1.getTipo().convertWasm() + ".load");
				
				if(e1.getTipo().equals("RNUM")&& e2.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				// Finalmente, el store
				Programa.codigo.println("\t" + e1.getTipo().convertWasm() + ".store");

			} else {
				// Copia de bloque (struct, array, etc.)
				Programa.codigo.println("\tlocal.get $src");
				Programa.codigo.println("\tlocal.get $dest");
				Programa.codigo.println("\ti32.const " + (e1.getTipo().getTamanyoTipo() / 4));
				Programa.codigo.println("\tcall $copyn");
			}
		}
	}
}

