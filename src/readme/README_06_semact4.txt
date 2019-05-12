README 

---------------------------
I. General Information 
---------------------------
Name: Jacqueline Mun 
Course: Compilers (CMPU 331) 
Assignment: Semantic Actions 4  


---------------------------
II. Assignment Details 
---------------------------
The purpose of this assignment is to implement eighteen new semantic actions that are responsible for all remaining operations - this includes procedure/function declarations, result variables, and allocation and deallocation of local storage.  


---------------------------
III. Important Files 
---------------------------
Main.java			   -->	Main class 

lexer/LexicalAnalyzer.java	   -->	Lexer class 

lexer/LexicalError.java 	   -->	Lexer Error class
 
lexer/Token.java 		   -->	Token class 

parser/Parser.java		   -->	Parser class 

parser/ParserError.java 	   -->	Parser Error class 

symboltable/SymbolTable.java	   -->	Symbol Table class 

symboltable/SymbolTableEntry.java  -->	Symbol Table Entry class 

symboltable/ArrayEntry.java	   --> 	Array Entry class 
				        (child of STE class)

symboltable/ConstantEntry.java	   -->	Constant Entry class
					(child of STE class)

symboltable/FunctionEntry.java	   -->	Function Entry class 
					(child of STE class)

symboltable/ProcedureEntry.java	    -->	Procedure Entry class
					(child of STE class)

symboltable/VariableEntry.java	    -->	Variable Entry class 
					(child of STE class)

symboltable/IODeviceEntry.java	    -->	IO Device Entry class
					(child of STE class)
 
symboltable/SymbolTableError.java   --> Symbol Table Error class 

semanticaction/SemanticActions.java -->	Semantic Actions class 

quadruples/Quadruples.java	    -->	Quadruples class 

			~ TEST FILES ~ 
testfiles/semac-phase4-test/array.pas  
testfiles/semac-phase4-test/array-ref.pas
testfiles/semac-phase4-test/expression.pas
testfiles/semac-phase4-test/fib.pas
testfiles/semac-phase4-test/func.pas
testfiles/semac-phase4-test/func-manyinputs.pas  
testfiles/semac-phase4-test/if.pas
testfiles/semac-phase4-test/many-read-writes.pas
testfiles/semac-phase4-test/missing-semicolon.pas
testfiles/semac-phase4-test/noparm.pas
testfiles/semac-phase4-test/proc.pas  
testfiles/semac-phase4-test/recursion.pas
testfiles/semac-phase4-test/simple.pas
testfiles/semac-phase4-test/testi.pas
testfiles/semac-phase4-test/ultimate.pas
testfiles/semac-phase4-test/uminus.pas
testfiles/semac-phase4-test/while.pas  


---------------------------
IV. Running the Code 
---------------------------
I used the IntelliJ IDEA to run the code. Open the "compiler" folder in IntelliJ. The Main.java class creates an instance of a parser, which calls the semantic actions, and gets the program to start. The symbol table classes are located in the symboltable package and the semantic action classes are located in the semanticaction package. When the parser object is created in main(), two boolean fields are passed in. The first one determines if the parse output will be printed and the second one determines if the TVI code output will be printed. Both are currently set to true, so that all of the output can be seen when the program is run. Set the respective boolean value parameter for the parser object to false to turn off debug mode for either the parser or TVI code. 

The text files for the parse table and the grammar table are located in the parser package and read in in the Parser class. The file path names read in the FileReader only include the file package and name (i.e "parser/parsetable.txt", "parser/TVI_Grammar_Augmented.txt") because the working directory includes the greater path (i.e compiler/src). In IntelliJ, the working directory can be manually specified by clicking the right-hand Main dropdown menu and clicking Edit Configurations.  

To run the program with a test file, I again go to the right-hand Main dropdown menu, clicked Edit Configurations, and inputted the file path name in the Program Arguments space. The test files for this phase are located in the "semac-phase4-test" package within the "testfiles" package. The test file path names are specified in section "III. Important Files".

Note: when the program is run with the test file "missing-semicolon.pas" (its path name is testfiles/semac-phase4-test/missing-semicolon.pas), it correctly throws a parser error and reports that there was a problem with input symbol IDENTIFIER on line 10. I just wanted to clarify that the error handler reports this because it was waiting for a semicolon for the last line of code on line 8, but the next thing it received was an IDENTIFIER on line 10 instead. 


---------------------------
V. Changes  
---------------------------
I had to add another overloaded generate() method, because in this phase, we use a version of generate() that takes parameter inputs we hadn't used before. I added a generate method that takes a String, String, and int as input. 
 
I also had to add an addParameter() method to both my ProcedureEntry class and FunctionEntry class. I previously only had methods to access and assign the parameterInfo field in both classes, but in semantic action #21 (implemented in this phase), we have to add a parameter to the parameterInfo field. Thus, I added the addParameter() method as needed. 

I made all the other necessary revisions described in the project specifications. 

 
