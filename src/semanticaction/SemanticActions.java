package semanticaction;

import lexer.Token;
import quadruples.Quadruples;
import symboltable.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class handles the Semantic Actions phase of the compiler.
 * All of the semantic actions executed (called by the parser) and
 * necessary helper methods are implemented here.
 *
 * @author jackiemun
 *
 */
public class SemanticActions
{

    /**
     * Enum that represents ETypes arithmetic & relational
     * They keep track of what kind of expression the semantic actions are
     * dealing with.
     */
    public enum EType
    {
        ARITHMETIC, RELATIONAL;
    }

    //private data members

    private boolean Insert = true;  //true when Insert mode, false when search mode
    private boolean Global = true;  //true if in Global environment, false if in local environment
    private boolean Array = false;  //true if next set of variables should be treated as an Array,  else false (simple)
    private int GlobalMemory = 0;   //the next free location in memory assigned for allocation to Global vars
    private int LocalMemory = 0;    //the next free location in memory assigned for allocation to local vars
    private int GlobalStore = 0;    //keeps tracks of an alloc statement
    private int LocalStore = 0;     //keeps track of alloc/free statements of functions & procedures
    private int TempVarID = 0;      //keeps track of how many temporary variables have been created
    private int NextParam;          //keeps track of the current index into the list on top of ParamStack
    private Quadruples Quadruples;               //keeps track of the Quadruples
    private Stack<Object> SemanticStack;         //semantic actions stack
    private SymbolTable GlobalTable;             //Global symbol table
    private SymbolTable LocalTable;              //local symbol table
    private SymbolTable ConstantTable;           //constant symbol table
    private SymbolTableEntry CurrentFunction;    //keeps track of the current function or procedure
    private Stack<Integer> ParamCount;           //parameter count stack
    private Stack<List<SymbolTableEntry>> ParamStack;   //parameter info stack

    /**
     * Constructor
     * Initializes the semantic stack, the Global symbol table, and the local symbol table
     */
    public SemanticActions() throws Exception
    {
        SemanticStack = new Stack<>();
        Quadruples = new Quadruples();
        GlobalTable = new SymbolTable(11);
        LocalTable = new SymbolTable(11);
        ConstantTable = new SymbolTable(11);
        ParamCount = new Stack<>();
        ParamStack = new Stack<>();

        // inserts main, read, and write into the Global symbol table as procedures with 0 parameters
        // and marks them as reserved

        ProcedureEntry p1 = new ProcedureEntry("MAIN", 0, null);
        p1.SetReserved(true);
        ProcedureEntry p2 = new ProcedureEntry("READ", 0, null);
        p2.SetReserved(true);
        ProcedureEntry p3 = new ProcedureEntry("WRITE", 0, null);
        p3.SetReserved(true);

        GlobalTable.Insert(p1.GetName(), p1);
        GlobalTable.Insert(p2.GetName(), p2);
        GlobalTable.Insert(p3.GetName(), p3);
    }

    /**
     * Executes a semantic action, using the given semantic action number and parser
     * If the semantic action number does not exit, informs that the actions has not yet been implemented
     * @param semactnumber the semantic action number to be executed
     * @param token the current token passed to the semantic action by the parser
     * @param print boolean that determines if debug mode on
     * @throws Exception
     */
    public void Execute(int semactnumber, Token token, boolean print) throws Exception
    {
        switch(semactnumber)
        {
            case 1:
                CaseOne();
                break;
            case 2:
                CaseTwo();
                break;
            case 3:
                CaseThree();
                break;
            case 4:
                CaseFour(token);
                break;
            case 5:
                CaseFive();
                break;
            case 6:
                CaseSix();
                break;
            case 7:
                CaseSeven(token);
                break;
            case 9:
                CaseNine();
                break;
            case 11:
                CaseEleven();
                break;
            case 13:
                CaseThirteen(token);
                break;
            case 15:
                CaseFifteen(token);
                break;
            case 16:
                CaseSixteen();
                break;
            case 17:
                CaseSeventeen(token);
                break;
            case 19:
                CaseNineteen();
                break;
            case 20:
                CaseTwenty();
                break;
            case 21:
                CaseTwentyOne();
                break;
            case 22:
                CaseTwentyTwo(token);
                break;
            case 24:
                CaseTwentyFour();
                break;
            case 25:
                CaseTwentyFive(token);
                break;
            case 26:
                CaseTwentySix();
                break;
            case 27:
                CaseTwentySeven();
                break;
            case 28:
                CaseTwentyEight();
                break;
            case 29:
                CaseTwentyNine();
                break;
            case 30:
                CaseThirty(token);
                break;
            case 31:
                CaseThirtyOne(token);
                break;
            case 32:
                CaseThirtyTwo(token);
                break;
            case 33:
                CaseThirtyThree(token);
                break;
            case 34:
                CaseThirtyFour(token, print);
                break;
            case 35:
                CaseThirtyFive();
                break;
            case 36:
                CaseThirtySix(token);
                break;
            case 37:
                CaseThirtySeven(token);
                break;
            case 38:
                CaseThirtyEight(token);
                break;
            case 39:
                CaseThirtyNine(token);
                break;
            case 40:
                CaseForty(token);
                break;
            case 41:
                CaseFortyOne(token);
                break;
            case 42:
                CaseFortyTwo(token);
                break;
            case 43:
                CaseFortyThree();
                break;
            case 44:
                CaseFortyFour(token);
                break;
            case 45:
                CaseFortyFive(token);
                break;
            case 46:
                CaseFortySix(token);
                break;
            case 47:
                CaseFortySeven(token);
                break;
            case 48:
                CaseFortyEight(token, print);
                break;
            case 49:
                CaseFortyNine(token);
                break;
            case 50:
                CaseFifty(token);
                break;
            case 51:
                CaseFiftyOne(token, print);
                break;
            case 510:
                CaseFiftyOneRead();
                break;
            case 511:
                CaseFiftyOneWrite();
                break;
            case 52:
                CaseFiftyTwo(token);
                break;
            case 53:
                CaseFiftyThree(token);
                break;
            case 54:
                CaseFiftyFour(token);
                break;
            case 55:
                CaseFiftyFive();
                break;
            case 56:
                CaseFiftySix();
                break;
            default:
                DefaultCase(semactnumber, print);
        }
    }

    /**
     * Default case that prints message saying the semantic action has not yet been implemented
     * @param semactnumber the semantic action number
     * @param print boolean that determines if debug mode on and message should be printed
     */
    public void DefaultCase(int semactnumber, boolean print)
    {
        if (print)
            System.out.println("Action #" + semactnumber + " not yet implemented!");
    }

    /**
     * Sets the boolean variable Insert to true to show we are in Insert mode
     */
    public void CaseOne()
    {
        Insert = true;
    }

    /**
     * Set the boolean variable Insert to false to show we are in search mode
     */
    public void CaseTwo()
    {
        Insert = false;
    }

    /**
     * Pops the appropriate tokens and creates either a new Array entry or a new variable entry
     * Then inserts them in the correct table (either the Global symbol table or the local symbol table)
     * @throws Exception
     */
    public void CaseThree() throws Exception
    {
        Token.Type tokentype = ((Token) SemanticStack.pop()).GetType();

        // for ArrayEntry

        if (Array)
        {
            // gets array upper bound, lower bound, and memory size

            int upperbound = Integer.parseInt(((Token) SemanticStack.pop()).GetValue());
            int lowerbound = Integer.parseInt(((Token) SemanticStack.pop()).GetValue());
            int memorysize = (upperbound - lowerbound) + 1;

            while ((!SemanticStack.empty()) && (((Token) SemanticStack.peek()).GetType()) == Token.Type.IDENTIFIER)
            {
                Token tok = (Token) SemanticStack.pop();
                ArrayEntry id = new ArrayEntry(tok.GetValue());
                id.SetType(tokentype);
                id.SetUpperBound(upperbound);
                id.SetLowerBound(lowerbound);
                id.SetArray(true);

                if (Global)
                {
                    id.SetAddress(GlobalMemory);
                    GlobalTable.Insert(tok.GetValue(), id);
                    GlobalMemory += memorysize;
                }
                else
                {
                    id.SetAddress(LocalMemory);
                    LocalTable.Insert(tok.GetValue(), id);
                    LocalMemory += memorysize;
                }
            }
        }

        // for VariableEntry

        else
        {
            while (!(SemanticStack.empty()) && (SemanticStack.peek() instanceof Token) &&
                  (((Token) SemanticStack.peek()).GetType()) == Token.Type.IDENTIFIER)
            {
                Token tok = (Token) SemanticStack.pop();

                VariableEntry id = new VariableEntry(tok.GetValue());
                id.SetType(tokentype);
                id.SetVariable(true);

                if (Global)
                {
                    id.SetAddress(GlobalMemory);
                    GlobalTable.Insert(tok.GetValue(), id);
                    GlobalMemory++;
                }
                else
                {
                    id.SetAddress(LocalMemory);
                    LocalTable.Insert(tok.GetValue(), id);
                    LocalMemory++;
                }
            }
        }
        Array = false;
    }

    /**
     * Pushes the token onto the semantic action stack to be used in action 3
     * @param token the token to be pushed - should be a type
     */
    public void CaseFour(Token token)
    {
        SemanticStack.push(token);
    }

    /**
     * Generates code for start of function
     * @throws Exception
     */
    public void CaseFive() throws Exception
    {
        //initialize for the beginning of a new function

        Insert = false;
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();

        //generates PROCBEGIN line to indicate beginning of function

        Generate("PROCBEGIN", id.GetName());
        LocalStore = Quadruples.GetNextQuad();

        //generates line for local memory

        Generate("alloc", "_");
    }

    /**
     * Sets the boolean variable Array to false to show that
     * the next set of variables is not an Array
     */
    public void CaseSix()
    {
        Array = true;
    }

    /**
     * Pushes on the token that will be the upper and lower bounds of arrays,
     * used during action 3
     * @param token the token to be pushed - should be an integer identifier
     */
    public void CaseSeven(Token token)
    {
        SemanticStack.push(token);
    }

    /**
     * Pops tokens off the semantic stack, uses them to Create IODevice entries
     * & a Procedure entry, and then inserts them in the Global symbol table
     * @throws Exception
     */
    public void CaseNine() throws Exception
    {

        Token id1 = (Token) SemanticStack.pop();
        Token id2 = (Token) SemanticStack.pop();
        Token id3 = (Token) SemanticStack.pop();

        // creates IODevice entries

        IODeviceEntry entry1 = new IODeviceEntry(id1.GetValue());
        entry1.SetReserved(true);
        IODeviceEntry entry2 = new IODeviceEntry(id2.GetValue());
        entry2.SetReserved(true);

        // creates Procedure entries

        ProcedureEntry entry3 = new ProcedureEntry(id3.GetValue(), 0, null);
        entry3.SetReserved(true);
        entry3.SetProcedure(true);

        // inserts entries in global table

        GlobalTable.Insert(id1.GetValue(), entry1);
        GlobalTable.Insert(id2.GetValue(), entry2);
        GlobalTable.Insert(id3.GetValue(), entry3);

        Generate("call", "main", "0");  // "call main 0"
        Generate("exit");             // "exit"
    }

    /**
     * Generates code for end of function
     * @throws Exception
     */
    public void CaseEleven() throws Exception
    {
        Global = true;

        // delete the current local symbol table

        LocalTable = new SymbolTable();
        CurrentFunction = null;

        // Backpatch amount of allocated local memory

        Backpatch(LocalStore, LocalMemory);

        // generates FREE comment and PROCEND command

        Generate("free", LocalMemory);
        Generate("PROCEND");
    }

    /**
     * Pushes the token onto the semantic stack
     * @param token the token to be pushed - should be an identifier
     */
    public void CaseThirteen(Token token)
    {
        SemanticStack.push(token);
    }

    /**
     * Stores the result of function
     * @param token token from parser
     * @throws Exception
     */
    public void CaseFifteen(Token token) throws Exception
    {
        // creates a variable to store the result of the function

        VariableEntry result = Create(token.GetValue() + "_RESULT", Token.Type.INTEGER);

        // set the result tag of the variable entry class

        result.SetResult(true);

        // Create a new function entry with name from the token & the newly created result variable

        SymbolTableEntry id = new FunctionEntry(token.GetValue(), 0, null, result);
        GlobalTable.Insert(id.GetName(), id);

        // switches to local memory

        Global = false;
        LocalMemory = 0;
        CurrentFunction = id;
        SemanticStack.push(id);
    }

    /**
     * Set type of function and its result
     */
    public void CaseSixteen()
    {
        // get TYPE & FunctionEntry id from stack

        Token type = (Token) SemanticStack.pop();
        FunctionEntry id = (FunctionEntry) SemanticStack.peek();
        id.SetType(type.GetType());

        // set the type of the result variable of id

        id.SetResultType(type.GetType());
        CurrentFunction = id;
    }

    /**
     * Creates a procedure in the symbol table with the name of the token
     * @param token token passed in from the parser
     * @throws Exception
     */
    public void CaseSeventeen(Token token) throws Exception
    {
        // Create a ProcedureEntry & Insert into the Global table

        SymbolTableEntry id = new ProcedureEntry(token.GetValue());
        GlobalTable.Insert(id.GetName(), id);
        Global = false;
        LocalMemory = 0;
        CurrentFunction = id;
        SemanticStack.push(id);
    }

    /**
     * Initializes the count of formal parameters that will be used to keep track
     * of the number of formal parameters for functions called
     */
    public void CaseNineteen()
    {
        // creates a stack and pushes 0

        ParamCount = new Stack<>();
        ParamCount.push(0);
    }

    /**
     * Gets the number of parameters
     */
    public void CaseTwenty()
    {
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();

        // pop number of parameters

        int numparams = ParamCount.pop();

        // id should be a function entry or a procedure entry - if so, sets ID's parameter number to number popped

        if (id.getClass() == FunctionEntry.class)
            ((FunctionEntry) id).SetNumberOfParameters(numparams);
        else if (id.getClass() == ProcedureEntry.class)
            ((ProcedureEntry) id).SetNumberOfParameters(numparams);
    }

    /**
     * Creates temporary variables to store parameter info
     * @throws Exception
     */
    public void CaseTwentyOne() throws Exception
    {
        Token type = (Token) SemanticStack.pop();

        int upperbound = -1;
        int lowerbound = -1;
        if (Array)
        {
            upperbound =  Integer.parseInt(((Token) SemanticStack.pop()).GetValue());
            lowerbound =  Integer.parseInt(((Token) SemanticStack.pop()).GetValue());
        }

        // temporary stack to reverse the order of the parameters

        Stack<Token> parameters = new Stack<>();

        while ((SemanticStack.peek() instanceof Token) &&
               ((Token) SemanticStack.peek()).GetType() == Token.Type.IDENTIFIER)
            parameters.push((Token) SemanticStack.pop());

        // For each parameter, creates an entry in the local symbol table

        while (!parameters.empty())
        {
            Token param = parameters.pop();
            SymbolTableEntry var;

            if (Array)
                var = new ArrayEntry(param.GetValue(), LocalMemory, type.GetType(), upperbound, lowerbound);
            else
                var = new VariableEntry(param.GetValue(), LocalMemory, type.GetType());

            var.SetParameter(true);
            LocalTable.Insert(var.GetName(), var);

            // adds a parameter to the STE CurrentFunction

            CurrentFunction.AddParameter(var);

            // increments LocalMemory and updates paramcount

            LocalMemory++;
            ParamCount.push(ParamCount.pop() + 1);
        }
        Array = false;
    }

    /**
     * Updates branch destinatin for IF to #t to next quad
     * @throws Exception
     */
    public void CaseTwentyTwo(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // throws type mismatch error if statement type is not relational

        if (etype != EType.RELATIONAL)
            throw new SemanticActionsError("\n ~ TYPE MISMATCH ERROR ON LINE " + token.GetLineCount()
                    + ": Invalid use of " + etype + " operator ~");

        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        Backpatch(etrue, Quadruples.GetNextQuad());
        SemanticStack.push(etrue);
        SemanticStack.push(efalse);
    }

    /**
     * Store line number of beginning of loop
     * This will be used later for backpatching
     */
    public void CaseTwentyFour()
    {
        int beginloop = Quadruples.GetNextQuad();
        SemanticStack.push(beginloop);
    }

    /**
     * Initialization for a WHILE loop
     * @throws Exception
     */
    public void CaseTwentyFive(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // Throws type mismatch error if terminating condition is not relational
        if (etype != EType.RELATIONAL)
            throw new SemanticActionsError("\n ~ TYPE MISMATCH ERROR ON LINE " + token.GetLineCount()
                    + ": Invalid use of " + etype + " operator ~");

        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        Backpatch(etrue, Quadruples.GetNextQuad());
        SemanticStack.push(etrue);
        SemanticStack.push(efalse);
    }

    /**
     * Write codes at end of WHILE loop
     * @throws Exception
     */
    public void CaseTwentySix() throws Exception
    {
        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();

        // Pops the first line of code in while loop (pushed on in action 24)

        int beginloop = (int) SemanticStack.pop();
        Generate("goto", beginloop);
        Backpatch(efalse, Quadruples.GetNextQuad());
    }

    /**
     * Sets up ELSE case
     * @throws Exception
     */
    public void CaseTwentySeven() throws Exception
    {
        // gets the first line of the else code

        List<Integer> skipelse = MakeList(Quadruples.GetNextQuad());

        // this will be backpatched later at the end of the else code

        Generate("goto", "_");
        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        Backpatch(efalse, Quadruples.GetNextQuad());
        SemanticStack.push(skipelse);
        SemanticStack.push(etrue);
        SemanticStack.push(efalse);
    }

    /**
     * Handles the end of ELSE statement
     */
    public void CaseTwentyEight()
    {
        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();

        // skipelse is pushed onto the stack in action 27

        List<Integer> skipelse = (List<Integer>) SemanticStack.pop();
        Backpatch(skipelse, Quadruples.GetNextQuad());
    }

    /**
     * Handles the end of IF without ELSE
     */
    public void CaseTwentyNine()
    {
        // just Backpatch the false case to the next line

        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        Backpatch(efalse, Quadruples.GetNextQuad());
    }

    /**
     * Checks to see if a variable has been declared
     * @param token the current token
     */
    public void CaseThirty(Token token) throws Exception
    {
        SymbolTableEntry id = LookUpID(token);

        if (id == null)
            throw new SemanticActionsError("\n ~ UNDECLARED VARIABLE ERROR ON LINE " + token.GetLineCount()
                    + ": variable " + token.GetValue() + " is undeclared ~ ");

        SemanticStack.push(id);
        SemanticStack.push(EType.ARITHMETIC);
    }

    /**
     * Variable assignment:
     * Puts the value of variable ID2 in variable ID1
     * @throws Exception
     */
    public void CaseThirtyOne(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // throws type mismatch error if ETYPE is not ARITHMETIC

        if (etype != EType.ARITHMETIC)
            throw new SemanticActionsError("\n ~ TYPE MISMATCH ERROR ON LINE " + token.GetLineCount() +
                    ": Invalid use of " + etype + " operator ~");

        SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
        SymbolTableEntry offset = (SymbolTableEntry) SemanticStack.pop();
        SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();

        // type-checking

        if (TypeCheck(id1, id2) == 3)
            throw new SemanticActionsError("\n ~ TYPE MISMATCH ERROR ON LINE " + token.GetLineCount() +
                    ": Cannot assign real value to integer variable ~");

        if (TypeCheck(id1, id2) == 2)
        {
            VariableEntry temp = Create(GetTempVar(), Token.Type.REAL);
            Generate("1tof", id2, temp);

            if (offset == null)
                Generate("move", temp, id1);
            else
                Generate("stor", temp, offset, id1);
        }
        else
        {
            if (offset == null)
                Generate("move", id2, id1);
            else
                Generate("stor", id2, offset, id1);
        }
    }

    /**
     * Ensures that the top of the stack is an Array & typechecks
     * @throws Exception
     */
    public void CaseThirtyTwo(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();

        // throws type mismatch error if current ETYPE is not arithmetic

        if (etype != EType.ARITHMETIC)
            throw new SemanticActionsError("\n ~ TYPE MISMATCH ERROR ON LINE " + token.GetLineCount() +
                    ": Invalid use of " + etype + " operator ~");

        // throws error if current ID on stack is not an Array

        if (!id.IsArray())
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount() + ": NOT AN ARRAY ~");
    }

    /**
     * Calculates the memory offset for an Array element
     * @throws Exception
     */
    public void CaseThirtyThree(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // if ETYPE is not arithmetic, throws type mismatch error

        if (etype != EType.ARITHMETIC)
            throw new SemanticActionsError("\n ~ TYPE MISMATCH ERROR ON LINE " + token.GetLineCount() +
                    ": Invalid use of " + etype + " operator ~");

        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();

        // if next value on the stack is not an integer, throw type mismatch error

        if ((id.GetType() != Token.Type.INTEGER) && (id.GetType() != Token.Type.INTCONSTANT))
            throw new SemanticActionsError("\n ~ TYPE MISMATCH ERROR ON LINE " + token.GetLineCount()
                    + ": Expected an integer, received a " + id.GetType() + " ~");

        ArrayEntry array = (ArrayEntry) SemanticStack.peek();

        // Create 2 temporary variables - 1 to hold the Array lower bound & 2 to hold memory offset

        VariableEntry temp1 = Create(GetTempVar(), Token.Type.INTEGER);
        VariableEntry temp2 = Create(GetTempVar(), Token.Type.INTEGER);
        Generate("move", array.GetLowerBound(), temp1);
        Generate("sub", id, temp1, temp2);
        SemanticStack.push(temp2);
    }

    /**
     * Function or procedure check
     * @param token current token
     * @param print boolean that determines if output is printed
     * @throws Exception
     */
    public void CaseThirtyFour(Token token, boolean print) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();

        // if next ID is a function, got to action 52

        if (id.IsFunction())
        {
            SemanticStack.push(etype);
            Execute(52, token, print);
        }
        // otherwise, push NULL on the stack (will be expanded in semantic IV)

        else
            SemanticStack.push(null);
    }

    /**
     * Set up to call a procedure
     * Procedures have no parameters and return nothing
     */
    public void CaseThirtyFive()
    {
        EType etype = (EType) SemanticStack.pop();

        // id is a procedure entry

        ProcedureEntry id = (ProcedureEntry) SemanticStack.peek();
        SemanticStack.push(etype);

        // push values to PARAMCOUNT and PARAMSTACK

        ParamCount.push(0);
        ParamStack.push(id.GetParameterInfo());
    }

    /**
     * Generate code to call a procedure
     * @throws Exception
     */
    public void CaseThirtySix(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();
        ProcedureEntry id = (ProcedureEntry) SemanticStack.pop();

        // Throws an error if there are 0 parameters

        if (id.GetNumberOfParameters() != 0)
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                    + ": Wrong number of parameters ~ ");

        // Generate a "CALL" to the ID

        Generate("call", id.GetName(), 0);
    }

    /**
     * Consume actual parameters in a list of parameters
     * @throws Exception
     */
    public void CaseThirtySeven(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // throws type mismatch error if etype is not arithmetic

        if (etype != EType.ARITHMETIC)
            throw new SemanticActionsError("\n ~ TYPE MISMATCH ERROR ON LINE " + token.GetLineCount() +
                    ": Invalid use of " + etype + " operator ~");

        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();

        // throws bad parameter error if id is not

        if (!(id.IsVariable()) && !(id.IsConstant()) &&
            !(id.IsFunction()) && !(id.IsArray()))
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount() + ": Bad parameter type ~");

        ParamCount.push(ParamCount.pop() + 1);

        // gets parameters

        Stack parameters = new Stack();
        while ( !(SemanticStack.peek() instanceof ProcedureEntry) &&
                !(SemanticStack.peek() instanceof FunctionEntry))
            parameters.push(SemanticStack.pop());

        SymbolTableEntry funcid = (SymbolTableEntry) SemanticStack.peek();

        // iterates over list of actual parameters supplied to a function input

        while (!parameters.empty())
            SemanticStack.push(parameters.pop());

        // checks to make sure type & number of parameters match, except if READ or WRITE

        if (!(funcid.GetName().equals("READ")) && !(funcid.GetName().equals("WRITE")))
        {
            if (!(parameters.empty()) && (int)parameters.peek() > funcid.GetNumberOFParameters())
                throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                        + ": Wrong number of parameters ~");

            SymbolTableEntry param = ParamStack.peek().get(NextParam);

            if (id.GetType() != param.GetType() && TypeCheck(id, param) != 0 && TypeCheck(id, param) != 1)
                throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                        + ": Bad parameter type ~");

            if (param.IsArray())
                if ((((ArrayEntry) id).GetLowerBound() != ((ArrayEntry) param).GetLowerBound()) ||
                        ((ArrayEntry) id).GetUpperBound() != ((ArrayEntry) param).GetUpperBound())
                    throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                            + ": Bad parameter type ~");

            NextParam++;
        }
    }

    /**
     * Ensures type is an arithmetic operation & pushes
     * @param token current token
     * @throws Exception
     */
    public void CaseThirtyEight(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // throws type mismatch error if we are not in an arithmetic operation

        if (etype != EType.ARITHMETIC)
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                    + ": Invalid use of " + etype + " operator ~");

        // token should be an operator

        SemanticStack.push(token);
    }

    /**
     * Change type to relational and add ETrue and EFalse variables as required
     * @throws Exception
     */
    public void CaseThirtyNine(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // throws type mismatch error if we're not in an arithmetic operation

        if (etype != EType.ARITHMETIC)
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                    + ": Invalid use of " + etype + " operator ~");

        SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
        Token operator = (Token) SemanticStack.pop();

        // the operator must be replaced with the proper TVI code
        // jump if the condition is me
        // ex: the token representing "<" should be replaced with "blt"

        String opcode = operator.GetOpCode();
        SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();

        // typecheck!

        if (TypeCheck(id1, id2) == 2)
        {
            VariableEntry temp = Create(GetTempVar(), Token.Type.REAL);
            Generate("1tof", id2, temp);
            Generate(opcode, id1, temp, "_");
        }
        else if (TypeCheck(id1, id2) == 3)
        {
            VariableEntry temp = Create(GetTempVar(), Token.Type.REAL);
            Generate("1tof", id1, temp);
            Generate(opcode, temp, id2, "_");
        }
        else
            Generate(opcode, id1, id2, "_");

        Generate("goto", "_");
        List<Integer> etrue = MakeList(Quadruples.GetNextQuad() - 2);
        List<Integer> efalse = MakeList(Quadruples.GetNextQuad() - 1);
        SemanticStack.push(etrue);
        SemanticStack.push(efalse);
        SemanticStack.push(EType.RELATIONAL);
    }

    /**
     * Pushes a unary plus/minus token onto the semantic stack
     * @param token the token to be pushed
     */
    public void CaseForty(Token token)
    {
        SemanticStack.push(token);
    }

    /**
     * Applies a unary plus/minus sign
     * @throws Exception
     */
    public void CaseFortyOne(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // throws type mismatch error if we are not in an arithmetic operation

        if (etype != EType.ARITHMETIC)
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                    + ": Invalid use of " + etype + " operator ~");

        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
        Token sign = (Token) SemanticStack.pop();

        // Handles if token type is unaryminus

        if (sign.GetType() == Token.Type.UNARYMINUS)
        {
            VariableEntry temp = Create(GetTempVar(), id.GetType());

            if (id.GetType() == Token.Type.INTEGER || id.GetType() == Token.Type.INTCONSTANT)
                Generate("uminus", id, temp);
            else
                Generate("fuminus", id, temp);

            SemanticStack.push(temp);
        }
        else
            SemanticStack.push(id);

        SemanticStack.push(EType.ARITHMETIC);
    }

    /**
     * Pushes an ADDOP operator (+, -, etc.) token onto the semantic stack
     * @param token the token to be pushed
     */
    public void CaseFortyTwo(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();
        String opcode = token.GetOpCode();

        // throws type mismatch error if the current operation is OR and etype is not RELATIONAL

        if (opcode.equals("or"))
        {
            if (etype != EType.RELATIONAL)
                throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                        + ": Invalid use of " + etype + " operator ~");

            // the top of the stack should be a list of integers

            List<Integer> efalse = (List<Integer>) SemanticStack.peek();
            Backpatch(efalse, Quadruples.GetNextQuad());
        }
        else
        {
            // throws type mismatch error if type is not ARITHMETIC

            if (etype != EType.ARITHMETIC)
                throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                        + ": Invalid use of " + etype + " operator ~");
        }

        // the token should be an operator

        SemanticStack.push(token);
    }

    /**
     * Performs an ADDOP based on the OP pooped from the semantic stack
     * @throws Exception
     */
    public void CaseFortyThree() throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // if the  etype is RELATIONAL

        if (etype == EType.RELATIONAL)
        {
            List<Integer> e2false = (List<Integer>) SemanticStack.pop();
            List<Integer> e2true = (List<Integer>) SemanticStack.pop();

            Token operator = (Token) SemanticStack.pop();
            List<Integer> e1false = (List<Integer>) SemanticStack.pop();
            List<Integer> e1true = (List<Integer>) SemanticStack.pop();
            List<Integer> etrue = Merge(e1true, e2true);

            List<Integer> efalse = e2false;
            SemanticStack.push(etrue);
            SemanticStack.push(efalse);
            SemanticStack.push(EType.RELATIONAL);
        }

        // else if etype is ARITHMETIC

        else
        {
            SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
            Token operator = (Token) SemanticStack.pop();

            // Gets the operator token's opcode

            String opcode = operator.GetOpCode();
            SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();

            if (TypeCheck(id1, id2) == 0)
            {
                // creates a temporary variable of type integer

                VariableEntry temp = Create(GetTempVar(), Token.Type.INTEGER);
                Generate(opcode, id1, id2, temp);
                SemanticStack.push(temp);
            }
            else if (TypeCheck(id1, id2) == 1)
            {
                // creates a temporary variable of type real

                VariableEntry temp = Create(GetTempVar(), Token.Type.REAL);
                Generate("f" + opcode, id1, id2, temp);
                SemanticStack.push(temp);
            }
            else if (TypeCheck(id1, id2) == 2)
            {
                // creates temporary variables of type real

                VariableEntry temp1 = Create(GetTempVar(), Token.Type.REAL);
                VariableEntry temp2 = Create(GetTempVar(), Token.Type.REAL);
                Generate("1tof", id2, temp1);
                Generate("f" + opcode, id1, temp1, temp2);
                SemanticStack.push(temp2);
            }
            else if (TypeCheck(id1, id2) == 3)
            {
                // creates temporary variable of type real

                VariableEntry temp1 = Create(GetTempVar(), Token.Type.REAL);
                VariableEntry temp2 = Create(GetTempVar(), Token.Type.REAL);
                Generate("ltof", id1, temp1);
                Generate("f" + opcode, temp1, id2, temp2);
                SemanticStack.push(temp2);
            }
            SemanticStack.push(EType.ARITHMETIC);
        }
    }

    /**
     * Pushes MULOP operator (*, /, etc.) onto the semantic stack
     * (Currently identical to action 42 but will be changed later)
     * @param token the token to be pushed
     */
    public void CaseFortyFour(Token token)
    {
        EType etype = (EType) SemanticStack.pop();

        // if type is RELATIONAL

        if (etype == EType.RELATIONAL)
        {

            List<Integer> efalse = (List<Integer>) SemanticStack.pop();
            List<Integer> etrue = (List<Integer>) SemanticStack.pop();

            // Backpatch if operation is AND

            if (token.GetOpCode().equals("and"))
                Backpatch(etrue, Quadruples.GetNextQuad());

            SemanticStack.push(etrue);
            SemanticStack.push(efalse);
        }
        SemanticStack.push(token);
    }

    /**
     * Performs MULOP based on the OP popped from the semantic stack
     * @throws Exception
     */
    public void CaseFortyFive(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // if type is RELATIONAL

        if (etype == EType.RELATIONAL)
        {
            List<Integer> e2false = (List<Integer>) SemanticStack.pop();
            List<Integer> e2true = (List<Integer>) SemanticStack.pop();
            Token operator = (Token) SemanticStack.pop();
            String opcode = operator.GetOpCode();

            // if operation is AND

            if (opcode.equals("and"))
            {
                List<Integer> e1false = (List<Integer>) SemanticStack.pop();
                List<Integer> e1true = (List<Integer>) SemanticStack.pop();

                List<Integer> etrue = e2true;
                List<Integer> efalse = Merge(e1false, e2false);
                SemanticStack.push(etrue);
                SemanticStack.push(efalse);
                SemanticStack.push(EType.RELATIONAL);
            }
        }

        // else if etype is ARITHMETIC

        else
        {
            SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
            Token operator = (Token) SemanticStack.pop();
            String opcode = operator.GetOpCode();
            SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();

            // mod & div require integer operands

            if (TypeCheck(id1, id2) != 0 && (opcode.equals("mod") || opcode.equals("div")))
                throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount() + ": operands of the "
                        + opcode + " operator must both be of type integer ~");

            // typecheck!

            if (TypeCheck(id1, id2) == 0)
                if (opcode.equals("mod")) {
                    VariableEntry temp1 = Create(GetTempVar(), Token.Type.INTEGER);
                    VariableEntry temp2 = Create(GetTempVar(), Token.Type.INTEGER);
                    VariableEntry temp3 = Create(GetTempVar(), Token.Type.INTEGER);
                    Generate("div", id1, id2, temp1);
                    Generate("mul", id2, temp1, temp2);
                    Generate("sub", id1, temp2, temp3);
                    SemanticStack.push(temp3);
                }
                else if (opcode.equals("/"))
                {
                    VariableEntry temp1 = Create(GetTempVar(), Token.Type.REAL);
                    VariableEntry temp2 = Create(GetTempVar(), Token.Type.REAL);
                    VariableEntry temp3 = Create(GetTempVar(), Token.Type.REAL);
                    Generate("1tof", id1, temp1);
                    Generate("1tof", id2, temp2);
                    Generate("fdiv", temp1, temp2, temp3);
                    SemanticStack.push(temp3);
                }
                else
                {
                    VariableEntry temp = Create(GetTempVar(), Token.Type.INTEGER);
                    Generate(opcode, id1, id2, temp);
                    SemanticStack.push(temp);
                }
            else if (TypeCheck(id1, id2) == 1)
            {
                VariableEntry temp = Create(GetTempVar(), Token.Type.REAL);

                if (opcode.equals("/"))
                    opcode = "div";

                Generate("f" + opcode, id1, id2, temp);
                SemanticStack.push(temp);
            }
            else if (TypeCheck(id1, id2) == 2)
            {
                VariableEntry temp1 = Create(GetTempVar(), Token.Type.REAL);
                VariableEntry temp2 = Create(GetTempVar(), Token.Type.REAL);
                Generate("1tof", id2, temp1);

                if (opcode.equals("/"))
                    opcode = "div";

                Generate("f" + opcode, id1, temp1, temp2);
                SemanticStack.push(temp2);
            }
            else if (TypeCheck(id1, id2) == 3)
            {
                VariableEntry temp1 = Create(GetTempVar(), Token.Type.REAL);
                VariableEntry temp2 = Create(GetTempVar(), Token.Type.REAL);
                Generate("1tof", id1, temp1);

                if (opcode.equals("/"))
                    opcode = "div";

                Generate("f" + opcode, temp1, id2, temp2);
                SemanticStack.push(temp2);
            }
            SemanticStack.push(EType.ARITHMETIC);
        }
    }

    /**
     * Looks up the value of a variable or constant entry from SymbolTable
     * @param token the current token
     * @throws Exception
     */
    public void CaseFortySix(Token token) throws Exception
    {
        if (token.GetType() == Token.Type.IDENTIFIER)
        {
            // looks for the token in the Global or local symbol table

            SymbolTableEntry id = LookUpID(token);

            // if token is not found

            if (id == null)
                throw new SemanticActionsError("\n ~ UNDECLARED VARIABLE ERROR ON LINE " + token.GetLineCount()
                        + ": variable " + token.GetValue() + " is undeclared ~");

            SemanticStack.push(id);
        }
        else if (token.GetType() == Token.Type.INTCONSTANT || token.GetType() == Token.Type.REALCONSTANT)
        {
            // looks for the token in the constant symbol table

            SymbolTableEntry id = LookUpConstant(token);

            // if token is not found

            if (id == null)
            {
                // id = constant entry of type integer and value from token;

                if (token.GetType() == Token.Type.INTCONSTANT)
                    id = new ConstantEntry(token.GetValue(), Token.Type.INTCONSTANT, token.GetValue());

                // id = constant entry of type real and value from token;

                else if (token.GetType() == Token.Type.REALCONSTANT)
                    id = new ConstantEntry(token.GetValue(), Token.Type.REALCONSTANT, token.GetValue());

                ConstantTable.Insert(id.GetName(), id);
            }
            SemanticStack.push(id);
        }
        SemanticStack.push(EType.ARITHMETIC);
    }

    /**
     * Handles reserved word NOT
     * @throws Exception
     */
    public void CaseFortySeven(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();

        // throws type mismatch error if type is not RELATIONAL

        if (etype != EType.RELATIONAL)
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                    + ": Invalid use of " + etype + " operator ~");

        // Handles NOT by swapping etrue and efalse on the stack

        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        SemanticStack.push(efalse);
        SemanticStack.push(etrue);
        SemanticStack.push(EType.RELATIONAL);
    }

    /**
     * Array LookUp
     * @throws Exception
     */
    public void CaseFortyEight(Token token, boolean print) throws Exception
    {
        SymbolTableEntry offset = (SymbolTableEntry) SemanticStack.pop();

        if (offset != null)

            // call action 52 with the token from the parser

            if (offset.IsFunction())
                Execute(52, token, print);
        else
        {
            SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
            VariableEntry temp = Create(GetTempVar(), id.GetType());
            Generate("load", id, offset, temp);
            SemanticStack.push(temp);
        }
        SemanticStack.push(EType.ARITHMETIC);
    }

    /**
     * Ensures this is a function and gets parameter data
     */
    public void CaseFortyNine(Token token) throws Exception
    {
        // get etype and id but do not change the stack

        EType etype = (EType) SemanticStack.pop();

        // id should be a function

        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
        SemanticStack.push(etype);

        // throws type mismatch if etype is not arithmetic

        if (etype != EType.ARITHMETIC)
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                    + ": Invalid use of " + etype + " operator ~");

        // throws a syntax error if id is not a function

        if (!id.IsFunction())
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount() + ": id is not a function ~");

        ParamCount.push(0);
        ParamStack.push(((FunctionEntry)id).GetParameterInfo());
    }

    /**
     * Generates code to assign memory for function parameters & calls function
     * @throws Exception
     */
    public void CaseFifty(Token token) throws Exception
    {
        // uses a temporary stack to reverse the parameter order
        // because the parameters must be generated from the bottom-most to the top-most

        Stack<SymbolTableEntry> parameters = new Stack<>();

        // for each parameter on the stack

        while (SemanticStack.peek() instanceof ArrayEntry || SemanticStack.peek() instanceof ConstantEntry ||
                SemanticStack.peek() instanceof VariableEntry)
            parameters.push((SymbolTableEntry) SemanticStack.pop());

        // Generate code for each of the parameters

        while (!parameters.empty())
        {
            Generate("param", parameters.pop());
            LocalMemory++;
        }

        EType etype = (EType) SemanticStack.pop();
        FunctionEntry id = (FunctionEntry) SemanticStack.pop();
        int numparams = ParamCount.pop();

        // throws error if id has the wrong number of parameters

        if (numparams > id.GetNumberOfParameters())
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount() +
                    ": wrong number of parameters ~");

        // Generate CALL with ID and number of params

        Generate("call", id.GetName(), numparams);
        ParamStack.pop();
        NextParam = 0;

        // creates a temporary variable to hold result and pushes it

        VariableEntry temp = Create(GetTempVar(), id.GetResult().GetType());
        Generate("move", id.GetResult(), temp);
        SemanticStack.push(temp);
        SemanticStack.push(EType.ARITHMETIC);
    }

    /**
     * Generates code to assign memory for procedure parameters and call function
     * Identical to action 50, except:
     *              1) procedures have no return value
     *              2) special functions 51READ & 51WRITE
     * @param token token passed in from parser
     * @param print boolean that determines if output will be printed
     * @throws Exception
     */
    public void CaseFiftyOne(Token token, boolean print) throws Exception
    {
        Stack<SymbolTableEntry> parameters = new Stack<>();

        while (SemanticStack.peek() instanceof ArrayEntry || SemanticStack.peek() instanceof ConstantEntry ||
               SemanticStack.peek() instanceof VariableEntry)
            parameters.push((SymbolTableEntry) SemanticStack.pop());

        EType etype = (EType) SemanticStack.pop();
        ProcedureEntry id = (ProcedureEntry) SemanticStack.pop();

        if (id.GetName().equals("READ") || id.GetName().equals("WRITE"))
        {
            // replace everything on the stack and call 51WRITE

            SemanticStack.push(id);
            SemanticStack.push(etype);

            while (!parameters.empty())
                SemanticStack.push(parameters.pop());

            // 510 represents "51READ"

            if (id.GetName().equals("READ"))
                Execute(510, token, print);

            // 511 represents "51WRITE"

            else
                Execute(511, token, print);
        }
        else
        {
            int numparams = ParamCount.pop();

            if (numparams != id.GetNumberOfParameters())
                throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                        + ": wrong number of parameters ~");

            while (!parameters.empty())
            {
                Generate("param", parameters.pop());
                LocalMemory++;
            }
            Generate("call", id.GetName(), numparams);
            ParamStack.pop();
            NextParam = 0;
        }
    }

    /**
     * Reads input from user
     * @throws Exception
     */
    public void CaseFiftyOneRead() throws Exception
    {
        // for each parameter on the stack in reverse order

        Stack<SymbolTableEntry> parameters = new Stack<>();

        while (SemanticStack.peek() instanceof VariableEntry)
            parameters.push((SymbolTableEntry) SemanticStack.pop());

        // generates code for each of the parameters

        while (!parameters.empty())
        {
            SymbolTableEntry id = parameters.pop();

            if (id.GetType() == Token.Type.REAL)
                Generate("finp", id);
            else
                Generate("inp", id);
        }
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
        ParamCount.pop();
    }

    /**
     * Display variable name and contents
     */
    public void CaseFiftyOneWrite() throws Exception
    {
        // for each parameter on the stack in reverse order

        Stack<SymbolTableEntry> parameters = new Stack<>();

        while (SemanticStack.peek() instanceof ConstantEntry || SemanticStack.peek() instanceof VariableEntry)
            parameters.push((SymbolTableEntry) SemanticStack.pop());

        // for each of the parameters, if either constant or simple variable,
        // Generate code to OUTP or FOUTP the value

        while (!parameters.empty())
        {
            SymbolTableEntry id = parameters.pop();
            if (id.IsConstant())
            {
                if (id.GetType() == Token.Type.REAL)
                    Generate("foutp", id.GetName());
                else
                    Generate("outp", id.GetName());
            }
            else
            {
                Generate("Print", " \"" + id.GetName() + " = \"");

                if (id.GetType() == Token.Type.REAL)
                    Generate("foutp", id);
                else
                    Generate("outp", id);
            }
            Generate("new1");
        }
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
        ParamCount.pop();
    }

    /**
     * Case for function with no parameters
     * @throws Exception
     */
    public void CaseFiftyTwo(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();

        // throws error if id is not a function

        if (!id.IsFunction())
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                    + ": id is not a function ~");

        // throws error if id is not defined as having 0 parameters

        if (id.GetNumberOFParameters() > 0)
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                    + ": wrong number of parameters ~");

        // generates call

        Generate("call", id.GetName(), 0);
        // initializes a memory location for return value and pushes to stack
        VariableEntry temp = Create("temp", id.GetType());
        Generate("move", ((FunctionEntry)id).GetResult(), temp);
        SemanticStack.push(temp);
        SemanticStack.push(null);
    }

    /**
     * Look up variable or function result
     */
    public void CaseFiftyThree(Token token) throws Exception
    {
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
        if (id.IsFunction())
        {
            if (id != CurrentFunction)
                throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                        + ": illegal procedure error ~ ");

            SemanticStack.push(((FunctionEntry)id).GetResult());
            SemanticStack.push(EType.ARITHMETIC);
        }
        else
        {
            SemanticStack.push(id);
            SemanticStack.push(etype);
        }
    }

    /**
     * Confirms if the statement is a procedure call
     * @throws Exception
     */
    public void CaseFiftyFour(Token token) throws Exception
    {
        // Only a procedure call can appear on a line itself - check next ID

        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();

        SemanticStack.push(etype);

        // throws an illegal procedure error if the next ID appears on a line by itself and is not a procedure

        if (! (id.IsProcedure()))
            throw new SemanticActionsError("\n ~ ERROR ON LINE " + token.GetLineCount()
                    + ": illegal procedure error ~ ");
    }

    /**
     * Generates an end-of-MAIN
     * (wrapper code)
     * @throws Exception
     */
    public void CaseFiftyFive() throws Exception
    {
        Backpatch(GlobalStore, GlobalMemory);
        Generate("free", GlobalMemory);
        Generate("PROCEND");
    }

    /**
     * Generates a start-of-MAIN
     * (wrapper code)
     * @throws Exception
     */
    public void CaseFiftySix() throws Exception
    {
        Generate("PROCBEGIN", "main");
        GlobalStore = Quadruples.GetNextQuad();

        // the underscore is a placeholder that will be  later filled by Backpatch

        Generate("alloc", "_");
    }


    /**
     * Inserts a new variable entry into the appropriate symbol table
     * and associates it with a valid memory address
     * @param name the token value that will be the entry name
     * @param type the token type that will be the entry type
     * @return the newly created variable entry
     * @throws Exception
     */
    public VariableEntry Create(String name, Token.Type type) throws Exception
    {
        VariableEntry ve;

        // Global

        if (Global)
        {
            int veaddress = GlobalMemory;
            ve = new VariableEntry(name, veaddress, type);
            ve.SetVariable(true);
            GlobalMemory++;
            GlobalTable.Insert(ve.GetName(), ve);
        }

        // local

        else
        {
            int veaddress = LocalMemory;
            ve = new VariableEntry(name, veaddress, type);
            ve.SetVariable(true);
            LocalMemory++;
            LocalTable.Insert(ve.GetName(), ve);
        }
        return ve;
    }

    /**
     * Helper function for Create()
     * Creates temporary variable names using the temporary variable id
     * @return temp variable name
     */
    public String GetTempVar()
    {
        String tempname = "$$temp" + TempVarID;
        TempVarID++;
        return tempname;
    }

    /**
     * Instantiates a new quadruple to store the intermediate code
     * Performs LookUp of location in memory of any specified identifiers
     * @param op given operator
     * @throws Exception
     */
    public void Generate(String op)
    {
        // Creates a new string Array to represent the new quad and instantiates it accordingly

        String[] quadarray = new String[4];
        quadarray[0] = op;
        quadarray[1] = quadarray[2] = quadarray[3] = null;

        // Adds the new quad to Quadruples

        CreateNewQuad(quadarray);
    }

    /**
     * Instantiates a new quadruple to store the intermediate code
     * Performs LookUp of location in memory of any specified identifiers
     * @param op given operator
     * @param name given string
     * @param num given number
     * @throws Exception
     */
    public void Generate(String op, String name, String num)
    {
        // Creates a new string Array to represent the new quad and instantiates it accordingly

        String[] quadarray = new String[4];
        quadarray[0] = op;
        quadarray[1] = name;
        quadarray[2] = num;
        quadarray[3] = null;

        // Adds the new quad to Quadruples

        CreateNewQuad(quadarray);
    }

    /**
     * Instantiates a new quadruple to store the intermediate code
     * Performs LookUp of location in memory of any specified identifiers
     * @param op given operator
     * @param entries variable number of Symbol Table Entries
     * @throws Exception
     */
    public void Generate(String op, SymbolTableEntry...entries) throws Exception
    {
        int numentries = entries.length;

        // Cannot have more than 3 STE's passed in
        // throw error

        if (numentries > 3)
            throw new SemanticActionsError("\n ~ ERROR: Too many symbol table entries passed into Generate ~");

        else
        {
            // Creates a new string Array to represent the new quad and instantiates it accordingly

            String[] quadarray = new String[4];
            quadarray[0] = op;

            // if param, gets the appropriate parameter prefix
            // adds the variable num of ste's to the quad
            // adds the variable num of ste's to the quad

            if (op.equals("param"))
                for (int i = 0; i < numentries; i++)
                {
                    String entryaddress = GetParamPrefix(entries[i]) + GetSTEAddress(entries[i]);
                    quadarray[i + 1] = entryaddress;
                }

            // else get general ste prefix

            else
                for (int i = 0; i < numentries; i++)
                {
                    String entryaddress = GetSTEPrefix(entries[i]) + GetSTEAddress(entries[i]);
                    quadarray[i + 1] = entryaddress;
                }

            // sets the empty/unfilled entries in quad to null

            if (numentries < 3)
                for (int i = numentries + 1; i < 4; i++)
                    quadarray[i] = null;

            // Adds the new quad to Quadruples

            CreateNewQuad(quadarray);
        }
    }

    /**
     * Instantiates a new quadruple to store the intermediate code
     * Performs LookUp of location in memory of any specified identifiers
     * @param op given operator
     * @param name given name
     * @param entries variable number of Symbol Table Entries
     * @throws Exception
     */
    public void Generate(String op, String name, SymbolTableEntry ... entries) throws Exception
    {
        int numentries = entries.length;

        // Because 2 string parameters were passed in, can only have up to 2 STE's passed in
        // throw error

        if (numentries > 2)
            throw new SemanticActionsError(" \n ~ ERROR: Too many symbol table entries passed into Generate() ~");

        else
        {
            // Creates a new string Array to represent the new quad and instantiates it accordingly

            String[] quadarray = new String[4];
            quadarray[0] = op;
            quadarray[1] = name;

            // adds the variable num of ste's to the quad

            for (int i = 0; i < numentries; i++)
            {
                String entryaddress = GetSTEPrefix(entries[i]) + GetSTEAddress(entries[i]);
                quadarray[i + 2] = entryaddress;
            }

            // sets the empty/unfilled entries in quad to null

            if (numentries < 2)
                for (int i = numentries + 2; i < 4; i++)
                    quadarray[i] = null;

            // Adds the new quad to Quadruples

            CreateNewQuad(quadarray);
        }
    }

    /**
     * Instantiates a new quadruple to store the intermediate code
     * Performs LookUp of location in memory of any specified identifiers
     * @param op given operator
     * @param num given number
     * @throws Exception
     */
    public void Generate(String op, int num) throws Exception
    {
        // Creates a new string Array to represent the new quad and instantiates it accordingly

        String[] quadarray = new String[4];
        quadarray[0] = op;
        quadarray[1] = Integer.toString(num);
        quadarray[2] = quadarray[3] = null;

        // Adds the new quad to Quadruples

        CreateNewQuad(quadarray);
    }

    public void Generate(String str1, String str2, int num) throws Exception
    {
        String[] quadarray = new String[4];
        quadarray[0] = str1;
        quadarray[1] = str2;
        quadarray[2] = Integer.toString(num);
        quadarray[3] = null;
        CreateNewQuad(quadarray);
    }

    /**
     * Instantiates a new quadruple to store the intermediate code
     * Performs LookUp of location in memory of any specified identifiers
     * @param op given operator
     * @param num given number
     * @param ste given STE
     * @throws Exception
     */
    public void Generate(String op, int num, SymbolTableEntry ste) throws Exception
    {
        // Creates a new string Array to represent the new quad and instantiates it accordingly

        String[] quadarray = new String[4];
        quadarray[0] = op;
        quadarray[1] = Integer.toString(num);
        quadarray[2] = GetSTEPrefix(ste) + GetSTEAddress(ste);
        quadarray[3] = null;

        // Adds the new quad to Quadruples

        CreateNewQuad(quadarray);
    }

    /**
     * Instantiates a new quadruple to store the intermediate code
     * Performs LookUp of location in memory of any specified identifiers
     * @param op
     * @param ste1
     * @param ste2
     * @param addr
     * @throws Exception
     */
    public void Generate(String op, SymbolTableEntry ste1, SymbolTableEntry ste2, String addr) throws Exception
    {
        // Creates a new string Array to represent the new quad and instantiates it accordingly

        String[] quadarray = new String[4];
        quadarray[0] = op;
        quadarray[1] = GetSTEPrefix(ste1) + GetSTEAddress(ste1);
        quadarray[2] = GetSTEPrefix(ste2) + GetSTEAddress(ste2);
        quadarray[3] = addr;

        // Adds the new quad to Quadruples

        CreateNewQuad(quadarray);
    }


    /**
     * Adds a new quadruple to Quadruples using the given arguments
     * @param args takes a variable number of String arguments (can be 1, 2, 3, or 4)
     */
    public void CreateNewQuad(String[] args)
    {
            Quadruples.AddQuad(args);
    }

    /**
     * Determines or assigns a symbol table entry an address
     * @param ste a given symbol table entry
     * @return the symbol table entry address
     * @throws Exception
     */
    public int GetSTEAddress(SymbolTableEntry ste) throws Exception
    {
        // if Array entry or variable entry, return the assigned address

        if (ste.getClass() == ArrayEntry.class || ste.getClass() == VariableEntry.class ||
           ste.getClass() == FunctionEntry.class)
            return ste.GetAddress();

        // else if constant (constants aren't assigned an address at initialization)

        else if (ste instanceof ConstantEntry)
        {
            // creates a temporary variable to store it

            VariableEntry temp = Create(GetTempVar(), ste.GetType());

            // moves the constant into the temp

            Generate("move", ste.GetName(), temp);

            // returns the address of the temp

            return temp.GetAddress();
        }
        else
            throw new SemanticActionsError("\n ERROR: Can't get or assign an address for the symbol table entry "
                    + ste);
    }

    /**
     * Determines the prefix of a symbol table entry for non-"param" Generate statements
     * @param ste a given symbol table entry
     * @return a string with the appropriate prefix
     */
    public String GetSTEPrefix(SymbolTableEntry ste)
    {
        // if in Global memory

        if (Global)
            return "_";

        else
        {
            // local memory

            SymbolTableEntry entry1 = LocalTable.LookUp(ste.GetName());
            SymbolTableEntry entry2 = ConstantTable.LookUp(ste.GetName());

            if (entry1 == null && entry2 == null)
                return "_";

            else
            {
                // if the entry1 is a parameter

                if (ste.IsParameter())
                    return "^%";

                // else the entry1 is not a parameter

                else
                    return "%";
            }
        }
    }

    /**
     * Gets the proper prefix for a parameter
     * To be used when generating code with the opcode "param"
     * @param param passed in parameter
     * @return String prefix
     */
    public String GetParamPrefix(SymbolTableEntry param)
    {
        if (Global)
            return "@_";
        else
        {
            if (param.IsParameter())
                return "%";
            else
                return "@%";
        }
    }

    /**
     * Compares and checks the type of two ids
     * @param id1 first id
     * @param id2 second id
     * @return an integer depending on the id types (corresponds to given table)
     */
    public int TypeCheck(SymbolTableEntry id1, SymbolTableEntry id2) throws Exception
    {
        Token.Type id1_type = id1.GetType();
        Token.Type id2_type = id2.GetType();

        // if id1 & id2 are both integers

        if ((id1_type == Token.Type.INTEGER && id2_type == Token.Type.INTCONSTANT) ||
           (id1_type == Token.Type.INTCONSTANT && id2_type == Token.Type.INTCONSTANT) ||
           (id1_type == Token.Type.INTEGER && id2_type == Token.Type.INTEGER) ||
           (id1_type == Token.Type.INTCONSTANT && id2_type == Token.Type.INTEGER))
            return 0;

        // if id1 & id2 are both reals

        else if ((id1_type == Token.Type.REALCONSTANT && id2_type == Token.Type.REALCONSTANT) ||
                (id1_type == Token.Type.REAL && id2_type == Token.Type.REAL) ||
                (id1_type == Token.Type.REALCONSTANT && id2_type == Token.Type.REAL) ||
                (id1_type == Token.Type.REAL && id2_type == Token.Type.REALCONSTANT))
            return 1;

        // if id1 is real & id2 is an integer

        else if ((id1_type == Token.Type.REALCONSTANT && id2_type == Token.Type.INTCONSTANT) ||
                (id1_type == Token.Type.REAL && id2_type == Token.Type.INTEGER) ||
                (id1_type == Token.Type.REAL && id2_type == Token.Type.INTCONSTANT) ||
                (id1_type == Token.Type.REALCONSTANT && id2_type == Token.Type.INTEGER))
            return 2;

        // if id1 is an integer & id2 is real

        else if ((id1_type == Token.Type.INTEGER && id2_type == Token.Type.REALCONSTANT) ||
                (id1_type == Token.Type.INTEGER && id2_type == Token.Type.REAL) ||
                (id1_type == Token.Type.INTCONSTANT && id2_type == Token.Type.REAL) ||
                (id1_type == Token.Type.INTCONSTANT && id2_type == Token.Type.REALCONSTANT))
            return 3;
        else
            throw new SemanticActionsError("\n ~ ERROR: Entries passed into typecheck() " +
                    "do not match one of the type conditions ~");
    }

    /**
     * Performs a Backpatch:
     * Inserts a given integer x into the second field of the quadruple at given index i
     * @param i quadruple index
     * @param x given integer to Insert in quadruple
     */
    public void Backpatch(int i, int x)
    {
        // inserts x in the second field of the quadruple at index i in Quadruples

        Quadruples.SetField(i, 1, Integer.toString(x));
    }

    /**
     * Performs Backpatch on a list of integers:
     * Inserts a given integer x into either the second field or the fourth field
     * of the quadruple at each index from list
     * @param list list of integers to be used as indices
     * @param x given integer to Insert in quadruple
     */
    public void Backpatch(List<Integer> list, int x)
    {
        // current quadruple is a goto
        // inserts x in the fourth field of the quadruple at index i in Quadruples

        for (int i : list)
            if (Quadruples.GetQuad(i)[0].equals("goto")) {

                // inserts x in the second field of the quadruple at index i in Quadruples

                Quadruples.SetField(i, 1, Integer.toString(x));
            }

            // current quadruple is a branch statement

            else
                Quadruples.SetField(i, 3, Integer.toString(x));
    }

    /**
     * Looks up the corresponding symbol table entry for a given token in the Global table or local table
     * @param id a given token
     * @return a symbol table entry
     */
    public SymbolTableEntry LookUpID(Token id)
    {
        // looks in the local table

        SymbolTableEntry ste = LocalTable.LookUp(id.GetValue());

        // if id is not in local table
        // looks in Global table

        if (ste == null)
            ste = GlobalTable.LookUp(id.GetValue());
        return ste;
    }

    /**
     * Looks up the corresponding symbol table entry for a given token in the constant table
     * @param id a given token
     * @return a symbol table entry
     */
    public SymbolTableEntry LookUpConstant(Token id)
    {
        SymbolTableEntry ste = ConstantTable.LookUp(id.GetValue());
        return ste;
    }

    /**
     * Merges two lists
     * @param list1 first list to be merged
     * @param list2 other list to be merged
     * @return list containing both lists merged
     */
    public List<Integer> Merge(List<Integer> list1, List<Integer> list2)
    {
        List<Integer> newlist = list1;
        newlist.addAll(list2);
        return newlist;
    }

    /**
     * Creates a list of one element, using the passed in integer as the element
     * @param i integer that the list will contain
     * @return list with i as the only element
     */
    public List<Integer> MakeList(int i)
    {
        List<Integer> list = new ArrayList<Integer>();
        list.add(i);
        return list;
    }

    /**
     * Prints the contents of the semantic stack as a column of tokens
     */
    public void SemStackDump()
    {
        if (!SemanticStack.empty())
        {
            System.out.println("\n-------------------------");
            System.out.println(" SEMANTIC STACK CONTENTS ");
            System.out.println("-------------------------");
            for (int i = SemanticStack.size()-1; i >= 0; i--)
            {
                Object element = SemanticStack.get(i);
                if (element instanceof Token)
                    System.out.print("Token: " + element + "\n");

                else if (element instanceof SymbolTableEntry)
                    System.out.println("STE: " + element);

                else if (element instanceof EType)
                    System.out.println("EType: " + element);

                else if (element instanceof List)
                    System.out.println("List: " + element);

                else if (element instanceof Integer)
                    System.out.println("Number: " + element);

                else
                    System.out.println(element);
            }
            System.out.println();
        }
    }

    /**
     * Prints out the TVI code output if the given boolean is true
     * by calling the Print() function on Quadruples
     * @param print boolean that determines if the TVI code will be printed or not
     */
    public void PrintTVICode(boolean print)
    {
        if (print)
        {
            System.out.println();
            Quadruples.Print();
        }
    }
}
