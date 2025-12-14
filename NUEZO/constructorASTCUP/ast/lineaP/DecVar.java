package ast.lineaP;

import ast.Programa;
import ast.ASTNode;
import ast.lineaP.Block;
import ast.lineaP.Declaracion;
import ast.tiposP.Tipo;
import ast.tiposP.TipoPuntero;
import ast.tiposP.TipoNombre;
import ast.expresionP.Expresion;
import ast.expresionP.IniArray;
import ast.expresionP.IniBlock;
import ast.tiposP.TipoBasico;
import java.util.ArrayList;
import java.util.List;
import ast.tiposP.TipoArray;
import ast.expresionP.ZNum;

public class DecVar extends Declaracion{
	private Tipo tipo;
	private String iden;
	private Expresion e;
	private boolean def = false;

	private boolean global = false;	
	//private int deltaReg = -1;

	public DecVar(Tipo tipo, String iden){
		this.tipo = tipo;
		this.iden = iden;	
	}

	public DecVar(Tipo tipo, String iden, Expresion e){
		this.tipo = tipo;
		this.iden = iden;
		this.e = e;	
	}

	public DecVar(Tipo tipo, String iden, Expresion e, int def){
		this.tipo = tipo;
		this.iden = iden;
		this.e = e;	
		this.def = true;
	}

	public Expresion getE(){
		return this.e;
	}

	public String toString(){
		if(def) return "(DEFINE " + tipo.toString() + ", " + iden +", "+e.toString()+")";
		if (e == null){//sin valor inicial
			return "(DECVAR " + tipo.toString() + ", " + iden + ")";
		}//con valor inicial (asignacion)
		return  "(DECVAR "+ tipo.toString() + ", " + iden + ", "+e.toString()+")";
	}

	public Tipo getTipo(){
		return tipo;
	}

	public String getNombre(){
		return iden;
	}

	public Boolean modifiable(){
		return !def;
	}

	@Override
    public void binding(){
        tipo.binding();
        ASTNode node = Programa.searchIdLastFun(iden);
        if (node == null){
            if(Programa.getSize() == 1){
               global = true;
            }
			if (e != null){
				e.binding();
			}
            Programa.insertar(iden, this);
        }
        else{
            System.out.println("ERROR: identificador en DECVAR " + iden + " no se puede utilizar en " + this);
            Programa.setFin();
        }

    }

	@Override 
	public void checkType(){
		this.tipo = tipo.reduceTipo();
		setTipo(tipo);
		if (e != null){
			e.checkType();
			if(Programa.getFin()!=1){
				Tipo tipoExp = e.getTipo();
				if(tipo instanceof TipoNombre){ // es un block
					Tipo aux = tipo.getLink().getTipo(); //devuelve un TipoBlock
					if(!(tipoExp instanceof TipoNombre) || !aux.equals(tipoExp.getLink().getTipo())){
						System.out.println("ERROR: mal tipada la declaracion " + this);
						Programa.setFin();
					}
				}
				else if(tipo instanceof TipoPuntero){
					if(!(tipoExp instanceof TipoPuntero || tipoExp.equals("ZNUM"))){
						System.out.println("ERROR: mal tipada la declaracion " + this);
						Programa.setFin();
					}
				}
				else if (tipo == null || (!tipo.equals(tipoExp) && !(tipo.equals("RNUM") && tipoExp.equals("ZNUM")))){
					System.out.println("ERROR: mal tipada la declaracion " + this);
					Programa.setFin();
				}
				else if(e instanceof IniArray){
					Tipo aux = tipo;
					((IniArray)e).setTipo2((TipoArray)aux);
				}
			}
		}
	}

	public int getTamArray(){
		ZNum tam = ((TipoArray)this.tipo).getTam().get(0);
		return tam.getZ();
	}

	public void generaCodigo(){
		if(e != null){
			if(!e.eAsig()){
				calcularDirRelativa();
				e.generaCodigo();
				if(!(e instanceof IniArray || e instanceof IniBlock)) {
					if(this.tipo.equals("RNUM")&& e.getTipo().equals("ZNUM")){
						Programa.codigo.println("\tf32.convert_i32_s");
					}
					Programa.codigo.println("\t" + this.tipo.convertWasm() + ".store");
				}
			}
			else {
				calcularDirRelativa();
				Programa.codigo.println("\tlocal.set $dest");

				e.calcularDirRelativa(); 
				Programa.codigo.println("\tlocal.set $src");

				if (e.getTipo() instanceof TipoBasico) {
					Programa.codigo.println("\tlocal.get $dest");

					Programa.codigo.println("\tlocal.get $src");
					Programa.codigo.println("\t" + e.getTipo().convertWasm() + ".load");

					if(this.tipo.equals("RNUM")&& e.getTipo().equals("ZNUM")){
						Programa.codigo.println("\tf32.convert_i32_s");
					}
					// Finalmente, el store
					Programa.codigo.println("\t" + this.tipo.convertWasm() + ".store");

				} else {
					// Copia de bloque (struct, array, etc.)
					Programa.codigo.println("\tlocal.get $src");
					Programa.codigo.println("\tlocal.get $dest");
					Programa.codigo.println("\ti32.const " + (e.getTipo().getTamanyoTipo() / 4));
					Programa.codigo.println("\tcall $copyn");
				}
			}

		}
		else if(tipo instanceof TipoNombre){
			calcularDirRelativa();
			Block b = (Block)tipo.getLink();
			List<DecVar> lcampos = b.getCampos();
			int desplazamiento = 0;
			for(DecVar campo: lcampos){
				if(campo.getE() != null){
					Programa.codigo.println("");
					Programa.codigo.println("\tcall $repeat");
					Programa.codigo.println("\ti32.const " + desplazamiento);
					Programa.codigo.println("\ti32.add");
					campo.getE().generaCodigo();
					if(!(campo.getE() instanceof IniArray || campo.getE() instanceof IniBlock)){ 
						if(campo.getTipo().equals("RNUM") && campo.getE().getTipo().equals("ZNUM")){
							Programa.codigo.println("\tf32.convert_i32_s");
						}
						Programa.codigo.println("\t" + campo.getTipo().convertWasm() + ".store");
					}
					Programa.codigo.println("");
				}
				desplazamiento+= campo.getTamanyo();
			}
		}

	}

	public void calcularDirRelativa(){
		if (global){
			Programa.codigo.println("\ti32.const " + (delta + 4));
		}
		else{
			Programa.codigo.println("\ti32.const " + delta);
        	Programa.codigo.println("\tlocal.get $localsStart");
        	Programa.codigo.println("\ti32.add");
		}
	}

	@Override
	public void setPos(){
		setDelta();
	}

	public boolean getGlobal(){
		return global;
	}

}