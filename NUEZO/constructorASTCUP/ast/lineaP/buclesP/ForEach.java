package ast.lineaP.buclesP;

import java.util.List;

import ast.lineaP.Instruccion;
import ast.tiposP.Tipo;
import ast.tiposP.TipoArray;
import ast.Programa;
import ast.ASTNode;
import ast.lineaP.DecVar;
import ast.lineaP.Return;
import ast.lineaP.Condicional;
import ast.lineaP.funcionP.Argumento;

public class ForEach extends Bucles{

    private Tipo tipo;
    private String id1;
    private String id2;
    private List<Instruccion> cuerpo;

    public ForEach(Tipo tipo, String id1, String id2, List<Instruccion> cuerpo) {
        this.tipo = tipo;
        this.id1 = id1;
        this.id2 = id2;
        this.cuerpo = cuerpo;
    }

    public String toString(){
        String s = "";
        for(int i = 0; i < cuerpo.size(); i++){
            if (i == (cuerpo.size() - 1)){
                s += cuerpo.get(i).toString();
            }
            else {
                s += cuerpo.get(i).toString() + ", ";
            }
        }

        return "(FOR EACH " + tipo + " " + id1 + " IN " + id2 + " DO (" + s + ")))";
    }

    @Override
    public void binding(){
        tipo.binding();
        if(id1.equals(id2)){
            System.out.println("ERROR en FOR EACH: coinciden los identificadores");
            Programa.setFin();
        }
        ASTNode node = Programa.searchId(id2);
        if(node == null){
            System.out.println("ERROR en FOR EACH: " + id2 + " no declarada");
            Programa.setFin();
        }
        else{
            for(Instruccion inst : cuerpo){
                if(inst instanceof Bucles || inst instanceof Condicional || inst instanceof Return){
					inst.setLink(this.getLink());
				}
            }
            this.link = node;
        }
        Programa.abrirBloque();
        Instruccion declaracion = new DecVar(tipo, id1);
        Programa.insertar(id1, declaracion);
        for(Instruccion inst : cuerpo){
            inst.binding();
        }
        Programa.print();
        Programa.cerrarBloque();
        cuerpo.add(0, declaracion);
    }

    @Override
    public void checkType(){
        ASTNode nodo = this.getLink();
        if(!(nodo.getTipo() instanceof TipoArray) || !(((TipoArray)(nodo.getTipo())).getTipoApuntado().equals(tipo.reduceTipo()))){
            System.out.println("ERROR mal tipado el bucle FOR EACH" + this);
			Programa.setFin();
        }
        else{
            for(Instruccion i: cuerpo) i.checkType();
        }
    }

     public void setPos(int delta) {
		int forEach = delta;
		for(Instruccion ins : cuerpo){
            ins.delta = forEach;
			ins.setPos(forEach);
			forEach += ins.getTamanyo();
		}
	}

    @Override
    public void generaCodigo() {
        int tamanyo = 0;
        if(this.link instanceof DecVar) tamanyo = ((DecVar)this.link).getTamArray();
        else tamanyo = ((Argumento)this.link).getTamArray();

        Programa.codigo.println("\tblock");
        // Inicializar indice
        Programa.codigo.println("\ti32.const 0");
        Programa.codigo.println("\tlocal.set $index");
        //Comenzar bucle
        Programa.codigo.println("\t  loop");

        // Condicion: if index >= length entonces salir
        Programa.codigo.println("\t  local.get $index");
        Programa.codigo.println("\t  i32.const "+tamanyo);
        Programa.codigo.println("\t  i32.ge_u");
        Programa.codigo.println("\t  br_if 1");

        // Calcular direccion del elemento array[index]
        if(this.link instanceof DecVar) ((DecVar)this.link).calcularDirRelativa();
        else ((Argumento)this.link).calcularDirRelativa();
        Programa.codigo.println("\t  local.get $index");
        Programa.codigo.println("\t  i32.const " + tipo.getTamanyoTipo());
        Programa.codigo.println("\t  i32.mul");                 // offset = index * tamanyo del tipo
        Programa.codigo.println("\t  i32.add");                 // direccion final
        Programa.codigo.println("\t  local.set $src");

        // Calcular direccion donde copiar el elemento (la de la variable local 'id1')
        ((DecVar)cuerpo.get(0)).calcularDirRelativa();
        Programa.codigo.println("\t  local.set $dest");

        // Copiar elemento desde array[index] a variable x usando copyn
        Programa.codigo.println("\t  local.get $src");
        Programa.codigo.println("\t  local.get $dest");
        Programa.codigo.println("\t  i32.const " + (tipo.getTamanyoTipo() / 4));
        Programa.codigo.println("\t  call $copyn");
        //guardamos el indice para que no haya problemas si  hay for each anidados
        Programa.codigo.println("\t  local.get $index");
        // Ejecutar cuerpo del bucle
        for (Instruccion ins : cuerpo) {
            ins.generaCodigo();
        }

        // index++
        Programa.codigo.println("\t  i32.const 1");
        Programa.codigo.println("\t  i32.add");
        Programa.codigo.println("\t  local.set $index");

        // volver al principio del bucle
        Programa.codigo.println("\t  br 0");
        Programa.codigo.println("\tend");
        Programa.codigo.println("\tend");
    }

    public int maxMemory(){
		int max = 0;
		int c = 0;
		for(Instruccion ins : cuerpo){
			if(ins instanceof DecVar){
                int aux = ins.getTamanyo();
				c += aux;
                if(c > max) max += aux;
			}
			else if(ins.isBlock()){
				int max1 = ins.maxMemory();
				if(c+max1 > max){
					max = c + max1;
				}
			}
		}
		return max;
	} 

}