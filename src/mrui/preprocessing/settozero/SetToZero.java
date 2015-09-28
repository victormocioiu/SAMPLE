package mrui.preprocessing.settozero;

import mrui.Mrui;
import mrui.plugin.PluginInfo;
import mrui.plugin.preprocessing.PreprocessingPlugin;
import mrui.utils.BatchTokenizer;
import fft.Fft;

public class SetToZero extends PreprocessingPlugin {

	int iNumberOfRegions;
	double[] daLower, daUpper;
	double rangeOfFrequencies;
	double maxPPM, minPPM;

	/**
	 * 
	 * Makes values in the given range all equal to zero Batch processing param
	 * description: Example:
	 * 
	 * SetToZero stz = new SetToZero(mrui, pluginInfo); String macro =
	 * "NrRegions lowPPM1 highPPM1 lowPPM2 highPPM2"; BatchTokenizer
	 * commandTokenizer = new BatchTokenizer(macro);
	 * svd.batchPerform(commandTokenizer);
	 * 
	 * @param int - NrRegions - Number selected of regions
	 * @param double - lowPPM - lower PPM of selected range - unit ppm
	 * @param double - highPPM - higher PPM of selected range - unit ppm
	 * 
	 * @version 1
	 */
	public SetToZero(Mrui mrui, PluginInfo pluginInfo) {
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
		iNumberOfRegions = commandTokenizer.getNextIntToken();
		daLower = new double[iNumberOfRegions];
		daUpper = new double[iNumberOfRegions];
		for (int iRegion = 0; iRegion < iNumberOfRegions; iRegion++) {
			daLower[iRegion] = commandTokenizer.getNextDoubleToken();
			daUpper[iRegion] = commandTokenizer.getNextDoubleToken();
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

		for (int iRegion = 0; iRegion < iNumberOfRegions; iRegion++) {

			int begin;
			try {
				begin = PPM2Point.convert(daLower[iRegion], maxPPM, minPPM,
						mrui.getData().getLength());

				int end = PPM2Point.convert(daUpper[iRegion], maxPPM, minPPM,
						mrui.getData().getLength());

				for (int i = begin; i <= end; i++) {
					fft[REAL][i] = 0;
					fft[IMAG][i] = 0;

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
}
