package fr.opensagres.xdocreport.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.form.ReportForm;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class ReportGenerator {
	private static final Logger logger = LoggerFactory.getLogger(ReportGenerator.class);
	
	
	public static String buildReport(String name, String outputFolder, String outputFileName) throws  XDocReportException, IOException {

	      logger.info("build init");
	      // 1) Load Docx file by filling Velocity template engine and cache it to the registry
	      InputStream in = ReportGenerator.class.getResourceAsStream("Project.docx");
	      IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity, false);

	      // 2) Create context Java model
	      IContext context = report.createContext();
	      ReportForm project = new ReportForm();
	      project.setName(name);
	      project.setResult("r1abcddefghijklmlr2abcddefghijklmln"+
	    		  "r3abcddefghijklmln"+
	    		  "r4abcddefghijklmln"+
	    		  "r5abcddefghijklmln"+
	    		  "r6abcddefghijklmln"+
	    		  "7abcddefghijklmln"+
	    		  "8abcddefghijklmln"+
	    		  "9abcddefghijklmln"+
	    		  "10abcddefghijklmln"+
	    		  "11abcddefghijklmln"+
	    		  "12abcddefghijklmln\n"+
	    		  "13abcddefghijklmln\n"+
	    		  "14abcddefghijklmln\n"+
	    		  "15abcddefghijklmln\n"+
	    		  "16abcddefghijklmln\n"+
	    		  "17abcddefghijklmln\n"+
	    		  "18abcddefghijklmln\n"+
	    		  "19abcddefghijklmln\n"+
	    		  "20abcddefghijklmln\n"+
	    		  "r21abcddefghijklmln\n");
	      project.setComment("1abcddefghijklmln\n"+
	    		  "2abcddefghijklmln\n"+
	    		  "3abcddefghijklmln\n"+
	    		  "4abcddefghijklmln\n"+
	    		  "5abcddefghijklmln\n"+
	    		  "6abcddefghijklmln\n"+
	    		  "7abcddefghijklmln\n"+
	    		  "8abcddefghijklmln\n"+
	    		  "9abcddefghijklmln\n"+
	    		  "10abcddefghijklmln\n"+
	    		  "11abcddefghijklmln\n"+
	    		  "12abcddefghijklmln\n"+
	    		  "13abcddefghijklmln\n"+
	    		  "14abcddefghijklmln\n"+
	    		  "15abcddefghijklmln\n"+
	    		  "16abcddefghijklmln\n"+
	    		  "17abcddefghijklmln\n"+
	    		  "18abcddefghijklmln\n"+
	    		  "19abcddefghijklmln\n"+
	    		  "20abcddefghijklmln\n"+
	    		  "21abcddefghijklmln\n");
	      context.put("project", project);
	      

	      // 3) Generate report by merging Java model with the Docx
	      File outputFile = new File(outputFolder,outputFileName);
	      logger.info(outputFile.getAbsolutePath());
	      
	      OutputStream out = new FileOutputStream(outputFile);
	      report.process(context, out);
	      IOUtils.closeQuietly(out);
	      
	      logger.info("build end");
	      
	      return outputFolder + outputFileName;

	  }
}
