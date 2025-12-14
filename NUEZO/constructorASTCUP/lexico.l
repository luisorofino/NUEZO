package alex;

import errors.GestionErroresExp;

%%
%cup
%column
%line
%class AnalizadorLexicoExp
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresExp errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresExp errores){
  	this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = ({digitoPositivo}{digito}* | 0)
parteDecimal = ({digito}* {digitoPositivo} | 0)
separador = [ \t\r\b\n]
comentario = {abreComentario} ~ {cierraComentario}

abreComentario = \<\<
cierraComentario = \>\>
sumaPrefijo = {operadorSuma}{operadorAsignacion}
restaPrefijo = {operadorResta}{operadorAsignacion}
multiPrefijo = {operadorMultiplicacion}{operadorAsignacion}
divPrefijo = {operadorDivision}{operadorAsignacion}
modPrefijo = {operadorModulo}{operadorAsignacion}
divEnteraPrefijo = {operadorDivisionEntera}{operadorAsignacion}

numeroEntero = [\+\-]?{parteEntera}
numeroReal = [\+\-]?{parteEntera}\.{parteDecimal}
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
operadorDivisionEntera = \/\/
operadorModulo = \%
operadorAsignacion = \:\=
operadorIgual = \=\=
operadorDistinto = \!\=
operadorNegacion = \!
operadorMayorIgual = \>\=
operadorMenorIgual = \<\=
operadorMayor = \>
operadorMenor = \<

parentesisApertura = \(
parentesisCierre = \)
corcheteApertura = \[
corcheteCierre = \]
llaveApertura = \{
llaveCierre = \}
coma  = \,
punto = \.
puntoComa = \;
barraBaja = \_

znum = znum
rnum = rnum
state = state
on = on
off = off
and = and
or = or
chain = chain
dir = dir
ampersand = \&
new = new
block = block
delete = delete
define = define

engine = engine
silent = silent
return = return
zRead = zRead
zWrite = zWrite
rRead = rRead
rWrite = rWrite
sRead = sRead
sWrite = sWrite
toR = toR
toZ = toZ
rename = rename
as = as
while = while
for = for
each = each
in = in
if = if
elif = elif
else = else
identificador = {letra}({letra}|{digito})*

%%
{separador}               {}
{comentario}              {}

{numeroEntero}		  {return ops.unidadEntero();}
{numeroReal}		  {return ops.unidadReal();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{operadorModulo} 	  {return ops.unidadMod();}
{operadorDivisionEntera}  {return ops.unidadDivEnt();}
{sumaPrefijo}		  {return ops.unidadSumaPrefijo();}
{restaPrefijo}		  {return ops.unidadRestaPrefijo();}
{multiPrefijo}		  {return ops.unidadMultiPrefijo();}
{divPrefijo}		  {return ops.unidadDivPrefijo();}
{modPrefijo} 		  {return ops.unidadModPrefijo();}
{divEnteraPrefijo}	  {return ops.unidadDivEnteraPrefijo();}

{operadorAsignacion}      {return ops.unidadAsignacion();} 
{operadorMayor}        	  {return ops.unidadMayor();}
{operadorMenor}        	  {return ops.unidadMenor();}
{operadorIgual}        	  {return ops.unidadIgual();}
{operadorDistinto}        {return ops.operadorDistinto();}
{operadorMayorIgual}      {return ops.unidadMayorIgual();}
{operadorMenorIgual}      {return ops.unidadMenorIgual();}
{operadorNegacion}        {return ops.unidadNegacion();}

{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{corcheteApertura}        {return ops.unidadCAp();}
{corcheteCierre}          {return ops.unidadCCierre();}
{llaveApertura}      	  {return ops.unidadLlAp();}
{llaveCierre}        	  {return ops.unidadLlCierre();}
{punto}        		  {return ops.unidadPunto();}
{coma}                    {return ops.unidadComa();}
{puntoComa}        	  {return ops.unidadPuntoComa();}
{barraBaja}        	  {return ops.unidadBarraBaja();}

{and}        	 	  {return ops.unidadAnd();}
{or}        		  {return ops.unidadOr();}
{znum}      		  {return ops.unidadZnum();}
{rnum}       		  {return ops.unidadRnum();}
{state}      		  {return ops.unidadState();}
{on}       		  {return ops.unidadOn();}
{off}      		  {return ops.unidadOff();}
{chain}     		  {return ops.unidadChain();}
{dir}        		  {return ops.unidadDir();}
{ampersand}		  {return ops.unidadAmpersand();}
{new}        	 	  {return ops.unidadNew();}
{block}        		  {return ops.unidadBlock();}
{engine}      		  {return ops.unidadEngine();}
{silent}       		  {return ops.unidadSilent();}
{return}       		  {return ops.unidadReturn();}
{while}       		  {return ops.unidadWhile();}
{for}        		  {return ops.unidadFor();}
{each}        		  {return ops.unidadEach();}
{in}        		  {return ops.unidadIn();}
{if}       		  {return ops.unidadIf();}
{elif}    		  {return ops.unidadElif();}
{else}  		  {return ops.unidadElse();}
{rename}     		  {return ops.unidadRename();}
{as}       		  {return ops.unidadAs();}
{delete}		  {return ops.unidadDelete();}
{define}		  {return ops.unidadDefine();}

{zRead}  		  {return ops.unidadZRead();}
{rRead}  		  {return ops.unidadRRead();}
{sRead}  		  {return ops.unidadSRead();}
{zWrite}  		  {return ops.unidadZWrite();}
{rWrite}  		  {return ops.unidadRWrite();}
{sWrite}  		  {return ops.unidadSWrite();}
{toR}  			  {return ops.unidadToR();}
{toZ}  			  {return ops.unidadToZ();}
{identificador}           {return ops.unidadId();}


[^]                       {ops.error();}  
