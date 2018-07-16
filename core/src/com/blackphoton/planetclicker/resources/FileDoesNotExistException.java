package com.blackphoton.planetclicker.resources;
/**
 * Thrown to indicate the file attempting to be accessed cannot be accessed
 */
public class FileDoesNotExistException extends RuntimeException {
	private static final long serialVersionUID = 392478193487015L;
	/**
	 * Constructs a <code>FileDoesNotExistException</code> with no
	 * detail message.
	 */
	public FileDoesNotExistException() {
		super();
	}

	/**
	 * Constructs an <code>FileDoesNotExistException</code> with the
	 * specified detail message.
	 *
	 * @param   s   the detail message.
	 */
	public FileDoesNotExistException(String s) {
		super(s);
	}
}
