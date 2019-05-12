package symboltable;

import lexer.Token;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class represents ArrayEntry objects. This is a child of SymbolTableEntry class,
 * so it extends SymbolTableEntry.
 *
 * @author jackiemun
 *
 */
public class ArrayEntry extends SymbolTableEntry
{
    private String Name;
    private int Address;
    private Token.Type Type;
    private int UpperBound;
    private int LowerBound;

    /**
     * Constructor that creates a ArrayEntry object with the entry Name
     * @param name array entry Name
     */
    public ArrayEntry(String name)
    {
        this.Name = name;
    }

    /**
     * Constructor that creates an ArrayEntry object with all of the entry fields
     * @param name array Name
     * @param address array Address
     * @param type array Type
     * @param upperbound array upper bound
     * @param lowerbound array lower boung
     */
    public ArrayEntry(String name, int address, Token.Type type, int upperbound, int lowerbound)
    {
        this.Name = name;
        this.Address = address;
        this.Type = type;
        this.UpperBound = upperbound;
        this.LowerBound = lowerbound;
    }

    @Override
    public boolean IsArray()
    {
        return true;
    }

    /**
     * Returns the Name of the array entry
     * @return Name, the array Name
     */
    public String GetName()
    {
        return Name;
    }

    /**
     * Sets the Name of the array entry to the given Name
     * @param name the String to be assigned as the entry Name
     */
    public void SetName(String name)
    {
        this.Name = name;
    }

    /**
     * Gets the array entry Address
     * @return Address, the array entry Address
     */
    public int GetAddress()
    {
        return Address;
    }

    /**
     * Sets the array entry Address to the given Address
     * @param address the int to be assigned to the Address
     */
    public void SetAddress(int address)
    {
        this.Address = address;
    }

    /**
     * Gets the array entry Type
     * @return Type, a Token Type that is the entry Type
     */
    public Token.Type GetType()
    {
        return Type;
    }

    /**
     * Sets the array entry Type to a given Type
     * @param type the Token Type to be assigned to the Type
     */
    public void SetType(Token.Type type)
    {
        this.Type = type;
    }

    /**
     * Gets the array upper bound
     * @return UpperBound, an int that is the array upper bound
     */
    public int GetUpperBound()
    {
        return UpperBound;
    }

    /**
     * Sets the array entry upper bound to the given int
     * @param upperbound the int to be assigned as the upper bound
     */
    public void SetUpperBound(int upperbound)
    {
        this.UpperBound = upperbound;
    }

    /**
     * Gets the array lower bound
     * @return LowerBound, an int that is the array lower bound
     */
    public int GetLowerBound()
    {
        return LowerBound;
    }

    /**
     * Sets the array lower bound to the given int
     * @param lowerbound the int to be assigned as the lower bound
     */
    public void SetLowerBound(int lowerbound)
    {
        this.LowerBound = lowerbound;
    }

    /**
     * Returns the array entry Name and fields
     * @return String representation of the array entry
     */
    public String ToString()
    {
        String str = "Name: " + Name + ", Address: " + Address + " , Type: " + Type + ", Upper Bound: " + UpperBound
                + ", Lower Bound: " + LowerBound;
        return str;
    }
}
