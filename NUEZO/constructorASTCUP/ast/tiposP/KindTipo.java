package ast.tiposP;

public enum KindTipo{
	ZNUM("ZNUM"), STATE("STATE"), RNUM("RNUM"), SILENT("SILENT");

	private final String nombre;

	private KindTipo(String nombre){
		this.nombre = nombre;
	}

	@Override
	public String toString(){
		return nombre;
	}
}