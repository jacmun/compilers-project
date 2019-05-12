
README 

---------------------------
I. General Information 
---------------------------
Name: Jacqueline Mun 
Course: Compilers (CMPU 331) 
Assignment: Parser 


---------------------------
II. Assignment Details 
---------------------------
The purpose of this assignment was to write a parser that takes an input and makes sure that it syntactically follows the rules of a language. The parser calls the Lexical Analyzer from assignment 1 to get the tokens in the input stream. The program uses a Vascal RHS Table and a Vascal Parse Table by loading them from disc and storing them in appropriate data structures.  


---------------------------
III. Important Files 
---------------------------
Main.java				Main class 
parser/Parser.java			Parser class 
parser/ParserError.java			Error class for the Parser 
simple.txt 				Sample input 
ult-corrected.txt 			Sample input 

	- auxiliary files for the Lexical Analyzer, which the Parser calls - 
lexer/LexicalAnalyzer.java 		Lexical Analyzer class 
lexer/Token.java			Token class
lexer/LexicalError.java 		Lexer Error class 


---------------------------
IV. Running the Code 
---------------------------
I used the IntelliJ IDEA to run the code. Open the "compiler" folder in IntelliJ. The Main.java class creates an instance of the parser and gets it to start. To run the parser with a test file, I went to the right-hand Main dropdown menu, clicked Edit Configurations, and inputted the file name in the Program Arguments space. 


---------------------------
V. Changes  
---------------------------
My lexical analyzer was printing tokens in my functions - I did this for testing and forgot to change it. I got rid of all the unnecessary print statements and changed the lexer so that the getNextToken() method returns a token and the token is displayed in Main.main(). I also fixed the way I was handling malformed numeric constants, which was an issue that was brought up in the grading of the first assignment.  


---------------------------
VI. Problems 
---------------------------
I wrote a panicMode() method to handle and recover from errors. However, I wasn't able to fully identify if it was working correctly, so I don't call it in my parser. I think that an error recovery method that has the possibility of working incorrectly might cause more panic than reassurance, so the current version of my parser simply reports errors and aborts. Hopefully, I'll be able to confidently call my panicMode() in the next iteration!  


