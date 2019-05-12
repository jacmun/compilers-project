import compiler.Compiler;

import java.io.*;

/**
 * CMPU-331 (Compilers), Spring 2019
 *
 * Main class
 * Reads in the file and calls the compiler
 *
 * @author jackiemun
 * @version
 */

public class Main
{

    public static void main(String[] args) throws Exception
    {
        // gets file from main arguments

        File file = new File(args[0]);

        // checks that the file exists

        if(!file.exists())
        {
            System.out.println("File " + args[0] + "does not exist");
            return;
        }

        // calls the compiler with the file and two boolean values
        // first boolean value indicates whether the parse stream should be printed
        // second boolean value indicates whether the tvi output should be printed

        Compiler compiler = new Compiler(file, true, true);
    }
}
