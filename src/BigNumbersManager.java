import java.util.ArrayList;

/**
 * It represents a Big Numbers manager with the operations associated with these.
 * 
 * @author  Roberto Castillejo Embid.
 * @version 1.0.
 *
 */
public class BigNumbersManager 
{
	private InputManager inputManager;		//Saves an InputManager.
	private OutputManager outputManager;	//Saves an OutputManager.
	private ArrayList<String> trace;		//Save the problem trace.
	private boolean isTrace;				//If it's a true trace, false otherwise.
	
	/**
	 * Default constructor of LargeNumberManager.
	 */
	public BigNumbersManager()
	{
		trace =  null;
		isTrace = false;
		inputManager = new InputManager();
		outputManager = null;
	}
	
	/**
	 * BigNumbersManager constructor that receives input parameters.
	 * 
	 * @param input		The name of the input file.
	 * @param isTrace	True if it is the trace, false otherwise.
	 */
	public BigNumbersManager(String input, boolean isTrace)
	{
		trace = new ArrayList<String>();
		this.isTrace = isTrace;
		inputManager = new InputManager(input);
		outputManager = new OutputManager();
	}
	
	/**
	 * Returns an InputManager object.
	 * 
	 * @return		An InputManager object.
	 */
	public InputManager getInputManager()
	{
		return inputManager;
	}
	
	/**
	 * Returns an OutputManager object.
	 * 
	 * @return		An OutputManager object.
	 */
	public OutputManager getOutputManager()
	{
		return outputManager;
	}
	
	/**
	 * Returns an ArrayList<String> with the trace of the problem.
	 * 
	 * @return		An ArrayList<String> with the trace of the problem.
	 */
	public ArrayList<String> getTrace()
	{
		return trace;
	}
	
	/**
	 * Returns whether or not the trace needs to be generated.
	 * 
	 * @return		True if the trace needs to be generated, false otherwise.
	 */
	public boolean isTrace()
	{
		return isTrace;
	}
	
	/**
	 * Changes an InputManager object to another one passed by parameter.
	 * 
	 * @param newInputManager		An InputManager object.
	 */
	public void setInputManager(InputManager newInputManager)
	{
		inputManager = newInputManager;
	}
	
	/**
	 * Changes an OutputManager object to another one passed by parameter.
	 * 
	 * @param newOutputManager		An OutputManager object.
	 */
	public void setOutputManager(OutputManager newOutputManager)
	{
		outputManager = newOutputManager;
	}
	
	/**
	 * Replaces an ArrayList<String> containing the problem trace with another 
	 * ArrayList<String> passed as a parameter.
	 * 
	 * @param newTrace		An ArrayList<String> object
	 */
	public void setTrace(ArrayList<String> newTrace)
	{
		trace = newTrace;
	}
	
	/**
	 * Changes the value of the variable that controls the construction of the trace 
	 * to another value passed as a parameter.
	 * 	
	 * @param isTrace		True to generate the trace, false otherwise.
	 */
	public void setIsTrace(boolean isTrace)
	{
		this.isTrace = isTrace;
	}
	
	/**
	 * Print the result to the console or generate the output file with the solution to the problem.
	 * 
	 * @param isInputFile		True if the output file is to be generated, false otherwise.
	 * @param file				The file name if you are generating the output file, null otherwise.
	 */
	public void generateResult(boolean isInputFile, String file)
	{
		BigNumber[] values = inputManager.getValues();
		String result = multiply(values[0], values[1]);
		if(!isInputFile){
			System.out.println(result);
		}
		else{
			outputManager.writeFile(file, result, null, false);
		}
	}
	
	/**
	 * Generate a file with the problem trace.
	 */
	public void generateTraceFile()
	{
		outputManager.writeFile(null, null, trace, true);
	}
	
	
	/**
	 * Multiply two big numbers.
	 * 
	 * @param a		A big number.
	 * @param b		A big number.
	 * @return		The result of multiplying the big numbers a and b.
	 */
	private String multiply(BigNumber a, BigNumber b)
	{
		//Adds the numbers to be multiplied to the trace
		addToTrace(a.getSign() + a.getValue() + " * " + b.getSign() + b.getValue(), isTrace);
		addToTrace("\n", isTrace);
		String result = null;
		String sign = null;
		String valueA = removeNonSignificanZeros(a.getValue());
		String valueB = removeNonSignificanZeros(b.getValue());
		
		//If a, b = 0 or the values ​​of a and b after removing the non-significant zeros are 0, then the result is 0 and the sign is positive.
		if(a.getValue().equals("0") || b.getValue().equals("0") || valueA.equals("0") || valueB.equals("0")){
			result = "0";
			sign = "+";
		}
		else{
			result = multiplyIntegers(a, b);
			result = removeNonSignificanZeros(result);
			sign = determineSign(a, b);
		}

		if(sign.equals("+")){
			addToTrace("\nResult: " + result, isTrace);
			return result;
		}
		else{
			addToTrace("\nResult: " + sign + result, isTrace);
			return sign + result;
		}
	}
	
	/**
	 * Multiply two large numbers without adding the multiplication sign to the result.
	 * 
	 * @param a		A big number.
	 * @param b		A big number.
	 * @return		The result of multiplying a and b without the sign.
	 */
	private String multiplyIntegers(BigNumber a, BigNumber b)
	{
		int n = maximum(a, b);
		int s = 0;
		BigNumber al = new BigNumber();
		BigNumber ar = new BigNumber();
		BigNumber bl = new BigNumber();
		BigNumber br = new BigNumber();
		BigNumber r = new BigNumber();
		BigNumber p = new BigNumber();
		BigNumber q = new BigNumber();
		
		//Base case
		if(n <= 9){ //To avoid overflow, the largest number cannot exceed 9 digits.
			return classicMultiply(a, b);
		}
		else{
			//Problem decomposition.
			s = n / 2;
			if(a.getValue().length() % 2 == 0){ //It has an even number of digits.
				al.setValor(a.getValue().substring(0, s)); 						//Left half of the value a
				ar.setValor(a.getValue().substring(s, a.getValue().length()));	//Right half of the value a
				bl.setValor(b.getValue().substring(0, s));						//Left half of the b value
				br.setValor(b.getValue().substring(s, b.getValue().length()));	//Right half of the b value
			}
			else{	//It has an odd number of digits.
				al.setValor(a.getValue().substring(0, s + 1)); 						//Left half of the value a
				ar.setValor(a.getValue().substring(s + 1, a.getValue().length()));	//Right half of the value a
				bl.setValor(b.getValue().substring(0, s + 1));						//Left half of the b value
				br.setValor(b.getValue().substring(s + 1, b.getValue().length()));	//Right half of the b value
			}
			//Add to the trace the value of s, al, ar, bl and br
			addToTrace("-----------------------------------", isTrace);
			addToTrace("Value of al: " + al.getValue(), isTrace);
			addToTrace("Value of ar: " + ar.getValue(), isTrace);
			addToTrace("Value of bl: " + bl.getValue(), isTrace);
			addToTrace("Value of br: " + br.getValue(), isTrace);

			//Recursive call.
			p.setValor(multiplyIntegers(al, bl));
			q.setValor(multiplyIntegers(ar, br));
			BigNumber alAr = new BigNumber(null, addBigNumbers(al, ar));
			BigNumber blBr = new BigNumber(null, addBigNumbers(bl, br));
			balanceNumbers(alAr, blBr);	//Balance the number of digits in both numbers.
			r.setValor(multiplyIntegers(alAr, blBr));
			//Add the values ​​of p, q, r, alAr and blBr to the trace
			addToTrace("Value of p es: " + p.getValue(), isTrace);
			addToTrace("Value of q es: " + q.getValue(), isTrace);
			addToTrace("Value of al + ar es: " + alAr.getValue(), isTrace);
			addToTrace("Value of bl + br es: " + blBr.getValue(), isTrace);
			addToTrace("Value of r es: " + r.getValue(), isTrace);
			
			//Combine
			//p * 10^2s
			BigNumber auxNum = new BigNumber();
			String tenPow2s = addZeros(2*s);
			auxNum.setValor(p.getValue().concat(tenPow2s));
			//(r-p-q) * 10^s
			BigNumber auxNum2 = new BigNumber();
			auxNum2.setValor(substractBigNumbers(r, p));
			auxNum2.setValor(substractBigNumbers(auxNum2, q));
			String substractPQ = auxNum2.getValue();	//Save the value of (r-p-q)
			String tenPowS = addZeros(s);
			auxNum2.setValor(auxNum2.getValue().concat(tenPowS));	
			//Sums
			String partialResult = addBigNumbers(auxNum, auxNum2);
			BigNumber auxNum3 = new BigNumber(null, partialResult);
			String result = addBigNumbers(auxNum3, q);
			
			addToTrace("Partial: " + p.getValue() + " * 1" + tenPow2s + " + " + substractPQ + 
					" * 1" + tenPowS + " + " + q.getValue() + "\n", isTrace);
			
			return result;
		}
		
	}
	
	/**
	 * Returns the length of the number with more digits than those passed as a parameter.
	 * 
	 * @param a		A big number.
	 * @param b		A big number.
	 * @return		The length of the number with the most digits.
	 */
	private int maximum(BigNumber a, BigNumber b)
	{
		int longA = a.getValue().length();
		int longB = b.getValue().length();
		
		if(longA >= longB){
			return a.getValue().length();
		}
		else{
			return b.getValue().length();	//It is the case longA < longB
		}
	}
	
	/**
	 * Multiply two large numbers passed as a parameter and return a String with the result of the multiplication.
	 * 
	 * @param a		A big number.
	 * @param b		A big number.
	 * @return		The result of multiplying two large numbers.
	 */
	private String classicMultiply(BigNumber a, BigNumber b)
	{
		Long numA = Long.parseLong(a.getValue());
		Long numB = Long.parseLong(b.getValue());
		Long result = numA * numB;
		
		return new String(result.toString());
	}
	
	/**
	 * Add the values ​​of two large numbers that have the same number of digits in their value.
	 * 
	 * @param a		A big number.
	 * @param b		A big number.
	 * @return		The sum of two large numbers.
	 */
	private String addBigNumbers(BigNumber a, BigNumber b)
	{
		BigNumber nA = a;
		BigNumber nB = b;
		balanceNumbers(nA, nB);
		String numA = a.getValue();
		String numB = b.getValue();
		String res = null;
		Integer n = 0;
		int carry = 0;
		int index = 0;
		
		for(int i = 0; i < numA.length(); i++){
			int partialA = Integer.parseInt(numA.substring(numA.length()- (i + 1), numA.length() - i));
			int partialB = Integer.parseInt(numB.substring(numB.length()- (i + 1), numB.length() - i));
			n = carry + partialA + partialB;
			carry = 0;
			if(n > 9){
				if(index < numA.length() - 1){
					carry += 1;
					n = n - 10;
				}
			}
			if(res == null){
				res = n.toString();
			}
			else{
				res = n.toString() + res;
			}
			index++;
		}
		
		return res;
	}
	
	/**
	 * The carry function balances the lengths of the numbers passed as a parameter so that they both 
	 * have the same length. Zeros are added to the left of the smaller value until it has the same 
	 * length as the larger of the two.
	 * 
	 * @param a		A big number.
	 * @param b		A big number.
	 */
	private void balanceNumbers(BigNumber a, BigNumber b)
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
	 * Subtract two large numbers passed as a parameter.
	 * 
	 * @param a		A big number.
	 * @param b		A big number.
	 * @return		The result of subtracting both numbers.
	 */
	private String substractBigNumbers(BigNumber a, BigNumber b)
	{
		BigNumber nA = a;
		BigNumber nB = b;
		balanceNumbers(nA, nB);
		String valueA = a.getValue();
		String valueB = b.getValue();
		String higher = null;
		String smaller = null;
		int length = a.getValue().length();
		String res = null;
		Integer n = 0;
		int carry = 0;
		
		//lengthArrange the values ​​in order from greatest to least.
		for(int i = 0; i < length; i++){
			if(higher == null && smaller == null){
				if(Integer.parseInt(valueA.substring(i, i + 1)) >= Integer.parseInt(valueB.substring(i, i + 1))){
					higher = valueA;
					smaller = valueB;
				}
				else if(Integer.parseInt(valueA.substring(i, i + 1)) < Integer.parseInt(valueB.substring(i, i + 1))){
					higher = valueB;
					smaller = valueA;
				}
			}
		}		
		
		//Proceed with the subtraction.
		for(int i = 0; i < length; i++){
			int partialHigher = Integer.parseInt(higher.substring(higher.length()- (i + 1), higher.length() - i));
			int partialSmaller = Integer.parseInt(smaller.substring(smaller.length()- (i + 1), smaller.length() - i));
			int aux = 0;

			partialSmaller += carry;
			if(partialHigher >= partialSmaller){
				n = partialHigher - partialSmaller;
				carry = 0;
			}
			else{
				aux = partialHigher + 10;
				n = aux - partialSmaller;
				carry = 1;
			}
			
			if(res == null){
				res = n.toString();
			}
			else{
				res = n.toString() + res;
			}
		}
		
		return res;
	}
	
	/**
	 * Returns a string consisting of as many zeros as the value we put in the parameter n.
	 * 
	 * @param n		An Integer number.
	 * @return		A string made up of as many zeros as we specify in the parameter n.
	 */
	private String addZeros(int n)
	{
		String aux = null;
		
		for(int i = 0; i < n; i++){
			if(aux == null){
				aux = "0";
			}
			else{
				aux = aux.concat("0");
			}
		}
		
		return aux;
	}
	
	/**
	 * Determine the sign of a multiplication of big numbers.
	 * 
	 * @param a		A big number.
	 * @param b		A big number.
	 * @return		The multiplication sign of a and b.
	 */
	private String determineSign(BigNumber a, BigNumber b)
	{
		String signA = a.getSign();
		String signB = b.getSign();
		
		if(signA.equals("+") && signB.equals("+") || signA.equals("-") && signB.equals("-")){
			return "+";
		}
		else{
			return "-";
		}
	}
	
	/**
	 * Add a line of text to the trace.
	 * 
	 * @param line		A string of characters.
	 * @param isTrace	True if it is the trace, false otherwise.
	 */
	private void addToTrace(String line, boolean isTrace)
	{
		if(isTrace){
			trace.add(line);
		}
	}
	
	/**
	 * Remove non-significant zeros from the number or leave a zero if all the digits in the number were zeros.
	 * 
	 * @param v		A value.
	 * @return		The value without the non-significant zeros, or a zero if all the digits were zeros.
	 */
	private String removeNonSignificanZeros(String v)
	{
		String aux = null;
		int limit = 0;
		
		for(int i = 0; i < v.length() && aux == null; i++){
			if(Integer.parseInt(v.substring(i, i + 1)) != 0){
				limit = i;
				aux = v.substring(limit, v.length());
			}
			else if(i == v.length() - 1 && Integer.parseInt(v.substring(v.length() - 1, v.length())) == 0){
				aux = "0";
			}
		}
		
		return aux;
	}
}
