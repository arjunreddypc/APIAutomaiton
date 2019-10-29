package test.programs;

import java.io.File;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Read {
	public static void main(String[] args) throws IOException
	{
		String path = System.getProperty("user.dir")+"\\test.xls";
		System.out.println(path);
		FileOutputStream out = new FileOutputStream(new File(path));
		
		XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet=workbook.createSheet("subscriberlist");
	    List<String> ban = new ArrayList<String>();
		List<String> subscriber = new ArrayList<String>();
		ban.add("118765");
		ban.add("123654");
		Row header=sheet.createRow(0);
		header.createCell(0).setCellValue("Ban");
		header.createCell(1).setCellValue("Subscriber");
		int temp=0;
		for (int i=0;i<ban.size();i++)
		{
			subscriber.add("1");
			subscriber.add("2");
			subscriber.add("3");
			subscriber.add("4");
			subscriber.add("5");
			for(int j=0;j<subscriber.size();j++)
			{
			Row row=sheet.createRow(temp+1);
			Cell cell1=row.createCell(0);
			cell1.setCellValue(ban.get(i));
			Cell cell=row.createCell(1);
			cell.setCellValue(subscriber.get(j));
			temp=temp+1;
			}
			subscriber.clear();
		}
//		workbook.write(out);
        out.close();
        workbook.close();
	}
	
}
