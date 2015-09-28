package mrui.custom.jmrui2xml;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class FeatureTextField extends JTextField {

	/**
	 * TextField that only accepts ints as input
	 * 
	 * @Victor
	 */
	private static final long serialVersionUID = 1L;

	final static String badchars = "`~!@#$%^&*()_+=\\|\"':;?/><, []{}";

	public FeatureTextField() {
		super(new FixedLengthPlainDocument(5), "", 5);
	}

	public FeatureTextField(String string) {
		super(new FixedLengthPlainDocument(3), string, 3);
	}

	public FeatureTextField(String string, int num) {
		super(new FixedLengthPlainDocument(num), string, num);
	}

	public void processKeyEvent(KeyEvent ev) {

		char c = ev.getKeyChar();

		if ((Character.isLetter(c) && !ev.isAltDown())
				|| badchars.indexOf(c) > -1) {
			ev.consume();
			return;
		} else
			super.processKeyEvent(ev);

	}
}
