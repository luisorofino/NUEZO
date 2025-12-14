package ast.lineaP;

import java.util.List;
import ast.expresionP.Expresion;
import ast.lineaP.DecVar;

import ast.*;
import ast.lineaP.buclesP.Bucles;

public class Condicional extends Instruccion{

	private Expresion exp;
	private List<Instruccion> cuerpo;
	private List<Condicional> lcond;
	private int tipo;//0 si if, 1 si elif, 2 si else

	public Condicional(int tipo, Expresion exp, List<Instruccion> cuerpo, List<Condicional> lcond){
		this.tipo = tipo;
		this.exp = exp;
		this.cuerpo = cuerpo;
		this.lcond = lcond;

	}

	public int getTipoIf(){
		return tipo;
	}

	public String toString(){
		if(tipo == 0){
			String s = "";
			for(int i = 0; i < cuerpo.size(); i++){
				if (i == (cuerpo.size() - 1)){
					s += cuerpo.get(i).toString();
				}
				else {
					s += cuerpo.get(i).toString() + ", ";
				}
			}
			
			String s2 = "";
			for(int i = lcond.size()-1; i >=0; i--){
				s2 +="\n" + lcond.get(i).toString();
			}				
			return "(IF ("+exp.toString()+") THEN DO ("+s+")"+s2+")";
		}
		else if(tipo==1){
			String s = "";
			for(int i = 0; i < cuerpo.size(); i++){
				if (i == (cuerpo.size() - 1)){
					s += cuerpo.get(i).toString();
				}
				else {
					s += cuerpo.get(i).toString() + ", ";
				}
			}
			return "(ELIF ("+exp.toString()+") THEN DO ("+s+"))";
		}
		else{
			String s = "";
			for(int i = 0; i < cuerpo.size(); i++){
				if (i == (cuerpo.size() - 1)){
					s += cuerpo.get(i).toString();
				}
				else {
					s += cuerpo.get(i).toString() + ", ";
				}
			}
			return "(ELSE DO ("+s+"))";
		}
	}

	@Override 
	public void binding(){
		if(tipo==0 || tipo==1) exp.binding();
		Programa.abrirBloque();
		for (Instruccion ins: cuerpo){ 
			if(ins instanceof Bucles || ins instanceof Condicional || ins instanceof Return){
				ins.setLink(this.getLink());
			}
			ins.binding();
		}
		Programa.print();
		Programa.cerrarBloque();
		if(lcond!=null && lcond.size()>0){
			for(Condicional c: lcond){
				c.setLink(this.getLink()); 
				c.binding();
			}
		}
	}

	@Override
	public void checkType(){
		if(exp != null) exp.checkType();
		if(exp!= null && !(exp.getTipo().equals("STATE"))){
			System.out.println("ERROR mal tipada la expresion del condicional " + this);
			Programa.setFin();
		}
		else{
			for(Instruccion i: cuerpo) i.checkType();
			if(lcond!=null && lcond.size()>0){
				for(Condicional c: lcond) c.checkType();
			}
		}
	}

	public boolean isBlock(){
		return true;
	}

	public void setPos(int delta) {
		int ifDelta = delta;
		for(Instruccion ins: cuerpo){
			ins.setPos(ifDelta);
			ifDelta += ins.getTamanyo();
		}
		if(lcond!=null && lcond.size()>0){
			for(Condicional c: lcond) c.setPos(delta);
		}
	}

	public int maxMemory(){
		int max = 0;
		int c = 0;

		for(Instruccion ins : cuerpo){
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

		if(lcond!=null && lcond.size()>0){
			for(Condicional ct: lcond) {
				int auxil = ct.maxMemory();
				if(auxil>max) max = auxil;
			}
		}
		return max;
	} 

	private void recorre(int i){
		Condicional c = lcond.get(i);
		c.generaCodigo();
		if(c.getTipoIf()==1 && i<lcond.size()-1){
			Programa.codigo.println("\telse");
			recorre(i+1);
			Programa.codigo.println("\tend");	
		}
		else if(c.getTipoIf()==1){
			Programa.codigo.println("\telse");
			Programa.codigo.println("\tend");
		}
	}

	public void generaCodigo(){
		if(tipo==0){
			exp.generaCodigo();
			Programa.codigo.println("\tif");
			for(Instruccion ins : cuerpo){
				ins.generaCodigo();
			}
			Programa.codigo.println("\telse");
			if(lcond!=null && lcond.size()>0){
				recorre(0);
			}
			Programa.codigo.println("\tend");		
		}
		else if(tipo==1){
			exp.generaCodigo();
			Programa.codigo.println("\tif");
			for(Instruccion ins : cuerpo){
				ins.generaCodigo();
			}
		}
		else{
			for(Instruccion ins : cuerpo){
				ins.generaCodigo();
			}
		}
	}

}