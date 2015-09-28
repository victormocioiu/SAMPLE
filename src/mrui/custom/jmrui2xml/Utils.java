package mrui.custom.jmrui2xml;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

class Utils {

	public Utils() {

	}

	/**
	 * Returns a list of doubles - [MinPPM MaxPPM], where MinPPM is the smaller
	 * value
	 * 
	 * @return the values in the corresponding text fields
	 */
	public static List<Double> getInterval(JTextField text, JTextField text2)
			throws NumberFormatException {

		List<Double> interval = new ArrayList<Double>();

		if (Double.parseDouble(text.getText()) < Double.parseDouble(text2
				.getText())) {
			// add smaller element first
			interval.add(Double.parseDouble(text.getText()));
			interval.add(Double.parseDouble(text2.getText()));
		} else {
			// add smaller element first
			interval.add(Double.parseDouble(text2.getText()));
			interval.add(Double.parseDouble(text.getText()));
		}

		return interval;

	}

}