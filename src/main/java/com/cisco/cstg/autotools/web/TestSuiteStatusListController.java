
package com.cisco.cstg.autotools.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.cisco.cstg.autotools.domain.appdb.TestSuiteStatus;
import com.cisco.cstg.autotools.semantic.test.TestMonitor;

@Controller
public class TestSuiteStatusListController {
    private static final Logger logger = LoggerFactory.getLogger(TestSuiteStatusListController.class);
    
    @Autowired
    private TestMonitor testMonitor;

    @RequestMapping(value="/test_suite_status.html", method = RequestMethod.GET)
    public ModelAndView setupForm(@ModelAttribute("testSuiteStatus") TestSuiteStatus testSuiteStatus) {
            logger.debug("INSIDE GET TEST SUITE STATUS CONTROLLER");
            List<TestSuiteStatus> result = testMonitor.getAllTestSuiteStatus();
            logger.debug("Got {} entities", result.size());
            ModelAndView mav = new ModelAndView("TestSuiteStatus");
            mav.addObject(result);
            return mav;
    }
    
    @RequestMapping(value="/test_suite_status.html", method = RequestMethod.POST)
    public String processSubmit(Model model, TestSuiteStatus testSuiteStatus, SessionStatus status) {
    	
        try {

            if(testSuiteStatus !=null){
         	   logger.debug("The values in Test Status- id: {} testId:{}",
         			   testSuiteStatus.getId(), testSuiteStatus.getTestSuiteId());
            }else{
         	   logger.debug("The TEST STATUS object is null");
            }

            testMonitor.runTestSuite(testSuiteStatus.getTestSuiteId());
            
         } catch (Exception e) {
             e.printStackTrace();
             final Writer result = new StringWriter();
 		    final PrintWriter printWriter = new PrintWriter(result);
 		    e.printStackTrace(printWriter);
 		    logger.debug(result.toString());
         }    	

        return "redirect:test_suite_status.html";
    }    
    
    @RequestMapping(value="/test_suite_email.html", method = RequestMethod.POST)
    public String sendEmail(Model model, TestSuiteStatus testSuiteStatus, SessionStatus status) {
    	
        try {

            if(testSuiteStatus !=null){
         	   logger.debug("The values in Test Status- id: {} testId:{}",
         			   testSuiteStatus.getId(), testSuiteStatus.getTestSuiteId());
            }else{
         	   logger.debug("The TEST STATUS object is null");
            }

            testMonitor.emailTestSuiteReport(testSuiteStatus.getTestSuiteId());
            
         } catch (Exception e) {
             e.printStackTrace();
             final Writer result = new StringWriter();
 		    final PrintWriter printWriter = new PrintWriter(result);
 		    e.printStackTrace(printWriter);
 		    logger.debug(result.toString());
         }    	

        return "redirect:test_suite_status.html";
    }    
    
}