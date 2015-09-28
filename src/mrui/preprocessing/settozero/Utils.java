package mrui.preprocessing.settozero;

import java.math.BigDecimal;
import java.math.RoundingMode;

import mrui.Mrui;

public class Utils {

	Mrui mrui;
	double rangeOfFrequencies;
	double maxPPM, minPPM;

	public Utils(Mrui mrui) {
		super();
		this.mrui = mrui;
	}

	public double getRangeOfFrequencies() {
		rangeOfFrequencies = 1 / (2 * BigDecimal
				.valueOf(mrui.getData().getStepTime())
				.setScale(2, RoundingMode.HALF_UP).doubleValue() * Math.pow(10,
				-3));
		return rangeOfFrequencies;
	}

	public double getMaxPPM() {
		maxPPM = BigDecimal
				.valueOf(
						-mrui.getData().ppmref
								+ ((getRangeOfFrequencies()) * 1e6 / mrui
										.getData().getTransmitterFrequency()))
				.setScale(2, RoundingMode.HALF_UP).doubleValue();

		return maxPPM;
	}

	public double getMinPPM() {
		minPPM = BigDecimal
				.valueOf(
						-mrui.getData().ppmref
								- ((getRangeOfFrequencies()) * 1e6 / mrui
										.getData().getTransmitterFrequency()))
				.setScale(2, RoundingMode.HALF_UP).doubleValue();
		return minPPM;
	}

}
