package mrui.custom.jmrui2xml;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mrui.Mrui;
import mrui.MruiImpl;
import mrui.data.Data;
import mrui.mode1d.Mode1DWinImpl;
import mrui.plugin.PluginInfo;
import mrui.preprocessing.getsnr.GetSNR;
import mrui.preprocessing.settozero.PPM2Point;
import mrui.preprocessing.settozero.Utils;
import mrui.utils.BatchTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import fft.Fft;

public class Controller {

	private ExportUI theView;
	private Model theModel;
	File config = new File("");
	Mrui mrui;
	PluginInfo pluginInfo;
	static Data data;

	public Controller(ExportUI theView, Model theModel, Mrui mrui,
			PluginInfo pluginInfo) {

		this.theView = theView;
		this.theModel = theModel;
		this.mrui = mrui;
		this.pluginInfo = pluginInfo;
		data = cloneData(mrui.getData());

		// GUI logic
		this.theView.addChbSetRefListener(new ChbSetRefListener());

		this.theView.addChbApoListener(new ChbApoListener());
		this.theView.addRaApoGaus(new RaApoGausListener());
		this.theView.addRaApoLore(new RaApoLoreListener());

		this.theView.addChbHLSVDListener(new ChbHLSVDListener());
		this.theView.addChbHLSVD2Listener(new ChbHLSVD2Listener());
		this.theView.addChbHLSVD3Listener(new ChbHLSVD3Listener());

		this.theView.addChbBaseCorListener(new ChbBaseCorListener());
		this.theView.addChbBaseCor2Listener(new ChbBaseCor2Listener());
		this.theView.addChbBaseCor3Listener(new ChbBaseCor3Listener());

		this.theView.addChbSetToZeroListener(new ChbSetToZeroListener());
		this.theView.addChbSetToZero2Listener(new ChbSetToZero2Listener());

		this.theView.addChbSetNrPointsListener(new ChbNrPointsListener());

		this.theView.addChbAlignLinstener(new ChbAlignCorListener());
		this.theView.addChbAlign2Linstener(new ChbAlignCor2Listener());
		this.theView.addChbAlign3Linstener(new ChbAlignCor3Listener());
		this.theView.addChbIsBrainHListener(new ChbIsBrainListener());
		this.theView.addChbCustomListener(new ChbCustomListener());
		this.theView.addChbNameListener(new ChbNameListener());
		this.theView.addChbPlaceListener(new ChbPlaceListener());

		this.theView.addButLoadListener(new ButLoadListener());
		this.theView
				.addButPreviewOriginalListener(new ButPreviewOriginalListener());
		this.theView.addButSaveListener(new ButSaveConfigListener());
		this.theView.addButExportListener(new ButExportListener());
		this.theView.addTabListener(new TabIsMVListener());
		this.theView.addChbLabelListener(new ChbLabelSV());
		this.theView.addChbKeyWordListener(new ChbKeyWordListener());

	}

	// Low level logic; check a box activate something, de-check it
	// deactivate...
	class ChbSetRefListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (!theView.chbSetRef.isSelected()) {
				theView.txtSetRef.setEnabled(false);
			} else {
				theView.txtSetRef.setEnabled(true);

			}

		}
	}

	class ChbApoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!theView.chbApo.isSelected()) {
				theView.raApoGaus.setEnabled(false);
				theView.raApoGaus.setSelected(false);

				theView.raApoLore.setEnabled(false);
				theView.raApoLore.setSelected(false);

				theView.txtApo.setEnabled(false);
			} else {
				theView.raApoGaus.setEnabled(true);
				theView.raApoLore.setEnabled(true);
				theView.txtApo.setEnabled(true);
			}
		}

	}

	class RaApoGausListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			theView.raApoLore.setSelected(false);

		}

	}

	class RaApoLoreListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			theView.raApoGaus.setSelected(false);

		}

	}

	class ChbHLSVDListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (!theView.chbHLSVD.isSelected()) {
				theView.txtHLSVDMin1.setEnabled(false);
				theView.txtHLSVDMax1.setEnabled(false);
				theView.txtHLSVDMin2.setEnabled(false);
				theView.txtHLSVDMax2.setEnabled(false);
				theView.txtHLSVDMin3.setEnabled(false);
				theView.txtHLSVDMax3.setEnabled(false);
				theView.chbHLSVD2.setEnabled(false);
				theView.chbHLSVD2.setSelected(false);
				theView.chbHLSVD3.setEnabled(false);
				theView.chbHLSVD3.setSelected(false);
				theView.txtHLSVDNrLore.setEnabled(false);
			} else {
				theView.txtHLSVDMin1.setEnabled(true);
				theView.txtHLSVDMax1.setEnabled(true);
				theView.txtHLSVDNrLore.setEnabled(true);
				theView.chbHLSVD2.setEnabled(true);
			}

		}

	}

	class ChbHLSVD2Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!theView.chbHLSVD2.isSelected()) {
				theView.txtHLSVDMin2.setEnabled(false);
				theView.txtHLSVDMax2.setEnabled(false);
				theView.chbHLSVD3.setEnabled(false);
				theView.txtHLSVDMin3.setEnabled(false);
				theView.txtHLSVDMax3.setEnabled(false);
			} else {
				theView.txtHLSVDMin2.setEnabled(true);
				theView.txtHLSVDMax2.setEnabled(true);
				theView.chbHLSVD3.setEnabled(true);
			}

		}
	}

	class ChbHLSVD3Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!theView.chbHLSVD3.isSelected()) {
				theView.txtHLSVDMin3.setEnabled(false);
				theView.txtHLSVDMax3.setEnabled(false);
			} else {
				theView.txtHLSVDMin3.setEnabled(true);
				theView.txtHLSVDMax3.setEnabled(true);
			}

		}

	}

	class ChbBaseCorListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (!theView.chbBaseCor.isSelected()) {
				theView.txtBaseCorMin1.setEnabled(false);
				theView.txtBaseCorMax1.setEnabled(false);
				theView.txtBaseCorMin2.setEnabled(false);
				theView.txtBaseCorMax2.setEnabled(false);
				theView.txtBaseCorMin3.setEnabled(false);
				theView.txtBaseCorMax3.setEnabled(false);
				theView.chbBaseCor2.setEnabled(false);
				theView.chbBaseCor2.setSelected(false);
				theView.chbBaseCor3.setEnabled(false);
				theView.chbBaseCor3.setSelected(false);
			} else {
				theView.txtBaseCorMin1.setEnabled(true);
				theView.txtBaseCorMax1.setEnabled(true);
				theView.chbBaseCor2.setEnabled(true);
			}

		}

	}

	class ChbBaseCor2Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!theView.chbBaseCor2.isSelected()) {
				theView.txtBaseCorMin2.setEnabled(false);
				theView.txtBaseCorMax2.setEnabled(false);
				theView.chbBaseCor3.setEnabled(false);
				theView.txtBaseCorMin3.setEnabled(false);
				theView.txtBaseCorMax3.setEnabled(false);
			} else {
				theView.txtBaseCorMin2.setEnabled(true);
				theView.txtBaseCorMax2.setEnabled(true);
				theView.chbBaseCor3.setEnabled(true);
			}

		}
	}

	class ChbBaseCor3Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!theView.chbBaseCor3.isSelected()) {
				theView.txtBaseCorMin3.setEnabled(false);
				theView.txtBaseCorMax3.setEnabled(false);
			} else {
				theView.txtBaseCorMin3.setEnabled(true);
				theView.txtBaseCorMax3.setEnabled(true);
			}

		}

	}

	class ChbSetToZeroListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!theView.chbSetToZero.isSelected()) {
				theView.txtSetToZeroMin1.setEnabled(false);
				theView.txtSetToZeroMax1.setEnabled(false);
				theView.txtSetToZeroMin2.setEnabled(false);
				theView.txtSetToZeroMax2.setEnabled(false);

			} else {
				theView.txtSetToZeroMin1.setEnabled(true);
				theView.txtSetToZeroMax1.setEnabled(true);
				theView.chbSetToZero2.setEnabled(true);
			}

		}

	}

	class ChbSetToZero2Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!theView.chbSetToZero2.isSelected()) {
				theView.txtSetToZeroMin2.setEnabled(false);
				theView.txtSetToZeroMax2.setEnabled(false);

			} else {
				theView.txtSetToZeroMin2.setEnabled(true);
				theView.txtSetToZeroMax2.setEnabled(true);
			}
		}
	}

	class ChbNrPointsListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!theView.chbNrPoints.isSelected()) {
				theView.txtNrPoints.setEnabled(false);
				theView.txtNrPointsMax.setEnabled(false);
				theView.txtNrPointsMin.setEnabled(false);
			} else {
				theView.txtNrPoints.setEnabled(true);
				theView.txtNrPointsMax.setEnabled(true);
				theView.txtNrPointsMin.setEnabled(true);
			}
		}
	}

	class ChbAlignCorListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!theView.chbAlignCor.isSelected()) {
				theView.txtAlignCor1.setEnabled(false);
				theView.chbAlignCor2.setEnabled(false);
				theView.txtAlignCor3.setEnabled(false);
				theView.chbAlignCor3.setEnabled(false);
				theView.txtAlignCor2.setEnabled(false);
				theView.chbIsBrainH.setEnabled(false);
				theView.txtSTDMin.setEnabled(false);
				theView.txtSTDMax.setEnabled(false);
				theView.txtAlignCorSNR.setEnabled(false);
			} else {
				theView.txtAlignCor1.setEnabled(true);
				theView.chbAlignCor2.setEnabled(true);
				theView.chbIsBrainH.setEnabled(true);
				theView.txtSTDMin.setEnabled(true);
				theView.txtSTDMax.setEnabled(true);
				theView.txtAlignCorSNR.setEnabled(true);
			}
		}
	}

	class ChbAlignCor2Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!theView.chbAlignCor2.isSelected()) {
				theView.txtAlignCor2.setEnabled(false);
				theView.chbAlignCor3.setEnabled(false);
				theView.txtAlignCor3.setEnabled(false);
			} else {
				theView.txtAlignCor2.setEnabled(true);
				theView.chbAlignCor3.setEnabled(true);
			}
		}
	}

	class ChbAlignCor3Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (!theView.chbAlignCor3.isSelected()) {
				theView.txtAlignCor3.setEnabled(false);
			} else {
				theView.txtAlignCor3.setEnabled(true);

			}
		}
	}

	class ChbCustomListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!theView.chbCustom.isSelected()) {
				theView.txtCustomMin.setEnabled(false);
				theView.txtCustomMax.setEnabled(false);
			} else {
				theView.txtCustomMin.setEnabled(true);
				theView.txtCustomMax.setEnabled(true);

			}

		}

	}

	class ChbNameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!theView.chbName.isSelected()) {
				theView.txtName.setEnabled(false);
			} else {
				theView.txtName.setEnabled(true);
			}
		}
	}

	class ChbPlaceListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!theView.chbWhere.isSelected()) {
				theView.txtPlace.setEnabled(false);
			} else {
				theView.txtPlace.setEnabled(true);
			}
		}
	}

	class ChbLabelSV implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!theView.chbLabels.isSelected()) {
				theView.txtLabelSV.setEnabled(false);
			} else {
				theView.txtLabelSV.setEnabled(true);
			}
		}
	}

	class ChbIsBrainListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!theView.chbIsBrainH.isSelected()) {
				theView.txtAlignCor1.setEnabled(true);
				theView.txtAlignCor2.setEnabled(true);
				theView.txtAlignCor3.setEnabled(true);
				theView.txtAlignCor1.setText("");
				theView.txtAlignCor2.setText("");
				theView.txtAlignCor3.setText("");
				theView.txtSTDMin.setEnabled(true);
				theView.txtSTDMin.setText("");
				theView.txtSTDMax.setEnabled(true);
				theView.txtSTDMax.setText("");
				theView.chbAlignCor2.setEnabled(true);
				theView.chbAlignCor2.setSelected(false);
				theView.chbAlignCor3.setEnabled(false);
				theView.chbAlignCor3.setSelected(false);

			} else {
				theView.txtAlignCor1.setEnabled(false);
				theView.txtAlignCor2.setEnabled(false);
				theView.chbAlignCor2.setSelected(true);
				theView.txtAlignCor3.setEnabled(false);
				theView.chbAlignCor3.setSelected(true);
				theView.txtAlignCor1.setText("3.21");
				theView.txtAlignCor2.setText("3.03");
				theView.txtAlignCor3.setText("1.29");
				theView.txtSTDMin.setEnabled(false);
				theView.txtSTDMin.setText("0");
				theView.txtSTDMax.setEnabled(false);
				theView.txtSTDMax.setText("Min");
				theView.chbAlignCor2.setEnabled(false);
			}
		}
	}

	class ChbKeyWordListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!theView.chbKeyWords.isSelected()) {
				theView.txtKeyWord.setEnabled(false);
			} else {
				theView.txtKeyWord.setEnabled(true);
			}
		}
	}

	class TabIsMVListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			if (mrui.isMrsi()) {
				theView.tabbedPane.setEnabledAt(2, true);
				theView.chbLabels.setEnabled(false);

			} else {
				theView.tabbedPane.setEnabledAt(2, false);
				theView.chbLabels.setEnabled(true);

			}

		}

		@Override
		public void focusLost(FocusEvent e) {
			if (mrui.isMrsi()) {
				theView.panLabelsMV.setEnabled(true);
				theView.chbLabels.setEnabled(false);
				theView.txtLabelSV.setEnabled(false);
				theView.btng.goToDefault();
			} else {
				theView.panLabelsMV.setEnabled(false);
				theView.chbLabels.setEnabled(true);

			}

		}
	}

	// Higher level stuff -yea"higher" :))

	// Load pre-existent configuration file
	class ButLoadListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"XML files", "xml");
			chooser.setFileFilter(filter);
			chooser.setDialogTitle("Load Config. File");
			chooser.setApproveButtonText("Load Config. File");
			chooser.setMultiSelectionEnabled(false);
			chooser.setCurrentDirectory(new File(mrui.getPreferences().lastWorkDirectory)); 

			int returnVal = chooser.showOpenDialog(theView);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				config = chooser.getSelectedFile();

			}

			if(returnVal == JFileChooser.CANCEL_OPTION)
				return; 

			// ddf

			File preProcessingFile = chooser.getSelectedFile();

			if (preProcessingFile.getName().endsWith(".xml")) {

				try {
					DOMParser parser = new DOMParser();
					parser.parse("file:" + preProcessingFile.getAbsolutePath());
					Document doc = parser.getDocument();
					doc.normalize();
					PreproSchema xmlLoad = new PreproSchema(doc, mrui);
					xmlLoad.loadPreproSchma(theView);

				} catch (Exception e1) {

					JOptionPane
							.showMessageDialog(theView,
									"Something is wrong with the Preprocessing XML file");
					e1.printStackTrace();
				}

			}
			mrui.getPreferences().lastWorkDirectory = preProcessingFile.getParent().toString(); 


		}
	}

	// Save your config file to the folder of your choice
	class ButSaveConfigListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			JFileChooser fChooser = new JFileChooser();

			fChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					if (f.isFile())
						return f.getName().endsWith(".xml");
					else if (f.isDirectory())
						return true;
					else
						return false;
				}

				public String getDescription() {
					return ("canonical XML format (*.xml)");
				}
			});

			fChooser.setDialogType(JFileChooser.SAVE_DIALOG);
			fChooser.setDialogTitle("Save Config. File");
			fChooser.setApproveButtonText("Save Config. File");
			
			fChooser.setCurrentDirectory(new File(mrui.getPreferences().lastWorkDirectory)); 
			
			int returnVal = fChooser.showSaveDialog(theView);
			File saveFile = null;
			if (returnVal == JFileChooser.APPROVE_OPTION)
				saveFile = fChooser.getSelectedFile();
			if (returnVal == JFileChooser.CANCEL_OPTION)
				return;

			if (saveFile != null) {

				FileOutputStream fw = null;
				try {
					String filename = saveFile.getPath();
					if (!filename.endsWith(".xml"))
						filename += ".xml";
					fw = new FileOutputStream(filename);

					// Creating an empty XML Document
					DocumentBuilderFactory dbfac = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
					org.w3c.dom.Document doc = docBuilder.newDocument();

					// Creating the XML tree

					PreproSchema xmlDoc = new PreproSchema(doc, mrui);

					// Output the XML
					TransformerFactory transfac = TransformerFactory
							.newInstance();
					Transformer trans = transfac.newTransformer();
					trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
					trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
							"no");
					trans.setOutputProperty(OutputKeys.INDENT, "yes");

					// create string from XML tree
					StringWriter sw = new StringWriter();
					StreamResult result = new StreamResult(sw);
					DOMSource source = new DOMSource(xmlDoc.prepare4Save(
							theView, fw));
					trans.transform(source, result);

					if (sw != null)
						fw.write(sw.toString().getBytes());

					fw.close();
					mrui.getPreferences().lastWorkDirectory = saveFile.getParent().toString(); 

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(theView, "File not saved. "
							+ ex.getMessage(), "Alert",
							JOptionPane.INFORMATION_MESSAGE);
					ex.printStackTrace();
					return;
				}
			}
		}
	}

	// Preview how the pipeline influences your signal
	class ButPreviewOriginalListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {
				if (theView.butPreviewOriginal.isSelected()) {
					theView.butPreviewOriginal.setText("Original");



					try {
						PreprocessData.Prepo(theView, theModel, mrui);
					} catch (Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(theView, ex.getMessage());

					}
				} else {
					theView.butPreviewOriginal.setText("Preview");
					((MruiImpl) mrui).Mode1D(cloneData(data));
					((Mode1DWinImpl) (mrui.getMainWindow().get1DWindow()))
							.resetContent();
				}
			} catch (Exception e1) {
				JOptionPane
						.showMessageDialog(
								theView,
								"One of the operations you are trying to perform is illegal. Please check to see if all the fields you have selected are reasonably completed");

				e1.printStackTrace();
			}

		}
	}

	// export

	class ButExportListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {



			JFileChooser fChooser = new JFileChooser();

			fChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					if (f.isFile())
						return f.getName().endsWith(".xml");
					else if (f.isDirectory())
						return true;
					else
						return false;
				}

				public String getDescription() {
					return ("*.xml");
				}
			});

			fChooser.setDialogType(JFileChooser.SAVE_DIALOG);
			fChooser.setDialogTitle("Export Results");
			fChooser.setApproveButtonText("ExportResults");
			fChooser.setCurrentDirectory(new File(mrui.getPreferences().lastWorkDirectory)); 


			int returnVal = fChooser.showSaveDialog(theView);
			File saveFile = null;
			if (returnVal == JFileChooser.APPROVE_OPTION)
				saveFile = fChooser.getSelectedFile();
			if (returnVal == JFileChooser.CANCEL_OPTION)
				return;

			if (saveFile != null) {

				FileOutputStream fw = null;
				try {
					String filename = saveFile.getPath();
					if (!filename.endsWith(".xml"))
						filename += ".xml";
					fw = new FileOutputStream(filename);

					// Creating an empty XML Document
					DocumentBuilderFactory dbfac = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
					org.w3c.dom.Document doc = docBuilder.newDocument();

					// Creating the XML tree

					// doc = xmlDoc.prepare4Save(theView, fw);

					// export processed data
					if (theView.chbName.isSelected()
							&& !theView.txtName.getText().equals("")
							&& theView.chbWhere.isSelected()
							&& !theView.txtPlace.getText().equals("")) {

						// Root node and attributes
						Element dataset = doc.createElement("DATASET");
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date today = new Date();
						dataset.setAttribute("CreatedBy", "jMRUI2XML");
						dataset.setAttribute("Version", "1.0");
						dataset.setAttribute("Date", formatter.format(today));
						// For building the Preprocessing node
						PreproSchema xmlDoc = new PreproSchema(doc, mrui);

						if (theView.butPreviewOriginal.isSelected()) {
							// Data has already been processed
							// (mrui.getData().fft has already been modified)
							// Generate Preprocessing Child for XML
							Element preProcessing = xmlDoc.prepare(theView, fw);
							mrui.getMainWindow().get1DWindow().getGraph()
									.calculatePreprocessingButKeepView(true);
							dataset.appendChild(preProcessing);

						} else {
							// Process data
							try {
								PreprocessData.Prepo(theView, theModel, mrui);
								mrui.getMainWindow()
										.get1DWindow()
										.getGraph()
										.calculatePreprocessingButKeepView(true);
							} catch (Exception ex) {
								ex.printStackTrace();
								JOptionPane.showMessageDialog(theView,
										ex.getMessage());
							}
							// Generate Preprocessing Child for XML
							Element preProcessing = xmlDoc.prepare(theView, fw);
							dataset.appendChild(preProcessing);
						}

						// data has been processed maxPPM and minPPM should be
						// stable now
						Utils u = new Utils(mrui);
						GetSNR snr = null;
						String[][] labelz = null;
						if (mrui.isMrsi()) {
							labelz = theView.getLabelz();

						}
						// snr is computed for all cases at once so it needs to
						// be outside the "voxel loop"
						if (theView.chbSNR.isSelected()) {
							snr = new GetSNR(mrui, pluginInfo);
							String macro = String.format("%f %f", theView
									.getAnyIntervalNumber(theView.txtSTDMin,
											theView.txtSTDMax)[0], theView
									.getAnyIntervalNumber(theView.txtSTDMin,
											theView.txtSTDMax)[1]);

							BatchTokenizer commandTokenizer = new BatchTokenizer(
									macro);
							snr.batchPerform(commandTokenizer);

						}

						// NEW XML
						Element grid = doc.createElement("Grid");
						if (!mrui.isMrsi()) {
							Element voxel = doc.createElement("Voxel");
							double[][] fid = mrui.getData().getSignal(0);
							double[][] fft = Fft.getInstance()
									.direct_1D_Estim_fft(fid, true);
							ArrayList<Double> temp = new ArrayList<Double>();// for
							// keeping
							// the
							// fft
							if (theView.chbCustom.isSelected()) {
								int firstPPMIndex = PPM2Point
										.convertExport(
												theView.getAnyIntervalNumber(
														theView.txtCustomMin,
														theView.txtCustomMax)[0],
												u.getMaxPPM(),
												u.getMinPPM(),
												mrui.getData().getInitialFFT(0)[0].length);
								int lastPPMIndex = PPM2Point
										.convertExport(
												theView.getAnyIntervalNumber(
														theView.txtCustomMin,
														theView.txtCustomMax)[1],
												u.getMaxPPM(),
												u.getMinPPM(),
												mrui.getData().getInitialFFT(0)[0].length);
								voxel.setAttribute("FirstPPM", String
										.valueOf(theView.getAnyIntervalNumber(
												theView.txtCustomMin,
												theView.txtCustomMax)[0]));
								voxel.setAttribute("LastPPM", String
										.valueOf(theView.getAnyIntervalNumber(
												theView.txtCustomMin,
												theView.txtCustomMax)[1]));

								for (int k = lastPPMIndex + 1; k > firstPPMIndex + 1; k--) {
									// output inverted to fit SC needs
									temp.add(fft[0][k]);
								}
								// enforce exact nr of points as in 'change nr
								// ponts in spec range'
								if (theView.chbNrPoints.isSelected()) {
									if ((theView.txtNrPointsMin.getText()
											.equals(theView.txtCustomMin
													.getText()) && theView.txtNrPointsMax
											.getText().equals(
													theView.txtCustomMax
															.getText()))
											|| (theView.txtNrPointsMin
													.getText()
													.equals(theView.txtCustomMax
															.getText()) && theView.txtNrPointsMax
													.getText()
													.equals(theView.txtCustomMin
															.getText()))) {

										if (temp.size() != Double
												.valueOf(theView.txtNrPoints
														.getText())) {

											for (int j = 0; j <= Math
													.abs(temp.size()
															- Double.valueOf(theView.txtNrPoints
																	.getText())); j++) {
												temp.add((double) 0);

											}
										}

									}
								}
								voxel.setAttribute("PointsNumber",
										String.valueOf(temp.size()));

							} else {
								voxel.setAttribute("FirstPPM",
										String.valueOf(u.getMinPPM()));
								voxel.setAttribute("LastPPM",
										String.valueOf(u.getMaxPPM()));
								voxel.setAttribute("PointsNumber", String
										.valueOf(mrui.getData().getLength()));

								for (int k = mrui.getData().getLength() - 1; k > -1; k--) {

									temp.add(fft[0][k]);

								}
							}

							if (theView.chbSNR.isSelected()) {

								voxel.setAttribute("SNR", Double
										.toString(snr.SNR4EaxhVoxel.get(0)));
							}
							Element tissue = doc.createElement("Tissue");
							if (theView.chbLabels.isSelected()
									&& theView.txtLabelSV.getText().length() != 0) {
								tissue.setAttribute("Type",
										theView.txtLabelSV.getText());
							} else {
								tissue.setAttribute("Type", "***");
							}
							voxel.appendChild(tissue);

							Element points = doc.createElement("Points");
							points.setTextContent(temp.toString()
									.substring(1, temp.toString().length() - 1)
									.replaceAll(",", ""));
							voxel.appendChild(points);
							dataset.appendChild(voxel);

						} else {

							for (int fidIndex = 0; fidIndex < mrui.getData()
									.getSignalsNb(); fidIndex++) {
								Element voxel = doc.createElement("Voxel");

								// Tissue element
								Element tissue = doc.createElement("Tissue");
								int x = fidIndex
										% mrui.getData().getDataWidthForMrsi()
										+ 1;
								int y = mrui.getData().getDataHeightForMrsi()
										- fidIndex
										/ mrui.getData().getDataWidthForMrsi();

								tissue.setAttribute("Type",
										labelz[x - 1][y - 1]);
								voxel.appendChild(tissue);

								// MAP position
								voxel.setAttribute("Xaxis", String.valueOf(x));
								voxel.setAttribute("Yaxis", String.valueOf(y));
								voxel.setAttribute("Zaxis", "0");

								// SNR
								if (theView.chbSNR.isSelected()) {

									voxel.setAttribute("SNR", Double
											.toString(snr.SNR4EaxhVoxel
													.get(fidIndex)));
								}

								// / FIRST LAST PPM AND NR POINTS
								double[][] fid = mrui.getData().getSignal(
										fidIndex);
								double[][] fft = Fft.getInstance()
										.direct_1D_Estim_fft(fid, true);
								ArrayList<Double> temp = new ArrayList<Double>();// for
								// keeping
								// the
								// fft
								if (theView.chbCustom.isSelected()) {
									int firstPPMIndex = PPM2Point
											.convertExport(
													theView.getAnyIntervalNumber(
															theView.txtCustomMin,
															theView.txtCustomMax)[0],
													u.getMaxPPM(),
													u.getMinPPM(),
													mrui.getData()
															.getInitialFFT(0)[0].length);
									int lastPPMIndex = PPM2Point
											.convertExport(
													theView.getAnyIntervalNumber(
															theView.txtCustomMin,
															theView.txtCustomMax)[1],
													u.getMaxPPM(),
													u.getMinPPM(),
													mrui.getData()
															.getInitialFFT(0)[0].length);
									voxel.setAttribute(
											"FirstPPM",
											String.valueOf(theView
													.getAnyIntervalNumber(
															theView.txtCustomMin,
															theView.txtCustomMax)[0]));
									voxel.setAttribute(
											"LastPPM",
											String.valueOf(theView
													.getAnyIntervalNumber(
															theView.txtCustomMin,
															theView.txtCustomMax)[1]));

									for (int k = lastPPMIndex + 1; k > firstPPMIndex + 1; k--) {
										// output inverted to fit SC needs
										temp.add(fft[0][k]);
									}
									// enforce exact nr of points as in 'change
									// nr
									// ponts in spec range'
									if (theView.chbNrPoints.isSelected()) {
										if ((theView.txtNrPointsMin.getText()
												.equals(theView.txtCustomMin
														.getText()) && theView.txtNrPointsMax
												.getText().equals(
														theView.txtCustomMax
																.getText()))
												|| (theView.txtNrPointsMin
														.getText()
														.equals(theView.txtCustomMax
																.getText()) && theView.txtNrPointsMax
														.getText()
														.equals(theView.txtCustomMin
																.getText()))) {

											if (temp.size() != Double
													.valueOf(theView.txtNrPoints
															.getText())) {

												for (int j = 0; j <= Math
														.abs(temp.size()
																- Double.valueOf(theView.txtNrPoints
																		.getText())); j++) {
													temp.add((double) 0);

												}
											}

										}
									}
									voxel.setAttribute("PointsNumber",
											String.valueOf(temp.size()));

								} else {
									voxel.setAttribute("FirstPPM",
											String.valueOf(u.getMinPPM()));
									voxel.setAttribute("LastPPM",
											String.valueOf(u.getMaxPPM()));
									voxel.setAttribute("PointsNumber",
											String.valueOf(mrui.getData()
													.getLength()));

									for (int k = mrui.getData().getLength() - 1; k > -1; k--) {

										temp.add(fft[0][k]);

									}
								}

								Element points = doc.createElement("Points");
								points.setTextContent(temp
										.toString()
										.substring(1,
												temp.toString().length() - 1)
										.replaceAll(",", ""));
								voxel.appendChild(points);

								// /////
								grid.appendChild(voxel);
							}
							dataset.appendChild(grid);
						}
						// // OLD XML
						// // build Case(s) nodes- 1 for each voxel/fidIndex
						// for (int fidIndex = 0; fidIndex < mrui.getData()
						// .getSignalsNb(); fidIndex++) {
						// Element currentCase = doc.createElement("Voxel");
						// currentCase.setAttribute("ID",
						// formatter.format(today));
						//
						// // Tissue\Label node (add to current case)
						// Element tissue = doc.createElement("Tissue");
						// // Compute map positions according to matrix
						// // notation
						// int x, y;
						// if (!mrui.isMrsi()) {
						// x = 1;
						// y = 1;
						// } else {
						// x = fidIndex
						// % mrui.getData().getDataWidthForMrsi()
						// + 1;
						// y = mrui.getData().getDataHeightForMrsi()
						// - fidIndex
						// / mrui.getData().getDataWidthForMrsi();
						// }
						// // setting the label according to case (SV/MV)
						// if (mrui.isMrsi()) {
						// tissue.setAttribute("Type",
						// labelz[x - 1][y - 1]);
						// } else {
						// if (theView.chbLabels.isSelected()
						// && theView.txtLabelSV.getText()
						// .length() != 0) {
						// tissue.setAttribute("Type",
						// theView.txtLabelSV.getText());
						// } else {
						// tissue.setAttribute("Type", "***");
						// }
						// }
						// currentCase.appendChild(tissue);
						// // Spectrum node(add to current case)
						// Element spectrum = doc.createElement("Spectrum");
						// // Parameters node(add to spectrum node)
						// Element parameters = doc
						// .createElement("Parameters");
						// // add corresponding SNR
						// if (theView.chbSNR.isSelected()) {
						//
						// parameters.setAttribute("SNR", Double
						// .toString(snr.SNR4EaxhVoxel
						// .get(fidIndex)));
						// }
						//
						// // compute number of points and first/last ppm
						// // also store the fft points
						// double[][] fid = mrui.getData().getSignal(fidIndex);
						// double[][] fft = Fft.getInstance()
						// .direct_1D_Estim_fft(fid, true);
						// ArrayList<Double> temp = new ArrayList<Double>();//
						// for
						// // keeping
						// // the
						// // fft
						// if (theView.chbCustom.isSelected()) {
						// int firstPPMIndex = PPM2Point
						// .convertExport(
						// theView.getAnyIntervalNumber(
						// theView.txtCustomMin,
						// theView.txtCustomMax)[0],
						// u.getMaxPPM(),
						// u.getMinPPM(),
						// mrui.getData().getInitialFFT(0)[0].length);
						// int lastPPMIndex = PPM2Point
						// .convertExport(
						// theView.getAnyIntervalNumber(
						// theView.txtCustomMin,
						// theView.txtCustomMax)[1],
						// u.getMaxPPM(),
						// u.getMinPPM(),
						// mrui.getData().getInitialFFT(0)[0].length);
						// parameters.setAttribute("FirstPPM", String
						// .valueOf(theView.getAnyIntervalNumber(
						// theView.txtCustomMin,
						// theView.txtCustomMax)[0]));
						// parameters.setAttribute("LastPPM", String
						// .valueOf(theView.getAnyIntervalNumber(
						// theView.txtCustomMin,
						// theView.txtCustomMax)[1]));
						//
						// for (int k = lastPPMIndex + 1; k > firstPPMIndex + 1;
						// k--) {
						// // output inverted to fit SC needs
						// temp.add(fft[0][k]);
						// }
						// // enforce exact nr of points as in 'change nr
						// // ponts in spec range'
						// if (theView.chbNrPoints.isSelected()) {
						// if ((theView.txtNrPointsMin.getText()
						// .equals(theView.txtCustomMin
						// .getText()) && theView.txtNrPointsMax
						// .getText().equals(
						// theView.txtCustomMax
						// .getText()))
						// || (theView.txtNrPointsMin
						// .getText()
						// .equals(theView.txtCustomMax
						// .getText()) && theView.txtNrPointsMax
						// .getText()
						// .equals(theView.txtCustomMin
						// .getText()))) {
						//
						// if (temp.size() != Double
						// .valueOf(theView.txtNrPoints
						// .getText())) {
						//
						// for (int j = 0; j <= Math
						// .abs(temp.size()
						// - Double.valueOf(theView.txtNrPoints
						// .getText())); j++) {
						// temp.add((double) 0);
						//
						// }
						// }
						//
						// }
						// }
						// parameters.setAttribute("PointsNumber",
						// String.valueOf(temp.size()));
						//
						// } else {
						// parameters.setAttribute("FirstPPM",
						// String.valueOf(u.getMinPPM()));
						// parameters.setAttribute("LastPPM",
						// String.valueOf(u.getMaxPPM()));
						// parameters.setAttribute("PointsNumber", String
						// .valueOf(mrui.getData().getLength()));
						//
						// for (int k = mrui.getData().getLength() - 1; k > -1;
						// k--) {
						//
						// temp.add(fft[0][k]);
						//
						// }
						// }
						// spectrum.appendChild(parameters);
						// // Points node add to spectrum node
						// Element points = doc.createElement("Points");
						// points.setTextContent(temp.toString()
						// .substring(1, temp.toString().length() - 1)
						// .replaceAll(",", ""));
						// spectrum.appendChild(points);
						//
						// // map position node add to spectrum node
						// Element map = doc.createElement("MapPosition");
						// map.setAttribute("Xaxis", String.valueOf(x));
						//
						// map.setAttribute("Yaxis", String.valueOf(y));
						// map.setAttribute("Zaxis", "0");
						// spectrum.appendChild(map);
						// currentCase.appendChild(spectrum);
						//
						// // needs to be inside the loop - adding one voxel at
						// // a time
						// dataset.appendChild(currentCase);
						//
						// }// ending voxel loop

						doc.appendChild(dataset);
						// Output the XML
						TransformerFactory transfac = TransformerFactory
								.newInstance();
						Transformer trans = transfac.newTransformer();
						trans.setOutputProperty(
OutputKeys.ENCODING, "UTF-8");
						trans.setOutputProperty(
								OutputKeys.OMIT_XML_DECLARATION, "no");
						trans.setOutputProperty(OutputKeys.INDENT, "yes");

						// create string from XML tree
						StringWriter sw = new StringWriter();
						StreamResult result = new StreamResult(sw);
						DOMSource source = new DOMSource(doc);
						trans.transform(source, result);

						if (sw != null)
							fw.write(sw.toString().getBytes());

						fw.close();

					} else {
						JOptionPane
								.showMessageDialog(
										theView,
										"In order to export you MUST state your name and the place where you are processing the data",
										"Alert",
										JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(theView, "File not saved. "
							+ ex.getMessage(), "Alert",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				mrui.getPreferences().lastWorkDirectory = saveFile.getParent().toString(); 

			}
		}
	}

	Data cloneData(Data source) {
		Data data = null;
		try {
			data = (Data) (source.clone());
		} catch (CloneNotSupportedException e) {

			e.printStackTrace();
		}
		return data;

	}

	public static void onClose() {
		data = null;
	}

}
