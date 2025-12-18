/**
 * Main class of the application.
 * 
 * @author  Roberto Castillejo Embid.
 * @version 1.0.
 *
 */
public class Multiply 
{
	private BigNumbersManager bigNumbersM;		//Save a big number manager.
	private Logger log;							//Save a Logger object.
	
	/**
	 * Constructor for Multiply.
	 */
	public Multiply()
	{
		bigNumbersM = null;
		log = new Logger();
	}
	
	/**
	 * Run the application.
	 * 
	 * @param args		The execution arguments.
	 */
	public static void main(String[] args)
	{
		Multiply mult = new Multiply();
		mult.runMultiply(args);
	}
	
	/**
	 * Returns a BigNumbersManager.
	 * 
	 * @return		A BigNumbersManager.
	 */
	public BigNumbersManager getBigNumbersManager()
	{
		return bigNumbersM;
	}
	
	/**
	 * Returns a Logger object.
	 * 
	 * @return		A Logger object.
	 */
	public Logger getLog()
	{
		return log;
	}
	
	/**
	 * Replace a BigNumbersManager with one passed by a parameter.
	 * 
	 * @param bigNumbersM		A BigNumbersManager.
	 */
	public void setBigNumbersManager(BigNumbersManager bigNumbersM)
	{
		this.bigNumbersM = bigNumbersM;
	}
	
	/**
	 * Replace a Logger object with another passed as a parameter.
	 * 
	 * @param log		A Logger object.
	 */
	public void setLog(Logger log)
	{
		this.log = log;
	}
	
	/**
	 * Run the application according to the set of chosen parameters.
	 * 
	 * Run options:
	 * Case 1: [-h]								Display the help.
	 * Case 2: [input.txt]						Display the solution via console.
	 * Case 3: [-t] [input.txt]					Generate the file with the trace and display the solution on the console.		
	 * Case 4: [-t] [input.txt] [output.txt]	Generate the trace and output files with the solution.
	 * Case 5: [input.txt] [output.txt]			Generate an output file with the solution.
	 * 
	 * @param v		A String array with the execution arguments.
	 */
	public void runMultiply(String[] v)
	{		
		//Case 1: Print the help
		if(v.length == 1 && v[0].equals("-h")){
			manageCase1();
		}
		//Case 2: Print the output to the console
		else if(v.length == 1 && !v[0].equals("-t") && !v[0].equals("-h")){
			manageCase2(v[0]);
		}
		//Case 3: Generate the trace file and print the output to the console
		else if(v.length == 2 && v[0].equals("-t")){
			manageCase3(v[1]);
		}
		//Case 4: Generate the trace file and display the output in a .txt file
		else if(v.length == 3 && v[0].equals("-t")){
			manageCase4(v[1], v[2]);
		}
		//Case 5: Display the output in a .txt file
		else if(v.length == 2 && !v[0].equals("-h")){
			manageCase5(v[0], v[1]);
		}
		//Cases different from the above
		else{
			printHelp(true);
		}
	}
	
	/**
	 * Prints help via console.
	 */
	private void manageCase1()
	{
		printHelp(false);
	}
	
	/**
	 * Print the output to the console.
	 * 
	 * @param input		The name of the input file.
	 */
	private void manageCase2(String input)
	{
		bigNumbersM = new BigNumbersManager(input, false);
		bigNumbersM.generateResult(false, null);
		bigNumbersM.getInputManager().getLog().writeToLog("The console output is displayed.");
	}
	
	/**
	 * Generate the file with the trace and print the result to the console.
	 * 
	 * @param input		The name of the input file.
	 */
	private void manageCase3(String input)
	{
		bigNumbersM = new BigNumbersManager(input, true);
		bigNumbersM.generateResult(false, null);
		bigNumbersM.getInputManager().getLog().writeToLog("The console output is displayed..");
		bigNumbersM.generateTraceFile();
	}
	
	/**
	 * Generates the trace file and displays the output in a .txt file
	 * 
	 * @param input		The name of the input file.
	 * @param output	The name of the output file.
	 */
	private void manageCase4(String input, String output)
	{
		bigNumbersM = new BigNumbersManager(input, true);
		bigNumbersM.generateResult(true, output);
		if(bigNumbersM.getOutputManager().isTxt() == false){
			printHelp(false);
			System.exit(-1);
		}
		bigNumbersM.generateTraceFile();
	}
	
	/**
	 * Display the program's output in a .txt file
	 * 
	 * @param input		The name of the input file.
	 * @param output	The name of the output file.
	 */
	private void manageCase5(String input, String output)
	{
		bigNumbersM = new BigNumbersManager(input,false);
		bigNumbersM.generateResult(true, output);
		if(bigNumbersM.getOutputManager().isTxt() == false){
			printHelp(false);
			System.exit(-1);
		}
	}
	
	/**
	 * Prints help via console.
	 * 
	 * @param syntaxError		True if there is a syntax error, false otherwise.
	 */
	private void printHelp(boolean syntaxError)
	{
		bigNumbersM = new BigNumbersManager();
		bigNumbersM.getInputManager().printHelp(syntaxError);
	}
}
