package mrui.preprocessing.settozero;

import mrui.Mrui;

public class PPM2Point {

	double maxPPM, minPPM;
	int lengthOfSignal;

	public PPM2Point(double maxPPM, double minPPM, int lengthOfSignal) {
		super();
		this.maxPPM = maxPPM;
		this.minPPM = minPPM;
		this.lengthOfSignal = lengthOfSignal;
	}

	public PPM2Point(Mrui mrui) {
		Utils u = new Utils(mrui);
		maxPPM = u.getMaxPPM();
		minPPM = u.getMinPPM();
		this.lengthOfSignal = mrui.getData().getLength();
	}

	// public PPM2Point() {
	// }

	/**
	 * 
	 * @param PPM
	 * @param maxPPM
	 * @param minPPM
	 * @param lengthOfSignal
	 * @return
	 * @throws Exception
	 */
	public static int convertExport(double PPM, double maxPPM, double minPPM,
			int lengthOfSignal) throws Exception {

		if (PPM > maxPPM || PPM < minPPM) {
			throw new Exception("PPM out of range");

		}

		double ratio = (maxPPM - minPPM) / lengthOfSignal;// how many ppms
															// are in the
															// interval
															// divided by
															// the number of
															// points,right?
		int point = (int) Math.floor((PPM - minPPM) / ratio);
		// int point = (int) Math.rint(PPM / ratio);
		if (point > (lengthOfSignal - 1) || point < 0) {
			throw new Exception(
					"Contact support,Charlie!!! NOW!!!Problems when exporting. FATAL 3RROR!!!");

		}

		return point;
	}

	public static int convert(double PPM, double maxPPM, double minPPM,
			int lengthOfSignal) throws Exception {

		if (PPM > maxPPM || PPM < minPPM) {
			throw new Exception("PPM out of range");

		}

		double ratio = (maxPPM - minPPM) / lengthOfSignal;// how many ppms
															// are in the
															// interval
															// divided by
															// the number of
															// points,right?
		int point = (int) Math.rint((PPM - minPPM) / ratio);
		if (point > (lengthOfSignal - 1) || point < 0) {
			throw new Exception("Contact support,Charlie!!! NOW!!!");

		}

		return point;
	}

	/**
	 * Converts a value in ppm to a vector index (int). Not recommended because
	 * maxPPM,minPPM or (less likely) lengthOfSignal might change since you
	 * first instantiated the class
	 * 
	 * @param PPM
	 * @return index
	 * @throws Exception
	 */
	public int convert(double PPM) throws Exception {
		// double ratio = (maxPPM - minPPM) / lengthOfSignal;
		// int point = (int) Math.rint((PPM - minPPM) / ratio);
		//
		// return point - 1;

		return convert(PPM, maxPPM, minPPM, lengthOfSignal);
	}

}
