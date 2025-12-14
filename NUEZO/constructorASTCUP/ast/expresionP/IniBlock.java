package ast.expresionP;

import java.util.ArrayList;
import java.util.List;

import ast.Programa;
import ast.tiposP.Tipo;
import ast.tiposP.TipoBlock;

public class IniBlock extends Expresion{

    private ArrayList<Expresion> vals;
     ArrayList<Tipo> tipos;

    public IniBlock(ArrayList<Expresion> vals){
        this.vals = vals;
        this.tipos = new ArrayList<Tipo>();
    }

    public String toString(){
        return "<(" + vals.toString() + ")>";
    }

    @Override
    public void binding(){
        for(Expresion e : vals) e.binding();
    }

    public boolean eAsig(){
		return false;
	}

    @Override
    public void checkType(){
        for(Expresion e: vals){
            e.checkType();
            this.tipos.add(e.getTipo().reduceTipo());
        }
        setTipo(new TipoBlock(tipos));
    }

    public void generaCodigo(){
		int desplazamiento = 0;
        int i=0;
		for(Expresion e: vals){
			Programa.codigo.println("");
			Programa.codigo.println("\tcall $repeat");
			Programa.codigo.println("\ti32.const " + desplazamiento);
			Programa.codigo.println("\ti32.add");
			e.generaCodigo();
			if(!(e instanceof IniArray || e instanceof IniBlock)) {
                if(tipos.get(i).equals("RNUM") && e.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
				Programa.codigo.println("\t" + tipos.get(i).convertWasm() + ".store");
            }
			Programa.codigo.println("");
			
			desplazamiento+= e.getTipo().getTamanyoTipo();
            i++;
		}
    }
}