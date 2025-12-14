package ast.lineaP.buclesP;

import java.util.List;

import ast.expresionP.Expresion;
import ast.lineaP.Instruccion;
import ast.lineaP.Declaracion;
import ast.lineaP.Asignacion;
import ast.Programa;
import ast.lineaP.DecVar;
import ast.lineaP.Return;
import ast.lineaP.Condicional;

public class For extends Bucles{
    private Instruccion ins;
    private Expresion exp;
    private List<Instruccion> cuerpo;
    private Asignacion asig;

    public For(Instruccion ins, Expresion exp, Asignacion asig, List<Instruccion> cuerpo) {
        this.ins = ins;
        this.exp = exp;
        this.cuerpo = cuerpo;
        this.asig = asig;
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

        return "(FOR ((" + ins.toString() + ", " + exp.toString() + ", " + asig.toString() + ") DO (" + s + ")))";
    }

    @Override
    public void binding(){
        Programa.abrirBloque();
		ins.binding();
        exp.binding();
        asig.binding();
        for(Instruccion inst : cuerpo){
            if(inst instanceof Bucles || inst instanceof Condicional || inst instanceof Return){
				inst.setLink(this.getLink());
			}
            inst.binding();
        }
        Programa.print();
        Programa.cerrarBloque();
    }

    @Override
    public void checkType(){
        ins.checkType();
        exp.checkType();
        if(!(exp.getTipo().equals("STATE"))){
            System.out.println("ERROR mal tipado el bucle FOR " + this);
			Programa.setFin();
        }
        else{
            asig.checkType();
            for(Instruccion i: cuerpo) i.checkType();
        }
    }

    public void setPos(int delta) {
		int forDelta = delta;
		ins.setPos(forDelta);
		if (ins instanceof DecVar){
			forDelta += ins.getTamanyo();
		}
		for(Instruccion k : cuerpo){
            k.delta = forDelta;			
			k.setPos(forDelta);
			forDelta += k.getTamanyo();
		}

	}

	public void generaCodigo(){

        	ins.generaCodigo();

        	Programa.codigo.println("\tblock");
        	Programa.codigo.println("\t loop");

        	exp.generaCodigo();
        
        	Programa.codigo.println(" i32.eqz");
        	Programa.codigo.println(" br_if 1");
        
        	for (Instruccion inst : cuerpo){
         	   inst.generaCodigo();
        	}

        	asig.generaCodigo();

        	Programa.codigo.println("\t br 0");
        	Programa.codigo.println("\t end");
        	Programa.codigo.println("\tend");

	}

	public int maxMemory(){
		int max = ins.getTamanyo();
		int c = max;
		for(Instruccion inst : cuerpo){
			if(inst instanceof DecVar){
                int aux = inst.getTamanyo();
				c += aux;
				if(c > max) max += aux;
			}
			else if(inst.isBlock()){
				int max1 = inst.maxMemory();
				if(c+max1 > max){
					max = c + max1;
				}
			}
		}
		return max;
	} 
}