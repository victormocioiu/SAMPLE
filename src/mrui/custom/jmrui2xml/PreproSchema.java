package mrui.custom.jmrui2xml;

import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import mrui.Mrui;
import mrui.preprocessing.settozero.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PreproSchema {

	Element preProcessing;
	org.w3c.dom.Document doc;
	Mrui mrui;
	Utils u;

	public PreproSchema(org.w3c.dom.Document doc, Mrui mrui) {
		this.doc = doc;
		this.mrui = mrui;
		//
	}

	public Document prepare4Save(ExportUI theView, FileOutputStream fw) {
		u = new Utils(mrui);
		this.preProcessing = doc.createElement("Preprocessing");
		try { // ref
			if (theView.chbSetRef.isSelected()) {
				if (!PreprocessData.isValid(theView.txtSetRef)) {
					fw.close();
					throw new PreproException("Set Reference");
				}

				Element setRef = doc.createElement("SetReference");
				Element ppm = doc.createElement("PPM");
				ppm.setTextContent(theView.txtSetRef.getText());

				setRef.appendChild(ppm);
				preProcessing.appendChild(setRef);

			}
			// apo
			if (theView.chbApo.isSelected()) {
				if ((theView.raApoGaus.isSelected() || theView.raApoLore
						.isSelected()) && !theView.txtApo.equals("")) {
					Element apo = doc.createElement("Apodization");
					Element raGauss = doc.createElement("Gaussian");
					Element raLor = doc.createElement("Lorenzian");
					Element hz = doc.createElement("Hz");
					hz.setTextContent(theView.txtApo.getText());
					if (theView.raApoGaus.isSelected()) {
						raGauss.appendChild(hz);
						apo.appendChild(raGauss);
						preProcessing.appendChild(apo);
					} else {
						raLor.appendChild(hz);
						apo.appendChild(raLor);
						preProcessing.appendChild(apo);
					}
				} else {
					fw.close();
					throw new PreproException(1, "Apoidization");
				}
			}
			// hlsvd
			if (theView.chbHLSVD.isSelected()) {
				if (!PreprocessData.isValid(theView.txtHLSVDMin1)
						|| !PreprocessData.isValid(theView.txtHLSVDMax1)) {
					fw.close();
					throw new PreproException("Water Filtering");
				}
				Element hlsvd = doc.createElement("WaterFiltering");
				Element nrLore = doc.createElement("NumberOfLorenzians");
				nrLore.setTextContent(theView.txtHLSVDNrLore.getText());
				hlsvd.appendChild(nrLore);
				Element minPpm1 = doc.createElement("MinPPM");
				Element maxPpm1 = doc.createElement("MaxPPM");
				minPpm1.setTextContent(theView.txtHLSVDMin1.getText());
				maxPpm1.setTextContent(theView.txtHLSVDMax1.getText());
				hlsvd.appendChild(minPpm1);
				hlsvd.appendChild(maxPpm1);
				if (theView.chbHLSVD2.isSelected()) {
					if (!PreprocessData.isValid(theView.txtHLSVDMin2)
							|| !PreprocessData.isValid(theView.txtHLSVDMax2)) {
						fw.close();
						throw new PreproException("Water Filtering");
					}
					Element minPpm2 = doc.createElement("MinPPM");
					Element maxPpm2 = doc.createElement("MaxPPM");
					minPpm2.setTextContent(theView.txtHLSVDMin2.getText());
					maxPpm2.setTextContent(theView.txtHLSVDMax2.getText());
					hlsvd.appendChild(minPpm2);
					hlsvd.appendChild(maxPpm2);

					if (theView.chbHLSVD3.isSelected()) {
						if (!PreprocessData.isValid(theView.txtHLSVDMin3)
								|| !PreprocessData
										.isValid(theView.txtHLSVDMax3)) {
							fw.close();
							throw new PreproException("Water Filtering");
						}
						Element minPpm3 = doc.createElement("MinPPM");
						Element maxPpm3 = doc.createElement("MaxPPM");
						minPpm3.setTextContent(theView.txtHLSVDMin3.getText());
						maxPpm3.setTextContent(theView.txtHLSVDMax3.getText());
						hlsvd.appendChild(minPpm3);
						hlsvd.appendChild(maxPpm3);

					}

				}
				preProcessing.appendChild(hlsvd);

			}
			// basecor
			if (theView.chbBaseCor.isSelected()) {
				if (!PreprocessData.isValid(theView.txtBaseCorMin1)
						|| !PreprocessData.isValid(theView.txtBaseCorMax1)) {
					fw.close();
					throw new PreproException("Baseline Correction");
				}
				Element base = doc.createElement("BaselineCorrection");
				Element minPpm1 = doc.createElement("MinPPM");
				Element maxPpm1 = doc.createElement("MaxPPM");
				minPpm1.setTextContent(theView.txtBaseCorMin1.getText());
				maxPpm1.setTextContent(theView.txtBaseCorMax1.getText());
				base.appendChild(minPpm1);
				base.appendChild(maxPpm1);
				if (theView.chbBaseCor2.isSelected()) {
					if (!PreprocessData.isValid(theView.txtBaseCorMin3)
							|| !PreprocessData.isValid(theView.txtBaseCorMax3)) {
						fw.close();
						throw new PreproException("Baseline Correction");
					}
					Element minPpm2 = doc.createElement("MinPPM");
					Element maxPpm2 = doc.createElement("MaxPPM");
					minPpm2.setTextContent(theView.txtBaseCorMin2.getText());
					maxPpm2.setTextContent(theView.txtBaseCorMax2.getText());
					base.appendChild(minPpm2);
					base.appendChild(maxPpm2);

					if (theView.chbBaseCor3.isSelected()) {
						if (!PreprocessData.isValid(theView.txtBaseCorMin3)
								|| !PreprocessData
										.isValid(theView.txtBaseCorMax3)) {
							fw.close();
							throw new PreproException("Baseline Correction");
						}
						Element minPpm3 = doc.createElement("MinPPM");
						Element maxPpm3 = doc.createElement("MaxPPM");
						minPpm3.setTextContent(theView.txtBaseCorMin3.getText());
						maxPpm3.setTextContent(theView.txtBaseCorMax3.getText());
						base.appendChild(minPpm3);
						base.appendChild(maxPpm3);

					}

				}
				preProcessing.appendChild(base);

			}

			// nrPoints
			if (theView.chbNrPoints.isSelected()) {
				if (!theView.txtNrPoints.equals("")) {

					Element nrPoints = doc.createElement("NumberOfPoints");
					Element nrP = doc.createElement("NrPoints");
					nrP.setTextContent(theView.txtNrPoints.getText());
					nrPoints.appendChild(nrP);
					Element nrPMin = doc.createElement("MinPPM");
					nrPMin.setTextContent(theView.txtNrPointsMin.getText());
					nrPoints.appendChild(nrPMin);
					Element nrPMax = doc.createElement("MaxPPM");
					nrPMax.setTextContent(theView.txtNrPointsMax.getText());
					nrPoints.appendChild(nrPMax);
					preProcessing.appendChild(nrPoints);
				} else {
					fw.close();
					throw new PreproException(1, "CustomNrPoints");
				}
			}
			// stz
			if (theView.chbSetToZero.isSelected()) {
				if (!PreprocessData.isValid(theView.txtSetToZeroMin1)
						|| !PreprocessData.isValid(theView.txtSetToZeroMax1)) {
					fw.close();
					throw new PreproException("Set To Zero");
				}
				Element stz = doc.createElement("SetToZero");
				Element minPpm1 = doc.createElement("MinPPM");
				Element maxPpm1 = doc.createElement("MaxPPM");
				minPpm1.setTextContent(theView.txtSetToZeroMin1.getText());
				maxPpm1.setTextContent(theView.txtSetToZeroMax1.getText());
				stz.appendChild(minPpm1);
				stz.appendChild(maxPpm1);
				if (theView.chbSetToZero2.isSelected()) {
					if (!PreprocessData.isValid(theView.txtSetToZeroMin2)
							|| !PreprocessData
									.isValid(theView.txtSetToZeroMax2)) {
						fw.close();
						throw new PreproException("Set To Zero");
					}
					Element minPpm2 = doc.createElement("MinPPM");
					Element maxPpm2 = doc.createElement("MaxPPM");
					minPpm2.setTextContent(theView.txtSetToZeroMin2.getText());
					maxPpm2.setTextContent(theView.txtSetToZeroMax2.getText());
					stz.appendChild(minPpm2);
					stz.appendChild(maxPpm2);

				}
				preProcessing.appendChild(stz);

			}
			// UL
			if (theView.chbUL.isSelected()) {
				Element ul = doc.createElement("SetToUnitLength");
				ul.setTextContent("true");
				preProcessing.appendChild(ul);
			}
			// align
			if (theView.chbAlignCor.isSelected()) {
				if (!PreprocessData.isValid(theView.txtAlignCor1)) {
					fw.close();
					throw new PreproException("Align Correction");
				}
				Element align = doc.createElement("AllignmentCorrection");

				Element isBrainH = doc.createElement("isBrainH");
				if (theView.chbIsBrainH.isSelected()) {
					isBrainH.setTextContent("true");
				} else {
					isBrainH.setTextContent("false");
				}
				align.appendChild(isBrainH);
				Element minSNR = doc.createElement("minSNR");
				minSNR.setTextContent(theView.txtAlignCorSNR.getText());
				align.appendChild(minSNR);

				Element minSTD = doc.createElement("minSTD");
				minSTD.setTextContent(theView.txtSTDMin.getText());
				align.appendChild(minSTD);

				Element maxSTD = doc.createElement("maxSTD");
				if (!theView.txtSTDMax.getText().equals("Min")) {
					maxSTD.setTextContent(theView.txtSTDMax.getText());
				} else {
					maxSTD.setTextContent(Double.toString(u.getMinPPM()));
				}
				align.appendChild(maxSTD);

				Element fPeak = doc.createElement("FirstPeak");
				fPeak.setTextContent(theView.txtAlignCor1.getText());
				align.appendChild(fPeak);

				if (theView.chbAlignCor2.isSelected()) {

					if (!PreprocessData.isValid(theView.txtAlignCor2)) {
						fw.close();
						throw new PreproException("Align Correction");
					}
					Element sPeak = doc.createElement("SecondPeak");
					sPeak.setTextContent(theView.txtAlignCor2.getText());
					align.appendChild(sPeak);

					if (theView.chbAlignCor3.isSelected()) {
						if (!PreprocessData.isValid(theView.txtAlignCor3)) {
							fw.close();
							throw new PreproException("Align Correction");
						}
						Element tPeak = doc.createElement("ThirdPeak");
						tPeak.setTextContent(theView.txtAlignCor3.getText());
						align.appendChild(tPeak);

					}
				}
				preProcessing.appendChild(align);
			}
			// Custom
			if (theView.chbCustom.isSelected()) {

				Element custom = doc.createElement("CustomPPMRange");
				Element firstPPM = doc.createElement("FirstPPM");
				firstPPM.setTextContent(String.valueOf(theView
						.getAnyIntervalNumber(theView.txtCustomMin,
								theView.txtCustomMax)[0]));
				custom.appendChild(firstPPM);
				Element lastPPM = doc.createElement("LastPPM");
				lastPPM.setTextContent(String.valueOf(theView
						.getAnyIntervalNumber(theView.txtCustomMin,
								theView.txtCustomMax)[1]));
				custom.appendChild(lastPPM);
				preProcessing.appendChild(custom);
			}

			// Aditional
			if (theView.chbLabels.isSelected() || theView.chbName.isSelected()
					|| theView.chbWhere.isSelected()
					|| theView.chbKeyWords.isSelected()) {
				Element adi = doc.createElement("AditionalInformation");
				if (theView.chbName.isSelected()) {
					Element name = doc.createElement("Name");
					name.setTextContent(theView.txtName.getText());
					adi.appendChild(name);
				}
				if (theView.chbWhere.isSelected()) {
					Element place = doc.createElement("Place");
					place.setTextContent(theView.txtPlace.getText());
					adi.appendChild(place);
				}
				if (!theView.editorPane.getText().equals("")) {
					Element observations = doc.createElement("Observations");
					observations.setTextContent(theView.editorPane.getText());
					adi.appendChild(observations);

				}
				if (theView.chbKeyWords.isSelected()) {
					Element keywords = doc.createElement("KeyWords");
					keywords.setTextContent(theView.txtKeyWord.getText());
					adi.appendChild(keywords);
				}

				preProcessing.appendChild(adi);
			}
			doc.appendChild(preProcessing);

		} catch (PreproException ex2) {
			JOptionPane.showMessageDialog(theView, ex2.getMessage());

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(theView,
					"File not saved. " + ex.getMessage(), "Alert",
					JOptionPane.INFORMATION_MESSAGE);

		}

		return doc;
	}

	public Element prepare(ExportUI theView, FileOutputStream fw) {
		u = new Utils(mrui);
		this.preProcessing = doc.createElement("Preprocessing");
		try { // ref
			if (theView.chbSetRef.isSelected()) {
				if (!PreprocessData.isValid(theView.txtSetRef)) {
					fw.close();
					throw new PreproException("Set Reference");
				}

				Element setRef = doc.createElement("SetReference");
				Element ppm = doc.createElement("PPM");
				ppm.setTextContent(theView.txtSetRef.getText());

				setRef.appendChild(ppm);
				preProcessing.appendChild(setRef);

			}
			// apo
			if (theView.chbApo.isSelected()) {

				if ((theView.raApoGaus.isSelected() || theView.raApoLore
						.isSelected())
						&& !theView.txtApo.equals("")
						&& theView.txtApo.getText().indexOf("-") == -1) {
					Element apo = doc.createElement("Apodization");
					Element raGauss = doc.createElement("Gaussian");
					Element raLor = doc.createElement("Lorenzian");
					Element hz = doc.createElement("Hz");
					hz.setTextContent(theView.txtApo.getText());
					if (theView.raApoGaus.isSelected()) {
						raGauss.appendChild(hz);
						apo.appendChild(raGauss);
						preProcessing.appendChild(apo);
					} else {
						raLor.appendChild(hz);
						apo.appendChild(raLor);
						preProcessing.appendChild(apo);
					}
				} else {
					fw.close();
					throw new PreproException(1, "Apoidization");
				}
			}
			// hlsvd
			if (theView.chbHLSVD.isSelected()) {
				if (!PreprocessData.isValid(theView.txtHLSVDMin1)
						|| !PreprocessData.isValid(theView.txtHLSVDMax1)) {
					fw.close();
					throw new PreproException("Water Filtering");
				}
				Element hlsvd = doc.createElement("WaterFiltering");
				Element nrLore = doc.createElement("NumberOfLorenzians");
				nrLore.setTextContent(theView.txtHLSVDNrLore.getText());
				hlsvd.appendChild(nrLore);
				Element minPpm1 = doc.createElement("MinPPM");
				Element maxPpm1 = doc.createElement("MaxPPM");
				minPpm1.setTextContent(theView.txtHLSVDMin1.getText());
				maxPpm1.setTextContent(theView.txtHLSVDMax1.getText());
				hlsvd.appendChild(minPpm1);
				hlsvd.appendChild(maxPpm1);
				if (theView.chbHLSVD2.isSelected()) {
					if (!PreprocessData.isValid(theView.txtHLSVDMin2)
							|| !PreprocessData.isValid(theView.txtHLSVDMax2)) {
						fw.close();
						throw new PreproException("Water Filtering");
					}
					Element minPpm2 = doc.createElement("MinPPM");
					Element maxPpm2 = doc.createElement("MaxPPM");
					minPpm2.setTextContent(theView.txtHLSVDMin2.getText());
					maxPpm2.setTextContent(theView.txtHLSVDMax2.getText());
					hlsvd.appendChild(minPpm2);
					hlsvd.appendChild(maxPpm2);

					if (theView.chbHLSVD3.isSelected()) {
						if (!PreprocessData.isValid(theView.txtHLSVDMin3)
								|| !PreprocessData
										.isValid(theView.txtHLSVDMax3)) {
							fw.close();
							throw new PreproException("Water Filtering");
						}
						Element minPpm3 = doc.createElement("MinPPM");
						Element maxPpm3 = doc.createElement("MaxPPM");
						minPpm3.setTextContent(theView.txtHLSVDMin3.getText());
						maxPpm3.setTextContent(theView.txtHLSVDMax3.getText());
						hlsvd.appendChild(minPpm3);
						hlsvd.appendChild(maxPpm3);

					}

				}
				preProcessing.appendChild(hlsvd);

			}
			// basecor
			if (theView.chbBaseCor.isSelected()) {
				if (!PreprocessData.isValid(theView.txtBaseCorMin1)
						|| !PreprocessData.isValid(theView.txtBaseCorMax1)) {
					fw.close();
					throw new PreproException("Baseline Correction");
				}
				Element base = doc.createElement("BaselineCorrection");
				Element minPpm1 = doc.createElement("MinPPM");
				Element maxPpm1 = doc.createElement("MaxPPM");
				minPpm1.setTextContent(theView.txtBaseCorMin1.getText());
				maxPpm1.setTextContent(theView.txtBaseCorMax1.getText());
				base.appendChild(minPpm1);
				base.appendChild(maxPpm1);
				if (theView.chbBaseCor2.isSelected()) {
					if (!PreprocessData.isValid(theView.txtBaseCorMin3)
							|| !PreprocessData.isValid(theView.txtBaseCorMax3)) {
						fw.close();
						throw new PreproException("Baseline Correction");
					}
					Element minPpm2 = doc.createElement("MinPPM");
					Element maxPpm2 = doc.createElement("MaxPPM");
					minPpm2.setTextContent(theView.txtBaseCorMin2.getText());
					maxPpm2.setTextContent(theView.txtBaseCorMax2.getText());
					base.appendChild(minPpm2);
					base.appendChild(maxPpm2);

					if (theView.chbBaseCor3.isSelected()) {
						if (!PreprocessData.isValid(theView.txtBaseCorMin3)
								|| !PreprocessData
										.isValid(theView.txtBaseCorMax3)) {
							fw.close();
							throw new PreproException("Baseline Correction");
						}
						Element minPpm3 = doc.createElement("MinPPM");
						Element maxPpm3 = doc.createElement("MaxPPM");
						minPpm3.setTextContent(theView.txtBaseCorMin3.getText());
						maxPpm3.setTextContent(theView.txtBaseCorMax3.getText());
						base.appendChild(minPpm3);
						base.appendChild(maxPpm3);

					}

				}
				preProcessing.appendChild(base);

			}

			// nrPoints
			if (theView.chbNrPoints.isSelected()) {
				if (!theView.txtNrPoints.equals("")) {

					Element nrPoints = doc.createElement("NumberOfPoints");
					Element nrP = doc.createElement("NrPoints");
					nrP.setTextContent(theView.txtNrPoints.getText());
					nrPoints.appendChild(nrP);
					Element nrPMin = doc.createElement("MinPPM");
					nrPMin.setTextContent(theView.txtNrPointsMin.getText());
					nrPoints.appendChild(nrPMin);
					Element nrPMax = doc.createElement("MaxPPM");
					nrPMax.setTextContent(theView.txtNrPointsMax.getText());
					nrPoints.appendChild(nrPMax);
					preProcessing.appendChild(nrPoints);
				} else {
					fw.close();
					throw new PreproException(1, "Custom number of points");
				}
			}
			// stz
			if (theView.chbSetToZero.isSelected()) {
				if (!PreprocessData.isValid(theView.txtSetToZeroMin1)
						|| !PreprocessData.isValid(theView.txtSetToZeroMax1)) {
					fw.close();
					throw new PreproException("Set To Zero");
				}
				Element stz = doc.createElement("SetToZero");
				Element minPpm1 = doc.createElement("MinPPM");
				Element maxPpm1 = doc.createElement("MaxPPM");
				minPpm1.setTextContent(theView.txtSetToZeroMin1.getText());
				maxPpm1.setTextContent(theView.txtSetToZeroMax1.getText());
				stz.appendChild(minPpm1);
				stz.appendChild(maxPpm1);
				if (theView.chbSetToZero2.isSelected()) {
					if (!PreprocessData.isValid(theView.txtSetToZeroMin2)
							|| !PreprocessData
									.isValid(theView.txtSetToZeroMax2)) {
						fw.close();
						throw new PreproException("Set To Zero");
					}
					Element minPpm2 = doc.createElement("MinPPM");
					Element maxPpm2 = doc.createElement("MaxPPM");
					minPpm2.setTextContent(theView.txtSetToZeroMin2.getText());
					maxPpm2.setTextContent(theView.txtSetToZeroMax2.getText());
					stz.appendChild(minPpm2);
					stz.appendChild(maxPpm2);

				}
				preProcessing.appendChild(stz);

			}
			// UL
			if (theView.chbUL.isSelected()) {
				Element ul = doc.createElement("SetToUnitLength");
				ul.setTextContent("true");
				preProcessing.appendChild(ul);
			}
			// align
			if (theView.chbAlignCor.isSelected()) {
				if (!PreprocessData.isValid(theView.txtAlignCor1)) {
					fw.close();
					throw new PreproException("Align Correction");
				}
				Element align = doc.createElement("AllignmentCorrection");
				Element isBrainH = doc.createElement("isBrainH");
				if (theView.chbIsBrainH.isSelected()) {
					isBrainH.setTextContent("YES");
				} else {
					isBrainH.setTextContent("NO");
				}
				align.appendChild(isBrainH);
				Element minSNR = doc.createElement("minSNR");
				minSNR.setTextContent(theView.txtAlignCorSNR.getText());
				align.appendChild(minSNR);

				Element minSTD = doc.createElement("minSTD");
				minSTD.setTextContent(theView.txtSTDMin.getText());
				align.appendChild(minSTD);

				Element maxSTD = doc.createElement("maxSTD");
				if (!theView.txtSTDMax.getText().equals("Min")) {
					maxSTD.setTextContent(theView.txtSTDMax.getText());
				} else {
					maxSTD.setTextContent(Double.toString(u.getMinPPM()));
				}
				align.appendChild(maxSTD);

				Element fPeak = doc.createElement("FirstPeak");
				fPeak.setTextContent(theView.txtAlignCor1.getText());
				align.appendChild(fPeak);

				if (theView.chbAlignCor2.isSelected()) {

					if (!PreprocessData.isValid(theView.txtAlignCor2)) {
						fw.close();
						throw new PreproException("Align Correction");
					}
					Element sPeak = doc.createElement("SecondPeak");
					sPeak.setTextContent(theView.txtAlignCor2.getText());
					align.appendChild(sPeak);

					if (theView.chbAlignCor3.isSelected()) {
						if (!PreprocessData.isValid(theView.txtAlignCor3)) {
							fw.close();
							throw new PreproException("Align Correction");
						}
						Element tPeak = doc.createElement("ThirdPeak");
						tPeak.setTextContent(theView.txtAlignCor3.getText());
						align.appendChild(tPeak);

					}
				}
				preProcessing.appendChild(align);
			}

			// Custom PPM Range
			if (theView.chbCustom.isSelected()) {

				Element custom = doc.createElement("CustomPPMRange");
				Element firstPPM = doc.createElement("FirstPPM");
				firstPPM.setTextContent(String.valueOf(theView
						.getAnyIntervalNumber(theView.txtCustomMin,
								theView.txtCustomMax)[0]));
				custom.appendChild(firstPPM);
				Element lastPPM = doc.createElement("LastPPM");
				lastPPM.setTextContent(String.valueOf(theView
						.getAnyIntervalNumber(theView.txtCustomMin,
								theView.txtCustomMax)[1]));
				custom.appendChild(lastPPM);
				preProcessing.appendChild(custom);
			}

			// Aditional
			if (theView.chbLabels.isSelected() || theView.chbName.isSelected()
					|| theView.chbWhere.isSelected()
					|| theView.chbKeyWords.isSelected()
					|| !theView.editorPane.getText().equals("")) {
				Element adi = doc.createElement("AditionalInformation");
				if (theView.chbName.isSelected()) {
					Element name = doc.createElement("Name");
					name.setTextContent(theView.txtName.getText());
					adi.appendChild(name);
				}
				if (theView.chbWhere.isSelected()) {
					Element place = doc.createElement("Place");
					place.setTextContent(theView.txtPlace.getText());
					adi.appendChild(place);
				}
				// if (theView.chbLabels.isSelected()) {
				// Element label = doc.createElement("Place");
				// place.setTextContent(theView.txtPlace.getText());
				// adi.appendChild(place);
				// }
				if (theView.chbKeyWords.isSelected()) {
					Element keywords = doc.createElement("KeyWords");
					keywords.setTextContent(theView.txtKeyWord.getText());
					adi.appendChild(keywords);
				}
				if (!theView.editorPane.getText().equals("")) {
					Element observations = doc.createElement("Observations");
					observations.setTextContent(theView.editorPane.getText());
					adi.appendChild(observations);
				}

				preProcessing.appendChild(adi);
			}

		} catch (PreproException ex2) {
			JOptionPane.showMessageDialog(theView, ex2.getMessage());

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(theView,
					"File not saved. " + ex.getMessage(), "Alert",
					JOptionPane.INFORMATION_MESSAGE);

		}

		return preProcessing;
	}

	public void loadPreproSchma(ExportUI theView) throws PreproException {
		try {
			if (doc.getElementsByTagName("SetReference").getLength() > 0) {
				NodeList setRef = doc.getElementsByTagName("SetReference");

				theView.chbSetRef.setEnabled(true);
				theView.chbSetRef.setSelected(true);
				theView.txtSetRef.setEnabled(true);
				if (!setRef.item(0).getTextContent().isEmpty()) {
					theView.txtSetRef.setText(String.valueOf(Double
							.valueOf(setRef.item(0).getTextContent())));
				}
			}
			// Apo

			if (doc.getElementsByTagName("Apodization").getLength() > 0) {
				NodeList apo = doc.getElementsByTagName("Apodization");
				theView.chbApo.setEnabled(true);
				theView.chbApo.setSelected(true);

				for (int i = 0; i < apo.item(0).getChildNodes().getLength(); i++) {
					if (apo.item(0).getChildNodes().item(i).getNodeName()
							.equals("Gaussian")) {
						theView.raApoGaus.setEnabled(true);
						theView.raApoGaus.setSelected(true);
						for (int j = 0; j < apo.item(0).getChildNodes().item(i)
								.getChildNodes().getLength(); j++) {
							if (apo.item(0).getChildNodes().item(i)
									.getChildNodes().item(j).getNodeName()
									.equals("Hz")) {
								theView.txtApo.setEnabled(true);
								theView.txtApo.setText(String.valueOf(Integer
										.valueOf(apo.item(0).getChildNodes()
												.item(i).getChildNodes()
												.item(j).getTextContent())));
							}
						}
					}
					if (apo.item(0).getChildNodes().item(i).getNodeName()
							.equals("Lorenzian")) {
						theView.raApoLore.setSelected(true);
						theView.raApoLore.setEnabled(true);
						for (int j = 0; j < apo.item(0).getChildNodes().item(i)
								.getChildNodes().getLength(); j++) {
							if (apo.item(0).getChildNodes().item(i)
									.getChildNodes().item(j).getNodeName()
									.equals("Hz")) {
								theView.txtApo.setEnabled(true);
								theView.txtApo.setText(String.valueOf(Integer
										.valueOf(apo.item(0).getChildNodes()
												.item(i).getChildNodes()
												.item(j).getTextContent())));
							}
						}

					}

				}
			}

			// HLSVD
			if (doc.getElementsByTagName("WaterFiltering").getLength() > 0) {
				int areasMin = 0, areasMax = 0;
				theView.chbHLSVD.setSelected(true);
				NodeList hlsvd = doc.getElementsByTagName("WaterFiltering");
				for (int i = 0; i < hlsvd.item(0).getChildNodes().getLength(); i++) {
					if (hlsvd.item(0).getChildNodes().item(i).getNodeName()
							.equals("NumberOfLorenzians")) {

						theView.txtHLSVDNrLore.setText(String.valueOf(Integer
								.valueOf(hlsvd.item(0).getChildNodes().item(i)
										.getTextContent())));
						theView.txtHLSVDNrLore.setEnabled(true);

					}

					if (hlsvd.item(0).getChildNodes().item(i).getNodeName()
							.equals("MinPPM")) {
						areasMin++;
						if (areasMin == 1) {
							theView.chbHLSVD2.setEnabled(true);
							theView.txtHLSVDMin1.setEnabled(true);
							theView.txtHLSVDMin1.setText(String.valueOf(Double
									.valueOf(hlsvd.item(0).getChildNodes()
											.item(i).getTextContent())));
						}
						if (areasMin == 2) {

							theView.chbHLSVD2.setSelected(true);
							theView.chbHLSVD3.setEnabled(true);
							theView.txtHLSVDMin2.setEnabled(true);
							theView.txtHLSVDMin2.setText(String.valueOf(Double
									.valueOf(hlsvd.item(0).getChildNodes()
											.item(i).getTextContent())));
						}
						if (areasMin == 3) {
							theView.chbHLSVD3.setSelected(true);
							theView.txtHLSVDMin3.setEnabled(true);
							theView.txtHLSVDMin3.setText(String.valueOf(Double
									.valueOf(hlsvd.item(0).getChildNodes()
											.item(i).getTextContent())));
						}

					}
					if (hlsvd.item(0).getChildNodes().item(i).getNodeName()
							.equals("MaxPPM")) {
						areasMax++;
						if (areasMax == 1) {
							theView.txtHLSVDMax1.setEnabled(true);
							theView.txtHLSVDMax1.setText(String.valueOf(Double
									.valueOf(hlsvd.item(0).getChildNodes()
											.item(i).getTextContent())));
						}
						if (areasMax == 2) {
							theView.txtHLSVDMax2.setEnabled(true);
							theView.txtHLSVDMax2.setText(String.valueOf(Double
									.valueOf(hlsvd.item(0).getChildNodes()
											.item(i).getTextContent())));
						}
						if (areasMax == 3) {
							theView.txtHLSVDMax3.setEnabled(true);
							theView.txtHLSVDMax3.setText(String.valueOf(Double
									.valueOf(hlsvd.item(0).getChildNodes()
											.item(i).getTextContent())));
						}

					}

				}

			}
			// baseline
			if (doc.getElementsByTagName("BaselineCorrection").getLength() > 0) {
				int areasMin = 0, areasMax = 0;
				theView.chbBaseCor.setSelected(true);
				NodeList base = doc.getElementsByTagName("BaselineCorrection");

				for (int i = 0; i < base.item(0).getChildNodes().getLength(); i++) {
					if (base.item(0).getChildNodes().item(i).getNodeName()
							.equals("MinPPM")) {
						areasMin++;
						if (areasMin == 1) {
							theView.txtBaseCorMin1.setEnabled(true);
							theView.chbBaseCor2.setEnabled(true);
							theView.txtBaseCorMin1.setText(String
									.valueOf(Double.valueOf(base.item(0)
											.getChildNodes().item(i)
											.getTextContent())));
						}
						if (areasMin == 2) {
							theView.chbBaseCor2.setSelected(true);
							theView.chbBaseCor3.setEnabled(true);
							theView.txtBaseCorMin2.setEnabled(true);
							theView.txtBaseCorMin2.setText(String
									.valueOf(Double.valueOf(base.item(0)
											.getChildNodes().item(i)
											.getTextContent())));
						}
						if (areasMin == 3) {
							theView.chbBaseCor3.setSelected(true);
							theView.txtBaseCorMin3.setEnabled(true);
							theView.txtBaseCorMin3.setText(String
									.valueOf(Double.valueOf(base.item(0)
											.getChildNodes().item(i)
											.getTextContent())));
						}

					}
					if (base.item(0).getChildNodes().item(i).getNodeName()
							.equals("MaxPPM")) {
						areasMax++;
						if (areasMax == 1) {
							theView.txtBaseCorMax1.setEnabled(true);
							theView.chbBaseCor2.setEnabled(true);
							theView.txtBaseCorMax1.setText(String
									.valueOf(Double.valueOf(base.item(0)
											.getChildNodes().item(i)
											.getTextContent())));
						}
						if (areasMax == 2) {
							theView.txtBaseCorMax2.setEnabled(true);
							theView.chbBaseCor3.setEnabled(true);
							theView.txtBaseCorMax2.setText(String
									.valueOf(Double.valueOf(base.item(0)
											.getChildNodes().item(i)
											.getTextContent())));
						}
						if (areasMax == 3) {
							theView.txtBaseCorMax3.setEnabled(true);
							theView.txtBaseCorMax3.setText(String
									.valueOf(Double.valueOf(base.item(0)
											.getChildNodes().item(i)
											.getTextContent())));
						}

					}

				}

			}
			// setToZero
			if (doc.getElementsByTagName("SetToZero").getLength() > 0) {
				int areasMin = 0, areasMax = 0;
				theView.chbSetToZero.setSelected(true);
				NodeList stz = doc.getElementsByTagName("SetToZero");

				for (int i = 0; i < stz.item(0).getChildNodes().getLength(); i++) {
					if (stz.item(0).getChildNodes().item(i).getNodeName()
							.equals("MinPPM")) {
						areasMin++;
						if (areasMin == 1) {
							theView.txtSetToZeroMin1.setEnabled(true);
							theView.chbSetToZero2.setEnabled(true);
							theView.txtSetToZeroMin1.setText(String
									.valueOf(Double.valueOf(stz.item(0)
											.getChildNodes().item(i)
											.getTextContent())));
						}
						if (areasMin == 2) {
							theView.txtSetToZeroMin2.setEnabled(true);
							theView.chbSetToZero2.setSelected(true);
							theView.txtSetToZeroMin2.setText(String
									.valueOf(Double.valueOf(stz.item(0)
											.getChildNodes().item(i)
											.getTextContent())));
						}

					}
					if (stz.item(0).getChildNodes().item(i).getNodeName()
							.equals("MaxPPM")) {
						areasMax++;
						if (areasMax == 1) {
							theView.txtSetToZeroMax1.setEnabled(true);
							theView.txtSetToZeroMax1.setText(String
									.valueOf(Double.valueOf(stz.item(0)
											.getChildNodes().item(i)
											.getTextContent())));
						}
						if (areasMax == 2) {
							theView.txtSetToZeroMax2.setEnabled(true);
							theView.txtSetToZeroMax2.setText(String
									.valueOf(Double.valueOf(stz.item(0)
											.getChildNodes().item(i)
											.getTextContent())));
						}

					}

				}

			}
			// nrPoints

			if (doc.getElementsByTagName("NumberOfPoints").getLength() > 0) {

				theView.chbNrPoints.setSelected(true);
				NodeList nrPoints = doc.getElementsByTagName("NumberOfPoints");

				for (int i = 0; i < nrPoints.item(0).getChildNodes()
						.getLength(); i++) {

					if (nrPoints.item(0).getChildNodes().item(i).getNodeName()
							.equals("MinPPM")) {
						theView.txtNrPointsMin.setEnabled(true);
						theView.txtNrPointsMin.setText(String.valueOf(Double
								.valueOf(nrPoints.item(0).getChildNodes()
										.item(i).getTextContent())));

					}
					if (nrPoints.item(0).getChildNodes().item(i).getNodeName()
							.equals("MaxPPM")) {
						theView.txtNrPointsMax.setEnabled(true);
						theView.txtNrPointsMax.setText(String.valueOf(Double
								.valueOf(nrPoints.item(0).getChildNodes()
										.item(i).getTextContent())));
					}
					if (nrPoints.item(0).getChildNodes().item(i).getNodeName()
							.equals("NrPoints")) {
						theView.txtNrPoints.setEnabled(true);
						theView.txtNrPoints.setText(String.valueOf(Integer
								.valueOf(nrPoints.item(0).getChildNodes()
										.item(i).getTextContent())));
					}

				}

			}

			// settoUL
			if (doc.getElementsByTagName("SetToUnitLength").getLength() > 0) {

				theView.chbUL.setSelected(true);
			}

			// allignmentCorrection
			if (doc.getElementsByTagName("AllignmentCorrection").getLength() > 0) {

				theView.chbAlignCor.setSelected(true);
				NodeList align = doc
						.getElementsByTagName("AllignmentCorrection");
				for (int i = 0; i < align.item(0).getChildNodes().getLength(); i++) {
					if (align.item(0).getChildNodes().item(i).getNodeName()
							.equals("isBrainH")) {
						theView.chbIsBrainH.setEnabled(true);
						theView.chbIsBrainH.setSelected(true);
					}

					if (align.item(0).getChildNodes().item(i).getNodeName()
							.equals("minSTD")) {

						theView.txtSTDMin.setText(String.valueOf(Double
								.valueOf(align.item(0).getChildNodes().item(i)
										.getTextContent())));
					}
					if (align.item(0).getChildNodes().item(i).getNodeName()
							.equals("maxSTD")) {
						theView.txtSTDMax.setText(String.valueOf(Double
								.valueOf(align.item(0).getChildNodes().item(i)
										.getTextContent())));
					}

					if (align.item(0).getChildNodes().item(i).getNodeName()
							.equals("minSNR")) {
						theView.txtAlignCorSNR.setText(String.valueOf(Double
								.valueOf(align.item(0).getChildNodes().item(i)
										.getTextContent())));
					}
					if (align.item(0).getChildNodes().item(i).getNodeName()
							.equals("FirstPeak")) {
						theView.txtAlignCor1.setEnabled(true);
						theView.txtAlignCor1.setText(String.valueOf(Double
								.valueOf(align.item(0).getChildNodes().item(i)
										.getTextContent())));

					}
					if (align.item(0).getChildNodes().item(i).getNodeName()
							.equals("SecondPeak")) {
						theView.chbAlignCor2.setEnabled(true);
						theView.chbAlignCor2.setSelected(true);
						theView.txtAlignCor2.setEnabled(true);
						theView.txtAlignCor2.setText(String.valueOf(Double
								.valueOf(align.item(0).getChildNodes().item(i)
										.getTextContent())));
					}
					if (align.item(0).getChildNodes().item(i).getNodeName()
							.equals("ThirdPeak")) {
						theView.chbAlignCor2.setEnabled(true);
						theView.chbAlignCor3.setSelected(true);
						theView.txtAlignCor3.setEnabled(true);
						theView.txtAlignCor3.setText(String.valueOf(Double
								.valueOf(align.item(0).getChildNodes().item(i)
										.getTextContent())));
					}

				}

			}

			if (doc.getElementsByTagName("CustomPPMRange").getLength() > 0) {

				theView.chbCustom.setSelected(true);
				NodeList cst = doc.getElementsByTagName("CustomPPMRange");
				for (int i = 0; i < cst.item(0).getChildNodes().getLength(); i++) {
					if (cst.item(0).getChildNodes().item(i).getNodeName()
							.equals("FirstPPM")) {
						theView.txtCustomMin.setEnabled(true);
						theView.txtCustomMin.setText(cst.item(0)
								.getChildNodes().item(i).getTextContent());

					}

					if (cst.item(0).getChildNodes().item(i).getNodeName()
							.equals("LastPPM")) {
						theView.txtCustomMax.setEnabled(true);
						theView.txtCustomMax.setText(cst.item(0)
								.getChildNodes().item(i).getTextContent());

					}
				}
			}

			if (doc.getElementsByTagName("AditionalInformation").getLength() > 0) {
				NodeList adi = doc.getElementsByTagName("AditionalInformation");
				for (int i = 0; i < adi.item(0).getChildNodes().getLength(); i++) {
					if (adi.item(0).getChildNodes().item(i).getNodeName()
							.equals("Name")) {
						theView.chbName.setSelected(true);
						theView.txtName.setEnabled(true);
						theView.txtName.setText(adi.item(0).getChildNodes()
								.item(i).getTextContent());

					}
					if (adi.item(0).getChildNodes().item(i).getNodeName()
							.equals("Place")) {
						theView.chbWhere.setSelected(true);
						theView.txtPlace.setEnabled(true);
						theView.txtPlace.setText(adi.item(0).getChildNodes()
								.item(i).getTextContent());

					}
					if (adi.item(0).getChildNodes().item(i).getNodeName()
							.equals("KeyWords")) {
						theView.chbKeyWords.setSelected(true);
						theView.txtKeyWord.setText(adi.item(0).getChildNodes()
								.item(i).getTextContent());

					}
					if (adi.item(0).getChildNodes().item(i).getNodeName()
							.equals("Observations")) {

						theView.editorPane.setText(adi.item(0).getChildNodes()
								.item(i).getTextContent());

					}
				}
			}

		} catch (Exception e1) {

			JOptionPane.showMessageDialog(theView,
					"Something is wrong with the Preprocessing XML file");
			e1.printStackTrace();
		}

	}
}