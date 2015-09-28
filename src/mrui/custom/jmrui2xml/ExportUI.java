package mrui.custom.jmrui2xml;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import mrui.Mrui;
import mrui.preprocessing.settozero.Utils;

public class ExportUI extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constant font
	Font titeledBorderFont = new Font("Arial", Font.BOLD + Font.ITALIC, 12);

	// tab that gets a nice listener
	JTabbedPane tabbedPane;

	// SetRef
	JPanel panSetRef;
	JCheckBox chbSetRef;
	FeatureTextField txtSetRef;

	// Apo
	JPanel panApo;
	JCheckBox chbApo;
	JRadioButton raApoLore;
	JRadioButton raApoGaus;
	FeatureTextField txtApo;

	// WaterFiltering
	JPanel panWF;
	JCheckBox chbHLSVD;
	FeatureTextField txtHLSVDMin1;
	FeatureTextField txtHLSVDMax1;
	JCheckBox chbHLSVD2;
	FeatureTextField txtHLSVDMin2;
	FeatureTextField txtHLSVDMax2;
	JCheckBox chbHLSVD3;
	FeatureTextField txtHLSVDMin3;
	FeatureTextField txtHLSVDMax3;
	FeatureTextField txtHLSVDNrLore;

	// BaselineCorrection
	JPanel panBaseCor;
	JCheckBox chbBaseCor;
	FeatureTextField txtBaseCorMin1;
	FeatureTextField txtBaseCorMax1;
	JCheckBox chbBaseCor2;
	FeatureTextField txtBaseCorMin2;
	FeatureTextField txtBaseCorMax2;
	JCheckBox chbBaseCor3;
	FeatureTextField txtBaseCorMin3;
	FeatureTextField txtBaseCorMax3;

	// SetToZero
	JPanel panStZ;
	JCheckBox chbSetToZero;
	FeatureTextField txtSetToZeroMin1;
	FeatureTextField txtSetToZeroMax1;
	JCheckBox chbSetToZero2;
	FeatureTextField txtSetToZeroMin2;
	FeatureTextField txtSetToZeroMax2;

	// ChangeNumberOfPointsInInterval
	JPanel panNrPoints;
	JCheckBox chbNrPoints;
	FeatureTextField txtNrPoints;
	FeatureTextField txtNrPointsMin;
	FeatureTextField txtNrPointsMax;

	// Controls: Load/Save/Export
	JPanel panControl;
	JButton butSave;
	JButton butLoad;
	JToggleButton butPreviewOriginal;
	JButton butExport;

	JPanel panUL;
	JCheckBox chbUL;

	// Alignment Correction
	JPanel panAlignCor;
	JCheckBox chbAlignCor;
	FeatureTextField txtAlignCor1;
	JCheckBox chbAlignCor2;
	FeatureTextField txtAlignCor3;
	JCheckBox chbAlignCor3;
	FeatureTextField txtAlignCor2;
	FeatureTextField txtAlignCorSNR;
	JCheckBox chbIsBrainH;
	FeatureTextField txtSTDMin;
	FeatureTextField txtSTDMax;

	// Additional Information
	JPanel panAditional;
	JCheckBox chbSNR;
	JCheckBox chbLabels;
	JTextField txtLabelSV;
	JTextField txtName;
	JTextField txtPlace;
	JCheckBox chbWhere;
	JCheckBox chbName;
	JTextField txtKeyWord;
	JCheckBox chbKeyWords;

	JPanel panLabelsMV;
	private JScrollPane scrollPane;
	JEditorPane editorPane;
	ButtonGrid btng;
	FeatureTextField txtCustomMin;
	FeatureTextField txtCustomMax;
	JCheckBox chbCustom;
	Mrui mrui;

	public ExportUI(Mrui mrui) {
		this.mrui = mrui;
		initComponents(mrui);
	}

	private void initComponents(Mrui mrui) {

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JFrame frame = (JFrame) e.getSource();
				Controller.onClose();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}

		});
		//MJ
		setAlwaysOnTop(true);
		setTitle("JMRUI2XML");
		//MJ
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setMinimumSize(new java.awt.Dimension(900, 720));
		
		setSize(new java.awt.Dimension(900, 720));
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		tabbedPane.setBackground(new Color(240, 240, 240));
		tabbedPane.setBounds(10, 11, 832, 644);
		getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Preprocessing", null, panel, null);
		panel.setLayout(null);

		// Set Reference Panel
		JPanel panelSetRef = new JPanel();
		panelSetRef.setBounds(7, 7, 351, 63);
		panelSetRef
				.setBorder(new TitledBorder(UIManager
						.getBorder("TitledBorder.border"), "I - Set Reference",
						TitledBorder.LEADING, TitledBorder.TOP,
						titeledBorderFont, null));
		panel.add(panelSetRef);
		panelSetRef.setLayout(null);

		chbSetRef = new JCheckBox("Set Reference");
		chbSetRef.setBounds(6, 18, 119, 23);
		panelSetRef.add(chbSetRef);

		txtSetRef = new FeatureTextField();
		txtSetRef.setBounds(131, 19, 60, 20);
		panelSetRef.add(txtSetRef);
		txtSetRef.setColumns(10);
		txtSetRef.setEnabled(false);

		JLabel lblPpm = new JLabel("ppm");
		lblPpm.setBounds(201, 22, 46, 14);
		panelSetRef.add(lblPpm);

		// Apodize
		JPanel panApo = new JPanel();
		panApo.setBounds(7, 74, 351, 79);

		panApo.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "II - Apodize",
				TitledBorder.LEADING, TitledBorder.TOP, titeledBorderFont, null));
		panel.add(panApo);
		panApo.setLayout(null);

		chbApo = new JCheckBox("Apodization");
		chbApo.setBounds(6, 17, 97, 23);
		panApo.add(chbApo);

		raApoGaus = new JRadioButton("Gaussian");
		raApoGaus.setBounds(105, 17, 83, 23);
		panApo.add(raApoGaus);
		raApoGaus.setEnabled(false);

		raApoLore = new JRadioButton("Lorentzian");
		raApoLore.setBounds(105, 43, 90, 23);
		panApo.add(raApoLore);
		raApoLore.setEnabled(false);

		txtApo = new FeatureTextField();
		txtApo.setBounds(194, 18, 60, 20);
		panApo.add(txtApo);
		txtApo.setColumns(10);
		txtApo.setEnabled(false);

		JLabel lblHz = new JLabel("Hz");
		lblHz.setBounds(264, 21, 31, 14);
		panApo.add(lblHz);

		// Water Filtering
		JPanel panWF = new JPanel();
		panWF.setBounds(7, 165, 351, 106);
		panWF.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "III - Water Filtering",
				TitledBorder.LEADING, TitledBorder.TOP, titeledBorderFont, null));
		panel.add(panWF);
		panWF.setLayout(null);

		chbHLSVD = new JCheckBox("HLSVD");
		chbHLSVD.setBounds(6, 20, 97, 23);
		panWF.add(chbHLSVD);

		txtHLSVDNrLore = new FeatureTextField();
		txtHLSVDNrLore.setBounds(20, 75, 60, 20);
		panWF.add(txtHLSVDNrLore);
		txtHLSVDNrLore.setColumns(2);
		txtHLSVDNrLore.setEnabled(false);

		JLabel lblNumberOfLorentzians = new JLabel("Nr of Lorentzians");
		lblNumberOfLorentzians.setBounds(6, 52, 99, 14);
		panWF.add(lblNumberOfLorentzians);

		txtHLSVDMin1 = new FeatureTextField();
		txtHLSVDMin1.setBounds(135, 20, 60, 20);
		panWF.add(txtHLSVDMin1);
		txtHLSVDMin1.setColumns(8);
		txtHLSVDMin1.setEnabled(false);

		txtHLSVDMax1 = new FeatureTextField();
		txtHLSVDMax1.setColumns(8);
		txtHLSVDMax1.setBounds(242, 20, 60, 20);
		panWF.add(txtHLSVDMax1);
		txtHLSVDMax1.setEnabled(false);

		txtHLSVDMin2 = new FeatureTextField();
		txtHLSVDMin2.setColumns(8);
		txtHLSVDMin2.setBounds(135, 46, 60, 20);
		panWF.add(txtHLSVDMin2);
		txtHLSVDMin2.setEnabled(false);

		txtHLSVDMax2 = new FeatureTextField();
		txtHLSVDMax2.setColumns(8);
		txtHLSVDMax2.setBounds(242, 46, 60, 20);
		panWF.add(txtHLSVDMax2);
		txtHLSVDMax2.setEnabled(false);

		txtHLSVDMin3 = new FeatureTextField();
		txtHLSVDMin3.setColumns(8);
		txtHLSVDMin3.setBounds(135, 73, 60, 20);
		panWF.add(txtHLSVDMin3);
		txtHLSVDMin3.setEnabled(false);

		txtHLSVDMax3 = new FeatureTextField();
		txtHLSVDMax3.setColumns(8);
		txtHLSVDMax3.setBounds(242, 73, 60, 20);
		panWF.add(txtHLSVDMax3);
		txtHLSVDMax3.setEnabled(false);

		JLabel lblTo = new JLabel("to");
		lblTo.setBounds(215, 23, 17, 14);
		panWF.add(lblTo);

		JLabel label = new JLabel("to");
		label.setBounds(215, 49, 17, 14);
		panWF.add(label);

		JLabel label_1 = new JLabel("to");
		label_1.setBounds(215, 76, 17, 14);
		panWF.add(label_1);

		JLabel lblPpm_1 = new JLabel("ppm");
		lblPpm_1.setBounds(312, 23, 29, 14);
		panWF.add(lblPpm_1);

		JLabel lblPpm_2 = new JLabel("ppm");
		lblPpm_2.setBounds(312, 49, 29, 14);
		panWF.add(lblPpm_2);

		JLabel lblPpm_3 = new JLabel("ppm");
		lblPpm_3.setBounds(312, 76, 29, 14);
		panWF.add(lblPpm_3);

		chbHLSVD2 = new JCheckBox();
		chbHLSVD2.setBounds(112, 45, 21, 23);
		panWF.add(chbHLSVD2);
		chbHLSVD2.setEnabled(false);

		chbHLSVD3 = new JCheckBox();
		chbHLSVD3.setBounds(112, 70, 21, 23);
		panWF.add(chbHLSVD3);
		chbHLSVD3.setEnabled(false);

		// Baseline Correction
		JPanel panBaseCor = new JPanel();
		panBaseCor.setBounds(7, 282, 351, 106);
		panBaseCor.setLayout(null);
		panBaseCor
				.setBorder(new TitledBorder(UIManager
						.getBorder("TitledBorder.border"),
						"IV - Baseline Correction", TitledBorder.LEADING,
						TitledBorder.TOP, titeledBorderFont, null));
		panel.add(panBaseCor);

		chbBaseCor = new JCheckBox("Baseline");
		chbBaseCor.setBounds(6, 20, 97, 23);
		panBaseCor.add(chbBaseCor);

		txtBaseCorMin1 = new FeatureTextField();
		txtBaseCorMin1.setColumns(8);
		txtBaseCorMin1.setBounds(135, 20, 60, 20);
		panBaseCor.add(txtBaseCorMin1);
		txtBaseCorMin1.setEnabled(false);
		txtBaseCorMax1 = new FeatureTextField();
		txtBaseCorMax1.setColumns(8);
		txtBaseCorMax1.setBounds(242, 20, 60, 20);
		panBaseCor.add(txtBaseCorMax1);
		txtBaseCorMax1.setEnabled(false);

		txtBaseCorMin2 = new FeatureTextField();
		txtBaseCorMin2.setColumns(8);
		txtBaseCorMin2.setBounds(135, 46, 60, 20);
		panBaseCor.add(txtBaseCorMin2);
		txtBaseCorMin2.setEnabled(false);

		txtBaseCorMax2 = new FeatureTextField();

		txtBaseCorMax2.setColumns(8);
		txtBaseCorMax2.setBounds(242, 46, 60, 20);
		panBaseCor.add(txtBaseCorMax2);
		txtBaseCorMax2.setEnabled(false);

		txtBaseCorMin3 = new FeatureTextField();
		txtBaseCorMin3.setColumns(8);
		txtBaseCorMin3.setBounds(135, 73, 60, 20);
		panBaseCor.add(txtBaseCorMin3);
		txtBaseCorMin3.setEnabled(false);

		txtBaseCorMax3 = new FeatureTextField();
		txtBaseCorMax3.setColumns(8);
		txtBaseCorMax3.setBounds(242, 73, 60, 20);
		panBaseCor.add(txtBaseCorMax3);
		txtBaseCorMax3.setEnabled(false);

		JLabel label_3 = new JLabel("to");
		label_3.setBounds(215, 23, 17, 14);
		panBaseCor.add(label_3);

		JLabel label_4 = new JLabel("to");
		label_4.setBounds(215, 49, 17, 14);
		panBaseCor.add(label_4);

		JLabel label_5 = new JLabel("to");
		label_5.setBounds(215, 76, 17, 14);
		panBaseCor.add(label_5);

		JLabel label_6 = new JLabel("ppm");
		label_6.setBounds(312, 23, 29, 14);
		panBaseCor.add(label_6);

		JLabel label_7 = new JLabel("ppm");
		label_7.setBounds(312, 49, 29, 14);
		panBaseCor.add(label_7);

		JLabel label_8 = new JLabel("ppm");
		label_8.setBounds(312, 76, 29, 14);
		panBaseCor.add(label_8);

		JLabel lblCorrection = new JLabel("Correction");
		lblCorrection.setBounds(16, 45, 66, 14);
		panBaseCor.add(lblCorrection);

		chbBaseCor2 = new JCheckBox();
		chbBaseCor2.setBounds(114, 45, 21, 23);
		panBaseCor.add(chbBaseCor2);
		chbBaseCor2.setEnabled(false);

		chbBaseCor3 = new JCheckBox();
		chbBaseCor3.setBounds(114, 72, 21, 23);
		panBaseCor.add(chbBaseCor3);
		chbBaseCor3.setEnabled(false);

		// Set To Zero
		JPanel panStZ = new JPanel();
		panStZ.setBounds(7, 482, 351, 84);
		panStZ.setLayout(null);
		panStZ.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "VI - Set to zero",
				TitledBorder.LEADING, TitledBorder.TOP, titeledBorderFont, null));
		panel.add(panStZ);

		chbSetToZero = new JCheckBox("Set to zero");
		chbSetToZero.setBounds(6, 20, 123, 23);
		panStZ.add(chbSetToZero);

		txtSetToZeroMin1 = new FeatureTextField();
		txtSetToZeroMin1.setColumns(8);
		txtSetToZeroMin1.setBounds(135, 20, 60, 20);
		panStZ.add(txtSetToZeroMin1);
		txtSetToZeroMin1.setEnabled(false);

		txtSetToZeroMax1 = new FeatureTextField();
		txtSetToZeroMax1.setColumns(8);
		txtSetToZeroMax1.setBounds(242, 20, 60, 20);
		panStZ.add(txtSetToZeroMax1);
		txtSetToZeroMax1.setEnabled(false);

		txtSetToZeroMin2 = new FeatureTextField();
		txtSetToZeroMin2.setColumns(8);
		txtSetToZeroMin2.setBounds(135, 46, 60, 20);
		panStZ.add(txtSetToZeroMin2);
		txtSetToZeroMin2.setEnabled(false);

		txtSetToZeroMax2 = new FeatureTextField();
		txtSetToZeroMax2.setColumns(8);
		txtSetToZeroMax2.setBounds(242, 46, 60, 20);
		panStZ.add(txtSetToZeroMax2);
		txtSetToZeroMax2.setEnabled(false);

		JLabel label_2 = new JLabel("to");
		label_2.setBounds(215, 23, 17, 14);
		panStZ.add(label_2);

		JLabel label_9 = new JLabel("to");
		label_9.setBounds(215, 49, 17, 14);
		panStZ.add(label_9);

		JLabel label_11 = new JLabel("ppm");
		label_11.setBounds(312, 23, 29, 14);
		panStZ.add(label_11);

		JLabel label_12 = new JLabel("ppm");
		label_12.setBounds(312, 49, 29, 14);
		panStZ.add(label_12);

		chbSetToZero2 = new JCheckBox();
		chbSetToZero2.setBounds(112, 45, 21, 23);
		panStZ.add(chbSetToZero2);
		chbSetToZero2.setEnabled(false);

		// Custom Nr. Points
		JPanel panNrPoints = new JPanel();
		panNrPoints
				.setBorder(new TitledBorder(UIManager
						.getBorder("TitledBorder.border"),
						"V - Change nr points in specified range",
						TitledBorder.LEADING, TitledBorder.TOP,
						titeledBorderFont, null));
		panNrPoints.setBounds(7, 392, 351, 79);
		panel.add(panNrPoints);
		panNrPoints.setLayout(null);

		chbNrPoints = new JCheckBox("I want to have");
		chbNrPoints.setBounds(6, 17, 111, 23);
		panNrPoints.add(chbNrPoints);

		txtNrPoints = new FeatureTextField();
		txtNrPoints.setBounds(123, 18, 60, 20);
		panNrPoints.add(txtNrPoints);
		txtNrPoints.setColumns(4);
		txtNrPoints.setEnabled(false);

		JLabel lblPointsFrom = new JLabel("points from");
		lblPointsFrom.setBounds(195, 21, 67, 14);
		panNrPoints.add(lblPointsFrom);

		txtNrPointsMin = new FeatureTextField();
		txtNrPointsMin.setBounds(40, 47, 60, 20);
		panNrPoints.add(txtNrPointsMin);
		txtNrPointsMin.setColumns(10);
		txtNrPointsMin.setEnabled(false);

		JLabel lblPpmTo = new JLabel("ppm to");
		lblPpmTo.setBounds(110, 50, 60, 14);
		panNrPoints.add(lblPpmTo);

		txtNrPointsMax = new FeatureTextField();
		txtNrPointsMax.setColumns(10);
		txtNrPointsMax.setBounds(158, 47, 60, 20);
		panNrPoints.add(txtNrPointsMax);
		txtNrPointsMax.setEnabled(false);

		JLabel lblPpm_4 = new JLabel("ppm");
		lblPpm_4.setBounds(228, 50, 25, 14);
		panNrPoints.add(lblPpm_4);

		// Set To UL
		JPanel panUL = new JPanel();
		panUL.setLayout(null);
		panUL.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "VII - Normalization",
				TitledBorder.LEADING, TitledBorder.TOP, titeledBorderFont, null));
		panUL.setBounds(392, 7, 405, 48);
		panel.add(panUL);

		chbUL = new JCheckBox("l2 norm");
		chbUL.setBounds(6, 17, 191, 23);
		panUL.add(chbUL);

		// Alignment Correction
		JPanel panAlignCor = new JPanel();
		panAlignCor.setLayout(null);
		panAlignCor.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"VIII - Alignment Correction", TitledBorder.LEADING,
				TitledBorder.TOP, titeledBorderFont, null));
		panAlignCor.setBounds(392, 70, 405, 229);
		panel.add(panAlignCor);

		chbAlignCor = new JCheckBox("Alignment correction");
		chbAlignCor.setBounds(6, 20, 145, 23);
		panAlignCor.add(chbAlignCor);

		txtAlignCor1 = new FeatureTextField();
		txtAlignCor1.setColumns(8);
		txtAlignCor1.setBounds(245, 149, 60, 20);
		panAlignCor.add(txtAlignCor1);
		txtAlignCor1.setEnabled(false);

		txtAlignCor2 = new FeatureTextField();
		txtAlignCor2.setColumns(8);
		txtAlignCor2.setBounds(245, 175, 60, 20);
		panAlignCor.add(txtAlignCor2);
		txtAlignCor2.setEnabled(false);

		txtAlignCor3 = new FeatureTextField();
		txtAlignCor3.setColumns(8);
		txtAlignCor3.setBounds(245, 202, 60, 20);
		panAlignCor.add(txtAlignCor3);
		txtAlignCor3.setEnabled(false);

		JLabel label_15 = new JLabel("ppm");
		label_15.setBounds(315, 153, 29, 14);
		panAlignCor.add(label_15);

		JLabel label_16 = new JLabel("ppm");
		label_16.setBounds(315, 179, 29, 14);
		panAlignCor.add(label_16);

		JLabel label_17 = new JLabel("ppm");
		label_17.setBounds(315, 206, 29, 14);
		panAlignCor.add(label_17);

		JLabel lblToTheFollowing = new JLabel("to the following peaks");
		lblToTheFollowing.setBounds(30, 42, 135, 28);
		panAlignCor.add(lblToTheFollowing);

		chbAlignCor2 = new JCheckBox();
		chbAlignCor2.setBounds(223, 176, 21, 23);
		panAlignCor.add(chbAlignCor2);
		chbAlignCor2.setEnabled(false);

		chbAlignCor3 = new JCheckBox();
		chbAlignCor3.setBounds(223, 202, 21, 23);
		panAlignCor.add(chbAlignCor3);
		chbAlignCor3.setEnabled(false);

		JLabel lblMinSnr = new JLabel("min SNR:");
		lblMinSnr.setBounds(10, 81, 56, 14);
		panAlignCor.add(lblMinSnr);

		txtAlignCorSNR = new FeatureTextField();
		txtAlignCorSNR.setBounds(68, 78, 46, 20);
		panAlignCor.add(txtAlignCorSNR);
		txtAlignCorSNR.setColumns(10);
		txtAlignCorSNR.setEnabled(false);

		chbIsBrainH = new JCheckBox("isBrainH");
		chbIsBrainH.setBounds(153, 20, 76, 23);
		panAlignCor.add(chbIsBrainH);
		chbIsBrainH.setEnabled(false);

		txtSTDMin = new FeatureTextField();
		txtSTDMin.setBounds(153, 106, 60, 20);
		panAlignCor.add(txtSTDMin);
		txtSTDMin.setColumns(10);
		txtSTDMin.setEnabled(false);

		txtSTDMax = new FeatureTextField();
		txtSTDMax.setColumns(10);
		txtSTDMax.setBounds(279, 106, 60, 20);
		panAlignCor.add(txtSTDMax);
		txtSTDMax.setEnabled(false);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 134, 389, 2);
		panAlignCor.add(separator_1);

		JLabel lblLowPpm = new JLabel("ppm to");
		lblLowPpm.setBounds(223, 109, 46, 14);
		panAlignCor.add(lblLowPpm);

		JLabel lblUseStdFrom = new JLabel("use STD from the following region: ");
		lblUseStdFrom.setBounds(153, 81, 216, 14);
		panAlignCor.add(lblUseStdFrom);

		JLabel lblPpm_5 = new JLabel("ppm");
		lblPpm_5.setBounds(309, 109, 46, 14);
		panAlignCor.add(lblPpm_5);

		JLabel lblCreateAlignmentTo = new JLabel(
				"Create alignment to custom peaks");
		lblCreateAlignmentTo.setBounds(18, 153, 216, 14);
		panAlignCor.add(lblCreateAlignmentTo);

		// Aditional Information
		JPanel panAditional = new JPanel();
		panAditional.setLayout(null);
		panAditional.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"X - Additional Information", TitledBorder.LEADING,
				TitledBorder.TOP, titeledBorderFont, null));
		panAditional.setBounds(392, 380, 405, 168);
		panel.add(panAditional);

		chbLabels = new JCheckBox("Label");
		chbLabels.setBounds(6, 20, 80, 23);
		panAditional.add(chbLabels);

		txtLabelSV = new JTextField();
		txtLabelSV.setBounds(87, 21, 86, 20);
		panAditional.add(txtLabelSV);
		txtLabelSV.setColumns(15);
		txtLabelSV.setEnabled(false);

		chbSNR = new JCheckBox("SNR for each voxel");
		chbSNR.setBounds(6, 53, 180, 23);
		panAditional.add(chbSNR);

		chbName = new JCheckBox("User's Name");
		chbName.setBounds(182, 20, 106, 23);
		panAditional.add(chbName);

		chbWhere = new JCheckBox("Place ");
		chbWhere.setBounds(182, 53, 71, 23);
		panAditional.add(chbWhere);

		txtName = new JTextField();
		txtName.setBounds(298, 21, 86, 20);
		panAditional.add(txtName);
		txtName.setColumns(10);
		txtName.setEnabled(false);

		txtPlace = new JTextField();
		txtPlace.setBounds(298, 54, 86, 20);
		panAditional.add(txtPlace);
		txtPlace.setColumns(10);
		txtPlace.setEnabled(false);

		chbKeyWords = new JCheckBox("Key Words");
		chbKeyWords.setBounds(6, 89, 97, 23);
		panAditional.add(chbKeyWords);

		txtKeyWord = new JTextField();
		txtKeyWord.setBounds(105, 90, 279, 20);
		panAditional.add(txtKeyWord);
		txtKeyWord.setColumns(45);
		txtKeyWord.setEnabled(false);

		JLabel lblMaxCharacters = new JLabel("Max " + txtKeyWord.getColumns()
				+ " characters");
		lblMaxCharacters.setBounds(16, 111, 113, 14);
		panAditional.add(lblMaxCharacters);

		// Custom range
		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new TitledBorder(null,
				"IX - Custom ppm range for XML export", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_12.setBounds(392, 305, 405, 64);
		panel.add(panel_12);
		panel_12.setLayout(null);

		chbCustom = new JCheckBox("Only the range between");
		chbCustom.setBounds(6, 21, 175, 23);
		panel_12.add(chbCustom);

		txtCustomMin = new FeatureTextField();
		txtCustomMin.setEnabled(false);
		txtCustomMin.setColumns(8);
		txtCustomMin.setBounds(183, 24, 60, 20);
		panel_12.add(txtCustomMin);

		JLabel lablPpmTo = new JLabel("ppm to");
		lablPpmTo.setBounds(253, 28, 40, 14);
		panel_12.add(lablPpmTo);

		txtCustomMax = new FeatureTextField();
		txtCustomMax.setEnabled(false);
		txtCustomMax.setColumns(8);
		txtCustomMax.setBounds(296, 24, 60, 20);
		panel_12.add(txtCustomMax);

		JLabel laabel_2 = new JLabel("ppm");
		laabel_2.setBounds(366, 28, 29, 14);
		panel_12.add(laabel_2);

		// BUTTONS
		panControl = new JPanel();
		panControl.setBounds(472, 552, 234, 73);
		panel.add(panControl);

		butSave = new JButton("Save config");
		butSave.setBounds(7, 7, 104, 23);

		butLoad = new JButton("Load config");
		butLoad.setBounds(121, 7, 103, 23);

		butPreviewOriginal = new JToggleButton(" Preview");
		butPreviewOriginal.setBounds(7, 41, 104, 23);
		panControl.setLayout(null);
		panControl.add(butSave);
		panControl.add(butLoad);
		panControl.add(butPreviewOriginal);

		butExport = new JButton("Export");
		butExport.setBounds(120, 41, 104, 23);
		panControl.add(butExport);

		JPanel panMeta = new JPanel();
		tabbedPane.addTab("Meta Data", null, panMeta, null);
		panMeta.setLayout(null);

		JLabel lblObservationsbriefDescriptionOf = new JLabel(
				"Observations and Other: ");
		lblObservationsbriefDescriptionOf.setBounds(10, 11, 256, 14);
		panMeta.add(lblObservationsbriefDescriptionOf);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 66, 688, 480);
		panMeta.add(scrollPane);

		editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);

		panLabelsMV = new JPanel();
		tabbedPane.addTab("LabelsMV", null, panLabelsMV, null);
		panLabelsMV.setLayout(null);
		if (mrui.isMrsi()) {
			btng = new ButtonGrid(mrui.getData().getDataWidthForMrsi(), mrui
					.getData().getDataHeightForMrsi());
			panLabelsMV.add(btng);
			panLabelsMV.add(btng.externalPanel());
		}

		// MruiImpl m = new MruiImpl(true);
		// Mrsi mrsi = new Mrsi(m);
		// MrsiImage referenceImage = new MrsiImage();
		// MrsiImage mriImage = new MrsiImage();
		// mrsiMap = new MrsiMap(mrsi, referenceImage, mriImage);
		// // if (!mrsi.imagePresent)
		// // mrsiMap.transparency = 1;
		// // if (!mrsi.waterFilePresent)
		// // mrsiMap.transparency = 0;
		// mrsiMap.calculateImages();
		// panLabelsMV.add(mrsiMap, BorderLayout.CENTER);

	}

	public String[][] getLabelz() {
		return btng.getLabelz();
	}

	void addTabListener(FocusListener listen) {
		tabbedPane.addFocusListener(listen);
	}

	void addChbCustomListener(ActionListener listenForChbCustom) {
		chbCustom.addActionListener(listenForChbCustom);
	}

	void addChbSetRefListener(ActionListener listenForChbSetRef) {
		chbSetRef.addActionListener(listenForChbSetRef);
	}

	void addChbApoListener(ActionListener listenForChbApo) {
		chbApo.addActionListener(listenForChbApo);
	}

	void addRaApoLore(ActionListener listenForRaApoLore) {
		raApoLore.addActionListener(listenForRaApoLore);
	}

	void addRaApoGaus(ActionListener listenForRaApoGaus) {
		raApoGaus.addActionListener(listenForRaApoGaus);
	}

	void addChbHLSVDListener(ActionListener listenForChbHLSVD) {
		chbHLSVD.addActionListener(listenForChbHLSVD);
	}

	void addChbHLSVD2Listener(ActionListener listenForChbHLSVD2) {
		chbHLSVD2.addActionListener(listenForChbHLSVD2);
	}

	void addChbHLSVD3Listener(ActionListener listenForChbHLSVD3) {
		chbHLSVD3.addActionListener(listenForChbHLSVD3);
	}

	void addChbBaseCorListener(ActionListener listenForChbBaseCor) {
		chbBaseCor.addActionListener(listenForChbBaseCor);
	}

	void addChbBaseCor2Listener(ActionListener listenForChbBaseCor2) {
		chbBaseCor2.addActionListener(listenForChbBaseCor2);
	}

	void addChbBaseCor3Listener(ActionListener listenForChbBaseCor3) {
		chbBaseCor3.addActionListener(listenForChbBaseCor3);
	}

	void addChbSetToZeroListener(ActionListener listenForChbSetToZero) {
		chbSetToZero.addActionListener(listenForChbSetToZero);
	}

	void addChbSetToZero2Listener(ActionListener listenForChbSetToZero2) {
		chbSetToZero2.addActionListener(listenForChbSetToZero2);
	}

	void addChbSetNrPointsListener(ActionListener listenForChbSetNrPoints) {
		chbNrPoints.addActionListener(listenForChbSetNrPoints);
	}

	void addButSaveListener(ActionListener listenForButSave) {
		butSave.addActionListener(listenForButSave);
	}

	void addButLoadListener(ActionListener listenForButLoad) {
		butLoad.addActionListener(listenForButLoad);
	}

	void addButPreviewOriginalListener(
			ActionListener listenForButPreviewOriginal) {
		butPreviewOriginal.addActionListener(listenForButPreviewOriginal);

	}

	void addButExportListener(ActionListener listenForButExport) {
		butExport.addActionListener(listenForButExport);

	}

	void addChbAlignLinstener(ActionListener listenForChbAlign) {
		chbAlignCor.addActionListener(listenForChbAlign);
	}

	void addChbAlign2Linstener(ActionListener listenForChbAlign2) {
		chbAlignCor2.addActionListener(listenForChbAlign2);
	}

	void addChbAlign3Linstener(ActionListener listenForChbAlign3) {
		chbAlignCor3.addActionListener(listenForChbAlign3);
	}

	void addChbIsBrainHListener(ActionListener listenForBrainH) {
		chbIsBrainH.addActionListener(listenForBrainH);
	}

	void addChbNameListener(ActionListener listenForBrainH) {
		chbName.addActionListener(listenForBrainH);
	}

	void addChbPlaceListener(ActionListener listenForBrainH) {
		chbWhere.addActionListener(listenForBrainH);
	}

	void addChbSNRListener(ActionListener listenForSnr) {
		chbSNR.addActionListener(listenForSnr);
	}

	void addChbLabelListener(ActionListener listenForLabels) {
		chbLabels.addActionListener(listenForLabels);
	}

	void addChbKeyWordListener(ActionListener listenForKey) {
		chbKeyWords.addActionListener(listenForKey);
	}

	void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage);
	}

	public void setApoHz(String method, String Hz) {
		chbApo.setSelected(true);
		if (method == "Gaussian") {
			raApoGaus.setEnabled(true);
			raApoGaus.setSelected(true);
			raApoLore.setSelected(true);// just to be sure
			try {
				Integer.parseInt(Hz);
				txtApo.setEnabled(true);
				txtApo.setText(Hz);
			} catch (NumberFormatException ex) {

				displayErrorMessage("The apodization value needs o be an integer! Check the config file!");
				chbApo.setSelected(true);
				raApoGaus.setSelected(true);
				raApoGaus.setEnabled(true);
			}

		} else {
			if (method == "Lorenzian") {
				raApoLore.setEnabled(true);
				raApoLore.setSelected(true);
				raApoGaus.setSelected(true);// just to be sure
				try {
					Integer.parseInt(Hz);
					txtApo.setEnabled(true);
					txtApo.setText(Hz);
				} catch (NumberFormatException ex) {

					displayErrorMessage("The apodization value needs o be an integer! Check the config file!");
					chbApo.setSelected(true);
					raApoLore.setSelected(true);
					raApoLore.setEnabled(true);
				}
			} else {
				displayErrorMessage("Invalid apodization method! Check the config file!");
				chbApo.setSelected(true);

			}
		}

	}

	public void setHLSVD1Interval(String MinPPM, String MaxPPM) {
		try {
			if (Integer.parseInt(MinPPM) < Integer.parseInt(MaxPPM)) {
				chbHLSVD.setSelected(true);
				txtHLSVDMin1.setEnabled(true);
				txtHLSVDMax1.setEnabled(true);
				// don't know why but they get enabled when I call this
				chbHLSVD2.setEnabled(true);
				chbHLSVD3.setEnabled(true);
				txtHLSVDMin1.setText(MinPPM);
				txtHLSVDMax1.setText(MaxPPM);
			} else {
				chbHLSVD.setSelected(true);
				txtHLSVDMin1.setEnabled(true);
				txtHLSVDMax1.setEnabled(true);
				// don't know why but they get enabled when I call this
				chbHLSVD2.setEnabled(true);
				chbHLSVD3.setEnabled(true);
				txtHLSVDMin1.setText(MaxPPM);
				txtHLSVDMax1.setText(MinPPM);
			}

		} catch (NumberFormatException ex) {
			displayErrorMessage("HLSVD value(s) need to be a number! Check the config file!");
		}

	}

	// somee GETTERS

	public double getSetReference() {
		return (double) Double.valueOf(txtSetRef.getText());
	}

	public String getApodizationType() {
		if (raApoGaus.isSelected()) {
			return "Gaussian";
		} else if (raApoLore.isSelected()) {
			return "Lorentzian";
		} else
			return "";

	}

	public double getApodizationValue() {
		return Double.valueOf(txtApo.getText());
	}

	public int getNrLorenziansValue() {
		return Integer.valueOf(txtHLSVDNrLore.getText());
	}

	/**
	 * 
	 * @param Min
	 * @param Max
	 * @return a data vector with the minimum value of the fields at index 0 and
	 *         max at index 1...go for index 2 and get a !!!FREE!!! null pointer
	 *         exception. GOD, I LOVE JAVA...NOT
	 */
	public double[] getAnyIntervalNumber(JTextField Min, JTextField Max) {
		Utils u = new Utils(mrui);
		double[] value = new double[2];
		double min, max;
		min = Double.valueOf(Min.getText());
		if (Max.getText().equals("")) {
			max = u.getMinPPM();
		} else {
			max = Double.valueOf(Max.getText());
		}
		if (min <= max) {
			value[0] = min;
			value[1] = max;
		} else {
			value[0] = max;
			value[1] = min;
		}

		return value;

	}

}
