package mrui.preprocessing.spectralalign;

public class MaxValAndIndex {
	double max;
	int index;

	/**
	 * Helper class. helps the code be more readable and, of course, returns the
	 * maximum for a given interval and the index.
	 * 
	 * @param peakIndex
	 * @param fft
	 */
	public MaxValAndIndex(int peakIndex, double[] fft) {
		double max = 0;
		int index = 0;
		if (peakIndex - 10 < 0 || peakIndex + 10 > fft.length) {
			if (peakIndex - 10 < 0) {//to the right extreme
				for (int i = 0; i < peakIndex + 10; i++) {// so I might be
															// searching a
															// few points
															// more..boo hoo
					if (fft[i] > max) {
						max = fft[i];
						index = i;
					}
				}
			} else {//to the left extreme
				for (int i = peakIndex - 10; i < fft.length; i++) {
					if (fft[i] > max) {
						max = fft[i];
						index = i;
					}
				}
			}
		} else {//usual case
			for (int i = peakIndex - 10; i < peakIndex + 10; i++) {
				if (fft[i] > max) {
					max = fft[i];
					index = i;
				}
			}
		}

		setMax(max);
		setIndex(index);
	}

	public MaxValAndIndex(double[] fft) {
		double max = 0;
		int index = 0;

		for (int i = 0; i < fft.length; i++) {
			if (fft[i] > max) {
				max = fft[i];
				index = i;
			}

		}
		setMax(max);
		setIndex(index);

	}

	public double getMax() {
		/**
		 * 
		 */
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public int getIndex() {
		/**
		 * returns the vector index of the maximum value
		 */
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
