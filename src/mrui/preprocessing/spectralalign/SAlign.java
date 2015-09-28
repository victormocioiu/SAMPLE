package mrui.preprocessing.spectralalign;

import java.util.ArrayList;

import mrui.Mrui;
import mrui.plugin.PluginInfo;
import mrui.plugin.preprocessing.PreprocessingPlugin;
import mrui.preprocessing.settozero.Utils;
import mrui.utils.BatchTokenizer;
import fft.Fft;

public class SAlign extends PreprocessingPlugin {
	boolean isBrainH;
	double[] peaks;
	double rangeOfFrequencies;
	double maxPPM, minPPM;
	double minSNR, lowPPMUsedForSTD, highPPMUsedForSTD;
	AlignCorrect ac;
	ArrayList<Integer> correctedVoxels = new ArrayList<Integer>();
	String name;

	/**
	 * 
	 * Aligns the spectra according to the theoretical position of the peaks the
	 * user inputs Example:
	 * 
	 * SAlign sa = new SAlign(mrui, pluginInfo); String macro =
	 * "isBrainH,minSNR, lowPPMUsedForSTD, highPPMUsedForSTD, FirstPeak, SecondPeak, ThirdPeak"
	 * ; BatchTokenizer commandTokenizer = new BatchTokenizer(macro);
	 * sa.batchPerform(commandTokenizer);
	 * 
	 * @param boolean - is it a brain proton MRS
	 * @param double - SNR - minimum Signal to Noise Ratio
	 * @param double - lowPPMUsedForSTD,highPPMUsedForSTD - interval to be used
	 *        for computing the SNR of the peaks
	 * @param double - FirstPeak, SecondPeak, ThirdPeak - as the name suggests
	 * 
	 * 
	 * @version 1
	 */
	public SAlign(Mrui mrui, PluginInfo pluginInfo) {
		super(mrui, pluginInfo);
		Utils u = new Utils(mrui);
		rangeOfFrequencies = u.getRangeOfFrequencies();
		maxPPM = u.getMaxPPM();
		minPPM = u.getMinPPM();

	}

	@Override
	protected void histo() {
		mrui.addHistoryState(getShortName() + " " + "PARAM");
	}

	@Override
	public boolean batchPerform(BatchTokenizer commandTokenizer) {
		isBrainH = commandTokenizer.getNextBoolToken();
		minSNR = commandTokenizer.getNextDoubleToken();
		lowPPMUsedForSTD = commandTokenizer.getNextDoubleToken();
		highPPMUsedForSTD = commandTokenizer.getNextDoubleToken();
		peaks = new double[commandTokenizer.countTokens()];
		int count = commandTokenizer.countTokens(); // using this to be sure I
													// don't get any strange
													// behavior in the for loop
													// because of .countTokens

		for (int nrPeaks = 0; nrPeaks < count; nrPeaks++) {
			peaks[nrPeaks] = commandTokenizer.getNextDoubleToken();
		}
		return defaultBatch();
	}

	@Override
	protected boolean beginPreprocessing() {

		return true;
	}

	@Override
	public void launch() {
		background();
	}

	@Override
	protected boolean endPreprocessing() {
		return true;
	}

	@Override
	protected boolean preprocessingFid(int fidIndex) {
		Integer correction;
		double[][] fid = mrui.getData().getSignal(fidIndex);

		for (int i = 0; i < fid[REAL].length; i++) {
			if (Double.isNaN(fid[REAL][i])) {
				fid[REAL][i] = 0;
			}

			if (Double.isNaN(fid[IMAG][i])) {
				fid[IMAG][i] = 0;
			}
		}
		double[][] fft;

		fft = Fft.getInstance().direct_1D_Estim_fft(fid, true);
		for (int i = 0; i < fft[REAL].length; i++) {
			if (Double.isNaN(fft[REAL][i])) {
				fft[REAL][i] = 0;
			}

			if (Double.isNaN(fft[IMAG][i])) {
				fft[IMAG][i] = 0;
			}
		}
		// find highest peak in range
		ac = new AlignCorrect(isBrainH, minPPM, maxPPM, minSNR,
				lowPPMUsedForSTD, highPPMUsedForSTD, peaks, fft[REAL]);

		correction = ac.getCorrection();
		if (correction == null) {
			// System.out
			// .println("cannot perform alignment.Spectra is left untouched");
		} else {

			// if (correctedVoxels.isEmpty()) {
			// correctedVoxels.set(0, fidIndex);
			// } else {
			if (!correctedVoxels.contains(fidIndex))
				correctedVoxels.add(fidIndex);
			// }
			name = ac.getName();

			fft[REAL] = ac.shift(correction, fft[REAL]);
		}

		for (int i = 0; i < fft[REAL].length; i++) {
			if (Double.isNaN(fft[REAL][i])) {
				fft[REAL][i] = 0;
			}

			if (Double.isNaN(fft[IMAG][i])) {
				fft[IMAG][i] = 0;
			}
		}

		fid = Fft.getInstance().reverse_1D_Estim_fft(fft, true);

		for (int i = 0; i < fid[REAL].length; i++) {
			if (Double.isNaN(fid[REAL][i])) {
				fid[REAL][i] = 0;
			}

			if (Double.isNaN(fid[IMAG][i])) {
				fid[IMAG][i] = 0;
			}
		}
		mrui.getData().setDataOfSignal(fidIndex, fid);
		// mrui.getMainWindow().get1DWindow().getGraph()
		// .calculatePreprocessingButKeepView(true);

		return true;
	}

	public String getPeakUsedForCorrection() {
		if (!correctedVoxels.isEmpty()) {
			// return correctedVoxels.toString();
			return name;
		} else {
			return "";
		}
	}

}
