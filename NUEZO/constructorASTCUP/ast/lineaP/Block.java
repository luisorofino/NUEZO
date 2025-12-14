package ast.lineaP;

import java.util.List;
import java.util.ArrayList;
import ast.*;
import ast.lineaP.DecVar;
import ast.tiposP.Tipo;
import ast.tiposP.TipoBlock;

public class Block extends Declaracion{

    private String iden;
    private List<DecVar> lcampos;
    private ArrayList<Tipo> tipos;
    private Tipo tipo;

    public Block(String iden, List<DecVar> lcampos){
        this.iden = iden;
        this.lcampos = lcampos;
        this.tipos = new ArrayList<Tipo>();
        for(DecVar camp : lcampos) tipos.add(camp.getTipo());
        this.tipo = new TipoBlock(tipos);
    }

    public List<DecVar> getCampos(){
        return lcampos;
    }

    @Override
    public String toString(){
        return "(BLOCK (" + iden + ", " + lcampos.toString() + "))";
    }

    public Tipo getTipoCampo(String id){
        for(DecVar dec : lcampos){
            if(dec.getNombre().equals(id)) return dec.getTipo();
        }
        return null;
    }

    public boolean getGlobal(){
		return true;
	}

    @Override
    public void binding(){
        ASTNode node = Programa.searchId(iden);
        if (node == null){
            Programa.insertar(iden, this);
            Programa.abrirBloque();
            for(DecVar camp : lcampos){
                camp.binding();
            }
			Programa.print();
            Programa.cerrarBloque();

        }
        else{
            System.out.println("ERROR: identificador en BLOCK " + iden + " no se puede utilizar en " + this);
            Programa.setFin();
        }
    } 

    @Override 
	public void checkType(){
		for(DecVar camp: lcampos) camp.checkType();
        setTipo(tipo.reduceTipo());
	}

    public int getTamanyo(){
        int tami = 0;
        for(DecVar dec : lcampos)
            tami += dec.getTamanyo();
        return tami;
    }

    public int getDeltaCampo(String id){
        int deltaCampo = 0;
        for(DecVar c: lcampos){
            if(c.getNombre().equals(id)) break;
            deltaCampo += c.getTipo().getTamanyoTipo();
        }
        return deltaCampo;
    }


}