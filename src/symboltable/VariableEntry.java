package symboltable;

import lexer.Token;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class represents VariableEntry objects. This is a child of SymbolTableEntry class,
 * so it extends SymbolTableEntry.
 *
 * @author jackiemun
 *
 */public class VariableEntry extends SymbolTableEntry
{
    private String Name;
    private int Address;
    private Token.Type Type;
    private boolean Result;   // Result tag

    /**
     * Constructor that takes in the entry Name
     * @param name entry Name
     */
    public VariableEntry(String name)
    {
        this.Name = name;
    }

    /**
     * Constructor that takes in all of the entry fields
     * @param name entry Name
     * @param address entry Address
     * @param type entry Type
     */
    public VariableEntry(String name, int address, Token.Type type)
    {
        this.Name = name;
        this.Address = address;
        this.Type = type;
    }

    @Override
    public boolean IsVariable()
    {
        return true;
    }

    /**
     * Gets the variable entry Name
     * @return Name, the entry Name
     */
    public String GetName()
    {
        return Name;
    }

    /**
     * Sets the variable entry Name to the given Name
     * @param name the String to be assigned to Name
     */
    public void SetName(String name)
    {
        this.Name = name;
    }

    /**
     * Gets the variable entry Address
     * @return Address, the entry Address
     */
    public int GetAddress()
    {
        return Address;
    }

    /**
     * Sets the variable entry Address to the given int
     * @param address the int to be assigned to Address
     */
    public void SetAddress(int address)
    {
        this.Address = address;
    }

    /**
     * Gets the variable entry Type
     * @return Type, the Token Type of the entry
     */
    public Token.Type GetType()
    {
        return Type;
    }

    /**
     * Sets the variable entry Type to the given Token Type
     * @param type the Token Type to be assigned to Type
     */
    public void SetType(Token.Type type)
    {
        this.Type = type;
    }

    /**
     * Gets the Result tag
     * @return Result, the boolean Result tag
     */
    public boolean IsResult()
    {
        return Result;
    }

    /**
     * Sets the Result tag to the passed in boolean
     * @param result boolean that the Result tag will be set to
     */
    public void SetResult(boolean result)
    {
        this.Result = result;
    }

    /**
     * Returns a String containing the variable entry Name and fields
     * @return String representation of the variable entry
     */
    public String ToString()
    {
        String str = "Name: " + Name + ", Address: " + Address + " , Type: " + Type;
        return str;
    }
}
