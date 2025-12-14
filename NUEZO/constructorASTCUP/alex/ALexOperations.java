package alex;

import constructorast.ClaseLexica;

public class ALexOperations {
  private AnalizadorLexicoExp alex;
  public ALexOperations(AnalizadorLexicoExp alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadId() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IDEN,alex.lexema()); 
  } 
  public UnidadLexica unidadEntero() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ENT,alex.lexema()); 
  } 
  public UnidadLexica unidadReal() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.REAL,alex.lexema()); 
  } 
  public UnidadLexica unidadSuma() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAS); 
  } 
  public UnidadLexica unidadResta() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MENOS); 
  } 
  public UnidadLexica unidadMul() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POR); 
  } 
  public UnidadLexica unidadDiv() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DIV); 
  } 
  
  public UnidadLexica unidadPAp() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PAP); 
  } 
  public UnidadLexica unidadPCierre() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PCIERRE); 
  } 
   public UnidadLexica unidadCAp() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CAP); 
  } 
  public UnidadLexica unidadCCierre() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CCIERRE); 
  }
  public UnidadLexica unidadLlAp() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.LAP); 
  } 
  public UnidadLexica unidadLlCierre() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.LCIERRE); 
  }
  public UnidadLexica unidadDelete(){
   return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DELETE); 
  }

  public UnidadLexica unidadIgual() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IGUAL); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.COMA); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EOF); 
  }
   public UnidadLexica unidadPunto() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTO);
   }

   public UnidadLexica unidadPuntoComa() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTOCOMA);
   }

   public UnidadLexica unidadBarraBaja() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BARRABAJA);
   }

   public UnidadLexica unidadMod() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MODUL);
   }

   public UnidadLexica unidadDivEnt() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIVENT);
   }

   public UnidadLexica unidadSumaPrefijo() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MASPREF);
   }

   public UnidadLexica unidadRestaPrefijo() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOSPREF);
   }

   public UnidadLexica unidadMultiPrefijo() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PORPREF);
   }

   public UnidadLexica unidadDivPrefijo() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIVPREF);
   }

   public UnidadLexica unidadModPrefijo() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MODULPREF);
   }

   public UnidadLexica unidadDivEnteraPrefijo() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIVENTPREF);
   }

   public UnidadLexica unidadAsignacion() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ASIG);
   }

   public UnidadLexica unidadMayor() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYOR);
   }

   public UnidadLexica unidadMenor() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOR);
   }

   public UnidadLexica operadorDistinto() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DISTINTO);
   }

   public UnidadLexica unidadMayorIgual() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYORIGUAL);
   }

   public UnidadLexica unidadMenorIgual() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENORIGUAL);
   }

   public UnidadLexica unidadNegacion() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NEGACION);
   }


   public UnidadLexica unidadZnum() {
    return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ZNUM);
   }
   public UnidadLexica unidadRnum() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RNUM);
   }
   public UnidadLexica unidadState() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.STATE);
   }
   public UnidadLexica unidadOn() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ON);
   }
   public UnidadLexica unidadOff() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OFF);
   }
   public UnidadLexica unidadAnd() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AND);
   }
   public UnidadLexica unidadOr() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OR);
   }
   public UnidadLexica unidadChain() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CHAIN);
   }
   public UnidadLexica unidadDir() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIR);
   }
   public UnidadLexica unidadAmpersand() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AMPERSAND);
   }
   public UnidadLexica unidadNew() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NEW);
   }
   public UnidadLexica unidadBlock() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BLOCK);
   }
   public UnidadLexica unidadEngine() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ENGINE);
   }
   public UnidadLexica unidadSilent() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.SILENT);
   }
   public UnidadLexica unidadReturn() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.RETURN); 
  }
   public UnidadLexica unidadZRead() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ZREAD);
   }
   public UnidadLexica unidadZWrite() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ZWRITE);
   }
   public UnidadLexica unidadRRead() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RREAD);
   }
   public UnidadLexica unidadRWrite() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RWRITE);
   }
    public UnidadLexica unidadDefine() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DEFINE);
   }

   public UnidadLexica unidadSRead() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.SREAD);
   }
   public UnidadLexica unidadSWrite() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.SWRITE);
   }
   public UnidadLexica unidadToR() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TOR);
   }
   public UnidadLexica unidadToZ() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TOZ);
   }
   public UnidadLexica unidadRename() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RENAME);
   }
   public UnidadLexica unidadAs() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AS);
   }
   public UnidadLexica unidadWhile() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WHILE);
   }
   public UnidadLexica unidadFor() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FOR);
   }
   public UnidadLexica unidadEach() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EACH);
   }
   public UnidadLexica unidadIn() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IN);
   }
   public UnidadLexica unidadIf() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IF);
   }
   public UnidadLexica unidadElif() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ELIF);
   }
   public UnidadLexica unidadElse() {
      return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ELSE);
   }

  public void error() {
    System.err.println("***"+alex.fila()+", "+alex.columna()+" Caracter inesperado: "+alex.lexema());
  }
  
}
