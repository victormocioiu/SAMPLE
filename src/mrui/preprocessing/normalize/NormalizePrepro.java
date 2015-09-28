package mrui.preprocessing.normalize;

//------------------------------------------------------------------------
//
//                  MRUI 98.0   TMR FMRX-CT97-0160
//
//------------------------------------------------------------------------
//                           NormalizePrepro.java
//------------------------------------------------------------------------
//
// HISTORY :
//------------------------------------------------------------------------
//|Date    |Author     |Method/Description                               |
//------------------------------------------------------------------------
//|03/99   |C.Couturier|creation                                         |
//------------------------------------------------------------------------
//
import mrui.Mrui;
import mrui.plugin.PluginInfo;
import mrui.plugin.preprocessing.PreprocessingPlugin;
import mrui.utils.BatchTokenizer;

/** this class allows to normalize the whole FIDs of Data */
public class NormalizePrepro extends PreprocessingPlugin {

  /** length of the FID to process */
  private int len;

  /** */
  private double factor;

  // ------------------------------------------------------------------------
  // constructor
  // ------------------------------------------------------------------------
  /**
   * constructor
   * 
   * @param mrui
   * @param output
   *          toolbar (for the historic)
   * @author C. Couturier
   * @version 1
   */
  public NormalizePrepro(Mrui mrui, PluginInfo pluginInfo) {
    super(mrui, pluginInfo);
  }

  // ------------------------------------------------------------------------
  // prepro()
  // ------------------------------------------------------------------------
  double[] minMax;

  // double aI, aR;
  double[][] dataRead;

  // ------------------------------------------------------------------------
  // histo()
  // ------------------------------------------------------------------------
  /**
   * This method records its work in the historic.
   * 
   * @return void
   * @author C. Couturier
   * @version 1
   */
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
    len = ((double[][]) (mrui.getData().getSignal(0)))[0].length;
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
    dataRead = mrui.getData().getSignal(fidIndex);
    minMax = mrui.getData().getMinMaxFIDAt(fidIndex);
    // aR = minMax[1] - minMax[0];
    // aI = minMax[3] - minMax[2];
    factor = minMax[5];
    for (int j = 0; j < len; j++) {
      dataRead[REAL][j] = dataRead[REAL][j] / factor;
      dataRead[IMAG][j] = dataRead[IMAG][j] / factor;
    }
    mrui.getData().setDataOfSignal(fidIndex, dataRead);
    return true;
  }
}
