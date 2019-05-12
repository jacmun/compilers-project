package symboltable;

import lexer.Token;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class handles the implementation of symbol table entries.
 * Objects of the SymbolTableEntry class will be used as values for SymbolTables (see SymbolTable class).
 *
 * There are a set of subclasses that extend the SymbolTableEntry class:
 * ArrayEntry, ConstantEntry, FunctionEntry, ProcedureEntry, VariableEntry, IODeviceEntry
 *
 * @author jackiemun
 *
 */
public class SymbolTableEntry
{
    private boolean Variable = false;
    private boolean Procedure = false;
    private boolean Function = false;
    private boolean FunctionResult = false;
    private boolean Parameter = false;
    private boolean Array = false;
    private boolean Reserved = false;
    private boolean Constant = false;
    private String Name;
    private int Address;
    private Token.Type Type;
    private int NumberOfParameters;

    /**
     * Sets private Variable, Variable, to given boolean
     * @param variable the boolean being assigned to Variable
     */
    public void SetVariable(boolean variable)
    {
        this.Variable = variable;
    }

    /**
     * Returns the boolean value of Variable
     * @return Variable, a boolean Variable
     */
    public boolean IsVariable()
    {
        return Variable;
    }

    /**
     * Sets private Variable, Procedure, to given boolean
     * @param procedure the boolean being assigned to Procedure
     */
    public void SetProcedure(boolean procedure)
    {
        this.Procedure = procedure;
    }

    /**
     * Returns the boolean value of Procedure
     * @return Procedure, a boolean Variable
     */
    public boolean IsProcedure()
    {
        return Procedure;
    }

    /**
     * Sets private Variable, Function, to given boolean
     * @param function the boolean being assigned to Function
     */
    public void SetFunction(boolean function)
    {
        this.Function = function;
    }

    /**
     * Returns the boolean value of Function
     * @return Function, a boolean Variable
     */
    public boolean IsFunction()
    {
        return Function;
    }

    /**
     * Sets private Variable, FunctionResult, to given boolean
     * @param functionResult the boolean being assigned to FunctionResult
     */
    public void SetFunctionResult(boolean functionResult)
    {
        this.FunctionResult = functionResult;
    }

    /**
     * Returns the boolean value of FunctionResult
     * @return FunctionResult, a boolean Variable
     */
    public boolean IsFunctionResult()
    {
        return FunctionResult;
    }

    /**
     * Sets private Variable, Parameter, to given boolean
     * @param parameter the boolean being assigned to Parameter
     */
    public void SetParameter(boolean parameter)
    {
        this.Parameter = parameter;
    }

    /**
     * Returns the boolean value of Parameter
     * @return Parameter, a boolean Variable
     */
    public boolean IsParameter()
    {
        return Parameter;
    }

    /**
     * Sets private Variable, Array, to given boolean
     * @param array the boolean being assigned to Array
     */
    public void SetArray(boolean array)
    {
        this.Array = array;
    }

    /**
     * Returns the boolean value of Array
     * @return Array, a boolean Variable
     */
    public boolean IsArray()
    {
        return Array;
    }

    /**
     * Sets private Variable, Reserved, to given boolean
     * @param reserved the boolean being assigned to Reserved
     */
    public void SetReserved(boolean reserved)
    {
        this.Reserved = reserved;
    }

    /**
     * Returns the boolean value of Reserved
     * @return Reserved, a boolean Variable
     */
    public boolean IsReserved()
    {
        return Reserved;
    }

    /**
     * Sets private Variable, Constant, to given boolean
     * @param constant the boolean being assigned to Constant
     */
    public void SetConstant(boolean constant)
    {
        this.Constant = constant;
    }

    /**
     * Returns the boolean value of Constant
     * @return Constant, a boolean Variable
     */
    public boolean IsConstant()
    {
        return Constant;
    }

    /**
     * Returns the name of the STE
     * @return Name
     */
    public String GetName()
    {
        return Name;
    }

    /**
     * Returns the address of the STE
     * @return Address
     */
    public int GetAddress()
    {
        return Address;
    }

    /**
     * Returns the token type of the STE
     * @return Type
     */
    public Token.Type GetType()
    {
        return Type;
    }

    /**
     * Returns the number of parameters for the STE
     * @return NumberOfParameters
     */
    public int GetNumberOFParameters()
    {
        return NumberOfParameters;
    }

    /**
     * Adds a STE parameter
     * Left empty - to be overloaded in ProcedureEntry and FunctionEntry classes
     * @param var STE to be added
     */
    public void AddParameter(SymbolTableEntry var)
    {

    }
}
