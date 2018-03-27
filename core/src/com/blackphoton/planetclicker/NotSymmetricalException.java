package com.blackphoton.planetclicker;
/**
 * Thrown to indicate that a shape, texture or other sort is not symmetrical, as in it's width and height are not the same
 * Applications can subclass this class to indicate similar exceptions.
 */
public class NotSymmetricalException extends RuntimeException {
	private static final long serialVersionUID = 392478193487014L;
	/**
	 * Constructs a <code>NotSymmetricalException</code> with no
	 * detail message.
	 */
	public NotSymmetricalException() {
		super();
	}

	/**
	 * Constructs an <code>NotSymmetricalException</code> with the
	 * specified detail message.
	 *
	 * @param   s   the detail message.
	 */
	public NotSymmetricalException(String s) {
		super(s);
	}
}
