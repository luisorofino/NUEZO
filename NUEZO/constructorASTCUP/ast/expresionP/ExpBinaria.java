package ast.expresionP;

import ast.Programa;
import ast.lineaP.KindAsig;
import ast.tiposP.Tipo;
import ast.expresionP.RNum;
import ast.expresionP.State;

public class ExpBinaria extends Expresion {
	private Expresion e1;
	private Expresion e2;
	private KindAsig kind;


	public ExpBinaria(Expresion e1, Expresion e2, KindAsig kind){
		this.e1 = e1;
		this.e2 = e2;
		this.kind = kind;
	}

	@Override
	public String toString(){
		return "(" + kind.toString() + " (" + e1.toString() + ", " + e2.toString() + "))"; 
	}

	@Override
	public void binding(){
		e1.binding();
		e2.binding();
	}

	@Override
	public boolean eAsig(){
		return false;
	}

	@Override
	public void checkType(){
		e1.checkType();
		e2.checkType();
		Tipo t1 = e1.getTipo();
		Tipo t2 = e2.getTipo();
		setTipo(e2.getTipo());
		switch(kind){				
			case POR: 
			case MAS: 
			case MENOS:
			case DIVENT:
			case DIV:
				if((t1.equals("ZNUM") && t2.equals("RNUM")) || (t1.equals("RNUM") && t2.equals("ZNUM"))) setTipo(RNum.Tipo());
				else if(t1.equals(t2) && (t1.equals("ZNUM") || t1.equals("RNUM"))) setTipo(t1);
				else{
					System.out.println("ERROR: fallo en tipo EXPBINARIA" + this);
					Programa.setFin();
				}
				break;
			case MODUL:
				if(t2.equals("ZNUM") && (t1.equals("ZNUM") || t1.equals("RNUM"))) setTipo(t1);
				else{
					System.out.println("ERROR: fallo en tipo EXPBINARIA" + this);
					Programa.setFin();
				}
				break;
			case MAYORIGUAL:
			case MENORIGUAL:
			case MAYOR:
			case MENOR:
				if((t1.equals("ZNUM") || t1.equals("RNUM")) && (t2.equals("ZNUM") || t2.equals("RNUM"))) setTipo(State.Tipo());
				else{
					System.out.println("ERROR: fallo en tipo EXPBINARIA" + this);
					Programa.setFin();
				}
				break;
			case IGUAL:
			case DISTINTO:
				if (t1.equals(t2)) setTipo(State.Tipo());
				else{
					System.out.println("ERROR: fallo en tipo EXPBINARIA" + this);
					Programa.setFin();
				}
				break;
			case AND:
			case OR:
				if (t1.equals(t2) && t1.equals("STATE")) setTipo(t1);
				else{
					System.out.println("ERROR: fallo en tipo EXPBINARIA" + this);
					Programa.setFin();
				}
				break;
			default:
				break;
			
		}
	}

	public void generaCodigo() {
		Expresion exp1 = e1;
		Expresion exp2 = e2;
    	switch (kind) {
      		case MAS:
				if(this.getTipo().equals("ZNUM")){
					exp1.generaCodigo();
    				exp2.generaCodigo();
					Programa.codigo.println("\ti32.add");
				}else{
					exp1.generaCodigo();
					if(exp1.getTipo().equals("ZNUM")){
						Programa.codigo.println("\tf32.convert_i32_s");
					}
					exp2.generaCodigo();
					if(exp2.getTipo().equals("ZNUM")){
						Programa.codigo.println("\tf32.convert_i32_s");
					}
					Programa.codigo.println("\tf32.add");
				}
        		break;

			case MENOS:
				if(this.getTipo().equals("ZNUM")){
					exp1.generaCodigo();
    				exp2.generaCodigo();
					Programa.codigo.println("\ti32.sub");
				}else{
					exp1.generaCodigo();
					if(exp1.getTipo().equals("ZNUM")){
						Programa.codigo.println("\tf32.convert_i32_s");
					}
					exp2.generaCodigo();
					if(exp2.getTipo().equals("ZNUM")){
						Programa.codigo.println("\tf32.convert_i32_s");
					}
					Programa.codigo.println("\tf32.sub");
				}
				break;

			case POR:
				if(this.getTipo().equals("ZNUM")){
					exp1.generaCodigo();
    				exp2.generaCodigo();
					Programa.codigo.println("\ti32.mul");
				}else{
					exp1.generaCodigo();
					if(exp1.getTipo().equals("ZNUM")){
						Programa.codigo.println("\tf32.convert_i32_s");
					}
					exp2.generaCodigo();
					if(exp2.getTipo().equals("ZNUM")){
						Programa.codigo.println("\tf32.convert_i32_s");
					}
					Programa.codigo.println("\tf32.mul");
				}
				break;

			case DIV:
				if(this.getTipo().equals("ZNUM")){
					exp1.generaCodigo();
    				exp2.generaCodigo();
					Programa.codigo.println("\ti32.div_s");
				}else{
					exp1.generaCodigo();
					if(exp1.getTipo().equals("ZNUM")){
						Programa.codigo.println("\tf32.convert_i32_s");
					}
					exp2.generaCodigo();
					if(exp2.getTipo().equals("ZNUM")){
						Programa.codigo.println("\tf32.convert_i32_s");
					}
					Programa.codigo.println("\tf32.div");
				}
        		break;

      		case DIVENT:
				if(exp1.getTipo().equals(exp2.getTipo())){
					if(exp1.getTipo().equals("RNUM")){
						exp1.generaCodigo();
						exp2.generaCodigo();
						Programa.codigo.println("\tf32.div");
						Programa.codigo.println("\ti32.trunc_f32_s");
					}
					else{
						exp1.generaCodigo();
						exp2.generaCodigo();
						Programa.codigo.println("\ti32.div_s");
					}
				}
				else{
					if(exp1.getTipo().equals("RNUM")){
						exp1.generaCodigo();
						Programa.codigo.println("\ti32.trunc_f32_s");
						exp2.generaCodigo();
						Programa.codigo.println("\ti32.div_s");
					}
					else{
						exp1.generaCodigo();
						Programa.codigo.println("\tf32.convert_i32_s");
						exp2.generaCodigo();
						Programa.codigo.println("\tf32.div");
						Programa.codigo.println("\ti32.trunc_f32_s");
					}
				}        		
				break;

      		case MODUL:
				exp1.generaCodigo();
				if(exp1.getTipo().equals("RNUM")){
					Programa.codigo.println("\ti32.trunc_f32_s");
				}
				exp2.generaCodigo();
        		Programa.codigo.println("\ti32.rem_s");
        		break;

      		case MENOR:
				exp1.generaCodigo();
        		if (exp1.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				exp2.generaCodigo();
				if(exp2.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				Programa.codigo.println("\tf32.lt");
				break;

      		case MAYOR:
				exp1.generaCodigo();
        		if (exp1.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				exp2.generaCodigo();
				if(exp2.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				Programa.codigo.println("\tf32.gt");
				break;

			case MENORIGUAL:
        		exp1.generaCodigo();
        		if (exp1.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				exp2.generaCodigo();
				if(exp2.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				Programa.codigo.println("\tf32.le");
				break;

      		case MAYORIGUAL:
				exp1.generaCodigo();
        		if (exp1.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				exp2.generaCodigo();
				if(exp2.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				Programa.codigo.println("\tf32.ge");
				break;

      		case AND:
				exp1.generaCodigo();
				exp2.generaCodigo();
        		Programa.codigo.println("\ti32.and");
        		break;

			case OR:
				exp1.generaCodigo();
				exp2.generaCodigo();
				Programa.codigo.println("\ti32.or");
				break;

			case IGUAL:
				exp1.generaCodigo();
				exp2.generaCodigo();
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".eq");
				break;

			case DISTINTO:
				exp1.generaCodigo();
				exp2.generaCodigo();
				Programa.codigo.println("\t" + exp1.getTipo().convertWasm() + ".ne");
				break;
      		default:
    	}
  	}
}