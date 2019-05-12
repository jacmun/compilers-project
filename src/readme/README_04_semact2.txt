README 

---------------------------
I. General Information 
---------------------------
Name: Jacqueline Mun 
Course: Compilers (CMPU 331) 
Assignment: Semantic Actions 2   


---------------------------
II. Assignment Details 
---------------------------
The purpose of this assignment is to update existing components, implement more semantic actions, and begin using the Quadruples class. 


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
semanticaction/SemanticActions.java	Semantic Actions class 
testfiles/phase2-1_ns.vas		Test file 2-1 
testfiles/phase2-2_ns.vas		Test file 2-2
testfiles/phase2-3_ns.vas		Test file 2-3
testfiles/phase2-4_ns.vas		Test file 2-4
testfiles/phase2-5_ns.vas		Test file 2-5 
testfiles/phase2-6_ns.vas		Test file 2-6
testfiles/phase2-7_ns.vas		Test file 2-7


		~ AUXILIARY FILES USED IN PROGRAM ~ 
lexer/LexicalAnalyzer.java		Lexer class 
lexer/LexicalError.java 		Lexer Error class 
lexer/Token.java 			Token class 
parser/Parser.java			Parser class 
parser/ParserError.java 		Parser Error class 



---------------------------
IV. Running the Code 
---------------------------
I used the IntelliJ IDEA to run the code. Open the "compiler" folder in IntelliJ. The Main.java class creates an instance of a parser, which calls the semantic actions, and gets the program to start. The symbol table classes are located in the symboltable package and the semantic action classes are located in the semanticaction package. When the parser object is created in main(), two boolean fields are passed in. The first one determines if the parse output will be printed and the second one determines if the TVI code output will be printed. Both are currently set to true, so that all of the output can be seen when the program is run. Set the respective boolean value parameter for the parser object to false to turn off debug mode for either the parser or TVI code. 

The text files for the parse table and the grammar table are located in the parser package and read in in the Parser class. The file path names read in the FileReader only include the file package and name (i.e "parser/parsetable.txt", "parser/TVI_Grammar_Augmented.txt") because the working directory includes the greater path (i.e compiler/src). In IntelliJ, the working directory can be manually specified by clicking the right-hand Main dropdown menu and clicking Edit Configurations.  

To run the program with a test file, I again go to the right-hand Main dropdown menu, clicked Edit Configurations, and inputted the file path name in the Program Arguments space. I used testfiles/phase2-1_ns.vas, testfiles/phase2-2_ns.vas, testfiles/phase2-3_ns.vas, testfiles/phase2-4_ns.vas, testfiles/phase2-5_ns.vas, testfiles/phase2-6_ns.vas, and testfiles/phase2-7_ns.vas to test my this phase of my program. 



---------------------------
V. Changes  
---------------------------
I found that it was unnecessary for my program to have a Quadruple class because in my implementation of the Quadruples class (which uses the sample Quadruples class code given in the project resources), each Quadruple can simply be represented as a String array. 

I changed my semantic stack in the SemanticActions class from a stack of Tokens to a stack of Objects, so that it can handle any data type. As a result, when I push or pop objects on/off the stack, I also typecast the objects to their appropriate type. 

The summary document for Semantic Actions 2 mentions that for generate(), you must be able to tell, for a given symbol table entry, if that corresponding symbol table is in global or local memory. It suggests including an extra field in SymbolTableEntry to keep track of which table it's in, which is what I ended up doing. Thus, every time a symbol table entry is inserted into a symbol table, I also added a line of code that sets this field to either global or local. 



 


