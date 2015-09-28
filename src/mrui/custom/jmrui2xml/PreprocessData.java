package mrui.custom.jmrui2xml;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mrui.Mrui;
import mrui.mode1d.Mode1DWinImpl;
import mrui.preprocessing.settozero.Utils;

public class PreprocessData {

	public static void Prepo(ExportUI theView, Model theModel, Mrui mrui)
			throws NumberFormatException, Exception {

		Utils u = new Utils(mrui);
		// final long startTime = System.nanoTime();
		// setRef
		if (theView.chbSetRef.isSelected())
			theModel.setReference(theView.getSetReference());
		// final long refEnd = System.nanoTime();
		// System.out.println("RefTime: " + ((startTime - refEnd) / 1000000000)
		// + " s");
		// apo
		if (theView.chbApo.isSelected())
			theModel.setApodization(theView.getApodizationType(),
					theView.getApodizationValue());
//		final long apoEnd = System.nanoTime();
//		System.out.println("ApoTime: " + ((refEnd - apoEnd) / 1000000000) + " s");
		// HLSVD
		if (theView.chbHLSVD.isSelected()) {
			if (theView.chbHLSVD2.isSelected()) {
				if (theView.chbHLSVD3.isSelected()) {
					theModel.setFilterParams(3, theView.getNrLorenziansValue(),
							theView.getAnyIntervalNumber(theView.txtHLSVDMin1,
									theView.txtHLSVDMax1), theView
									.getAnyIntervalNumber(theView.txtHLSVDMin2,
											theView.txtHLSVDMax2), theView
									.getAnyIntervalNumber(theView.txtHLSVDMin3,
											theView.txtHLSVDMax3));
				} else {

					theModel.setFilterParams(2, theView.getNrLorenziansValue(),
							theView.getAnyIntervalNumber(theView.txtHLSVDMin1,
									theView.txtHLSVDMax1), theView
									.getAnyIntervalNumber(theView.txtHLSVDMin2,
											theView.txtHLSVDMax2));
				}

			} else {
				theModel.setFilterParams(1, theView.getNrLorenziansValue(),
						theView.getAnyIntervalNumber(theView.txtHLSVDMin1,
								theView.txtHLSVDMax1));

			}
		}
//		final long hlsvdEnd = System.nanoTime();
//		System.out.println("HLSVDTime: " + ((apoEnd - hlsvdEnd) / 1000000000)
//				+ " s");

		// BaselineCorrection - taking into account certain intervals
		if (theView.chbBaseCor.isSelected()) {
			if (theView.chbBaseCor2.isSelected()) {
				if (theView.chbBaseCor3.isSelected()) {

					if (theView.getAnyIntervalNumber(theView.txtBaseCorMin1,
							theView.txtBaseCorMax1)[0] < u.getMinPPM()
							|| theView.getAnyIntervalNumber(
									theView.txtBaseCorMin1,
									theView.txtBaseCorMax1)[1] > u.getMaxPPM()
							|| theView.getAnyIntervalNumber(
									theView.txtBaseCorMin2,
									theView.txtBaseCorMax2)[0] < u.getMinPPM()
							|| theView.getAnyIntervalNumber(
									theView.txtBaseCorMin2,
									theView.txtBaseCorMax2)[1] > u.getMaxPPM()
							|| theView.getAnyIntervalNumber(
									theView.txtBaseCorMin3,
									theView.txtBaseCorMax3)[0] < u.getMinPPM()
							|| theView.getAnyIntervalNumber(
									theView.txtBaseCorMin3,
									theView.txtBaseCorMax3)[1] > u.getMaxPPM()) {
						throw new Exception(
								"Please check the Baseline Correction field. At least one of the values is out of bounds");
					}

					theModel.setBaselineCorrection(3, theView
							.getAnyIntervalNumber(theView.txtBaseCorMin1,
									theView.txtBaseCorMax1), theView
							.getAnyIntervalNumber(theView.txtBaseCorMin2,
									theView.txtBaseCorMax2), theView
							.getAnyIntervalNumber(theView.txtBaseCorMin3,
									theView.txtBaseCorMax3));

				} else {
					if (theView.getAnyIntervalNumber(theView.txtBaseCorMin1,
							theView.txtBaseCorMax1)[0] < u.getMinPPM()
							|| theView.getAnyIntervalNumber(
									theView.txtBaseCorMin1,
									theView.txtBaseCorMax1)[1] > u.getMaxPPM()
							|| theView.getAnyIntervalNumber(
									theView.txtBaseCorMin2,
									theView.txtBaseCorMax2)[0] < u.getMinPPM()
							|| theView.getAnyIntervalNumber(
									theView.txtBaseCorMin2,
									theView.txtBaseCorMax2)[1] > u.getMaxPPM()) {
						throw new Exception(
								"Please check the Baseline Correction field. At least one of the values is out of bounds");
					}

					theModel.setBaselineCorrection(2, theView
							.getAnyIntervalNumber(theView.txtBaseCorMin1,
									theView.txtBaseCorMax1), theView
							.getAnyIntervalNumber(theView.txtBaseCorMin2,
									theView.txtBaseCorMax2));

				}

			} else {
				if (theView.getAnyIntervalNumber(theView.txtBaseCorMin1,
						theView.txtBaseCorMax1)[0] < u.getMinPPM()
						|| theView.getAnyIntervalNumber(theView.txtBaseCorMin1,
								theView.txtBaseCorMax1)[1] > u.getMaxPPM()) {
					throw new Exception(
							"Please check the Baseline Correction field. At least one of the values is out of bounds");
				}
				theModel.setBaselineCorrection(1, theView.getAnyIntervalNumber(
						theView.txtBaseCorMin1, theView.txtBaseCorMax1));

			}
		}

//		final long baseCorEnd = System.nanoTime();
//		System.out.println("BCTime: " + ((hlsvdEnd - baseCorEnd) / 1000000000)
//				+ " s");

		// Custom nr. Points
		if (theView.chbNrPoints.isSelected()) {
			theModel.setCustomNrOfPoints(theView.getAnyIntervalNumber(
					theView.txtNrPointsMin, theView.txtNrPointsMax)[0], theView
					.getAnyIntervalNumber(theView.txtNrPointsMin,
							theView.txtNrPointsMax)[1], Integer
					.valueOf(theView.txtNrPoints.getText()));
		}

//		final long customPointsEnd = System.nanoTime();
//		System.out.println("CustomPointsTime: "
//				+ ((baseCorEnd - customPointsEnd) / 1000000000) + " s");
		// SetToZero
		if (theView.chbSetToZero.isSelected()) {

			if (theView.chbSetToZero2.isSelected()) {

				theModel.setTOZero(2, theView.getAnyIntervalNumber(
						theView.txtSetToZeroMin1, theView.txtSetToZeroMax1),
						theView.getAnyIntervalNumber(theView.txtSetToZeroMin2,
								theView.txtSetToZeroMax2));

			} else {

				theModel.setTOZero(1, theView.getAnyIntervalNumber(
						theView.txtSetToZeroMin1, theView.txtSetToZeroMax1));

			}
		}
//		final long stzEnd = System.nanoTime();
//		System.out.println("StZTime: " + ((customPointsEnd - stzEnd) / 1000000000)
//				+ " s");
		// Normalize
		if (theView.chbUL.isSelected()) {
			theModel.setUnitLengthNorm();
		}
//		final long normEnd = System.nanoTime();
//		System.out.println("NormTime: " + ((stzEnd - normEnd) / 1000000000)
//				+ " s");
		// Alignment correction

		if (theView.chbAlignCor.isSelected()) {
			if (theView.chbAlignCor2.isSelected()) {
				if (theView.chbAlignCor3.isSelected()) {
					if (theView.chbIsBrainH.isSelected()) {

						theView.txtSTDMax
								.setText(String.valueOf(u.getMinPPM()));
						theView.txtSTDMax.paintImmediately(theView.txtSTDMax
								.getVisibleRect());
						

					}
					theModel.setAlignCor(theView.chbIsBrainH.isSelected(),
							Double.valueOf(theView.txtAlignCorSNR.getText()),
							theView.getAnyIntervalNumber(theView.txtSTDMin,
									theView.txtSTDMax)[0], theView
									.getAnyIntervalNumber(theView.txtSTDMin,
											theView.txtSTDMax)[1], Double
									.valueOf(theView.txtAlignCor1.getText()),
							Double.valueOf(theView.txtAlignCor2.getText()),
							Double.valueOf(theView.txtAlignCor3.getText()));
					if (theModel.getAlignPeak().isEmpty()) {
						JOptionPane
								.showMessageDialog(theView,
										"Cannot perform alignment.Spectra is left untouched");

					} else {

						JOptionPane.showMessageDialog(theView,
								"Alignment performed according to the "
										+ theModel.getAlignPeak() + " peak");
					}

				} else {
					if (theView.chbIsBrainH.isSelected()) {
						theView.txtSTDMax
								.setText(String.valueOf(u.getMinPPM()));
					}
					theModel.setAlignCor(theView.chbIsBrainH.isSelected(),
							Double.valueOf(theView.txtAlignCorSNR.getText()),
							theView.getAnyIntervalNumber(theView.txtSTDMin,
									theView.txtSTDMax)[0], theView
									.getAnyIntervalNumber(theView.txtSTDMin,
											theView.txtSTDMax)[1], Double
									.valueOf(theView.txtAlignCor1.getText()),
							Double.valueOf(theView.txtAlignCor2.getText()));
					if (theModel.getAlignPeak().isEmpty()) {
						JOptionPane
								.showMessageDialog(theView,
										"Cannot perform alignment.Spectra is left untouched");

					} else {

						JOptionPane.showMessageDialog(theView,
								"Alignment performed according to the "
										+ theModel.getAlignPeak() + " peak");
					}

				}

			} else {
				if (theView.chbIsBrainH.isSelected()) {
					theView.txtSTDMax.setText(String.valueOf(u.getMinPPM()));
				}
				theModel.setAlignCor(theView.chbIsBrainH.isSelected(), Double
						.valueOf(theView.txtAlignCorSNR.getText()), theView
						.getAnyIntervalNumber(theView.txtSTDMin,
								theView.txtSTDMax)[0], theView
						.getAnyIntervalNumber(theView.txtSTDMin,
								theView.txtSTDMax)[1], Double
						.valueOf(theView.txtAlignCor1.getText()));
				if (theModel.getAlignPeak().isEmpty()) {
					JOptionPane
							.showMessageDialog(theView,
									"Cannot perform alignment.Spectra is left untouched");

				} else {

					JOptionPane.showMessageDialog(
							theView,
							"Alignment performed according to the "
									+ theModel.getAlignPeak() + " peak");
				}

			}
		}

		// final long alignCorEnd = System.nanoTime();
//		System.out.println("AlignTime: " + (normEnd - alignCorEnd) / 100000000
//				+ " s");
//
		// System.out.println("Total Time: " + (startTime - alignCorEnd)
		// / 1000000000 + " s");
		((Mode1DWinImpl) mrui.getMainWindow().get1DWindow()).graph
				.initialView();

	}

	public static boolean isValid(JTextField tested) {

		if (!tested.equals("")) {

			if (tested.getText().indexOf("-") != -1) {
				if (tested.getText().indexOf("-") != 0) {
					return false;
				}
			}

			if (tested.getText().indexOf(".") != -1) {
				if (tested.getText().indexOf(".") == 0
						|| tested.getText().indexOf(".") > 2) {

					return false;
				}
			}

			return true;
		} else {
			return false;
		}

	}
}
