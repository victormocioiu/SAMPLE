package mrui.custom.jmrui2xml;

import fft.Fft;
import mrui.Mrui;
import mrui.plugin.PluginInfo;
import mrui.plugin.PluginManager;
import mrui.preprocessing.apodize.ApodizePrepro;
import mrui.preprocessing.baselinegabrmn.BaseLineGabrmn;
import mrui.preprocessing.interp.Interpolate;
import mrui.preprocessing.settozero.PPM2Point;
import mrui.preprocessing.settozero.SetToZero;
import mrui.preprocessing.settozero.Utils;
import mrui.preprocessing.spectralalign.SAlign;
import mrui.preprocessing.svdfilter.SvdFilterPrepro;
import mrui.preprocessing.unitlengthnormalisation.ULNorm;
import mrui.preprocessing.zerofilling.ZeroFillingPrepro;
import mrui.utils.BatchTokenizer;

public class Model {

	Mrui mrui;
	PluginInfo pluginInfo;
	double rangeOfFrequencies;

	String alignPeak;
	private Utils u;

	public Model(Mrui mrui, PluginInfo pluginInfo) {
		this.mrui = mrui;
		this.pluginInfo = pluginInfo;
		
		this.u = new Utils(mrui);
		rangeOfFrequencies = u.getRangeOfFrequencies();

	}

	public void setReference(double newReferncePPM) {

		mrui.getData().setHzRef(
				(mrui.getData().ppmref - newReferncePPM)
						* ((mrui.getData().getTransmitterFrequency() / Math
								.pow(10, 6))));

	}

	public void setApodization(String type, double value) {

		ApodizePrepro apo = new ApodizePrepro(mrui, pluginInfo);
		String macro = type + " " + Double.toString(value);
		BatchTokenizer commandTokenizer = new BatchTokenizer(macro);
		apo.batchPerform(commandTokenizer);
	}

	public void setFilterParams(int regions, int nrLore, double[]... minMax) {

		double[] lowPPM = new double[regions];
		double[] highPPM = new double[regions];
		int index = 0;
		for (double[] interval : minMax) {
			lowPPM[index] = interval[0];
			highPPM[index] = interval[1];
			index++;
		}

		SvdFilterPrepro svd = new SvdFilterPrepro(mrui, pluginInfo);
		String macro = "";
		int rowHankel, nrPointsSVD;

		if (mrui.getData().getLength() <= 2048) {
			rowHankel = mrui.getData().getLength() / 2;

		} else {
			rowHankel = 1024;
		}

		if (mrui.getData().getLength() <= 2048) {
			nrPointsSVD = mrui.getData().getLength();

		} else {
			nrPointsSVD = 2048;
		}

		switch (regions) {
		case 1:
			macro = String.format("%s %s %d %d %d %d %f %f %d %f %f",
					"Lanczos", "NoTotalLS", nrLore, rowHankel, nrPointsSVD,
					mrui.getData().getLength(), 0.0, -1000.0, regions,
					(lowPPM[0] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6),
					(highPPM[0] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6));
			break;
		case 2:
			macro = String.format("%s %s %d %d %d %d %f %f %d %f %f %f %f",
					"Lanczos", "NoTotalLS", nrLore, rowHankel, nrPointsSVD,
					mrui.getData().getLength(), 0.0, -1000.0, regions,
					(lowPPM[0] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6),
					(highPPM[0] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6),
					(lowPPM[1] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6),
					(highPPM[1] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6));
			break;
		case 3:
			macro = String.format(
					"%s %s %d %d %d %d %f %f %d %f %f %f %f %f %f", "Lanczos",
					"NoTotalLS", nrLore, rowHankel, nrPointsSVD, mrui.getData()
							.getLength(), 0.0, -1000.0, regions,
					(lowPPM[0] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6),
					(highPPM[0] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6),
					(lowPPM[1] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6),
					(highPPM[1] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6),
					(lowPPM[2] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6),
					(highPPM[2] + mrui.getData().ppmref)
							* (mrui.getData().getTransmitterFrequency() / 1e6));
			break;
		}
		BatchTokenizer commandTokenizer = new BatchTokenizer(macro);
		svd.batchPerform(commandTokenizer);

	}

	public void setBaselineCorrection(int regions, double[]... minMax) {
		double[] lowPPM = new double[regions];
		double[] highPPM = new double[regions];
		int index = 0;
		for (double[] interval : minMax) {
			lowPPM[index] = interval[0];
			highPPM[index] = interval[1];
			index++;
		}
		BaseLineGabrmn base = new BaseLineGabrmn(mrui, pluginInfo);
		String macro = "";
		switch (regions) {
		case 1:
			macro = String.format("%d %f %f", regions, lowPPM[0], highPPM[0]);
			break;
		case 2:
			macro = String.format("%d %f %f %f %f", regions, lowPPM[0],
					highPPM[0], lowPPM[1], highPPM[1]);
			break;
		case 3:
			macro = String.format(" %d %f %f %f %f %f %f", regions, lowPPM[0],
					highPPM[0], lowPPM[1], highPPM[1], lowPPM[2], highPPM[2]);
			break;
		}
		BatchTokenizer commandTokenizer = new BatchTokenizer(macro);
		base.batchPerform(commandTokenizer);

		double[][] fid = mrui.getData().getSignal(1);
		double[][] fft;

		fft = Fft.getInstance().direct_1D_Estim_fft(fid, true);

	}

	public void setTOZero(int regions, double[]... minMax) {
		double[] lowPPM = new double[regions];
		double[] highPPM = new double[regions];
		int index = 0;
		for (double[] interval : minMax) {
			lowPPM[index] = interval[0];
			highPPM[index] = interval[1];
			index++;
		}
		SetToZero stz = new SetToZero(mrui, pluginInfo);
		String macro = "";
		switch (regions) {
		case 1:
			macro = String.format("%d %f %f", regions, lowPPM[0], highPPM[0]);
			break;
		case 2:
			macro = String.format("%d %f %f %f %f", regions, lowPPM[0],
					highPPM[0], lowPPM[1], highPPM[1]);
			break;

		}
		BatchTokenizer commandTokenizer = new BatchTokenizer(macro);
		stz.batchPerform(commandTokenizer);
		double[][] fid = mrui.getData().getSignal(1);
		double[][] fft;

		fft = Fft.getInstance().direct_1D_Estim_fft(fid, true);

	}

	public void setCustomNrOfPoints(double lowPPM, double highPPM,
			int nbOfPoints) throws Exception {

		int point1 = PPM2Point.convert(lowPPM, u.getMaxPPM(), u.getMinPPM(),
				mrui.getData().getLength());
		int point2 = PPM2Point.convert(highPPM, u.getMaxPPM(), u.getMinPPM(),
				mrui.getData().getLength());

		// later czech the index of
		double multiplicationFactor = 1.0 * nbOfPoints
				/ Math.abs(point2 - point1);

		if (multiplicationFactor > 1) {
			ZeroFillingPrepro zf = new ZeroFillingPrepro(mrui, pluginInfo);
			int temp = (int) (multiplicationFactor * mrui.getData().getLength() - mrui
					.getData().getLength());
			BatchTokenizer commandTokenizer = new BatchTokenizer(
					String.valueOf(temp));
			zf.batchPerform(commandTokenizer);

		} else if (multiplicationFactor < 1) {
			Interpolate inter = new Interpolate(mrui, pluginInfo);
			String macro = String.format("%d %f %f", nbOfPoints, lowPPM,
					highPPM);
			BatchTokenizer commandTokenizer = new BatchTokenizer(macro);
			inter.batchPerform(commandTokenizer);

		} else {
			// do nothing ; :P

		}

		double[][] fid = mrui.getData().getSignal(1);
		double[][] fft;

		fft = Fft.getInstance().direct_1D_Estim_fft(fid, true);

	}

	public void setUnitLengthNorm() {
		ULNorm uln = new ULNorm(mrui, pluginInfo);
		String macro = "";
		BatchTokenizer commandTokenizer = new BatchTokenizer(macro);
		uln.batchPerform(commandTokenizer);
		double[][] fid = mrui.getData().getSignal(1);
		double[][] fft;

		fft = Fft.getInstance().direct_1D_Estim_fft(fid, true);

	}

	public void setAlignCor(boolean isBrainH, double minSNR,
			double lowPPMUsedForSTD, double highPPMUsedForSTD, double... peaks) {
		int count = peaks.length;
		if (isBrainH) {
			lowPPMUsedForSTD = u.getMinPPM();
		}

		SAlign sa = new SAlign(mrui, pluginInfo);
		String macro = "";

		switch (count) {
		case 1:
			macro = String.format("%B %.2f %.2f %.2f %.2f ", isBrainH, minSNR,
					lowPPMUsedForSTD, highPPMUsedForSTD, peaks[0]);
			break;

		case 2:
			macro = String.format("%B %.2f %.2f %.2f %.2f %.2f", isBrainH,
					minSNR, lowPPMUsedForSTD, highPPMUsedForSTD, peaks[0],
					peaks[1]);
			break;

		case 3:
			macro = String.format("%B %.2f %.2f %.2f %.2f %.2f %.2f", isBrainH,
					minSNR, lowPPMUsedForSTD, highPPMUsedForSTD, peaks[0],
					peaks[1], peaks[2]);
			break;
		}
		BatchTokenizer commandTokenizer = new BatchTokenizer(macro);
		sa.batchPerform(commandTokenizer);

		setAlignPeak(sa.getPeakUsedForCorrection());
		double[][] fid = mrui.getData().getSignal(1);
		double[][] fft;

		fft = Fft.getInstance().direct_1D_Estim_fft(fid, true);

	}

	public String getAlignPeak() {
		return alignPeak;
	}

	public void setAlignPeak(String alignPeak) {
		this.alignPeak = alignPeak;
	}

	public void singleLineApodization() {
		String apodize = "APODIZE Lorentzian 5.0";
		if (PluginManager.executeBatch(mrui, apodize))
			System.out.println("we are ready");
	}
}
