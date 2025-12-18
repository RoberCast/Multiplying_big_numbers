import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This represents the program's output file manager. The output files are the program trace and the program's output.
 * 
 * @author  Roberto Castillejo Embid.
 * @version 1.0.
 * 
 */
public class OutputManager 
{
	private Logger log;		//Save a Logger object.
	private boolean isTxt;	//Checks if a file has the .txt extension
	
	/**
	 * Constructor of OutputManager.
	 */
	public OutputManager()
	{
		log = new Logger();
		isTxt = false;
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
	 * Returns whether the input file has the .txt extension
	 * 
	 * @return		True if it has a .txt extension, false otherwise.
	 */
	public boolean isTxt()
	{
		return isTxt;
	}
	
	/**
	 * Replace a Logger object with another passed as a parameter.
	 * 
	 * @param nLog		A Logger object.
	 */
	public void setLog(Logger nLog)
	{
		log = nLog;
	}
	
	/**
	 * Change if an output file has the .txt extension to another value passed as a parameter.
	 * 
	 * @param isTxt		A new value indicating whether it is a .txt extension or not.
	 */
	public void setTxt(boolean isTxt)
	{
		this.isTxt = isTxt;
	}
	
	/**
	 * Generates a file containing the problem trace or its solution. The parameter passing is as follows:
	 * 
	 * Output file: The file name and the problem result are passed. The remaining parameters are v = null and isTrace = false.
	 * 
	 * Trace file: The first two parameters are file = null and result = null. The remaining parameters are an ArrayList<String> with the trace content and isTrace = true.
	 * 
	 * @param file			The name of the output file.
	 * @param result		The result of the problem.
	 * @param v				An ArrayList<String> containing the trace content.
	 * @param isTrace		True if it is the trace file or false otherwise.
	 */
	public void writeFile(String file, String result, ArrayList<String> v, boolean isTrace)
	{
		LocalDateTime dateNow = LocalDateTime.now();
		String dateAndTime = dateNow.getYear() + "_" + dateNow.getMonthValue() + "_" + dateNow.getDayOfMonth() + "_" + dateNow.getHour() + "_" + dateNow.getMinute() + "_" + dateNow.getSecond();
		String traceName = dateAndTime + ".txt";
		File newFile = null;
		FileWriter out = null;
		PrintWriter pw = null;
		String success = "Information: File " + "\"" + file + "\"" + " successfully generated.";
		String fileExists = "Error: The file " + "\"" + file + "\"" + " already exists.";
		String traceSuccess = "Information: Trace file successfully generated.";
		
		if(isTrace){
			newFile = new File(traceName);
		}
		else{
			newFile = new File(file);
		}
		
		try{
			if(isTrace){   //Code for trace output.
				out = new FileWriter(newFile);
				pw = new PrintWriter(out);
				//Write the result to the file.
				for(int i = 0; i < v.size(); i++){
					pw.println(v.get(i));
				}
				//It shows the user that the writing process was successful..
				System.out.println();
				System.out.println(traceSuccess);
				//Write the event to the log.
				log.writeToLog(traceSuccess);
			}
			else{    //Code for the output file with the solution to the problem.
				if(file.contains(".txt")){ //It is a .txt file
					isTxt = true;
					if(!newFile.exists()){
						out = new FileWriter(newFile);
						pw = new PrintWriter(out);
						//Write the result to the file.
						pw.println(result);
						//It shows the user that the writing process was successful..
						System.out.println();
						System.out.println(success);
						//Write the event to the log.
						log.writeToLog(success);
					}
					else{ //The output file already exists.
						System.out.println();
						System.out.println(fileExists);
						//Write the event to the log.
						log.writeToLog(fileExists);
						System.exit(-1);
					}
				}
				else{
					String notTxt = "Error: The output file must be a text file with the .txt extension.";
					System.out.println(notTxt);
					//Write the event to the log.
					log.writeToLog(notTxt);
				}
			}
		}
		catch(Exception e){
			if(isTrace){
				String traceError = "Error: There was a problem generating the trace file.";
				System.out.println(traceError);
				//Write the event to the log
				log.equals(traceError);
			}
			else{
				String outputError = "Error: There was a problem generating the output file: " + file;
				System.out.println(outputError);
				//Write the event to the log
				log.equals(outputError);
			}
		}
		finally{
			try{
				//It ensure the file is closed
				if(out != null){
					out.close();
				}
			}
			catch(Exception e){
				if(isTrace){
					String closeTraceError = "Error: There was a problem closing the trace file."; 
					System.out.println(closeTraceError);
					//Write the event to the log.
					log.writeToLog(closeTraceError);
				}
				else{
					String closeOutputError = "Error: There was a problem closing the output file: " + file;
					System.out.println(closeOutputError);
					//Write the event to the log.
					log.writeToLog(closeOutputError);
				}
			}
		}
	}
}
