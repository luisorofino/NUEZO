package ast.expresionP;

import java.util.ArrayList;
import java.util.List;

import ast.Programa;
import ast.tiposP.Tipo;
import ast.tiposP.TipoArray;

public class IniArray extends Expresion{

	private Expresion val;
	private ArrayList<Expresion> vals;
	
	public IniArray(Expresion val){
		this.val = val;
		this.vals = null;
	}

	public IniArray(ArrayList<Expresion> vals){
		this.val = null;
		this.vals = vals;
	}

	public String toString(){
		if(val == null)
			return vals.toString();
		else return "[" + val.toString() + "]";
	}

	@Override
	public void binding(){
		if(val != null) val.binding();
		else {
			for(Expresion e : vals) e.binding();
		}	
	}

	@Override
	public void checkType(){
		Tipo t;
		if(val != null) {
			val.checkType();
			t = new TipoArray(val.getTipo(), new ArrayList<ZNum>(List.of(new ZNum("-1"))));
		}
		else {
			vals.get(0).checkType();
			Tipo aux = vals.get(0).getTipo().reduceTipo();
			for(Expresion e : vals) {
				e.checkType();
				if(!aux.equals(e.getTipo().reduceTipo())){
					System.out.println("ERROR: fallo en tipo INIARRAY" + this);
					Programa.setFin();
				}
			}
			t = new TipoArray(aux,new ArrayList<ZNum>(List.of(new ZNum(""+vals.size()))));
		}
		setTipo(t.reduceTipo());
	}

	public void setTipo2(TipoArray tipo){
		setTipo(tipo);
		Tipo t = tipo.getTipoApuntado();
		if(val != null){
			if(t instanceof TipoArray)
				((IniArray)val).setTipo2((TipoArray)t);
			else val.setTipo(t);
		}
		else{
			for(Expresion e : vals){
				if(t instanceof TipoArray)
					((IniArray)e).setTipo2((TipoArray)t);
				else e.setTipo(t);
			}
		}
	}

	public boolean eAsig(){
		return false;
	}

	public void generaCodigo(){
        int size = ((TipoArray) this.getTipo()).getTam().get(0).getZ();
        Expresion e;
        for(int i = 0; i < size; i++){
            Programa.codigo.println("\tcall $repeat");
            if(val != null)
                e = val;
            else e = vals.get(i);
            Programa.codigo.println("\ti32.const " + i*e.getTipo().getTamanyoTipo());
            Programa.codigo.println("\ti32.add");
            e.generaCodigo();
            if(!(e instanceof IniArray || e instanceof IniBlock)){
				if(this.tipo.equals("RNUM") && e.getTipo().equals("ZNUM")){
					Programa.codigo.println("\tf32.convert_i32_s");
				}
                Programa.codigo.println("\t" + this.tipo.convertWasm() + ".store");
			}
        }
        Programa.codigo.println("\tdrop");
    }
}