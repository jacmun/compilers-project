package symboltable;

import lexer.Token;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class represents FunctionEntry objects. This is a child of SymbolTableEntry class,
 * so it extends SymbolTableEntry.
 *
 * @author jackiemun
 *
 */
public class FunctionEntry extends SymbolTableEntry
{
    private String Name;
    private int NumberOfParameters;
    private List ParameterInfo;
    private VariableEntry Result;
    private Token.Type Type;

    /**
     * Constructor that takes in the fields for a function entry
     * @param name entry Name
     * @param numberofparameters number of parameters for entry
     * @param parameterinfo list of parameter information
     * @param result a Variable Entry
     */
    public FunctionEntry(String name, int numberofparameters, List parameterinfo, VariableEntry result)
    {
        this.Name = name;
        this.NumberOfParameters = numberofparameters;
        this.ParameterInfo = parameterinfo;
        this.Result = result;
    }

    @Override
    public boolean IsFunction()
    {
        return true;
    }

    /**
     * Gets the function entry, entry Name
     * @return Name
     */
    public String GetName()
    {
        return Name;
    }

    /**
     * Sets the function Name to the given Name
     * @param name the String to be assigned to Name
     */
    public void SetName(String name)
    {
        this.Name = name;
    }

    /**
     * Gets the number of parameters for the entry
     * @return NumberOfParameters, the int representing the number of parameters
     */
    public int GetNumberOfParameters()
    {
        return NumberOfParameters;
    }

    /**
     * Sets the entry's number of parameters to the given integer
     * @param numberofparameters the int to be assigned to NumberOfParameters
     */
    public void SetNumberOfParameters(int numberofparameters)
    {
        this.NumberOfParameters = numberofparameters;
    }

    /**
     * Gets the list of parameter information for the entry
     * @return ParameterInfo, the list of parameter information
     */
    public List GetParameterInfo()
    {
        return ParameterInfo;
    }

    /**
     * Sets the entry's parameter information to the given list of parameter information
     * @param parameterinfo the list of parameter information to be assigned to ParameterInfo
     */
    public void SetParameterInfo(List parameterinfo)
    {
        this.ParameterInfo = parameterinfo;
    }

    /**
     * Gets the VariableEntry Result for the entry
     * @return Result, the VariableEntry
     */
    public VariableEntry GetResult()
    {
        return Result;
    }

    /**
     * Sets the entry's Result to the given VariableEntry
     * @param result the VariableEntry that Result will be set to
     */
    public void SetResult(VariableEntry result)
    {
        this.Result = result;
    }

    /**
     * Adds a parameter to the list of parameters, ParameterInfo
     * @param var the given SymbolTableEntry to add to ParameterInfo
     */
    @Override
    public void AddParameter(SymbolTableEntry var)
    {
        if (ParameterInfo == null)
        {
            ParameterInfo = new ArrayList();
            ParameterInfo.add(var);
        }
        else
            ParameterInfo.add(var);
    }

    /**
     * Gets the Type for the entry
     * @return Token Type
     */
    public Token.Type GetType()
    {
        return Type;
    }

    /**
     * Sets the entry's Type to the given Type
     * @param type the Token Type to be set to Type
     */
    public void SetType(Token.Type type)
    {
        this.Type = type;
    }

    /**
     * Sets the entry's Result Type to the given Type
     * @param type the Token Type to be set to resultType
     */
    public void SetResultType(Token.Type type)
    {
        Result.SetType(type);
    }

    /**
     * Returns a String containing the entry Name and fields
     * @return String representation of the function entry
     */
    public String ToString()
    {
        String str = "Entry: " + Name + ", Num of Parameters: " + NumberOfParameters
                + " , Parameter Info: " + ParameterInfo;
        return str;
    }
}
