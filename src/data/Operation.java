/*
* Questa classe consente di verificare data una stringa 
* se essa rappresenta un'operazione algebrica e in tal caso 
* fornisce i metodi per risolvere tale operazione
*/
package data;

/**
 *
 * @author Andrea
 */
public class Operation {
    
    String source;
    public Operation(){}
    public Operation(String s){this.source = s;}
    
    /***************PRIMO PASSO DI CONVALIDA DELLA STRINGA*********************/
    public boolean validate(){
        return checkSource(this.source);
    }
    public boolean validate(String s){
        return checkSource(s);
    }
    private boolean checkSource(String s){
        if(s == null) return false;
        
        int i = 0;
        while(i<s.length()){
            int c = (int) (s.charAt(i));
            if(c >=40 && c<=57);
            else if(c == 32);
            else if(c == 94);
            else return false;
            i++;
        }
        return true;
    }
    /*****************FINE PRIMO PASSO*****************************************/
    /*********************ESEGUE IL PARSING DELLA STRINGA**********************/
    private String formatStringToOperation(String s){
        if(!validate(s)) return null;
        String tmp="";
        for(int i = 0; i<s.length(); i++){
            if(s.charAt(i) == ',') tmp = tmp+'.';
            else if(s.charAt(i) == ' ') ;
            else tmp = tmp+s.charAt(i);
        }
        return tmp;
    }
    public String parseOperation(String s){
        return this.formatStringToOperation(s);
    }
    public String parseOperation(){
        if(this.source == null) return null;
        return this.formatStringToOperation(source);
    }
    public String removeResult(String s){
        return this.makeRemoveResult(s);
    }
    private String makeRemoveResult(String s){
        String tmp="";
        int i = 0;
        while((s.charAt(i) == ' ') || !(s.charAt(i) == '=') && (i<s.length()-1)){
            tmp = tmp+s.charAt(i);
            i++;
        }
        return tmp;
    }
    /*******************FINE METODI PARSING************************************/
    private double solveOperation(String s){
        s = parseOperation(s);
      
        return 0;
    }
    public double getDouble(){
        return this.solveOperation(source);
    }
    public double getDouble(String s){
        return this.solveOperation(s);
    }
    
    /**
     * @param str*
     * @return ****************************************************************/
    public double expToVal(final String str) {
    class Parser {
        int pos = -1, c;

        void eatChar() {
            c = (++pos < str.length()) ? str.charAt(pos) : -1;
        }

        void eatSpace() {
            while (Character.isWhitespace(c)) eatChar();
        }

        double parse() {
            eatChar();
            double v = parseExpression();
            if (c != -1) throw new RuntimeException("Unexpected: " + (char)c);
            return v;
        }

        // Grammar:
        // expression = term | expression `+` term | expression `-` term
        // term = factor | term `*` factor | term `/` factor | term brackets
        // factor = brackets | number | factor `^` factor
        // brackets = `(` expression `)`

        double parseExpression() {
            double v = parseTerm();
            for (;;) {
                eatSpace();
                if (c == '+') { // addition
                    eatChar();
                    v += parseTerm();
                } else if (c == '-') { // subtraction
                    eatChar();
                    v -= parseTerm();
                } else {
                    return v;
                }
            }
        }

        double parseTerm() {
            double v = parseFactor();
            for (;;) {
                eatSpace();
                if (c == '/') { // division
                    eatChar();
                    v /= parseFactor();
                } else if (c == '*' || c == '(') { // multiplication
                    if (c == '*') eatChar();
                    v *= parseFactor();
                } else {
                    return v;
                }
            }
        }

        double parseFactor() {
            double v;
            boolean negate = false;
            eatSpace();
            if (c == '(') { // brackets
                eatChar();
                v = parseExpression();
                if (c == ')') eatChar();
            } else { // numbers
                if (c == '+' || c == '-') { // unary plus & minus
                    negate = c == '-';
                    eatChar();
                    eatSpace();
                }
                StringBuilder sb = new StringBuilder();
                while ((c >= '0' && c <= '9') || c == '.') {
                    sb.append((char)c);
                    eatChar();
                }
                if (sb.length() == 0) throw new RuntimeException("Unexpected: " + (char)c);
                v = Double.parseDouble(sb.toString());
            }
            eatSpace();
            if (c == '^') { // exponentiation
                eatChar();
                v = Math.pow(v, parseFactor());
            }
            if (negate) v = -v; // exponentiation has higher priority than unary minus: -3^2=-9
            return v;
        }
    }
    return new Parser().parse();
}
    /**************************************************************************/
}
