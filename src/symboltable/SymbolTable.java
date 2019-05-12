package symboltable;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * This class handles the implementation of symbol tables.
 * Symbol tables will be used by the semantic actions to Insert names and information,
 * and look up names. A symbol table is represented as a hash table that takes a
 * String name as a key and a SymbolTableEntry (see SymbolTableEntry class) as a value.
 *
 * This compiler will use three symbol tables:
 * a constant table, a global symbol table, and a local symbol table
 *
 * @author jackiemun
 *
 */
public class SymbolTable
{
    private Hashtable<String, SymbolTableEntry> HashTable;

    /**
     * Default constructor that initializes the hash table, representing the symbol table, without a specified size
     */
    public SymbolTable()
    {
        HashTable = new Hashtable<>();
    }

    /**
     * Constructor that intializes the hash table, representing the symbol table, to given size
     * @param size the size of the hashtable
     */
    public SymbolTable(int size)
    {
        HashTable = new Hashtable<>(size);
    }

    /**
     * @param symbol the key being looked up in the table
     * @return returns the attributes for symbol if symbol exists in the table; otherwise, returns null
     */
    public SymbolTableEntry LookUp(String symbol)
    {
        if (HashTable.containsKey(symbol))
            return HashTable.get(symbol);
        else
            return null;
    }

    /**
     * Inserts a new String key, Symbol Table Entry pair in the hashtable
     * @param key the String that acts as a key for the entry in the hashtable
     * @param entry the SymbolTableEntry that is the value in the hashtable
     */
    public void Insert(String key, SymbolTableEntry entry) throws Exception
    {
        if (HashTable.containsKey(key))
            throw new SymbolTableError(key);

        HashTable.put(key, entry);
    }

    /**
     * Returns the number of entries in the hashtable
     * @return the size of the hashtable
     */
    public int GetSize()
    {
        return HashTable.size();
    }



    /**
     * Prints the contents of the table - name and contents of each field in the entry
     */
    public void DumpTable()
    {
        System.out.println("-------------------------");
        System.out.println(" SYMBOL TABLE CONTENTS ");
        System.out.println("-------------------------");
        Enumeration<String> keys = HashTable.keys();
        String name;
        while (keys.hasMoreElements())
        {
            name = keys.nextElement();
            System.out.println("ENTRY  - " + HashTable.get(name).toString() + "\n");
        }
    }
}
