package fr.opensagres.xdocreport.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.facade.WebFacade;
import fr.opensagres.xdocreport.form.ReportForm;
import fr.opensagres.xdocreport.model.EmployeeEntity;
import fr.opensagres.xdocreport.utils.ReportGenerator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final String REPORT_FORM = "reportForm";
	@Autowired
	private WebFacade webFacade;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		ReportForm form = new ReportForm();
		model.addAttribute(REPORT_FORM, form);
		
		EmployeeEntity employee = webFacade.findById(1L);
		form.setName(employee.getFirstname() + ", " + employee.getLastname());
		
		employee.setFirstname("MiguelLucio");
		employee.setUpdatedDate(new Date());
		webFacade.saveOrUpdateEmployee(employee);
		
		logger.info("name is: " + employee.getFirstname());
		return "home";
	}
	
	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
 
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Hello World");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");
 
		return model;
 
	}
	
	//Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
	 
			ModelAndView model = new ModelAndView();
			if (error != null) {
				model.addObject("error", "Invalid username and password!");
			}
	 
			if (logout != null) {
				model.addObject("msg", "You've been logged out successfully.");
			}
			model.setViewName("login");
	 
			return model;
	 
		}
		
	@RequestMapping(value = "/generateReport.html", method = RequestMethod.POST)
	public String generateReport(@ModelAttribute(REPORT_FORM) ReportForm form,HttpServletResponse response) throws IOException, XDocReportException {
		logger.info("START: generateReport");
		
		
//		logger.info("File to Open: " + completePath);

		try{
			String outputFolder = "/opt/reports/";
			String outputFileName = "DocxProjectWithVelocity_Out.docx";
		    String reportFullPath = ReportGenerator.buildReport(form.getName(), outputFolder,  outputFileName);
		    
			File file = new File(reportFullPath);
			byte[] byteArray = FileUtils.readFileToByteArray(file);
		
			response.setContentLength((int)file.length());
			response.setDateHeader("Expires", 0 );

		    response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

			
			response.setHeader("Content-Disposition", "attachment; filename=\"" + outputFileName+"\""); 
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(byteArray, 0, byteArray.length);
			outputStream.flush();			
			outputStream.close();
		}catch(IOException e) {
			logger.info("Error : Template file not found");
			logger.error(e.getMessage(), e);
			throw e;
		} catch (XDocReportException e) {
			logger.info("Error : Report building failed");
			logger.error(e.getMessage(), e);
			throw e;
		}
		logger.info("END: generateReport");
		return null;
	}
	
	@RequestMapping(value = "/generateJasperReport.html", method = RequestMethod.POST)
	public String generateJasperReport(HttpServletResponse response) {
		logger.info("START: generateJasperReport");
		// 1. Add report parameters. 
		HashMap<String, Object> parametrs= new HashMap<String, Object>(); 
		parametrs.put("Title", "User Report");
		// 1.1 Add aux list that won't be used in report
		Collection<EmployeeEntity> employeeAuxList = new ArrayList<EmployeeEntity>();
		EmployeeEntity empleyeeHoax = new EmployeeEntity() {{
			setFirstname("fakeName");
		}};
		employeeAuxList.add(empleyeeHoax);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeAuxList);
		// 2. Retrieve template
//		InputStream reportStream = this.getClass().getResourceAsStream("jaspersample.jrxml");
		InputStream reportStream = this.getClass().getResourceAsStream("jaspersample.jasper");
		try {
//			// 3. Convert template to JasperDesign
//			JasperDesign jd = JRXmlLoader.load(reportStream);
//			
//			// 4. Compile design to JasperReport
//			JasperReport jr = JasperCompileManager.compileReport(jd);
			
			JasperReport jr = (JasperReport) JRLoader.loadObject(reportStream);
			
			// 5. Create the JasperPrint object
			// Make sure to pass the JasperReport, report parameters, and data source
			JasperPrint jp = JasperFillManager.fillReport(jr, parametrs, dataSource);
			
			// Set our response properties
			// Here you can declare a custom filename
			String fileName = "myreport.docx";
			response.setHeader("Content-Disposition", "inline; filename="+ fileName);

			// Set content type
//			response.setContentType("application/pdf");
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

			// Export is most important part of reports
//			JRPdfExporter exporter = new JRPdfExporter(); 
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//			exporter.exportReport();
			
			JRDocxExporter exporter = new JRDocxExporter();
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
//			exporter.exportReport();
			
			exporter.setExporterInput(new SimpleExporterInput(jp));
	        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
	        exporter.exportReport();
		} catch (JRException e) {
			logger.info("Error : JasperReport building failed");
			logger.error(e.getMessage(), e);
		} 
		catch (IOException e) {
			logger.info("Error : JasperReport building failed");
			logger.error(e.getMessage(), e);
		}
		
		logger.info("END: generateJasperReport");
		return null;
	}
	
}
