package lexer;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class is used to Create Token objects. Each token has a Type Type, where Type is an enum
 * declared in this class, and a String Value.
 *
 * @author jackiemun
 *
 */
public class Token
{
    /**
     * Enum that represents possible token types
     */
    public enum Type
    {
        PROGRAM, BEGIN, END, VAR, FUNCTION, PROCEDURE, RESULT, INTEGER, REAL, ARRAY, OF, NOT, IF, THEN, ELSE, WHILE,
        DO, IDENTIFIER, INTCONSTANT, REALCONSTANT, RELOP, MULOP, ADDOP, ASSIGNOP, COMMA, SEMICOLON, COLON, LPAREN,
        RPAREN, DOUBLEDOT, LBRACKET, RBRACKET, UNARYMINUS, UNARYPLUS, ENDMARKER, ENDOFFILE;

        /**
         * Creates and returns a string representation of a token Type
         * @param type the Type to represent in string from
         * @return passed in Type in string form
         */
        public String TypeToString(Type type)
        {
            String str = "" + type;
            return str;
        }
    }

    private Type Type;
    private String Value;
    private int LineCount;

    /**
     * Constructor for a Token (Type-Value pair)
     * @param type the token Type
     * @param value the token Value
     */
    public Token(Type type, String value)
    {
        this.Type = type;
        this.Value = value;
    }

    /**
     * Gets the Type of a token
     * @return token Type
     */
    public Type GetType()
    {
        return Type;
    }

    /**
     * Gets the Value of a token
     * @return token Value
     */
    public String GetValue()
    {
        return Value;
    }

    /**
     * Sets the Type of a token to a give Type
     * @param type the Type being set
     */
    public void SetType(Type type)
    {
        this.Type = type;
    }

    /**
     * Sets the Value of a token to a given Value
     * @param value the Value being set
     */
    public void SetValue(String value)
    {
        this.Value = value;
    }


    /**
     * Gets the Type of a token as a string
     * @return token Type in string format
     */
    public String GetTypeAsString()
    {
        String str = "" + Type;
        return str;
    }

    /**
     * Gets a token's line count
     * @return the line count
     */
    public int GetLineCount()
    {
        return LineCount;
    }

    /**
     * Sets a token's line count
     * @param lineCount int passed in to be set to linecount
     */
    public void SetLineCount(int lineCount)
    {
        this.LineCount = lineCount;
    }

    /**
     * Gets the TVI opcode associated with an operator token
     * @return String TVI opcode
     */
    public String GetOpCode()
    {
        String opcode;

        switch (this.Type)
        {
            case MULOP:
                if (Integer.parseInt(this.Value) == 1)
                {
                    opcode = "mul";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 3)
                {
                    opcode = "div";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 2)
                {
                    opcode = "/";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 4)
                {
                    opcode = "mod";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 5)
                {
                    opcode = "and";
                    break;
                }
            case ADDOP:
                if (Integer.parseInt(this.Value) == 1)
                {
                    opcode = "add";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 2)
                {
                    opcode = "sub";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 3)
                {
                    opcode = "or";
                    break;
                }
            case RELOP:
                if (Integer.parseInt(this.Value) == 1)
                {
                    opcode = "beq";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 2)
                {
                    opcode = "bne";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 3)
                {
                    opcode = "blt";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 4)
                {
                    opcode = "bgt";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 5)
                {
                    opcode = "ble";
                    break;
                }
                else if (Integer.parseInt(this.Value) == 6)
                {
                    opcode = "bge";
                    break;
                }
            default:
                opcode = null;
                break;
        }

        return opcode;
    }

    /**
     * Creates and returns a string representation of a token
     * @return string representing the token
     */
    public String TokenToString()
    {
        String output;

        output = "[" + Type + ", " + Value + "]";

        return output;
    }
}
