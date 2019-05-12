package symboltable;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class handles error reporting for Symbol Tables - it extends Exception.
 *
 * @author jackiemun
 *
 */
public class SymbolTableError extends Exception
{

    /**
     * Constructor that prints a symbol table error message with
     * the affected key (in the symbol table)
     * @param key symbol table key affected by the error
     */
    public SymbolTableError(String key)
    {
        System.out.println(" \n - SYMBOL TABLE ERROR - \n " +
                "The name " + key + " already exists in the symbol table. \n");
    }
}
