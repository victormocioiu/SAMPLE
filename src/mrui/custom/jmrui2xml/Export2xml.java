package mrui.custom.jmrui2xml;

import javax.swing.JOptionPane;

import mrui.Mrui;
import mrui.plugin.PluginInfo;
import mrui.plugin.custom.CustomPlugin;

public class Export2xml extends CustomPlugin {

	public Export2xml(Mrui mrui, PluginInfo pluginInfo) {
		super(mrui, pluginInfo);

		if(mrui.getData()==null){
			JOptionPane.showMessageDialog(null,  "Empty dataset, in order to work with JMRUI2XML please load some data", "Empty dataset" , JOptionPane.ERROR_MESSAGE );
			return; 
		}
		
		ExportUI eui = new ExportUI(this.mrui);
		Model theModel = new Model(this.mrui, pluginInfo);

		Controller theController = new Controller(eui, theModel, mrui,
				pluginInfo);
		eui.setVisible(true);

	}

}
