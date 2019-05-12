package lexer;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class handles error reporting for the Lexical Analyzer - It extends Exception.
 *
 * The error handler recognizes the following errors: invalid character, ill-formed constants,
 * ill-formed comments, and identifiers that exceed the maximum length. An error
 * message is returned when an error is encountered.
 *
 * @author jackiemun
 *
 */
public class LexicalError extends Exception
{
    /**
     * Default Constructor that simply reports that
     * a lexer error was encountered
     */
    public LexicalError()
    {
        System.out.println("\n - LEXER ERROR! - \n");
    }

    /**
     * Constructor that prints a more detailed lexer error message
     * @param message message to be printed
     * @param line the line where the error was encountered
     */
    public LexicalError(String message, int line)
    {
        System.out.println("\n - LEXER ERROR: " + message + " on line " + line + " - \n");
    }
}
