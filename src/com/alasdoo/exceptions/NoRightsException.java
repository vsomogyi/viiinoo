package com.alasdoo.exceptions;

/**
 *  Exception occures when user have no rigth for some operation
 * @author Vinofil
 *
 */
public class NoRightsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoRightsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoRightsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public NoRightsException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public NoRightsException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
