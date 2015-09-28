package mrui.preprocessing.spectralalign;

import mrui.preprocessing.settozero.PPM2Point;

public class AlignObject {

	private boolean valid;
	private Double snr, height;
	private Integer correction;
	private String name;
	public double minPPM, maxPPM;
	private double voxSNR;

	/**
	 * 
	 * @param peak
	 *            - theoretical value of the peak
	 * @param maxPPM
	 * @param minPPM
	 * @param fft
	 *            - the real part of the spectra
	 * @param lowPPMUsedForSTD
	 * @param highPPMUsedForSTD
	 * @param minSNR
	 *            - minimum SNR ( aka peakSNR has to be higher than this in
	 *            order for the object to be valid
	 */
	public AlignObject(double peak, double maxPPM, double minPPM, double[] fft,
			double lowPPMUsedForSTD, double highPPMUsedForSTD, double minSNR,
			String name) {

		this.setName(name);
		this.minPPM = minPPM;
		this.maxPPM = maxPPM;
		int peakIndex = 0, position;
		double max, peakSNR = 0, voxMax;

		try {
			peakIndex = PPM2Point.convert(peak, maxPPM, minPPM, fft.length);
		} catch (Exception e) {

			e.printStackTrace();
		}

		MaxValAndIndex mvi = new MaxValAndIndex(peakIndex, fft);

		max = mvi.getMax();
		position = mvi.getIndex();
		try {
			peakSNR = max / getSTD(lowPPMUsedForSTD, highPPMUsedForSTD, fft);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		if (peakSNR < minSNR) {
			setValid(false);
		} else {
			setValid(true);
			setSnr(peakSNR);
			setHeight(max);
			setCorrection(peakIndex - position);
		}

	}

	public AlignObject(double[] fft, double lowPPMUsedForSTD,
			double highPPMUsedForSTD, double minPPM, double maxPPM) {
		this.minPPM = minPPM;
		this.maxPPM = maxPPM;
		double max = 0, peakSNR = 0;
		MaxValAndIndex mvi = new MaxValAndIndex(fft);
		max = mvi.getMax();
		try {
			peakSNR = max / getSTD(lowPPMUsedForSTD, highPPMUsedForSTD, fft);
		} catch (Exception e) {

			e.printStackTrace();
		}
		setSnr(peakSNR);

	}

	private void setName(String name) {

		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param lowPPMUsedForSTD
	 * @param highPPMUsedForSTD
	 * @param fft
	 * @return standard deviation for the given interval
	 * @throws Exception
	 */
	private double getSTD(double lowPPMUsedForSTD, double highPPMUsedForSTD,
			double[] fft) throws Exception {
		int begin, end;
		double temp = 0;
		begin = PPM2Point.convert(lowPPMUsedForSTD, maxPPM, minPPM, fft.length);
		end = PPM2Point.convert(highPPMUsedForSTD, maxPPM, minPPM, fft.length);
		double mean = getMean(begin, end, fft);
		for (int i = begin; i != end; i++) {

			temp += Math.pow(fft[i] - mean, 2);

		}
		temp = Math.sqrt(temp / (end - begin + 1));
		return temp;
	}

	/**
	 * 
	 * @param lowPPMUsedForSTD
	 * @param highPPMUsedForSTD
	 * @param fft
	 * @return mean for the given interval;
	 * @throws Exception
	 */
	private double getMean(int begin, int end, double[] fft) throws Exception {
		double temp = 0;

		for (int i = begin; i != end; i++) {
			temp += fft[i];
		}
		temp /= (end - begin + 1);
		return temp;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public double getSnr() {
		return snr;
	}

	public void setSnr(double snr) {
		this.snr = snr;
	}

	public int getCorrection() {
		return correction;
	}

	public void setCorrection(int correction) {
		this.correction = correction;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public double getVoxelSNR() {

		return voxSNR;

	}

	private void setVoxelSNR(double voxSNR) {
		this.voxSNR = voxSNR;
	}

}
