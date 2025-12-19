import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This represents the program's input file manager. Input files include the help file and the 
 * file the user creates with the input data.
 * 
 * @author  Roberto Castillejo Embid.
 * @version 1.0.
 * 
 */
public class InputManager 
{
	private String helpFile;		//Saves the path to the help file.
	private String inputFile;		//Saves the input file name.
	private BigNumber[] values;		//Saves an array with the values ​​from the input file.
	private Logger log;				//Saves a Logger object.
	private int numberOfValues;		//Ensures that the number of allowed values ​​is not exceeded.
	
	/**
	 * Default constructor of InputManager.
	 */
	public InputManager()
	{
		helpFile = "help/help.txt";
		inputFile = null;
		log = new Logger();
		numberOfValues = 0;
		values = new BigNumber[2];
	}
	
	/**
	 * InputManager constructor that receives the input file name as a parameter.
	 *  
	 * @param input		The name of the input file.
	 */
	public InputManager(String input)
	{
		this();	
		inputFile = input;
		readInput(input);
	}
	
	/**
	 * Returns the path to the help file.
	 * 
	 * @return		The path to the help file.
	 */
	public String getHelpFile()
	{
		return helpFile;
	}
	
	/**
	 * Returns the name of the input file.
	 * 
	 * @return		The name of the input file.
	 */
	public String getInputFile()
	{
		return inputFile;
	}
	
	/**
	 * Returns an array of big numbers with the values ​​of the problem.
	 * 
	 * @return		An array of big numbers with the values ​​of the problem.
	 */
	public BigNumber[] getValues()
	{
		return values;
	}
	
	/**
	 * Returns the number of values.
	 * 
	 * @return		The number of values.
	 */
	public int getNumberOfValues()
	{
		return numberOfValues;
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
	 * Change the path of the help file to another one passed by parameter.
	 * 
	 * @param newPath		The new path to the help file.
	 */
	public void setHelpFile(String newPath)
	{
		helpFile = newPath;
	}
	
	/**
	 * Change the name of the input file to another one passed as a parameter.
	 * 
	 * @param newInputFile		The name of the input file.
	 */
	public void setInputFile(String newInputFile)
	{
		inputFile = newInputFile;
	}
	
	/**
	 * Replace the array with the problem values ​​with another array of values ​​passed as a parameter.
	 * 
	 * @param newValues		An array with new values.
	 */
	public void setValues(BigNumber[] newValues)
	{
		values = newValues;
	}
	
	/**
	 * Change the number of values ​​in the problem to another value passed as a parameter.
	 * 
	 * @param newNumberOfValues		A new number of values.
	 */
	public void setNumberOfValues(int newNumberOfValues)
	{
		numberOfValues = newNumberOfValues;
	}
	
	/**
	 * Replace a Logger object with another passed as a parameter.
	 * 
	 * @param nLog		A new Logger object.
	 */
	public void setLog(Logger nLog)
	{
		log = nLog;
	}
	
	/**
	 * Print the application help.
	 * 
	 * @param syntaxError		True if it is a syntax error, false otherwise.
	 */
	public void printHelp(boolean syntaxError)
	{
		if(syntaxError){
			String syntaxErr = "Error: The syntax is incorrect.";
			System.out.println(syntaxErr);
			//Writes the event to the log.
			log.writeToLog(syntaxErr);
		}
		readFile(helpFile, true);
	}
	
	/**
	 * Read the application's input file.
	 * 
	 * @param input		The name of the input file.
	 */
	public void readInput(String input)
	{
		readFile(input, false);
	}
	
	/**
	 * This function balances the digits of two big numbers passed 
	 * as a parameter. Zeros are added to the front of the number with 
	 * fewer digits until both numbers have the same number of digits.
	 * 
	 * @param a		A big number.
	 * @param b		A big number.
	 */
	public void balanceBigNumber(BigNumber a, BigNumber b)
	{
		long longA = a.getValue().length();
		long longB = b.getValue().length();
		String lowerValue = null;
		long difference = 0;
		
		if(longA < longB){
			lowerValue = a.getValue();
			difference = longB - longA;
		}
		else{
			lowerValue = b.getValue();
			difference = longA - longB;
		}
		
		StringBuffer buff = new StringBuffer();
		for(int i = 0; i < difference; i++){
			buff.append("0");
		}
		buff.append(lowerValue);
		if(longA < longB){
			a.setValor(buff.toString());
		}
		else{
			b.setValor(buff.toString());
		}
	}
	
	
	/**
	 * Input file reader. The file path is passed to it as a parameter.
	 * 
	 * @param file	 	The input file path.
	 * @param isHelp	True if the help file is read, false if the input file is read.
	 */
	private void readFile(String file, boolean isHelp)
	{
		BufferedReader bfr = null;
		
		File f = new File(file);
		
		if(!f.exists() && !isHelp) {  //Error if input file does not exist
			String fileNotExist = "Error: The file " + file + " does not exist.";
			handleError(fileNotExist);
		}
		
		if(f.exists()) {  //Checks if the file has a .txt extension
			if(!file.contains(".txt")) {
				String fileNotTxt = "Error: The file " + file + " does not have a .txt extension.";
				handleError(fileNotTxt);
			}
		}
		
		try{
			//Opens the file.
			InputStream is = getClass().getResourceAsStream(file);
			bfr = new BufferedReader(new InputStreamReader(is));
			String line = bfr.readLine();
			
			//Reads the file.
			if(line == null) //Is an empty file
			{
				if(isHelp){
					String emptyHelp = "Error: The help file is empty.";
					System.out.println(emptyHelp);
					//Write the event to the log.
					log.writeToLog(emptyHelp);
				}
				else{
					String emptyInput = "Error: The input file is empty.";
					System.out.println(emptyInput);
					//Write the event to the log.
					log.writeToLog(emptyInput);
				}
				printHelp(false);
				System.exit(-1);
			}
			else{
				while(line != null){
					if(isHelp){
						System.out.println(line);
					}
					else{
						if(line.length() > 0){
							String[] aux = line.split(" ");
							handleInputValue(aux);
						}
					}
					//It updated the line in the file.
					line = bfr.readLine();
				}
				
				//It ensures that the array with the fewest elements is set to zeros at the beginning..
				if(!isHelp){
					balanceValues();
					//Limit the digits of a number to 2000. At this point, both numbers have the same
					//number of digits.
					if(values[0].getValue().length() > 2000){ 
						String limitError = "Error: The digit limit for numbers has been exceeded.";
						handleError(limitError);
					}
				}
			}	
		}
		catch(IOException e){
			String fileError = "Error: There was a problem reading the file.";
			handleError(fileError);
		}
		finally{
			//Closes the file.
			try{
				if(bfr != null){
					bfr.close();
				}
			}catch(IOException e){
				String closeError = "Error: There was a problem closing the file.";
				handleError(closeError);
			}
		}
	}
	
	/**
	 * It processes the input of a line read by the input manager to adjust it to the 
	 * requirements of the problem.
	 * 
	 * @param input		A line from the input file.
	 */
	private void handleInputValue(String[] input)
	{
		for(int i = 0; i < input.length; i++){
			//Handling for input file values.
			if(!input[i].equals("")){
				if(numberOfValues < 2){
					saveBigNumbers(input, i);
				}
				else{
					String excessValuesError = "Error: There can only be 2 values in the input file.";
					handleError(excessValuesError);
				}
			}
		}
	}
	
	/**
	 * Save the large numbers that have been read in the input manager array in the correct format.
	 *  
	 * @param input		One line from the input file.
	 * @param index		An integer number.
	 */
	private void saveBigNumbers(String[] input, int index)
	{
		String sign = null;
		String digit = null;
		String[] digtAux = null;
		
		int auxNum = 0;
		if(input[index].startsWith("+") || input[index].startsWith("-")){
			sign = input[index].substring(0,1);
			digit = input[index].substring(1,input[index].length());
		}
		else{
			sign = "+";
			digit = input[index];
			
		}
		digtAux = digit.split("");
		int[] value = new int[digtAux.length];
		try{
			for(int i = 0; i < digtAux.length; i++){
				auxNum = Integer.parseInt(digtAux[i]);
				value[i] = auxNum;
			}
		}
		catch(NumberFormatException e){
			String characterError = "Error: Decimals and special characters are not allowed in the input file.";
			handleError(characterError);
		}
		values[numberOfValues] = new BigNumber(sign, digit);
		numberOfValues++;
	}
	
	/**
	 * Balance the values ​​of large numbers so that they have the same number of digits.
	 */
	private void balanceValues()
	{	
		try{
			int longVal1 = values[0].getValue().length();
			int longVal2 = values[1].getValue().length();
			BigNumber value1 = values[0];
			BigNumber value2 = values[1];
		
			//The lengths of the value of each LargeNumber are compared, and the number with fewer digits is balanced by putting zeros in front.
			if(longVal1 < longVal2){ 
				balanceBigNumber(value1, value2);
			}
			else if(longVal2 < longVal1){
				balanceBigNumber(value2, value1);
			}
		}
		catch(Exception e){
			String fileError = "Error: Invalid input file.";
			handleError(fileError);
		}
	}
	
	/**
	 * Take the necessary actions to inform the user of an error.
	 * 
	 * @param errorName		A String of information about an error.
	 */
	private void handleError(String errorName)
	{
		System.out.println(errorName);
		//Write the event to the log.
		log.writeToLog(errorName);
		printHelp(false);
		System.exit(-1);
	}
}
