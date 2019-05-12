package quadruples;

import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * CMPU-331 (Compilers), Spring 2019
 *
 * The Quadruples class stores and manages quadruples.
 * A quadruple stores lines of intermediate code as it is generated
 * and is represented as a String array of size 4.
 *
 * @author jackiemun
 *
 */
public class Quadruples
{
    private Vector<String[]> Quadruple;
    private int NextQuad;

    /**
     * Constructor that initializes a Quadruple vector that will store the individual quadruple arrays
     * Creates a dummy quad and adds it to Quadruple
     */
    public Quadruples()
    {
        Quadruple = new Vector<String[]>();
        NextQuad = 0;
        String[] dummyQuad = new String[4];
        dummyQuad[0] = dummyQuad[1] = dummyQuad[2] = dummyQuad[3] = null;
        Quadruple.add(NextQuad,dummyQuad);
        NextQuad++;
    }

    /**
     * Gets the element in the given field of the quadruple located at the given index of Quadruple
     * @param quadIndex the index access into Quadruple
     * @param field the quad field where the desired element is located
     * @return
     */
    public String GetField(int quadIndex, int field)
    {
        return Quadruple.elementAt(quadIndex)[field];
    }

    /**
     * Sets the element in the given field of the quadruple located at the given index of Quadruple
     * @param quadIndex the index access into Quadruple
     * @param index the index into the quad
     * @param field the element that the quad field will be set to
     */
    public void SetField(int quadIndex, int index, String field)
    {
        Quadruple.elementAt(quadIndex)[index] = field;
    }

    /**
     * Gets the next quad in Quadruple
     * @return a quadruples
     */
    public int GetNextQuad()
    {
        return NextQuad;
    }

    /**
     * Increments the value of NextQuad
     */
    public void IncrementNextQuad()
    {
        NextQuad++;
    }

    /**
     * Gets the quadruple at the given index
     * @param index index into Quadruple
     * @return a quad
     */
    public String[] GetQuad(int index)
    {
        return (String []) Quadruple.elementAt(index);
    }

    /**
     * Adds a quad to Quadruple
     * @param quad the quad to be added to Quadruple
     */
    public void AddQuad(String[] quad)
    {
        Quadruple.add(NextQuad, quad);
        NextQuad++;
    }

    /**
     * Prints a string representation of the Quadruple:
     * prints each quad, in Quadruple, and all of its elements
     */
    public void Print()
    {
        int quadLabel = 1;

        System.out.println("CODE");

        Enumeration<String[]> e = this.Quadruple.elements();
        e.nextElement();

        while (e.hasMoreElements())
        {
            String[] quad = e.nextElement();
            System.out.print(quadLabel + ":  " + quad[0]);

            if (quad[1] != null)
                System.out.print(" " + quad[1]);

            if (quad[2] != null)
                System.out.print(", " + quad[2]);

            if (quad[3] != null)
                System.out.print(", " + quad[3]);

            System.out.println();
            quadLabel++;
        }
    }
}
