package symboltable;

import lexer.Token;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class represents ConstantEntry objects. This is a child of SymbolTableEntry class,
 * so it extends SymbolTableEntry.
 *
 * @author jackiemun
 *
 */
public class ConstantEntry extends SymbolTableEntry
{
    private String Name;
    private Token.Type Type;
    private String Value;

    /**
     * Constructor
     * @param name constant entry Name
     * @param type constant entry Type
     */
    public ConstantEntry(String name, Token.Type type, String value)
    {
        this.Name = name;
        this.Type = type;
        this.Value = value;
    }

    @Override
    public boolean IsConstant()
    {
        return true;
    }

    /**
     * Gets the entry Name
     * @return String Name, the constant entry Name
     */
    public String GetName()
    {
        return Name;
    }

    /**
     * Sets the entry Name to given Name
     * @param name the String passed in to be assigned to Name
     */
    public void SetName(String name)
    {
        this.Name = name;
    }

    /**
     * Gets the constant entry Type
     * @return Type, a Token Type
     */
    public Token.Type GetType()
    {
        return Type;
    }

    /**
     * Sets the constant entry Type to the given Type
     * @param type the Token Type to be assigned to Type
     */
    public void SetType(Token.Type type)
    {
        this.Type = type;
    }

    /**
     * Gets the constant entry Value
     * @return Value, an integer
     */
    public String GetValue()
    {
        return Value;
    }

    /**
     * Sets the constant entry Value to the given Value
     * @param value the int to be assigned to Value
     */
    public void SetValue(String value)
    {
        this.Value = value;
    }

    /**
     * Returns a String of the entry Name and fields
     * @return String representation of the constant entry
     */
    public String ToString()
    {
        String str = "Name: " + Name + " , Type: " + Type;
        return str;
    }
}
