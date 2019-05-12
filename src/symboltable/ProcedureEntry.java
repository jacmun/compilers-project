package symboltable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class represents ProcedureEntry objects. This is a child of SymbolTableEntry class,
 * so it extends SymbolTableEntry.
 *
 * @author jackiemun
 *
 */public class ProcedureEntry extends SymbolTableEntry
{
    private String Name;
    private int NumberOfParameters;
    private List ParameterInfo;

    /**
     * Constructor that only takes in a Name
     * @param name the entry Name
     */
    public ProcedureEntry(String name)
    {
        this.Name = name;
    }

    /**
     * Constructor that takes in all the entry fields
     * @param name the entry Name
     * @param numberofparameters the number of parameters
     * @param parameterinfo List containing the parameter information
     */
    public ProcedureEntry(String name, int numberofparameters, List parameterinfo)
    {
        this.Name = name;
        this.NumberOfParameters = numberofparameters;
        this.ParameterInfo = parameterinfo;
    }

    @Override
    public boolean IsProcedure()
    {
        return true;
    }

    /**
     * Gets the procedure entry Name
     * @return Name, the entry Name
     */
    public String GetName()
    {
        return Name;
    }

    /**
     * Sets the entry Name to the given Name
     * @param name the String to be assigned to Name
     */
    public void SetName(String name)
    {
        this.Name = name;
    }

    /**
     * Gets the number of parameters for the procedure entry
     * @return int that represents the number of parameters
     */
    public int GetNumberOfParameters()
    {
        return NumberOfParameters;
    }

    /**
     * Sets the number of parameters to the given int
     * @param numberofparameters the int to be assigned to NumberOfParameters
     */
    public void SetNumberOfParameters(int numberofparameters)
    {
        this.NumberOfParameters = numberofparameters;
    }

    /**
     * Gets the list containing the entry's parameter information
     * @return the list of parameter information
     */
    public List GetParameterInfo()
    {
        return ParameterInfo;
    }

    /**
     * Sets the entry's parameter information to the given list
     * @param parameterinfo the list to be assigned to ParameterInfo
     */
    public void SetParameterInfo(List parameterinfo)
    {
        this.ParameterInfo = parameterinfo;
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
     * Returns a String containing the entry Name and fields
     * @return a String representation of the procedure entry
     */
    public String ToString()
    {
        String str = "Name: " + Name + ", Num of Parameters: " + NumberOfParameters
                + " , Parameter Info: " + ParameterInfo;
        return str;
    }
}
