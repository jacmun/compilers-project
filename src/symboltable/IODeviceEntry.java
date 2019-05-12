package symboltable;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class represents IODeviceEntry objects. This is a child of SymbolTableEntry class,
 * so it extends SymbolTableEntry.
 *
 * @author jackiemun
 *
 */
public class IODeviceEntry extends SymbolTableEntry
{
    private String Name;

    /**
     * Constructor
     * @param name the entry Name
     */
    public IODeviceEntry(String name)
    {
        this.Name = name;
    }

    /**
     * Gets the IO Device entry Name
     * @return Name, the entry Name
     */
    public String GetName()
    {
        return Name;
    }

    /**
     * Sets the entry Name to given Name
     * @param name the String to be assigned to Name
     */
    public void SetName(String name)
    {
        this.Name = name;
    }

    /**
     * Returns a String containing the entry Name
     * @return String representation of the entry
     */
    public String ToString()
    {
        String str = "Name: " + Name;
        return str;
    }
}
