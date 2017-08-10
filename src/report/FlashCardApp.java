package report;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

public class FlashCardApp extends AbstractSampleApp {

	String our_jasper_template_jrxml = "reports/FlashKard_A4_1.jrxml";
	String our_compiled_template_jasper = "reports/FlashKard_A4_1.jasper";
	
	TransformDataSource dataSource;
	private int mMode;
	
	public FlashCardApp(int aMode){
		mMode = aMode;
	}
	
	public void compile() throws JRException
	{
		long start = System.currentTimeMillis();
		JasperCompileManager.compileReportToFile(
                our_jasper_template_jrxml,			//the path to the jrxml file to compile
                our_compiled_template_jasper);		//the path and name we want to save the 		
		System.err.println("Compilling time : " + (System.currentTimeMillis() - start));
	}

	public void prepare() throws JRException
	{
		long start = System.currentTimeMillis();

		dataSource = new TransformDataSource("data/flashCard.csv");
		dataSource.transform();
		
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
			
			JasperFillManager.fillReportToFile("reports/FlashKard_A4_1.jasper", parameters, dataSource);
			System.err.println("Filling time : " + (System.currentTimeMillis() - start));
		}
		
	}
	
	public void pdf(int mode) throws JRException
	{
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
			compile();
			
			prepare();
			dataSource.setMode(TransformDataSource.MODE_FRONT);
			fill();
			pdf(TransformDataSource.MODE_FRONT);
			
			//prepare();
			dataSource.reset();
			dataSource.setMode(TransformDataSource.MODE_BACK);
			fill();
			pdf(TransformDataSource.MODE_BACK);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args)
	{
		FlashCardApp runner;
		runner = new FlashCardApp(TransformDataSource.MODE_FRONT);
		runner.process();
	}

	@Override
	public void test() throws JRException {
		// TODO Auto-generated method stub
		
	}
	
	
}
