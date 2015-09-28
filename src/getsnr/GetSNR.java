package mrui.preprocessing.getsnr;

import java.util.ArrayList;

import fft.Fft;
import mrui.Mrui;
import mrui.plugin.PluginInfo;
import mrui.plugin.custom.CustomPlugin;
import mrui.plugin.preprocessing.PreprocessingPlugin;
import mrui.preprocessing.settozero.PPM2Point;
import mrui.preprocessing.settozero.Utils;
import mrui.preprocessing.spectralalign.AlignObject;
import mrui.preprocessing.spectralalign.MaxValAndIndex;
import mrui.utils.BatchTokenizer;

public class GetSNR extends PreprocessingPlugin {
	int temp = -1;
	double maxPPM, minPPM;
	private double lowPPMUsedForSTD;
	private double highPPMUsedForSTD;
	public ArrayList<Double> SNR4EaxhVoxel = new ArrayList<Double>(mrui
			.getData().getDataWidthForMrsi()
			* mrui.getData().getDataHeightForMrsi());

	public GetSNR(Mrui mrui, PluginInfo pluginInfo) {
		super(mrui, pluginInfo);
		Utils u = new Utils(mrui);

		maxPPM = u.getMaxPPM();
		minPPM = u.getMinPPM();

	}

	@Override
	protected void histo() {
		mrui.addHistoryState(getShortName());
	}

	@Override
	public boolean batchPerform(BatchTokenizer commandTokenizer) {

		lowPPMUsedForSTD = commandTokenizer.getNextDoubleToken();
		highPPMUsedForSTD = commandTokenizer.getNextDoubleToken();

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

		double[][] fft = mrui.getData().getInitialFFT(fidIndex);
		AlignObject max = new AlignObject(fft[REAL], lowPPMUsedForSTD,
				highPPMUsedForSTD, minPPM, maxPPM);
		if (temp != fidIndex) {
			SNR4EaxhVoxel.add(max.getSnr());
			temp = fidIndex;
		}

		return true;
	}
}
