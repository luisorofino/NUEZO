package ast.lineaP;

import ast.tiposP.*;
import ast.lineaP.funcionP.Argumento;
import ast.expresionP.Expresion;
import ast.lineaP.buclesP.Bucles;


import java.util.List;
import ast.*;

public class Funcion extends Declaracion {
	private String name;
	private Tipo type;
	private List<Argumento> largs;
	private List<Instruccion> decfun;

	public Funcion(Tipo type, String name, List<Argumento> largs, List<Instruccion> decfun){
		this.name = name;
		this.type = type;
		this.largs = largs;
		this.decfun = decfun;
	}

	public String getName(){
		return name;
	}

	public List<Argumento> getArgs(){
		return this.largs;
	}
	
	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i < decfun.size();i++){
			s += "\n" + decfun.get(i).toString();
		}
		return "->FUNCION: " + type.toString() +" " + name.toString() + " " + largs.toString() + " {\n" + s + "\n\n}";
	}
	@Override
	public void binding(){
		ASTNode node = Programa.searchId(name);
		type.binding();
		if (node == null){
			Programa.insertar(name, this);
			Programa.abrirBloque();
			for(Argumento arg: largs){
				arg.binding();
			}
			int numReturn = 0;
			for(Instruccion ins: decfun){
				if (ins instanceof Return){
					numReturn += 1;
					ins.setLink(this);
				}
				else if(ins instanceof Bucles || ins instanceof Condicional){
					ins.setLink(this);
				}
				ins.binding();
			}
			if (numReturn == 0 && !type.equals(new TipoBasico(KindTipo.SILENT))){
				System.out.println("ERROR falta return en FUNCION " +this.name);
				Programa.setFin();

			}
			Programa.print();
			Programa.cerrarBloque();
		}
		else{
			System.out.println("ERROR: identificador en FUNCION " + name + " no se puede utilizar en " + this);
			Programa.setFin();
		}

	}

	@Override
	public void checkType(){
		this.type = type.reduceTipo();
		for(Argumento arg: largs){
			arg.checkType();
		}
		for(Instruccion ins: decfun){
			ins.checkType();
		}
	}

	public Tipo getTipo(){
		return this.type;
	}

	public boolean isBlock(){
		return true;
	}

	public int maxMemory(){
		int max = 0;
		int c = 0;

		for(Argumento arg : largs){
			c += arg.getTam();
			if(c>max) max += arg.getTam();
		}
		for(Instruccion ins : decfun){
			if(ins instanceof DecVar){
				c += ins.getTamanyo();
				if(c>max) max += ins.getTamanyo();
			}
			else if(ins.isBlock()){
				int max1 = ins.maxMemory();
				if(c+max1 > max){
					max = c + max1;
				}
			}
		}
		return max;
	} 

	public void setPos(){
		int funDelta = 0;
		this.delta = 0;
		//primero los argumentos y luego las instrucciones
		for(Argumento arg : largs){
			arg.delta = funDelta;
			funDelta += arg.getTam();
		}
		for(Instruccion ins : decfun){
			ins.delta = funDelta;
			ins.setPos(funDelta);
			funDelta += ins.getTamanyo();
		}
	}

	public void generaCodigo(){

		int tam = maxMemory() + 4;
        	Programa.codigo.print("(func $" + name);
        	if (! type.equals("SILENT")){
				tam += type.getTamanyoTipo();
           		Programa.codigo.print(" (result "+type.convertWasm()+")");
        	}
       		Programa.codigo.println("");
        	Programa.codigo.println("\t(local $localsStart i32)");
        	Programa.codigo.println("\t(local $temp i32)");
			Programa.codigo.println("\t(local $retAddr i32)");
			Programa.codigo.println("\t(local $index i32)");
			Programa.codigo.println("\t(local $src i32)");
			Programa.codigo.println("\t(local $dest i32)");
        	Programa.codigo.println("\ti32.const " + tam); //  ;; let this be the stack size needed (params+locals+2)*4");
        	Programa.codigo.println("\tcall $reserveStack"); // ;; returns old MP (dynamic link)");
        
        	Programa.codigo.println("\tlocal.set $temp");
        	Programa.codigo.println("\tglobal.get $MP");
        	Programa.codigo.println("\tlocal.get $temp");
        	Programa.codigo.println("\ti32.store"); // Guardo el MP antiguo en mp
        	Programa.codigo.println("\tglobal.get $MP");
        	Programa.codigo.println("\ti32.const 4"); // salto el mp antiguo y el sp
        	Programa.codigo.println("\ti32.add");
        	Programa.codigo.println("\tlocal.set $localsStart\n"); // La funcion empieza aqui
        	for (Instruccion instruccion: decfun){
				instruccion.generaCodigo(); 
				if (instruccion instanceof Return){
					break;
				}
				Programa.codigo.println("");
        	}
		if (type.equals("SILENT")){
			Programa.codigo.println("call $freeStack");
		}
		Programa.codigo.println(")");

	}

}