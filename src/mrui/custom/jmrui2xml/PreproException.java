package mrui.custom.jmrui2xml;

public class PreproException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of PreproException with the given message */

	public PreproException(String stage) {
		super(
				"Please check out the "
						+ stage
						+ " field in the XML file. There seems to be a problem with it.");
	}

	public PreproException(int number, String stage) {
		super(
				"Please check out the "
						+ stage
						+ " field. Are you sure it is correctly filled? If you do not wish to apply this stage to your signal(s) then please deselect it");
	}

}
