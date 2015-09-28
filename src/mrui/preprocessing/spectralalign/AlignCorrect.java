package mrui.preprocessing.spectralalign;

/**
 * Class that tries to align spectra according to some given peaks .
 * 
 * @author Victor
 * 
 */
public class AlignCorrect {

	protected Integer correction;
	protected String name;

	public AlignCorrect() {

	}

	public AlignCorrect(boolean isBrainH, double minPPM, double maxPPM,
			double minSNR, double lowPPMUsedForSTD, double highPPMUsedForSTD,
			double[] peaks, double[] fft) {

		toCorrectOrNot(isBrainH, minPPM, maxPPM, minSNR, lowPPMUsedForSTD,
				highPPMUsedForSTD, peaks, fft);

	}

	/**
	 * Decides whether correction can be performed or not. The result can be
	 * found out by calling getCorrection()
	 * 
	 */
	private void toCorrectOrNot(boolean isBrainH, double minPPM, double maxPPM,
			double minSNR, double lowPPMUsedForSTD, double highPPMUsedForSTD,
			double[] peaks, double[] fft) {
		int nrOfPeaks = peaks.length;
		if (isBrainH == false) {
			if (nrOfPeaks == 1) {
				AlignObject firstPeak = new AlignObject(peaks[0], maxPPM,
						minPPM, fft, lowPPMUsedForSTD, highPPMUsedForSTD,
						minSNR, "First");
				if (firstPeak.isValid())
					setCorrection(firstPeak.getCorrection());
				setName(firstPeak.getName());

			}

			if (nrOfPeaks == 2) {
				AlignObject firstPeak = new AlignObject(peaks[0], maxPPM,
						minPPM, fft, lowPPMUsedForSTD, highPPMUsedForSTD,
						minSNR, "First");

				AlignObject secondPeak = new AlignObject(peaks[1], maxPPM,
						minPPM, fft, lowPPMUsedForSTD, highPPMUsedForSTD,
						minSNR, "Second");

				if (firstPeak.isValid() && secondPeak.isValid()) {

					if (firstPeak.getSnr() > secondPeak.getSnr()) {
						setCorrection(firstPeak.getCorrection());
						setName(firstPeak.getName());
					} else {
						setCorrection(secondPeak.getCorrection());
						setName(secondPeak.getName());
					}

				} else {
					if (firstPeak.isValid()) {
						setCorrection(firstPeak.getCorrection());
						setName(firstPeak.getName());

					}
					if (secondPeak.isValid()) {
						setCorrection(secondPeak.getCorrection());
						setName(secondPeak.getName());
					}
				}
			}

			if (nrOfPeaks == 3) {
				AlignObject firstPeak = new AlignObject(peaks[0], maxPPM,
						minPPM, fft, lowPPMUsedForSTD, highPPMUsedForSTD,
						minSNR, "First");

				AlignObject secondPeak = new AlignObject(peaks[1], maxPPM,
						minPPM, fft, lowPPMUsedForSTD, highPPMUsedForSTD,
						minSNR, "Second");

				AlignObject thirdPeak = new AlignObject(peaks[2], maxPPM,
						minPPM, fft, lowPPMUsedForSTD, highPPMUsedForSTD,
						minSNR, "Third");

				if (firstPeak.isValid() && secondPeak.isValid()
						&& thirdPeak.isValid()) {
					if (firstPeak.getSnr() > secondPeak.getSnr()) {
						if (firstPeak.getSnr() > thirdPeak.getSnr()) {
							setCorrection(firstPeak.getCorrection());
							setName(firstPeak.getName());
						} else {
							setCorrection(thirdPeak.getCorrection());
							setName(thirdPeak.getName());
						}
					} else {
						if (secondPeak.getSnr() > thirdPeak.getSnr()) {
							setCorrection(secondPeak.getCorrection());
							setName(secondPeak.getName());
						} else {
							setCorrection(thirdPeak.getCorrection());
							setName(thirdPeak.getName());
						}

					}
				} else {
					if (firstPeak.isValid() && secondPeak.isValid()) {
						if (firstPeak.getSnr() > secondPeak.getSnr()) {
							setCorrection(firstPeak.getCorrection());
							setName(firstPeak.getName());
						} else {
							setCorrection(secondPeak.getCorrection());
							setName(secondPeak.getName());
						}
					} else {
						if (firstPeak.isValid() && thirdPeak.isValid()) {
							if (firstPeak.getSnr() > thirdPeak.getSnr()) {
								setCorrection(firstPeak.getCorrection());
								setName(firstPeak.getName());
							} else {
								setCorrection(thirdPeak.getCorrection());
								setName(thirdPeak.getName());
							}
						} else {
							if (secondPeak.isValid() && thirdPeak.isValid()) {
								if (secondPeak.getSnr() > thirdPeak.getSnr()) {
									setCorrection(secondPeak.getCorrection());
									setName(secondPeak.getName());
								} else {
									setCorrection(thirdPeak.getCorrection());
									setName(thirdPeak.getName());
								}
							} else {
								if (firstPeak.isValid())
									setCorrection(firstPeak.getCorrection());
								setName(firstPeak.getName());
								if (secondPeak.isValid())
									setCorrection(secondPeak.getCorrection());
								setName(secondPeak.getName());
								if (thirdPeak.isValid())
									setCorrection(thirdPeak.getCorrection());
								setName(thirdPeak.getName());
							}

						}
					}
				}

			}
		} else {// specific for brain proton MRS
				// read help file...if it exists...if not mail the jMRUI ppl

			double multCreatinaColina = 3;
			double multCreatinaLipids = 10;
			double multColinaLipids = 10;

			AlignObject creatina = new AlignObject(peaks[0], maxPPM, minPPM,
					fft, lowPPMUsedForSTD, highPPMUsedForSTD, minSNR,
					"Creatine");

			AlignObject colina = new AlignObject(peaks[1], maxPPM, minPPM, fft,
					lowPPMUsedForSTD, highPPMUsedForSTD, minSNR, "Coline");

			AlignObject lipids = new AlignObject(peaks[2], maxPPM, minPPM, fft,
					lowPPMUsedForSTD, highPPMUsedForSTD, minSNR, "Lipids");

			if (creatina.isValid() && correction == null) {
				if (colina.isValid() && correction == null) {
					if (creatina.getHeight() * multCreatinaColina < colina
							.getHeight())
						setCorrection(colina.getCorrection());
					setName(colina.getName());

				}

				if (lipids.isValid() && correction == null) {
					if (creatina.getHeight() * multCreatinaLipids < lipids
							.getHeight())
						setCorrection(lipids.getCorrection());
					setName(lipids.getName());
				}

				if (correction == null)
					setCorrection(creatina.getCorrection());
				setName(creatina.getName());
			}

			if (colina.isValid() && correction == null) {
				if (lipids.isValid() && correction == null) {
					if (colina.getHeight() * multColinaLipids < lipids
							.getHeight())
						setCorrection(lipids.getCorrection());
					setName(lipids.getName());
				}

				if (correction == null)
					setCorrection(colina.getCorrection());
				setName(colina.getName());
			}

			if (lipids.isValid() && correction == null) {
				setCorrection(lipids.getCorrection());
				setName(lipids.getName());
			}

		}

	}

	/**
	 * Shifts the spectrum to the left or to the right,according to correction;
	 * 
	 * @param correction
	 * @param fft
	 * @return fft
	 */
	public double[] shift(Integer correction, double[] fft) {

		if (!correction.equals(null))// let's play it safe,who knows what kind
										// of dev will use this later on
		{
			if (correction < 0) {// shift to the left -> higher index
				correction = -correction;
				
				double[] temp = new double[fft.length];
				
				
				for (int i = 0; i < fft.length - correction; i++) {
					temp[i] = fft[i + correction];
				}
				for (int i = fft.length - correction + 1; i < fft.length; i++) {
					temp[i] = 0;
				}
				fft = temp;
				return fft;

			} else {
				if (correction > 0) {// shift max to the right -> lower index
					
					double[] temp = new double[fft.length];
					
					
					for (int i = fft.length-1; i> correction-1;i--){
						temp[i-correction] = fft[i];
					}
					
					for (int i = 0; i<correction;i++){
						temp[i] = 0;
					}
					
					fft = temp;
					return fft;
				}
			}

		}

		return fft;

	}

	/**
	 * 
	 * @return number of points to correct for or null if peaks don't have
	 *         enough SNR
	 */
	public Integer getCorrection() {
		return correction;
	}

	public void setCorrection(Integer correction) {
		this.correction = correction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
