/**
 * It represents a big number.
 * 
 * @author  Roberto Castillejo Embid.
 * @version 1.0.
 *
 */
public class BigNumber 
{
	private String sign;		//Keep the sign of the number.
	private String value;		//Stores the value of the number.
	
	/**
	 * Default BigNumber constructor.
	 */
	public BigNumber()
	{
		sign = null;
		value = null;
	}
	
	/**
	 * BigNumber constructor with parameters.
	 * 
	 * @param sign		The sign of the number.
	 * @param value		The value of the number.
	 */
	public BigNumber(String sign, String value)
	{
			this.sign = sign;
			this.value = value;
	}
	
	/**
	 * Returns the value of the number.
	 * 
	 * @return		The value of the number.
	 */
	public String getValue()
	{
		return value;
	}
	
	/**
	 * Returns the sign of the number.
	 * 
	 * @return		The sign of the number.
	 */
	public String getSign()
	{
		return sign;
	}
	
	/**
	 * Change the value of the number to another value passed as a parameter.
	 * 
	 * @param value		The new value.
	 */
	public void setValor(String value)
	{
		this.value = value;
	}
	
	/**
	 * Change the sign of the number to another sign passed as a parameter.
	 * 
	 * @param sign		The sign of the number.
	 */
	public void setSigno(String sign)
	{
		this.sign = sign;
	}	
	
	/**
	 * Returns a hashCode value for BigNumber.
	 * 
	 * @return		A hashCode value for BigNumber.
	 */
	@Override
	public int hashCode()
	{
		return 31 * ((sign == null ? 0: sign.hashCode())) + 31 * ((value == null ? 0 : value.hashCode()));
	}
	
	/**
	 * Returns true if two BigNumber objects are equal.
	 * 
	 * @param o		A BigNumber object.
	 * @return 		True if both objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o)
	{
		if(o ==  this){
			return true;
		}
		
		if(o == null){
			return false;
		}
		
		if(!(o instanceof BigNumber)){
			return false;
		}
		else{
			BigNumber ng = (BigNumber) o;
			return ng.sign.equals(sign) && ng.value.equals(value);
		}
	}
	
	/**
	 * Returns a String representation of BigNumber.
	 * 
	 * @return		A String representation of BigNumber.
	 */
	@Override
	public String toString()
	{
		StringBuffer buff = new StringBuffer();
		
		buff.append("BigNumber: [");
		buff.append(sign);
		buff.append(", ");
		buff.append(value);
		buff.append("]");
		
		return buff.toString();
	}
}
