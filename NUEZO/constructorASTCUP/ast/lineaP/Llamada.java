package ast.lineaP;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import ast.expresionP.Expresion;
import ast.expresionP.Variable;
import ast.lineaP.Funcion;
import ast.*;
import ast.lineaP.funcionP.Argumento;

public class Llamada extends Instruccion{
	private String fun;
	private List<Expresion> largs;
	private Boolean asigned;

	public Llamada(String fun, List<Expresion> largs){
		this.fun = fun;
		this.largs = largs;
		this.asigned = false;
	}

	public Llamada(String fun, Expresion exp){
		this.fun = fun;
		this.largs = new ArrayList<Expresion>(Arrays.asList(exp));
		this.asigned=false;
	}

	@Override
	public String toString(){
		return 	"(LLAMADA (" + fun + ", " + largs.toString() + "))";	
	}

	@Override
    public void binding(){
        ASTNode node = Programa.searchId(fun);
        if (node != null){
            this.link = node;
        }
        else{
            System.out.println("ERROR: LLAMADA funcion " + fun + " no declarada");
            Programa.setFin();
        }

        for(Expresion e : largs)
            e.binding();
    }

	public String getNombre(){
		return fun;
	}

	@Override
	public void checkType(){
		if (!(this.getLink() instanceof Funcion)){
			System.out.println("ERROR en Llamada " + this+". No es el nombre de una funcion");
			Programa.setFin();
		}
		else{	
			Funcion funcion = (Funcion) this.getLink();
			List<Argumento> listFunArgs = funcion.getArgs();
			if (listFunArgs.size() != largs.size()){
				System.out.println("ERROR en Llamada " + this+". No coincide el numero de argumentos");
				Programa.setFin();
			}
			else{
				for(int i = 0; i < listFunArgs.size(); i++){
					largs.get(i).checkType();
					Argumento argu = listFunArgs.get(i);
					if (!(largs.get(i).getTipo().equals(argu.getTipo()))){
						System.out.println("ERROR en Llamada " + this+". El tipo de los argumentos no coincide");
						Programa.setFin();
						return;
					}
					else if (argu.getRef() == 1 && !largs.get(i).eAsig()){//ref	
						System.out.println("ERROR en Llamada " + this+". "+largs.get(i)+" no se puede pasar por referencia");
						Programa.setFin(); return;
					}
					
				}
			}
			setTipo(this.getLink().getTipo());
		} 
	}

	public void setAsigned(){
		this.asigned = true;
	}
	
	public void generaCodigo(){
		int delta = 0;
		Funcion funcion = (Funcion) this.getLink();
		List<Argumento> listFunArgs = funcion.getArgs();
		int pos = 0;
		for(Expresion exp : largs){
			delta += exp.getTipo().getTamanyoTipo();
			if (!(exp.eAsig())){
				Programa.codigo.println("\tglobal.get $SP");
				Programa.codigo.println("\ti32.const " + delta);
				Programa.codigo.println("\ti32.add");
				exp.generaCodigo();

				Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");

			}
			else{
				if (listFunArgs.get(pos).getRef() == 0){
					exp.calcularDirRelativa();
					Programa.codigo.println("\tglobal.get $SP");
					Programa.codigo.println("\ti32.const " + (listFunArgs.get(pos).getDelta() + 4));
					Programa.codigo.println("\ti32.add");
					Programa.codigo.println("\ti32.const " + (exp.getTipo().getTamanyoTipo()/4));
					Programa.codigo.println("\tcall $copyn");
					
				}
				else{
					Programa.codigo.println("\tglobal.get $SP");
					delta -= exp.getTipo().getTamanyoTipo();
					delta += 4;
					Programa.codigo.println("\ti32.const " + (listFunArgs.get(pos).getDelta() + 4));
					Programa.codigo.println("\ti32.add");
					exp.calcularDirRelativa();
					Programa.codigo.println("\t" + exp.getTipo().convertWasm() + ".store");
				}
			}			
			pos += 1;
		}
		Programa.codigo.println("\tcall $" + fun);
		if(!this.asigned && (!(this.link.getTipo().equals("SILENT")))){
			Programa.codigo.println("\tdrop");
		}
		else if (!(this.link.getTipo().equals("SILENT"))){
			Programa.codigo.println("\t" + getLink().getTipo().convertWasm() + ".load");

		}

	}

}