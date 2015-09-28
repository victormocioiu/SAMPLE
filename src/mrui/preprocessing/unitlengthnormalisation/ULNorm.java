package mrui.preprocessing.unitlengthnormalisation;

import mrui.Mrui;
import mrui.MruiImpl;
import mrui.mode1d.Mode1DWinImpl;
import mrui.plugin.PluginInfo;
import mrui.plugin.preprocessing.PreprocessingPlugin;
import mrui.utils.BatchTokenizer;
import fft.Fft;

public class ULNorm extends PreprocessingPlugin {

	public ULNorm(Mrui mrui, PluginInfo pluginInfo) {
		super(mrui, pluginInfo);

	}

	@Override
	protected void histo() {
		mrui.addHistoryState(getShortName());
	}

	@Override
	public boolean batchPerform(BatchTokenizer commandTokenizer) {

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
		double normR = 0;

		fft = Fft.getInstance().direct_1D_Estim_fft(fid, true);
		for (int i = 0; i < fft[REAL].length; i++) {
			if (Double.isNaN(fft[REAL][i])) {
				fft[REAL][i] = 0;
			}

			if (Double.isNaN(fft[IMAG][i])) {
				fft[IMAG][i] = 0;
			}
		}

		for (int i = 0; i < fft[REAL].length; i++) {
			normR += Math.pow(fft[REAL][i], 2);

		}
		normR = Math.sqrt(normR);

		for (int i = 0; i < fft[REAL].length; i++) {
			fft[REAL][i] = fft[REAL][i] / normR;
			fft[IMAG][i] = fft[IMAG][i] / normR;
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

		return true;
	}

}
