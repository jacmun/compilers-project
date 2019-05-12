package semanticaction;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class handles error reporting for the Semantic Actions - it extends Exception.
 *
 * @author jackiemun
 *
 */
public class SemanticActionsError extends Exception
{
    /**
     * Constructor that prints the given semantic action error message
     * @param message error message
     */
    public SemanticActionsError(String message)
    {
        System.out.println(message);
    }
}
