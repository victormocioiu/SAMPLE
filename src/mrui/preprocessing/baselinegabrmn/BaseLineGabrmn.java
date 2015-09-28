package mrui.preprocessing.baselinegabrmn;

import mrui.Mrui;
import mrui.plugin.PluginInfo;
import mrui.plugin.preprocessing.PreprocessingPlugin;
import mrui.preprocessing.settozero.PPM2Point;
import mrui.preprocessing.settozero.Utils;
import mrui.utils.BatchTokenizer;
import fft.Fft;

public class BaseLineGabrmn extends PreprocessingPlugin implements Runnable {
	int iNumberOfRegions;
	double[] daLower = new double[2];

	double[] daUpper = new double[2];

	double rangeOfFrequencies;
	double maxPPM, minPPM;

	public BaseLineGabrmn(Mrui mrui, PluginInfo pluginInfo) {
		super(mrui, pluginInfo);
		Utils u = new Utils(mrui);
		rangeOfFrequencies = u.getRangeOfFrequencies();
		maxPPM = u.getMaxPPM();
		minPPM = u.getMinPPM();

	}

	@Override
	protected void histo() {
		mrui.addHistoryState(getShortName());
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

		double[] BCFReal = new double[iNumberOfRegions];
		double[] BCFImag = new double[iNumberOfRegions];
		double RealFactor, ImagFactor;
		RealFactor = ImagFactor = 0;

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
		
		
		
		int begin = 0;
		int end = 0;

		for (int iRegion = 0; iRegion < iNumberOfRegions; iRegion++) {

			try {
				begin = PPM2Point.convert(daLower[iRegion], maxPPM, minPPM,
						mrui.getData().getLength());
				end = PPM2Point.convert(daUpper[iRegion], maxPPM, minPPM, mrui
						.getData().getLength());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = begin; i < end; i++) {
				BCFReal[iRegion] += fft[REAL][i];
				BCFImag[iRegion] += fft[IMAG][i];
			}
			BCFReal[iRegion] /= (end - begin + 1);
			BCFImag[iRegion] /= (end - begin + 1);
		}

		for (int i = 0; i < iNumberOfRegions; i++) {
			RealFactor += BCFReal[i];
			ImagFactor += BCFImag[i];
		}
		RealFactor /= iNumberOfRegions;
		ImagFactor /= iNumberOfRegions;
		// add.to.each
		for (int i = 0; i < mrui.getData().getLength(); i++) {
			fft[REAL][i] = fft[REAL][i] - RealFactor;
			fft[IMAG][i] = fft[IMAG][i] - ImagFactor;
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

	@Override
	public void run() {

	}
}
