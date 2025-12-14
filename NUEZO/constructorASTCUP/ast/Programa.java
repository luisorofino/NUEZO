package ast;

import java.util.List;
import java.util.ArrayList;

import ast.lineaP.Instruccion;
import ast.lineaP.funcionP.Argumento;
import ast.tiposP.*;
import ast.expresionP.*;
import ast.lineaP.*;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;

public class Programa extends ASTNode {
	private List<Instruccion> lgobs;
	public static PilaTablaSimbolos pila;

	private static int fin;

	public static PrintWriter codigo;

	public Programa(List<Instruccion> lgobs){
		this.fin=0;
		this.lgobs = lgobs;
		this.pila = new PilaTablaSimbolos();
	}
	
	public String toString(){
		String s = "";
		for(int i = 0; i < lgobs.size(); i++){
			if (i == lgobs.size() - 1){
				s += lgobs.get(i).toString() + "\n";

			}
			else {
				s += lgobs.get(i).toString() + "\n";
			}
		}
		return "PROGRAMA:\n\n" + s;
	}

	private void inicializarFunciones(){
		List<Argumento> largs = new ArrayList<Argumento>();
		largs.add(new Argumento(new TipoBasico(KindTipo.ZNUM), "x", 0));
		pila.insertar("zWrite", new Funcion(new TipoBasico(KindTipo.SILENT), "zWrite", largs,new ArrayList<Instruccion>()));
		List<Argumento> largs1 = new ArrayList<Argumento>();
		largs1.add(new Argumento(new TipoBasico(KindTipo.STATE), "x", 0));
		pila.insertar("sWrite", new Funcion(new TipoBasico(KindTipo.SILENT), "sWrite", largs1, new ArrayList<Instruccion>()));
		List<Argumento> largs2 = new ArrayList<Argumento>();
		largs2.add(new Argumento(new TipoBasico(KindTipo.RNUM), "x", 0));
		pila.insertar("rWrite", new Funcion(new TipoBasico(KindTipo.SILENT), "rWrite",largs2, new ArrayList<Instruccion>()));
		pila.insertar("zRead", new Funcion(new TipoBasico(KindTipo.ZNUM), "zRead", new ArrayList<Argumento>(), new ArrayList<Instruccion>()));
		pila.insertar("sRead", new Funcion(new TipoBasico(KindTipo.STATE), "sRead", new ArrayList<Argumento>(),new ArrayList<Instruccion>()));
		pila.insertar("rRead", new Funcion(new TipoBasico(KindTipo.RNUM), "rRead", new ArrayList<Argumento>(),new ArrayList<Instruccion>()));

	}

	public void binding(){
		pila.abrirBloque();
		inicializarFunciones();
		for (int i = 0; i < lgobs.size(); i++){
			lgobs.get(i).binding();
		}
		pila.print();
		pila.cerrarBloque();
	}


	@Override
	public NodeKind nodeKind(){
		return NodeKind.PROGRAMA;
	}

	@Override
	public void checkType(){
		for(int i = 0; i < lgobs.size(); i++){
			lgobs.get(i).checkType();
		}
	}

	public static int getFin(){
		return fin;
	}

	public static void setFin(){
		fin=1;
	}

	public static void insertar(String name, ASTNode node){
		if(fin==0) pila.insertar(name, node);
	}

	public static void abrirBloque(){
		pila.abrirBloque();
	}

	public static void cerrarBloque(){
		pila.cerrarBloque();
	}

	public static ASTNode searchId(String name){
		return pila.searchId(name);
	}

	public static ASTNode searchIdLastFun(String name){
		return pila.searchIdLastFun(name);
	}

	public static void print(){
		pila.print();
	}	

	private void calcularDeltas(){
		for(Instruccion ins: lgobs){
			ins.setPos();
		}
	}

	public void generaCodigo(){
		try{

		calcularDeltas();
		codigo = new PrintWriter(new FileWriter("codigo.wat"));
		FileReader inicio = new FileReader("generacion/inicio.wat");
		inicio.transferTo(codigo);
		codigo.println("\ti32.const " + maxMemory());
		codigo.println("\tcall $reserveStack");
		codigo.println("\tlocal.set $temp");
		codigo.println("\tglobal.get $MP");
		codigo.println("\tlocal.get $temp");
		codigo.println("\ti32.store");
		codigo.println("\tglobal.get $MP");
		codigo.println("\ti32.const 4");
		codigo.println("\ti32.add");
		codigo.println("\tlocal.set $localsStart\n");
		
		Instruccion insMain = null;
		for(Instruccion ins : lgobs){
			if (ins instanceof DecVar || ins instanceof Block){
				ins.generaCodigo();
			}
			else if (ins instanceof Funcion){
				Funcion fun = (Funcion) ins;
				if (fun.getName().equals("engine")){
					insMain = ins;
				}
			}
			Programa.codigo.println("");
		}
		codigo.println("\tcall $engine");
		codigo.println("\ti32.load");
		codigo.println("\tcall $printi32");
		codigo.println("\tcall $freeStack");
		codigo.println(")");
		insMain.generaCodigo();
		Programa.codigo.println("");
		for(Instruccion ins : lgobs){
			if (!(ins instanceof DecVar) && !(ins.equals(insMain))){
				ins.generaCodigo();
				Programa.codigo.println("");
			}
		}
	
		inicio = new FileReader("generacion/final.wat");
		codigo.println("\n\n\n ;;FUNCIONES DEFINIDAS");
		inicio.transferTo(codigo);
		inicio.close();
        codigo.close();
		} catch(Exception e){
			Programa.setFin();
			
		}
	}

	public static int getSize(){
		return pila.getSize();
	}

	public void updateDelta(int tam){
		pila.updateDelta(tam);
	}

	public int maxMemory(){
		int max = 0;
		for(Instruccion ins : lgobs){
			if(ins instanceof DecVar){
				max += ins.getTamanyo();
			}
		}
		return max + 4;
		
	}

}