package ast.lineaP.buclesP;

import java.util.List;

import ast.expresionP.Expresion;
import ast.lineaP.Instruccion;
import ast.lineaP.DecVar;

import ast.Programa;
import ast.lineaP.Return;
import ast.lineaP.Condicional;

public class While extends Bucles{
    private Expresion exp;
    private List<Instruccion> cuerpo;

    public While(Expresion exp, List<Instruccion> cuerpo) {
        this.exp = exp;
        this.cuerpo = cuerpo;
    }

    public String toString(){
        String s = "";
        for(int i = 0; i < cuerpo.size(); i++){
            if (i == (cuerpo.size() - 1)){
                s += cuerpo.get(i).toString();
            }
            else {
                s += cuerpo.get(i).toString() + ", ";
            }
        }

        return "(WHILE " + exp.toString() + " DO (" + s + ")))";
    }

    @Override
    public void binding(){
        exp.binding();
        Programa.abrirBloque();
        for(Instruccion ins : cuerpo){
            if(ins instanceof Bucles || ins instanceof Condicional || ins instanceof Return){
				ins.setLink(this.getLink());
			}
            ins.binding();
        }
        Programa.print();
        Programa.cerrarBloque();
    }

    @Override
    public void checkType(){
        exp.checkType();
        if(Programa.getFin()!=1){
            if(!(exp.getTipo().equals("STATE"))){
                System.out.println("ERROR mal tipado el bucle WHILE " + this);
                Programa.setFin();
            }
            else{
                for(Instruccion i: cuerpo) i.checkType();
            }
        }
    }

    public void setPos(int delta) {
		int whileDelta = delta;
		for(Instruccion ins : cuerpo){
            ins.delta = whileDelta;
			ins.setPos(whileDelta);
			whileDelta += ins.getTamanyo();
		}
	}

	public void generaCodigo(){
       
        	Programa.codigo.println("\tblock");
        	Programa.codigo.println("\t  loop");

        	exp.generaCodigo();
        
        	Programa.codigo.println("\t i32.eqz");
        	Programa.codigo.println("\t br_if 1");
        
        	for (Instruccion ins : cuerpo){
            		ins.generaCodigo();
        	}

       		Programa.codigo.println("\t br 0");
        	Programa.codigo.println("\t end");
        	Programa.codigo.println("\tend");
    	}

	public int maxMemory(){
		int max = 0;
		int c = 0;
		for(Instruccion ins : cuerpo){
			if(ins instanceof DecVar){
                int aux = ins.getTamanyo();
				c += aux;
                if(c > max) max += aux;
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

}
