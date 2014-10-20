package fr.opensagres.xdocreport.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
		return null;
	}
	
}
