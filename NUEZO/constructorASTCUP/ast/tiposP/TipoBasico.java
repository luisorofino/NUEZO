package ast.tiposP;

public class TipoBasico extends Tipo {
	private KindTipo tipo;

	public TipoBasico(KindTipo tipo){
		this.tipo = tipo;
	}	

	public String toString(){
		return tipo.toString();
	}

	@Override
	public void binding(){}

	@Override
	public int getTamanyoTipo(){
		int tam = 0;
		switch(tipo){
			case ZNUM:
			case RNUM:
			case STATE:
				tam = 4;
				break;
		}
		return tam;
	}
}

