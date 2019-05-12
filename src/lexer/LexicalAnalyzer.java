package lexer;

import java.io.File;
import java.io.FileReader;
import java.io.PushbackReader;
import java.util.*;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class is the Lexical Analyzer component of the Compiler.
 * It takes an input source File, containing a program written in Vascal (a custom
 * language that is a subset of Pascal), and isolates tokens.
 * Tokens are represented as type-value pairs.
 *
 * The error handler recognizes the following errors: invalid character, ill-formed constants,
 * ill-formed comments, and identifiers that exceed the maximum length. An error
 * message is returned when an error is encountered.
 *
 * @author jackiemun
 *
 */
public class LexicalAnalyzer
{
    // private class data members

    File File;
    PushbackReader PushbackReader;
    private Token LastToken;
    private static final String VALID_CHARS =
            "!ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890.,;:<>/*[]+-=()}{\t\n\r ";
    private static final String VALID_PUNCT = ",;:<>/*[]+-=()}{";
    private static final String SINGLE_PUNCT = "*/,;[]()=";
    private static final String DOUBLE_PUNCT = "<>:";
    private static final String PLUS_MINUS = "+-";
    private boolean IsComment = false;
    private final int CHAR_LIMIT = 32;
    private boolean EndOfFile;
    private boolean EndFound;
    private int LineCount = 1;
    private Token TheToken;
    private final String KEYWORD_ARR[] = {"PROGRAM", "BEGIN", "END", "VAR", "FUNCTION", "PROCEDURE", "RESULT",
            "INTEGER", "REAL", "ARRAY", "OF", "NOT", "IF", "THEN", "ELSE", "WHILE", "DO"};
    private final Set<String> KEYWORDS_SET = new HashSet<>(Arrays.asList(KEYWORD_ARR));


    /**
     * Constructor for the lexer.LexicalAnalyzer
     * @param file the File being read by the lexer
     * @throws Exception
     */
    public LexicalAnalyzer(File file) throws Exception
    {
        this.File = file;
        this.PushbackReader = new PushbackReader(new FileReader(file), 2);
        EndOfFile = false;
        EndFound = false;
    }

    /**
     * Returns if the end of the File has been reached
     * @return the boolean variable endOFFile
     */
    public boolean IsEndOfFile()
    {
        return EndOfFile;
    }

    /**
     * Reads the File character by character.
     * Returns the next character in the File. Checks if the end of the File has been reached
     * @return the next character
     * @throws Exception
     */
    public char NextChar() throws Exception
    {
        int tempchar;

        // Uses a pushbackreader to read the file stream and to pushback tokens

        if (!(PushbackReader.ready()))
        {
            // indicates that the end of the file has been reached

            EndFound = true;
            EndOfFile = true;
        }

        tempchar = PushbackReader.read();

        return (char) tempchar;
    }

    /**
     * Creates and returns an end of File Token
     * @return an end of File lexer.Token
     * @throws Exception
     */
    public Token EndOfFile()
    {
        TheToken = new Token(Token.Type.ENDOFFILE, null);
        return TheToken;
    }

    /**
     * Returns where the lexer is at in a File
     * @return LineCount, the current line number
     */
    public int GetLine()
    {
        return LineCount;
    }

    /**
     * Checks if the identifier length is within the character limits
     * @param length the length of an identifier
     */
    public void CheckLength(int length) throws Exception
    {
        if (length > CHAR_LIMIT)
            throw new LexicalError("identifier too long", LineCount);
    }

    /**
     * Reads a new Token character by character.
     * This function is called when a Token is created and the lexer moves onto a new Token.
     * @throws Exception
     */
    public Token ReadNewToken() throws Exception
    {

        char character = NextChar();

        String strchar = "" + character;

        // checks if the character is valid

        if (!(VALID_CHARS.contains(strchar)) && !(IsComment))
            if (!EndFound)
                throw new LexicalError(strchar + " is not a valid character", LineCount);

        String lexeme = "";

        // checks for new lines, spaces, tabs, and comments

        while (character == ' ' || character == '\n' || character == '\t' || character == '\r' || character == '{')
        {
            lexeme = "";

            if (character == '\n')
                LineCount++;

            else if (character == '{')
            {
                IsComment = true;
                ReadComment(character);
            }
            character = NextChar();
        }

        // handles the end of the file

        if (EndFound)
        {
            TheToken = EndOfFile();
            EndOfFile = true;
        }

        // throws error for malformed comments

        else if (character == '}')
            throw new LexicalError("Ill-formed comment", LineCount);

        // if the character is a letter, calls for the start of an alphanumeric token

        else if (Character.isLetter(character))
        {
            lexeme += character;
            TheToken = ReadAlphaNum(lexeme);
        }

        // if the character is a digit, calls for the start of a digit token

        else if (Character.isDigit(character))
        {
            lexeme += character;
            TheToken = ReadDigit(lexeme);
        }

        // handles if the character is a dot

        else if (character == '.')
            TheToken = ReadDot();

        // other punctuation

        else
        {
            String str = "" + character;

            if (SINGLE_PUNCT.contains(str))
                TheToken = GetSinglePunct(str);

            else if (DOUBLE_PUNCT.contains(str))
                TheToken = GetDoublePunct(str);

            else if (PLUS_MINUS.contains(str))
                TheToken = GetPlusMinus(str);

            else throw new Error();
        }
        return TheToken;
    }

    /**
     * Creates and returns a Token for a single punctuation character (i.e no lookahead or pushback needed)
     * @param str a string containing the single punctuation character to be tokenized
     * @return a punctuation lexer.Token
     */
    public Token GetSinglePunct(String str)
    {
        switch (str)
        {
            case "*":
                TheToken = new Token(Token.Type.MULOP, Integer.toString(1));
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case "/":
                TheToken = new Token(Token.Type.MULOP, Integer.toString(2));
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case ",":
                TheToken = new Token(Token.Type.COMMA, null);
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case ";":
                TheToken = new Token(Token.Type.SEMICOLON, null);
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case "[":
                TheToken = new Token(Token.Type.LBRACKET, null);
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case "]":
                TheToken = new Token(Token.Type.RBRACKET, Integer.toString(4));
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case "(":
                TheToken = new Token(Token.Type.LPAREN, null);
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case ")":
                TheToken = new Token(Token.Type.RPAREN, null);
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case "=":
                TheToken = new Token(Token.Type.RELOP, Integer.toString(1));
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
        }
        return null;
    }

    /**
     * Creates and returns a Token for possible double punctuation (i.e have to look ahead at next character)
     * @param str the character containing the possible double punctuation to be tokenized
     * @return a punctuation lexer.Token
     * @throws Exception
     */
    public Token GetDoublePunct(String str) throws Exception
    {
        char character = NextChar();
        switch (str)
        {
            case "<":
                if (character == '>')
                {
                    Token token = new Token(Token.Type.RELOP, Integer.toString(2));
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
                }
                else if (character == '=')
                {
                    Token token = new Token(Token.Type.RELOP, Integer.toString(5));
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
                }
                else
                {
                    PushbackReader.unread((int) character);
                    Token token = new Token(Token.Type.RELOP, Integer.toString(3));
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
                }
            case ">":
                if (character == '=')
                {
                    Token token = new Token(Token.Type.RELOP, Integer.toString(6));
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
                }
                else
                {
                    PushbackReader.unread((int) character);
                    Token token = new Token(Token.Type.RELOP, Integer.toString(4));
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
                }
            case ":":
                if (character == '=')
                {
                    Token token = new Token(Token.Type.ASSIGNOP, null);
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
                }
                else
                {
                    PushbackReader.unread((int) character);
                    Token token = new Token(Token.Type.COLON, null);
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
                }
        }
        return null;
    }

    /**
     * Creates, prints, and returns a Token for a + or - character, depending on the last Token
     * @param str a String containing a + or - character
     * @return a lexer.Token for a + or - character
     */
    public Token GetPlusMinus(String str)
    {
        Token.Type lasttokentype = LastToken.GetType();
        Token token;

        // case of ADDOP's + and -

        if (lasttokentype == Token.Type.RPAREN || lasttokentype == Token.Type.RBRACKET ||
            lasttokentype == Token.Type.IDENTIFIER || lasttokentype == Token.Type.INTCONSTANT ||
            lasttokentype == Token.Type.REALCONSTANT)
        {
            switch (str)
            {
                case "+":
                    token = new Token(Token.Type.ADDOP, Integer.toString(1));
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
                case "-":
                    token = new Token(Token.Type.ADDOP, Integer.toString(2));
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
            }
        }

        // case of unary plus and unary minus

        else
        {
            switch (str)
            {
                case "+":
                    token = new Token(Token.Type.UNARYPLUS, null);
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
                case "-":
                    token = new Token(Token.Type.UNARYMINUS, null);
                    token.SetLineCount(LineCount);
                    LastToken = token;
                    return token;
            }
        }
        return null;
    }

    /**
     * Called when the lexer is in a comment. Keeps reading characters and treating them as white space until
     * a closing brace is encountered. Prints an error for ill-formed comments. Keeps reading characters.
     * @param character the current character
     * @throws Exception
     */
    public void ReadComment(char character) throws Exception
    {
        while (!(character == '}'))
        {
            character = NextChar();

            if (character == '\n')
                LineCount++;
        }

        // ill-formed comment error

        if (character != '}')
            throw new LexicalError("ill-formed comment", LineCount);

        IsComment = false;
    }

    /**
     * Continues building a lexeme that starts with a letter and can contain alphanumerics.
     * Creates and returns a Token for either a keyword, String operator, or identifier.
     * @param lexeme a String representing a lexeme starting with a letter
     * @throws Exception
     */
    public Token ReadAlphaNum(String lexeme) throws Exception
    {
        int length = 1;
        char character = NextChar();

        // keeps reading characters while they are alphanumeric

        while (Character.isLetter(character) || Character.isDigit(character))
        {
             lexeme += character;
             length++;
             character = NextChar();
        }

        CheckLength(length);
        lexeme = lexeme.toUpperCase();
        PushbackReader.unread((int) character);

        // if the string read in is a keyword

        if (KEYWORDS_SET.contains(lexeme))
            TheToken = GetKeyword(lexeme);

        // if the string read in is an operator

        else if (lexeme.equalsIgnoreCase("DIV") || lexeme.equalsIgnoreCase("MOD") ||
                 lexeme.equalsIgnoreCase("AND") || lexeme.equalsIgnoreCase("OR"))
            TheToken = GetStringOp(lexeme);

        // else its an identifier

        else TheToken = GetIdentifier(lexeme);

        return TheToken;
    }

    /**
     * Creates and returns the Token for a keyword.
     * @param lexeme a String representing a keyword lexeme
     * @return a keyword lexer.Token
     */
    public Token GetKeyword(String lexeme)
    {
        switch (lexeme)
        {
            case("PROGRAM"):
                TheToken = new Token(Token.Type.PROGRAM, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("BEGIN"):
                TheToken = new Token(Token.Type.BEGIN, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("END"):
                TheToken = new Token(Token.Type.END, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("VAR"):
                TheToken = new Token(Token.Type.VAR, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("FUNCTION"):
                TheToken = new Token(Token.Type.FUNCTION, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("PROCEDURE"):
                TheToken = new Token(Token.Type.PROCEDURE, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("RESULT"):
                TheToken = new Token(Token.Type.RESULT, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("INTEGER"):
                TheToken = new Token(Token.Type.INTEGER, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("REAL"):
                TheToken = new Token(Token.Type.REAL, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("ARRAY"):
                TheToken = new Token(Token.Type.ARRAY, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("OF"):
                TheToken = new Token(Token.Type.OF, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("NOT"):
                TheToken = new Token(Token.Type.NOT, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("IF"):
                TheToken = new Token(Token.Type.IF, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("THEN"):
                TheToken = new Token(Token.Type.THEN, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("ELSE"):
                TheToken = new Token(Token.Type.ELSE, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("WHILE"):
                TheToken = new Token(Token.Type.WHILE, null);
                TheToken.SetLineCount(LineCount);
                break;
            case("DO"):
                TheToken = new Token(Token.Type.DO, null);
                TheToken.SetLineCount(LineCount);
                break;
        }
        LastToken = TheToken;
        return TheToken;
    }

    /**
     * Creates and returns the Token for an identifier.
     * @param lexeme a String representing an identifier lexeme
     * @return an identifier lexer.Token
     */
    public Token GetIdentifier(String lexeme)
    {
        TheToken = new Token(Token.Type.IDENTIFIER, lexeme);
        TheToken.SetLineCount(LineCount);
        LastToken = TheToken;
        return TheToken;
    }

    /**
     * Creates and returns the Token for a String operator.
     * @param lexeme a String representing one of the String operators
     * @return a String operation lexer.Token
     */
    public Token GetStringOp(String lexeme)
    {
        switch (lexeme.toUpperCase())
        {
            case "DIV":
                TheToken = new Token(Token.Type.MULOP, Integer.toString(3));
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case "MOD":
                TheToken = new Token(Token.Type.MULOP, Integer.toString(4));
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case "AND":
                TheToken = new Token(Token.Type.MULOP, Integer.toString(5));
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
            case "OR":
                TheToken = new Token(Token.Type.ADDOP, Integer.toString(3));
                TheToken.SetLineCount(LineCount);
                LastToken = TheToken;
                return TheToken;
        }
        return null;
    }

    /**
     * Continues building a constant lexeme. Accepts valid punctuation, dots, or scientific notation and
     * calls the appropriate function to continue building the constant.
     * @param lexeme a String representing a constant lexeme
     * @throws Exception
     */
    public Token ReadDigit(String lexeme) throws Exception
    {
        char character = NextChar();

        // continues reading characters while they are digits

        while (Character.isDigit(character))
        {
            lexeme += character;
            character = NextChar();
        }

        String str = "" + character;

        // if the lexeme is a constant

        if (character == ' ' || character == '\n' || character == '\t' || character == '\r' ||
            character == '{' || VALID_PUNCT.contains(str))
        {
            TheToken = GetIntConstant(lexeme, character);

            if (character == '\n')
                LineCount++;
        }

        // if the lexeme contains a dot

        else if (character == '.')
            TheToken = ReadIntDot(lexeme);

        // if the lexeme contains scientific notaiton

        else if (character == 'e' || character == 'E')
        {
            lexeme += character;
            TheToken = ReadSci(lexeme, character);
        }

        // else malformed constant error

        else
        {
            System.out.println(character);
            System.out.println(lexeme);
            throw new LexicalError("Malformed constant", LineCount);
        }
        return TheToken;
    }


    /**
     * Creates and returns a Token for an integer constant. Pushes back the current character that is not in the int
     * @param lexeme a String representing an integer constant lexeme
     * @param character the current character to be pushed back
     * @return an integer constant lexer.Token
     * @throws Exception
     */
    public Token GetIntConstant(String lexeme, char character) throws Exception
    {
        PushbackReader.unread((int) character);
        Token token = new Token(Token.Type.INTCONSTANT, lexeme);
        token.SetLineCount(LineCount);
        LastToken = token;
        return token;
    }

    /**
     * Looks at the next character to check if a constant followed by a dot is either an int constant followed by a
     * double dot or a real constant
     * @param lexeme a String representing a constant followed by a dot - could be an int or real constant
     * @throws Exception
     */
    public Token ReadIntDot(String lexeme) throws Exception
    {
        char character = NextChar();

        // double dot case

        if (character == '.')
            TheToken = GetIntDoubleDot(lexeme, character);

        // real constant case

        else if (Character.isDigit(character))
        {
            lexeme += '.';
            TheToken = GetRealConstant(lexeme, character);
        }

        // malformed constant error

        else
            throw new LexicalError("Malformed constant", LineCount);

        return TheToken;
    }

    /**
     * Creates and returns a Token for an integer constant that is followed by a double dot.
     * Pushes back the read dot character in the double dot.
     * @param lexeme a String representing an int constant lexeme
     * @param character the current character to be pushed back
     * @return an integer constant TheToken
     * @throws Exception
     */
    public Token GetIntDoubleDot(String lexeme, char character) throws Exception
    {
        char[] cbuff = {character, character};
        PushbackReader.unread(cbuff);
        TheToken = new Token(Token.Type.INTCONSTANT, lexeme);
        TheToken.SetLineCount(LineCount);
        LastToken = TheToken;
        return TheToken;
    }

    /**
     * Continues building the real constant. If the constant is complete, creates and returns a Token.
     * If scientific notation is encountered, calls a function to continue reading & building the constant.
     * @param lexeme a String representing a real constant lexeme
     * @param character the current character
     * @return a real constant lexer.Token
     * @throws Exception
     */
    public Token GetRealConstant(String lexeme, char character) throws Exception
    {
        // continues reading characters while they are digits

        while (Character.isDigit(character))
        {
            lexeme += character;
            character = NextChar();
        }

        // finishes and returns the token

        String str = "" + character;
        if (character == ' ' || character == '\n' || character == '\t' || character == '\r' ||
            character == '{' || VALID_PUNCT.contains(str))
        {
            if (character == '\n')
                LineCount++;

            PushbackReader.unread((int) character);
            TheToken = new Token(Token.Type.REALCONSTANT, lexeme);
            TheToken.SetLineCount(LineCount);
            LastToken = TheToken;
            return TheToken;
        }

        // if the lexeme contains scientific notation

        else if (character == 'e' || character == 'E')
        {
            lexeme += character;
            TheToken = ReadSci(lexeme, character);
        }

        // malformed real constant error

        else
            throw new LexicalError("Malformed real constant", LineCount);

        return TheToken;
    }

    /**
     * Continues building a real constant that has scientific notation.
     * Prints an error and exits if there is an ill-formed constant.
     * @param lexeme a String representing a real constant lexeme with scientific notation
     * @param character the current character
     * @throws Exception
     */
    public Token ReadSci(String lexeme, char character) throws Exception
    {
        character = NextChar();

        // builds scientific notation after the + or - symbol

        if (character == '+' || character == '-')
        {
            lexeme += character;
            TheToken = ReadSciPlusMinus(lexeme);
        }

        // if the character is a digit, calls function to build real constant

        else if (Character.isDigit(character))
            TheToken = GetRealConstant(lexeme, character);

        // illegal constant error

        else
            throw new LexicalError("Not a legal constant", LineCount);

        return TheToken;
    }

    /**
     * Continues reading and building a real constant that has scientific notation and plus or minus symbol.
     * @param lexeme a String representing a real constant lexeme with scientific notation
     * @throws Exception
     */
    public Token ReadSciPlusMinus(String lexeme) throws Exception
    {
        char character = NextChar();

        // if the character is a digit, calls function to build real constant

        if (Character.isDigit(character))
            TheToken = GetRealConstant(lexeme, character);

        // malformed real constant error

        else
            throw new LexicalError("Malformed real constant", LineCount);

        return TheToken;
    }

    /**
     * Looks ahead to see if the Token is an end marker or a double dot - creates and prints the appropriate Token.
     * @return an end marker or double dot Token
     * @throws Exception
     */
    public Token ReadDot() throws Exception
    {
        char character = NextChar();

        // double dot case

        if (character == '.')
        {
            TheToken = new Token(Token.Type.DOUBLEDOT, null);
            TheToken.SetLineCount(LineCount);
            LastToken = TheToken;
            return TheToken;
        }

        // end marker case

        else
        {
            PushbackReader.unread((int) character);
            TheToken = new Token(Token.Type.ENDMARKER, null);
            TheToken.SetLineCount(LineCount);
            LastToken = TheToken;
            return TheToken;
        }
    }
}
