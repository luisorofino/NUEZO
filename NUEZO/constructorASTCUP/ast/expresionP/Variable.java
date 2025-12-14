package ast.expresionP;

import ast.Programa;
import ast.ASTNode;
import ast.lineaP.DecVar;
import ast.lineaP.funcionP.Argumento;

public class Variable extends Expresion{
    private String id;
    private Boolean m = true;

    public Variable(String id){
        this.id = id;
    }

    public String toString(){
        return "VAR " + id;
    }

    @Override
    public boolean eAsig(){
        return m;
    }

    @Override
    public void binding(){
        ASTNode node = Programa.searchId(id);
        if (node != null){
            this.link = node;
            if(!(this.link instanceof DecVar || this.link instanceof Argumento)){
                System.out.println("ERROR: desconocido");
                Programa.setFin();
            }
            else if(this.link instanceof DecVar) this.m = ((DecVar)this.link).modifiable();
            else this.m = true;
        }
        else{
            System.out.println("ERROR: identificador en VARIABLE " + id + " no se puede utilizar en " + this);
            Programa.setFin();
        }
    }

    @Override
    public void checkType(){
        setTipo(this.link.getTipo().reduceTipo());
    }

    public void generaCodigo() {
		calcularDirRelativa();
		Programa.codigo.println("\t" + this.getTipo().convertWasm() + ".load");		
	}

	public void calcularDirRelativa(){
		int dir = this.link.getDelta();
		if (this.link.getGlobal()){	
			dir += 4;
			Programa.codigo.println("\ti32.const " + dir);
		}
		else{
            Programa.codigo.println("\ti32.const " + dir);
			Programa.codigo.println("\tlocal.get $localsStart");
        	Programa.codigo.println("\ti32.add");
			if (this.link instanceof Argumento && ((Argumento)this.link).getRef()==1)
				Programa.codigo.println("\ti32.load");
		}
		
	}

}