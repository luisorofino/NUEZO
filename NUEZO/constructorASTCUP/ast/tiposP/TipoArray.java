package ast.tiposP;

import ast.expresionP.ZNum;

import java.util.ArrayList;

public class TipoArray extends Tipo{
	
	private Tipo tipo;
	private ArrayList<ZNum> tam;

	public TipoArray (Tipo tipo, ArrayList<ZNum> tam){
		this.tipo = tipo;
		this.tam = tam;
	}

	@Override
	public String toString(){
		return tipo.toString() + " CHAIN " + tam.toString();
	}

	@Override
	public void binding(){
		tipo.binding();
	}

	public Tipo reduceTipo(){
		this.tipo = tipo.reduceTipo();
		if(this.tipo instanceof TipoArray){
			TipoArray aux = (TipoArray) tipo;
			this.tam.addAll(aux.getTam());
			this.tipo = aux.getTipoPrimario();
		}
		return this;
	}
	public Tipo getTipoPrimario(){
		if(this.tipo instanceof TipoArray)
			return ((TipoArray) tipo).getTipoPrimario();
		return tipo.reduceTipo();
	}

	public Tipo getTipoApuntado(){
		this.reduceTipo();
		if(tam.size() == 1)
			return this.tipo;
		ArrayList<ZNum> aux = new ArrayList<>(tam);
		aux.remove(0);
		return new TipoArray(this.tipo, aux);
	}

	public ArrayList<ZNum> getTam(){
		return this.tam;
	}

	@Override
    public boolean equals(Object o){
        if (this == null || o == null){
            return false;
        }
        if(!(o instanceof TipoArray)) return false;
		TipoArray oaux = (TipoArray) o;
		
		if(!tipo.equals(oaux.getTipoPrimario())) return false;
		ArrayList<ZNum> aux = oaux.getTam();
		if(tam.size() != aux.size()) return false;
		for(int i = 0; i < tam.size(); i++){
			if(tam.get(i).equals(-1)) tam.set(i,aux.get(i));
			else if ((!aux.get(i).equals(-1)) && (!tam.get(i).equals(aux.get(i)))) return false;
		}
        return true;
    }

	public int getNumElems(){
        int n = 1;
        for(ZNum z : tam){
            n *= z.getZ();
        }
        return n;
    }

    @Override
    public int getTamanyoTipo(){
        return tipo.getTamanyoTipo() * getNumElems();
    }
}