package report.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

import net.sf.jasperreports.charts.xml.JRAreaChartFactory;
import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRPropertyExpression;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.util.AbstractSampleApp;
import net.sf.jasperreports.engine.util.JRLoader;

public class TransformDataSource extends AbstractSampleApp implements JRDataSource {

	public static int MODE_NONE = -1;
	public static int MODE_FRONT = 0;
	public static int MODE_BACK = 1;

	// ------------------------------------------------------

	private HashMap<String, TwoValueContainer> mDataRow;
	private ArrayList<HashMap> mDataArray;
	private int mCurrentRow;

	// ------------------------------------------------------

	private int mMode;

	private JRCsvDataSource dataSource;
	private String mDataFile;

	public TransformDataSource(String dataFile) {
		mMode = MODE_FRONT;
		mDataFile = dataFile;
		mCurrentRow = -1;
	}

	public void setMode(int mode) {
		mMode = mode;
	}

	public void transformFromTwo() throws JRException {
		long start = System.currentTimeMillis();
		boolean firstRow = true;
		int totalCount = 0, totalCountMain = 0;
		Map<String, Integer> mColumnNames;
		Set mKeySet;
		Object[] mKeySetObj = null;

		dataSource = new JRCsvDataSource(JRLoader.getLocationInputStream(mDataFile));
		dataSource.setUseFirstRowAsHeader(true);
		dataSource.setRecordDelimiter("\r\n");
		dataSource.setFieldDelimiter('|');

		mDataArray = new ArrayList();
		
		while (dataSource.next()) {
		
			if (firstRow) {
				mColumnNames = dataSource.getColumnNames();
				mKeySet = mColumnNames.keySet();
				mKeySetObj = mKeySet.toArray();
				firstRow = false;
				System.err.println("transform : " + mColumnNames);
			}

			if (totalCount % 2 == 0){ 
				// Main lang
				totalCountMain++;
				mDataRow = new HashMap();
			} else {
				mDataRow = mDataArray.get(totalCountMain-1);
			}

			for (int i = 0; i < mKeySetObj.length; i++) {

				int mode = MODE_NONE;
				TwoValueContainer valueTwoContainer = null;

				String nameOld = mKeySetObj[i].toString();
				Object value = dataSource.getFieldValue(new JRStubField(nameOld));

				if (totalCount % 2 == 0) {
					// Main lang
					valueTwoContainer = new TwoValueContainer();
					mode = MODE_FRONT;
					valueTwoContainer.mName = nameOld;
					valueTwoContainer.mFirst = new CustomValueContainer(mode, nameOld, value);
					mDataRow.put(nameOld, valueTwoContainer);
				} else {
					// Second lang
					mode = MODE_BACK;
					valueTwoContainer = mDataRow.get(nameOld); 
					valueTwoContainer.mSecnd = new CustomValueContainer(mode, nameOld, value);
					mDataRow.put(nameOld, valueTwoContainer);
				}
			}
			
			if (totalCount % 2 == 0){ 
				// Main lang
				mDataArray.add(mDataRow);
			} else {
				mDataArray.set(totalCountMain-1, mDataRow);
				//System.out.println("mDataRow["+totalCountMain+"]"+mDataRow);
			}
			
			totalCount++;
		}
		
		if (totalCountMain % 2 != 0) {
			mDataRow = addEmptyDataRow(mDataArray.get(0));
			mDataArray.add(mDataRow);
		}
				
		reverseBack();

		System.err.println("Transform time : " + (System.currentTimeMillis() - start));

	}

	public void transformFromOne() throws JRException {
		long start = System.currentTimeMillis();
		boolean firstRow = true;

		dataSource = new JRCsvDataSource(JRLoader.getLocationInputStream(mDataFile));
		dataSource.setUseFirstRowAsHeader(true);
		dataSource.setRecordDelimiter("\r\n");
		// dataSource.setFieldDelimiter('|');

		mDataArray = new ArrayList();

		Map<String, Integer> mColumnNames;
		Set mKeySet;
		Object[] mKeySetObj = null;
		int totalCount = 0;

		while (dataSource.next()) {

			if (firstRow) {
				mColumnNames = dataSource.getColumnNames();
				mKeySet = mColumnNames.keySet();
				mKeySetObj = mKeySet.toArray();
				// System.err.println("transform : " + mColumnNames);
				firstRow = false;
			}

			TwoValueContainer valueTwoContainer;

			mDataRow = new HashMap();
			for (int i = 0; i < mKeySetObj.length; i++) {
				int mode = MODE_FRONT;
				String nameOld = mKeySetObj[i].toString();
				String nameNew = mKeySetObj[i].toString();
				Object value = dataSource.getFieldValue(new JRStubField(nameOld));
				valueTwoContainer = new TwoValueContainer();
				valueTwoContainer.mName = nameOld;
				if (nameOld.startsWith("L2_")) {
					mode = MODE_BACK;
					nameNew = nameOld.replaceAll("L2", "L1");
					valueTwoContainer = mDataRow.get(nameNew);
					valueTwoContainer.mSecnd = new CustomValueContainer(mode, nameOld, value);
				} else {
					valueTwoContainer.mFirst = new CustomValueContainer(mode, nameOld, value);
				}
				// System.err.println("Transform : " +
				// "["+mode+"]["+nameOld+"]["+nameOld+"]["+value+"]");
				// CustomValueContainer valueContainer = new
				// CustomValueContainer(mode, nameNew, value);
				mDataRow.put(nameNew, valueTwoContainer);

			}
			mDataArray.add(mDataRow);
			totalCount++;
		}

		if (totalCount % 2 != 0) {
			// System.err.println("Transform add empty:
			// ["+totalCount+"]["+totalCount%2+"]");
			mDataRow = addEmptyDataRow(mDataArray.get(0));
			mDataArray.add(mDataRow);
		}

		reverseBack();

		/*
		 * dataSource = new
		 * JRCsvDataSource(JRLoader.getLocationInputStream(mDataFile));
		 * dataSource.setUseFirstRowAsHeader(true);
		 * dataSource.setRecordDelimiter("\r\n");
		 */
		System.err.println("Transform time : " + (System.currentTimeMillis() - start));
	}

	private HashMap<String, TwoValueContainer> addEmptyDataRow(HashMap hashMap) {

		HashMap<String, TwoValueContainer> ret = new HashMap();

		java.util.Iterator<String> itr = hashMap.keySet().iterator();
		while (itr.hasNext()) {
			String element = itr.next();

			TwoValueContainer val = new TwoValueContainer();
			val.mName = element;
			val.mFirst = new CustomValueContainer(MODE_FRONT);
			val.mSecnd = new CustomValueContainer(MODE_BACK);

			ret.put(element, val);
		}

		return ret;
	}

	private void reverseBack() {
		for (int i = 0; i < mDataArray.size() - 1; i += 2) {
			HashMap<String, TwoValueContainer> mDataRow1 = mDataArray.get(i);
			HashMap<String, TwoValueContainer> mDataRow2 = mDataArray.get(i + 1);
			// 0<>1, 1<>2

			//System.err.println("reverseBack["+i+"]["+mDataArray.size()+"] : " +
			//"\n["+mDataRow1+"]\n["+mDataRow2+"]");

			java.util.Iterator<String> itr = mDataRow1.keySet().iterator();
			while (itr.hasNext()) {
				String element = itr.next();
				CustomValueContainer vs1 = mDataRow1.get(element).mSecnd;
				CustomValueContainer vs2 = mDataRow2.get(element).mSecnd;
				Object swapVal = vs1.mValue;
				vs1.mValue = vs2.mValue;
				vs2.mValue = swapVal;
			}
			// System.err.println("reverseBack : " +
			// "\n["+mDataRow1+"]\n["+mDataRow2+"]");
		}
		// mDataArray.add(mDataRow);
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		Object ret;

		mDataRow = mDataArray.get(mCurrentRow);
		// String fKey = mMode+"_"+field.getName();
		String fKey = field.getName();
		Object val = mDataRow.get(fKey);

		ret = "[" + mCurrentRow + "][" + fKey + "](" + val + ")";
		if (mMode == MODE_FRONT)
			ret = mDataRow.get(fKey).mFirst.mValue;
		else
			ret = mDataRow.get(fKey).mSecnd.mValue;

		// System.err.println("getFieldValue["+mDataArray.size()+"]["+mCurrentRow+"]["+fKey+"]
		// : " + ret.toString());

		/*
		 * ret = dataSource.getFieldValue(field); if(mMode == MODE_BACK) {
		 * 
		 * JRTransformField field2 = new JRTransformField(field,
		 * field.getName().replace("L1", "L2")); ret =
		 * dataSource.getFieldValue(field2);
		 * 
		 * }
		 */
		return ret;
	}

	public void reset() throws JRException {
		mCurrentRow = -1;
	}

	@Override
	public boolean next() throws JRException {
		boolean ret;
		// ret = dataSource.next();
		mCurrentRow++;
		ret = (mCurrentRow < mDataArray.size());
		return ret;
	}

	@Override
	public void test() throws JRException {
		// TODO Auto-generated method stub
	}

	class TwoValueContainer {
		private String mName;
		CustomValueContainer mFirst;
		CustomValueContainer mSecnd;

		public String toString() {
			return "TwoValueContainer{[" + mName + "]mFirst[" + mFirst + "]mSecnd[" + mSecnd + "]}";
		}
	}

	class CustomValueContainer {

		private int mMode;
		private String mName;
		private Object mValue;

		public CustomValueContainer(int aMode) {
			mMode = aMode;
			mName = "";
			mValue = "";
		}

		public CustomValueContainer(int aMode, String aName, Object aValue) {
			mMode = aMode;
			mName = aName;
			mValue = aValue;
		}

		public String toString() {
			return "{[" + mMode + "][" + mName + "][" + mValue + "]}";
		}

	}

	// ------------------------------------------------------
	class JRStubField implements JRField, JRCloneable {

		private String mName;

		public JRStubField(String newName) {
			mName = newName;
		}

		@Override
		public JRPropertiesHolder getParentProperties() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public JRPropertiesMap getPropertiesMap() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasProperties() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			return mName;
		}

		@Override
		public JRPropertyExpression[] getPropertyExpressions() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Class<?> getValueClass() {
			return String.class;
		}

		@Override
		public String getValueClassName() {
			return "String";
		}

		@Override
		public void setDescription(String arg0) {
			// TODO Auto-generated method stub

		}

		public java.lang.Object clone() {
			JRStubField clone = null;

			try {
				clone = (JRStubField) super.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return clone;
		}

	}

	class JRTransformField implements JRField, JRCloneable {

		private JRField mParent;
		private String mName;

		public JRTransformField(JRField parent, String newName) {
			mParent = parent;
			mName = newName;
		}

		@Override
		public JRPropertiesHolder getParentProperties() {
			return mParent.getParentProperties();
		}

		@Override
		public JRPropertiesMap getPropertiesMap() {
			return mParent.getPropertiesMap();
		}

		@Override
		public boolean hasProperties() {
			return mParent.hasProperties();
		}

		@Override
		public String getDescription() {
			return mParent.getDescription();
		}

		@Override
		public String getName() {
			return mName;
		}

		@Override
		public JRPropertyExpression[] getPropertyExpressions() {
			return mParent.getPropertyExpressions();
		}

		@Override
		public Class<?> getValueClass() {
			return mParent.getValueClass();
		}

		@Override
		public String getValueClassName() {
			return mParent.getValueClassName();
		}

		@Override
		public void setDescription(String arg0) {
			mParent.setDescription(arg0);
		}

		public java.lang.Object clone() {
			JRTransformField clone = null;

			try {
				clone = (JRTransformField) super.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return clone;
		}
	}

}
