README 

---------------------------
I. General Information 
---------------------------
Name: Jacqueline Mun 
Course: Compilers (CMPU 331) 
Assignment: Semantic Actions 3   


---------------------------
II. Assignment Details 
---------------------------
The purpose of this assignment is to implement semantic actions that handle the code for subscripts, relational and boolean operators. 


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

quadruples/Quadruples.java		Quadruples class 

			~ TEST FILES ~ 
testfiles/phase3-1.vas			Test file 3-1 
testfiles/phase3-2.vas			Test file 3-2
testfiles/phase3-3.vas			Test file 3-3
testfiles/phase3-4.vas			Test file 3-4
testfiles/phase3-5.vas			Test file 3-5 
testfiles/phase3-6.vas			Test file 3-6
testfiles/phase3-7.vas			Test file 3-7
testfiles/phase3-8.vas			Test file 3-8

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

To run the program with a test file, I again go to the right-hand Main dropdown menu, clicked Edit Configurations, and inputted the file path name in the Program Arguments space. I used the test files specified in section "III. Important Files" to test this phase of my program. 



---------------------------
V. Changes  
---------------------------
I had to add two more overloaded generate() methods because in this phase, we use two more versions of generate(), with parameters inputs that we previously had not seen before. Thus, I had to add a generate method that takes a String, int, and SymbolTableEntry as input and a generate method that takes a String, SymbolTableEntry, SymbolTableEntry, and String as input. 

I had to fix my getOpCode() method in my Token class to account for RELOP operations. I had previously only included opcodes for MULOP and ADDOP operations because they were the only ones needed/being handled. 

I added a line count in my error handling, so that when an error is reported, the line where the error was found is also specified. 

I made all the other necessary revisions to previous semantic actions mentioned in the project specifications. 

 
