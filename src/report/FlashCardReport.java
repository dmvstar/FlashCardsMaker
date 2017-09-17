package report;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.util.AbstractSampleApp;
import net.sf.jasperreports.engine.util.JRLoader;
import report.data.TransformDataSource;

public class FlashCardReport extends AbstractSampleApp {

	String reportName = "FlashKard_A4_4";
	String reportData = "flashCard.csv";
	
	//String our_jasper_template_jrxml = "reports/"+reportName+".jrxml";
	//String our_compiled_template_jasper = "reports/"+reportName+".jasper";
	
	TransformDataSource dataSource;
	
	private String mReportTemplateJrxml = "reports/"+reportName+".jrxml";
	private String mReportCompileJasper = "reports/"+reportName+".jasper";
	private String mReportPrintJasper   = "reports/"+reportName+".jrprint";
		
	private String mOutputPath =  mReportPrintJasper.replaceAll(".jrprint", "");
	private String mDataFilePath = "data/"+reportData;
	private JLabel mLblProgress = null;
	private String mReportPrintPdf = mReportPrintJasper.replaceAll(".jrprint", "");
		
	public FlashCardReport(){
	}
	
	public FlashCardReport(JLabel lblProgress) {
		mLblProgress  = lblProgress;
	}

	public void compile() throws JRException
	{
		long start = System.currentTimeMillis();
		JasperCompileManager.compileReportToFile(
				mReportTemplateJrxml,			//the path to the jrxml file to compile
				mReportCompileJasper);		//the path and name we want to save the 		
		System.err.println("Compilling time : " + (System.currentTimeMillis() - start));
	}

	public void path() throws JRException
	{
		System.out.println("FileNames: \n["
				+mDataFilePath+"]\n["
				+mReportTemplateJrxml+"]\n["
				+mReportCompileJasper+"]\n["
				+mReportPrintJasper+"]\n["
				+mReportPrintJasper.replaceAll(".jrprint", "")+"]");

		mReportCompileJasper = mReportTemplateJrxml.replaceAll(".jrxml", ".jasper");
		mReportPrintJasper = mReportCompileJasper.replaceAll("\\.jasper", ".jrprint");	
		mReportPrintPdf = mOutputPath;// mReportPrintJasper.replaceAll(".jrprint", "");
		
		System.out.println("FileNames: \n["
				+mDataFilePath+"]\n["
				+mReportTemplateJrxml+"]\n["
				+mReportCompileJasper+"]\n["
				+mReportPrintJasper+"]\n["
				+mReportPrintPdf+"]");
	}
	
	public void prepare() throws JRException
	{
		long start = System.currentTimeMillis();
		
		dataSource = new TransformDataSource(mDataFilePath);
		
		dataSource.transformFromTwo();
		
		System.err.println("Prepare time : " + (System.currentTimeMillis() - start));
		
	}

	
	public void fill() throws JRException
	{
		long start = System.currentTimeMillis();
		// data source filling
		{
			start = System.currentTimeMillis();
			/*
			JRCsvDataSource dataSource = new JRCsvDataSource(JRLoader.getLocationInputStream("data/flashCard.csv"));
			dataSource.setUseFirstRowAsHeader(true);
			dataSource.setRecordDelimiter("\r\n");
			//dataSource.setUseFirstRowAsHeader(true);
			//dataSource.setColumnNames(columnNames);
			*/
			/*
			*/
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", "Flashcard Report");
			parameters.put("DataFile", "flashCard.csv  - CSV data source");
			Set<String> states = new HashSet<String>();
			states.add("Active");
			states.add("Trial");
			parameters.put("IncludedStates", states);
			
			JasperFillManager.fillReportToFile(
					mReportCompileJasper, 
					mReportPrintJasper,
					parameters, dataSource);
			System.err.println("Filling time ["+reportName+"]: " + (System.currentTimeMillis() - start));
		}
		
	}
	
	public void pdf(int mode) throws JRException
	{
		/*
		File[] files = getFiles(new File("reports"), "jrprint");
		for(int i = 0; i < files.length; i++)
		{
			File reportFile = files[i];
			long start = System.currentTimeMillis();
			JasperExportManager.exportReportToPdfFile(
					reportFile.getAbsolutePath(), 
					reportFile.getPath().replaceAll(".jrprint", "")+"-"+mode+".pdf");
			System.err.println("Report PDF creation time : " + (System.currentTimeMillis() - start));
		}
		*/
		long start = System.currentTimeMillis();
		JasperExportManager.exportReportToPdfFile(
				mReportPrintJasper, 
				mReportPrintPdf+"-"+mode+".pdf");
		System.err.println("Report PDF creation time : " + (System.currentTimeMillis() - start));
		
		
	}
	
	public void print() throws JRException
	{
		File[] files = getFiles(new File("reports"), "jrprint");
		for(int i = 0; i < files.length; i++)
		{
			File reportFile = files[i];
			long start = System.currentTimeMillis();
			JasperPrintManager.printReport(reportFile.getAbsolutePath(), true);
			System.err.println("Report : " + reportFile + ". Printing time : " + (System.currentTimeMillis() - start));
		}
	}


	
	public void process(){
		
		
		
		try {
			if (mLblProgress != null) mLblProgress.setText("Compile...");
			compile();
			if (mLblProgress != null) mLblProgress.setText("Prepare...");
			prepare();
			dataSource.setMode(TransformDataSource.MODE_FRONT);
			if (mLblProgress != null) mLblProgress.setText("Fill 1...");
			fill();
			if (mLblProgress != null) mLblProgress.setText("Pdf 1..."+new File(mReportPrintPdf+"-"+TransformDataSource.MODE_FRONT+".pdf").getName());
			pdf(TransformDataSource.MODE_FRONT);
			
			//prepare();
			dataSource.reset();
			dataSource.setMode(TransformDataSource.MODE_BACK);
			if (mLblProgress != null) mLblProgress.setText("Fill 2...");
			fill();
			if (mLblProgress != null) mLblProgress.setText("Pdf 2..."+new File(mReportPrintPdf+"-"+TransformDataSource.MODE_BACK+".pdf").getName());
			pdf(TransformDataSource.MODE_BACK);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		FlashCardReport runner;
		runner = new FlashCardReport();
		runner.process();
	}

	@Override
	public void test() throws JRException {

		// TODO Auto-generated method stub

	}

	public void setDataFile(String aDefaultDataFile) {
		mDataFilePath = aDefaultDataFile;
		
	}

	public void setReportTemplate(String aReportTemplatePath) {
		mReportTemplateJrxml = aReportTemplatePath;
		
	}

	public void setOutputName(String aOutputPath) {
		mOutputPath = aOutputPath;
		
	}
	
	
}
