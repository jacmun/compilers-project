README 

---------------------------
I. General Information 
---------------------------
Name: Jacqueline Mun 
Course: Compilers (CMPU 331) 
Assignment: Symbol Table & Semantic Action 1  


---------------------------
II. Assignment Details 
---------------------------
The purpose of this assignment was to design a symbol table class and complete semantic actions phase one. 


---------------------------
III. Important Files 
---------------------------
Main.java				Main class 
symboltable/SymbolTable.java	 	Symbol Table class 
symboltable/SymbolTableEntry.java	Symbol Table Entry class 
symboltable/ArrayEntry.java		Array Entry class 
					(child of Symbol Table entry class)
symboltable/ConstantEntry.java		Constant Entry class
					(child of Symbol Table Entry class)
symboltable/FunctionEntry.java		Function Entry class 
					(child of Symbol Table entry class)
symboltable/ProcedureEntry.java		Procedure Entry class
					(child of Symbol Table Entry class)
symboltable/VariableEntry.java		Variable Entry class 
					(child of Symbol Table entry class)
symboltable/IODeviceEntry.java		IO Device Entry class
					(child of Symbol Table Entry class) 
symboltable/SymbolTableError.java 	Symbol Table Error class 
semanticaction/SemanticActionOne.java	Semantic Action 1 class 
testfiles/simple.txt 			Test file to be used with program 
testfiles/ult-corrected.txt 		Test file to be used with program 

		~ AUXILIARY FILES USED IN PROGRAM ~ 
lexer/LexicalAnalyzer.java		Lexer class 
lexer/LexicalError.java 		Lexer Error class 
lexer/Token.java 			Token class 
parser/Parser.java			Parser class 
parser/ParserError.java 		Parser Error class 



---------------------------
IV. Running the Code 
---------------------------
I used the IntelliJ IDEA to run the code. Open the "compiler" folder in IntelliJ. The Main.java class creates an instance of a parser, which calls semantic actions one, and gets the program to start. The symbol table classes are located in the symboltable package and the semantic action classes are located in the semanticaction package. The parser object currently has "debug mode" set to true, so that the output can be seen when the program is run. Set the boolean value parameter for the parser object to false to turn off debug mode. 

The text files for the parse table and the grammar table are located in the parser package and read in in the Parser class. The file path names read in the FileReader only include the file package and name (i.e "parser/parsetable.txt") because the working directory includes the greater path (i.e compiler/src). In IntelliJ, the working directory can be manually specified by clicking the right-hand Main dropdown menu and clicking Edit Configurations.  

To run the program with a test file, I again go to the right-hand Main dropdown menu, clicked Edit Configurations, and inputted the file path name in the Program Arguments space. I mainly used testfiles/simple.txt and testfiles/ult-corrected.txt to test my program. 



---------------------------
V. Changes  
---------------------------
I changed the lexical analyzer, so that the token type is represented as an enum data type in the Token class. I originally represented the token type using private data members and a hash map in the Lexical Analyzer class - it worked at the time, but it was ugly and not good implementation, which is why I changed it.
 
I altered my parse() method in the Parser class. I found something that I thought could be better done as a separate function - I split the work done if the stack symbol is a non-terminal into another helper function and now have the parse() method call it.

I changed various variables in the Parser class that were being used to determine what mode/step the parser was in (i.e print determines if dumpStack() will print the stack contents, match determines if the parse was a match, etc.). I originally had them all as ints, where 1 was true and anything else was false. I don't know why I just didn't have them as booleans in the first place, but I changed them all to be boolean values.  

I updated the grammar table, so that the parser now uses the augmented version of the Vascal grammar table to create the right hand side table in initRHStable(). I also updated the parser, so that when a semantic action number is encountered on the stack, the appropriate semantic action is called. 





