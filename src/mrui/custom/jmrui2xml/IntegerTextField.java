package mrui.custom.jmrui2xml;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class IntegerTextField extends JTextField {

	/**
	 * TextField that only accepts ints and the character '.' as input
	 * 
	 * @Victor
	 */
	private static final long serialVersionUID = 1L;

	final static String badchars = "`~!@#$%^&*()_+=\\|\"':;?/>-<, ";

	public IntegerTextField(String string) {
		super(new FixedLengthPlainDocument(3), string, 3);
	}

	public IntegerTextField(String string, int num) {
		super(new FixedLengthPlainDocument(num), string, num);
	}

	public void processKeyEvent(KeyEvent ev) {

		char c = ev.getKeyChar();

		if ((Character.isLetter(c) && !ev.isAltDown())
				|| badchars.indexOf(c) > -1) {
			ev.consume();
			return;
		}
		// if (c == '.' && getDocument().getLength() != 1) //this piece of code
		// may allow you o put a character only on a designated position
		// ev.consume();
		else
			super.processKeyEvent(ev);

	}
}

class FixedLengthPlainDocument extends PlainDocument {

	private int maxlength;

	// This creates a Plain Document with a maximum length
	// called maxlength.
	FixedLengthPlainDocument(int maxlength) {
		this.maxlength = maxlength;
	}

	// This is the method used to insert a string to a Plain Document.
	public void insertString(int offset, String str, AttributeSet a)
			throws BadLocationException {

		// If the current length of the string
		// + the length of the string about to be entered
		// (through typing or copy and paste)
		// is less than the maximum length passed as an argument..
		// We call the Plain Document method insertString.
		// If it isn't, the string is not entered.

		if (!((getLength() + str.length()) > maxlength)) {
			super.insertString(offset, str, a);
		}
	}
}