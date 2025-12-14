package ast.tiposP;

public class TipoAux extends Tipo{
	private Tipo tipo;

	public TipoAux(Tipo tipo){
		this.tipo = tipo;
	}
	
	@Override
	public String toString(){
		return "(" + tipo.toString() + ")";
	}

	@Override
	public void binding(){
		tipo.binding();
	}

	@Override
	public Tipo reduceTipo(){
		this.tipo = tipo.reduceTipo();
		return tipo.reduceTipo();
	}

	@Override
	public int getTamanyoTipo(){
		return tipo.getTamanyoTipo();
	}
}