package parser;

import lexer.Token;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class handles error reporting for the Parser - it extends Exception.
 *
 * @author jackiemun
 *
 */
public class ParserError extends Exception
{

    /**
     * Default constructor that simply reports that a parser error
     * was encountered
     */
    public ParserError()
    {
        System.out.println("\n - PARSER ERROR - \n");
    }

    /**
     * Constructor that prints a parser error message
     * @param message message to be printed
     */
    public ParserError(String message)
    {
        System.out.println("\n - PARSER ERROR: - \n" + message);
    }

    /**
     * Constructor that prints a parser error message with the
     * affected input symbol (as a string) and the line where the error occurred
     * @param input input symbol, represented as a string, involved with the error
     * @param line line where the error occurred
     */
    public ParserError(String input, int line)
    {
        System.out.println("\n - PARSER ERROR: Problem with input symbol " + input + " on line " + line + " - \n");
    }

    /**
     * Constructor that prints a parser error message with the
     * affected input symbol (as a Token Type) and the line where the error occurred
     * @param input input symbol, represented as a Token Type, involved with the error
     * @param line line where the error occurred
     */
    public ParserError(Token.Type input, int line)
    {
        System.out.println("\n - PARSER ERROR: Problem with input symbol " + input + " on line " + line + " - \n");
    }

    /**
     * Constructor that prints a parser error message with
     * the affected input symbol (as a Token Type)
     * @param input input symbol, represented as a Token Type, involved with the error
     */
    public ParserError(Token.Type input)
    {
        System.out.println("\n - PARSER ERROR: - \n" + input);
    }

}
