package ast.expresionP;

public abstract class Constante extends Expresion{
    @Override
    public void binding(){}
    @Override
    public void checkType(){}
    @Override
    public boolean eAsig(){
        return false;
    }
}