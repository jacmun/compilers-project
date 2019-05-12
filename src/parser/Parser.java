package parser;

import lexer.LexicalAnalyzer;
import lexer.Token;
import semanticaction.SemanticActions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class is the Parser component of the Compiler. It receives a stream of tokens
 * from the Lexical Analyzer and ensures that the stream of tokens follows the rules
 * of the language, using the RHS Table and the Parse Table (both read in from disk).
 *
 * The RHS Table contains a list of productions for all non-terminals in the Vascal
 * grammar. The Parse Table is a matrix indexed by a ParseStack symbol (non-terminals & terminals)
 * and the current symbol (terminals) - each cell contains an integer value, representing either
 * a production number in the RHS Table, an accept action, or an error.
 *
 * @author jackiemun
 *
 */
public class Parser
{
    // private class data members

    private LexicalAnalyzer Lexer;
    private SemanticActions SemanticAction;
    private String[] RhsTable;
    private Integer[][] ParseTable;
    private ArrayList<String> ParseTableRow;
    private ArrayList<String> ParseTableCol;
    private Stack<String> ParseStack;
    private Token InputSymbol;
    private Token PrevInputSymbol;
    private String InputSymbolTypeString;
    private String StackSymbol;
    private int Step = 1;

    /**
     * Constructor
     * Initializes rhs table, Parse table, and ParseStack
     * Calls the Parse method, while the ParseStack is not empty
     * @param lexer an instance of a Lexical Analyzer object
     * @param parseprint boolean that determines whether or not Parse ParseStack will be printed (== 1 to Print)
     * @throws Exception
     */
    public Parser(LexicalAnalyzer lexer, boolean parseprint, boolean tviprint) throws Exception
    {
        this.Lexer = lexer;
        InputSymbol = lexer.ReadNewToken();
        InputSymbolTypeString = InputSymbol.GetTypeAsString();

        // calls methods to initialize the rhs table, Parse table, and ParseStack

        InitRHStable();
        InitParseTable();
        SetUpStack();
        SetUpSemanticAction();

        while (!(ParseStack.empty()))
            Parse(parseprint);

        // Prints out the TVI code output

        SemanticAction.PrintTVICode(tviprint);
    }

    /**
     * Sets up the semantic action object and passes in the current input symbol
     */
    public void SetUpSemanticAction() throws Exception
    {
        SemanticAction = new SemanticActions();
    }

    /**
     * Sets up the symbol ParseStack
     * Initializes the ParseStack and pushes the last symbol (endoffile) and first symbol (goal)
     */
    public void SetUpStack()
    {
        ParseStack = new Stack<>();
        ParseStack.push("ENDOFFILE");
        ParseStack.push("<GOAL>");
    }

    /**
     * Reads in the Vascal Parse table and initializes ParseTable array
     */
    public void InitParseTable()
    {
        try
        {
            // Reads in Parse table from disk

            BufferedReader breader = new BufferedReader(new FileReader("parser/parsetable.txt"));
            String line = breader.readLine();
            String[] input = line.split(",");

            ParseTableCol = new ArrayList<>();
            ParseTableRow = new ArrayList<>();
            ParseTable = new Integer[37][39];
            ParseTableRow.add(input[0].toUpperCase());
            int index = 1;

            // sets up arraylist representing the Parse table columns (indexed by ParseStack symbols)

            for (int i = 0; i < input.length; i++)
                ParseTableCol.add(i, input[i].toUpperCase());

            // sets up arraylist representing the Parse table rows (indexed by input symbols)
            // and the array representing the Parse table cells

            while ((line = breader.readLine()) != null)
            {
                input = line.split(",");
                input[0] = input[0].toLowerCase();
                ParseTableRow.add(index, input[0].toUpperCase());
                for (int i = 1; i < 39; i++)
                    ParseTable[index][i] = Integer.parseInt(input[i]);
                index++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Reads in the Vascal grammar table and initializes RhsTable array
     */
    public void InitRHStable()
    {
        try
        {
            // reads in rhs table from disk

            BufferedReader breader = new BufferedReader(new FileReader("parser/TVI_Grammar_Augmented.txt"));
            String[] input;
            String line;
            int index = 1;
            RhsTable = new String[68];

            // Initializes array representing the rhs table

            while ((line = breader.readLine()) != null)
            {
                input = line.split("::=");

                if (input.length >= 2)
                    RhsTable[index] = input[1].toUpperCase();
                else
                    RhsTable[index] = "";

                index++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a given symbol is a nonterminal
     * @param symbol the symbol being checked
     * @return true if the given symbol is a nonterminal; otherwise, false
     */
    public boolean IsNonTerminal(String symbol)
    {
        // if the symbol is not an empty string

        if (symbol.length() > 0)
        {
            // returns true if symbol is in non-terminal format

            char firstchar = symbol.charAt(0);
            char lastchar = symbol.charAt(symbol.length() - 1);
            return (firstchar == '<' && lastchar == '>');
        }
        else
            return false;
    }

    /**
     * @param printstack boolean that determines if ParseStack contents will be printed
     * @throws Exception
     */
    public void Parse(boolean printstack) throws Exception
    {

        StackSymbol = ParseStack.peek().toUpperCase();

        // if the ParseStack symbol is a semantic action number, calls the correct semantic action

        if (StackSymbol.contains("#"))
            CallSemAction(printstack);

        // if the ParseStack symbol is terminal and matches the current input symbol

        else if (!IsNonTerminal(StackSymbol) && (InputSymbolTypeString.equals(StackSymbol)))
            SymbolMatch(printstack);

        // if the ParseStack symbol is a non-terminal

        else if (IsNonTerminal(StackSymbol))
            NonTermStackAction(printstack);

        // if the terminal on top of the ParseStack doesn't match the next input symbol, throws an error

        else if (!IsNonTerminal(StackSymbol) && !(InputSymbolTypeString.equals(StackSymbol)))
            throw new ParserError("Expected " + StackSymbol + " on line " + (InputSymbol.GetLineCount() - 1));
    }

    /**
     * Executes a semantic action
     * @param printstack boolean that determines if ParseStack contents will be printed
     * @throws Exception
     */
    public void CallSemAction(boolean printstack) throws Exception
    {
        int semactnum = Integer.parseInt(StackSymbol.substring(1));
        DumpStack(printstack, StackSymbol, InputSymbolTypeString, false, false,
                false, true, null, semactnum);
        SemanticAction.Execute(semactnum, PrevInputSymbol, printstack);
        ParseStack.pop();
    }

    /**
     * Pops the non-terminal on top of the ParseStack, calls DumpStack() for a symbol match,
     * and gets a new input symbol
     * @param printstack boolean that determines if ParseStack contents will be printed
     * @throws Exception
     */
    public void SymbolMatch(boolean printstack) throws Exception
    {
        DumpStack(printstack, StackSymbol, InputSymbolTypeString, true, false,
                false, false,null, -1);
        ParseStack.pop();

        if (StackSymbol.equals("ENDOFFILE") && printstack)
            System.out.println("\n! ACCEPT !");

        PrevInputSymbol = InputSymbol;
        InputSymbol = Lexer.ReadNewToken();
        InputSymbolTypeString = InputSymbol.GetTypeAsString();
    }

    /**
     * Called if the current ParseStack symbol is a non-terminal
     * Completes the appropriate action depending on the Parse table cell entry
     * @param printstack boolean that determines if ParseStack contents will be printed
     * @throws Exception
     */
    public void NonTermStackAction(boolean printstack) throws Exception
    {
        int rowindex = ParseTableRow.indexOf(InputSymbolTypeString);
        int colindex = ParseTableCol.indexOf(StackSymbol);

        // throws error if either the InputSymbol or the StackSymbol don't exist as indices on Parse table

        if (rowindex == -1 || colindex == -1)
            throw new ParserError();

        // throws error if parsing entry is empty/null

        else if (ParseTable[rowindex][colindex] == null)
            throw new ParserError(InputSymbolTypeString, (Lexer.GetLine() - 1));

        // gets appropriate Parse table cell entry

        int cell = ParseTable[rowindex][colindex];

        // if cell entry is epsilon, pop

        if (cell < 0)
            EpsilonEntry(printstack, cell);

        // if cell entry is 999, throws error

        else if (cell == 999)
            throw new ParserError(InputSymbolTypeString, Lexer.GetLine());

        // if cell entry is production number

        else if (cell>0 && cell< RhsTable.length)
        {
            // gets rhs of production and splits into individual symbols

            String rhs = RhsTable[cell];
            String[] temparray = rhs.split(" ");
            ArrayList<String> rhsarray = new ArrayList<String>(Arrays.asList(temparray));

            if (rhsarray.size() <= 0)
                EpsilonEntry(printstack, cell);

            else
                PushRHS(printstack, rhsarray, cell);
        }

        // else throw an error

        else
            throw new ParserError(InputSymbolTypeString, (InputSymbol.GetLineCount()));
    }

    /**
     * Replaces the non-terminal on top of the ParseStack with the RHS of the production
     * @param printstack boolean that determines if ParseStack contents will be printed
     * @param rhsarray ArrayList that contains the rhs being pushed if the Parse was a push
     * @param cell the current cell being looked at in the Parse table
     */
    public void PushRHS(boolean printstack, ArrayList<String> rhsarray, int cell)
    {
        // removes first entry b/c first entry is always whitespace

        rhsarray.remove(0);

        DumpStack(printstack, StackSymbol, InputSymbolTypeString, false, true,
                false, false, rhsarray, cell);

        // pops symbol on top of ParseStack

        ParseStack.pop();

        // pushes rhs symbols on ParseStack in reverse order

        for (int i = (rhsarray.size() - 1); i >= 0; i--)
            ParseStack.push(rhsarray.get(i));
    }

    /**
     * Pops the non-terminal on top of the ParseStack and calls DumpStack() for an epsilon entry,
     * @param printstack boolean that determines if ParseStack contents will be printed
     * @param cell the current cell being looked at in the Parse table
     */
    public void EpsilonEntry(boolean printstack, int cell)
    {
        DumpStack(printstack, StackSymbol, InputSymbolTypeString, false, false,
                true, false,null, cell);
        ParseStack.pop();
    }

    /**
     * Error recovery that bails out of the current construct and looks for a safe symbol to restart parsing
     * @throws Exception
     */
    public void panicMode() throws Exception
    {
        ArrayList<String> skippedStackSymbols = new ArrayList<>();

        // skips tokens until next semi-colon is found

        while (!InputSymbol.equals("SEMICOLON") && !(InputSymbol.equals("ENDOFFILE")))
        {
            PrevInputSymbol = InputSymbol;
            InputSymbol = Lexer.ReadNewToken();
            InputSymbolTypeString = InputSymbol.GetTypeAsString();
        }

        // if semicolon token is found

        if (InputSymbol.equals("SEMICOLON"))
        {
            // pop the Parse ParseStack until <statement_list_tail> is found

            while (!StackSymbol.equalsIgnoreCase("<statement_list_tail>") && !ParseStack.empty())
            {
                StackSymbol = ParseStack.pop().toUpperCase();
                skippedStackSymbols.add(StackSymbol);
            }

            // when <statement_list_tail> is on Parse ParseStack and semi-colon is encountered, push expected symbols

            if (StackSymbol.equalsIgnoreCase("<statement_list_tail>"))
            {
                ParseStack.push("SEMICOLON");
                ParseStack.push("<statement>");
                ParseStack.push("<statement_list_tail>");
            }
        }

        // if endoffile is hit, unrecoverable error

        if (InputSymbol.equals("ENDOFFILE"))
            throw new ParserError("Unrecoverable error");
    }

    /**
     * Prints the current contents of the Parse ParseStack and the appropriate action if printing is "on"
     * @param on boolean that determines if ParseStack will be printed
     * @param popped the current ParseStack symbol being popped
     * @param token the current input symbol
     * @param match boolean that determines if the Parse was a match
     * @param push boolean that determines if the Parse was a push
     * @param eps boolean that determines if the Parse was an epsilon (equals 1 if epsilon encountered)
     * @param rhsarray ArrayList that contains the rhs being pushed if the Parse was a push
     * @param cell the current cell being looked at in the Parse table
     */
    public void DumpStack(boolean on, String popped, String token, boolean match, boolean push, boolean eps,
                          boolean semact, List<String> rhsarray, int cell)
    {
        // if printing is set to on

        if (on)
        {
            System.out.println("\n>>- " + Step + " -<<");
            System.out.println("ParseStack ::==> " + ParseStack);

            // prints contents of ParseStack for a match

            if (match)
                System.out.println("Popped " + popped + " with token " + token + " -> * MATCH *  {consume tokens} ");

            // prints contents of ParseStack for a push

            else if (push)
                System.out.println("Popped " + popped + " with token " + token +
                        " -> $ PUSH $   [ " + cell + " ] " + popped + " ::= " + rhsarray.toString());

            // prints contents of ParseStack for epsilon

            else if (eps)
                System.out.println("Popped " + popped + " with token " + token +
                        " -> @ EPSILON @   [ " + cell + " ] <" + popped + "> ::= @ EPSILON @ ");

            // prints contents  of ParseStack for semantic action number

            else if (semact)
                System.out.println("Popped " + popped + " with token " + token +
                        " -> # SEMANTIC ACTION #   [ " + cell + " ] ");

            Step++;
        }
    }
}
