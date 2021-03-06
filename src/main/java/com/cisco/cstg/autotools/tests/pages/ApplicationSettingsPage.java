package com.cisco.cstg.autotools.tests.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.cstg.autotools.domain.appdb.CustomerInfo;
import com.cisco.cstg.autotools.support.exception.LinkNotFoundException;
import com.cisco.cstg.autotools.support.exception.NoCustomerAndRoleInfo;

public class ApplicationSettingsPage extends LandingPage {

	protected static final Logger logger = LoggerFactory.getLogger(ApplicationSettingsPage.class);
	
	private List<CustomerInfo> userRoleList;
	
	public List<CustomerInfo> getUserRoleList() 
								throws NoCustomerAndRoleInfo, LinkNotFoundException{
		if(userRoleList==null || userRoleList.size()==0){
			this.clickOnLNPlink(APPLICATION_SETTINGS);
			waitForContentArea();
		}
		return userRoleList;
	}

	public void setUserRoleList(List<CustomerInfo> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public ApplicationSettingsPage(WebDriver driver) {
		super(driver);
		setUserRoleList(new ArrayList<CustomerInfo>());
	}

	@Override
	public WebDriver waitForContentArea() throws NoCustomerAndRoleInfo{
		// wait until the content area is loaded
		pageLoaded.until(ExpectedConditions.visibilityOfElementLocated(By.className(CONTENT_AREA_CLASS_NAME)));
		logger.debug("CONTENT AREA LOADED");
		try{
			
			logger.info("WAITING FOR THE APP SETTINGS OUTER IFRAME TO LOAD");
			driver = pageLoaded.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
			logger.info("FOUND APP SETTINGS OUTER IFRAME AND SWITCHED TO IT");
	
			// get the div elements that contain the customer name and role info
			WebElement divElement = pageLoaded.until(ExpectedConditions.visibilityOfElementLocated(By.id("appsettings")));
			logger.info("WAITING FOR THE APP SETTINGS INNER IFRAME TO LOAD");
			WebElement innerIframe = divElement.findElement(By.tagName("iframe"));
			pageLoaded.until(ExpectedConditions.visibilityOf(innerIframe));
			logger.info("FOUND APP SETTINGS INNER IFRAME AND SWITCHING TO IT");
			driver.switchTo().frame(innerIframe);
			long start = System.nanoTime();
			long stop = System.nanoTime();
			boolean foundRoleDiv=false;
			while( !foundRoleDiv && (new Long(TimeUnit.SECONDS.convert((stop - start), TimeUnit.NANOSECONDS)).intValue() <= 45)){
				
				pageLoaded.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("col-xs-6")));
				List<WebElement> divs = pageLoaded.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("ng-scope")));
				for (WebElement div : divs) {
					if(div.getAttribute("ng-repeat")!=null && div.getAttribute("ng-repeat").equalsIgnoreCase("cust in userDetails.customerRoleMap")){
						// found the role info
						CustomerInfo aCustInfo = new CustomerInfo();
						List<WebElement> custDivs = div.findElements(By.className("col-xs-6"));
						for(WebElement custDiv : custDivs) {
							
							 if(custDiv.getText().contains("Role:")){
								 aCustInfo.setCustomerRole(custDiv.getText().replaceAll("Role:", "").trim());
								 logger.debug("UserRole: {}", custDiv.getText());
								 foundRoleDiv = true;
							 }else{
								 aCustInfo.setCustomerName(custDiv.getText().trim());
								 logger.debug("CustomerName: {}", custDiv.getText());
							 }
						}
						userRoleList.add(aCustInfo);
					}
					
				}
				stop = System.nanoTime();
			}
			if(new Long(TimeUnit.SECONDS.convert((stop - start), TimeUnit.NANOSECONDS)).intValue() >= 45
					|| foundRoleDiv==false){
				// no customer info
				throw new NoCustomerAndRoleInfo();		
			}
			
			driver.switchTo().defaultContent();
			logger.info("SWITCHING BACK TO MAIN IFRAME workspaceViewFrame");		
	}catch(NoSuchElementException exp){
		logger.info("CANNOT FIND APP SETTINGS LINK");
		driver.switchTo().defaultContent();
		throw new NoCustomerAndRoleInfo();
	}catch(TimeoutException exp){
		driver.switchTo().defaultContent();
		throw new NoCustomerAndRoleInfo();
	}
		return driver;
	}
	
	/** 
	 * figures out if a customer has a given role for a given customer
	 * @param role
	 */
	public boolean hasARoleForCustomer(String role, String Customer)
							throws LinkNotFoundException, NoCustomerAndRoleInfo{
		boolean userHasARole=false;
			for (CustomerInfo userRoleInfo : getUserRoleList()) {
				if(userRoleInfo.getCustomerName().equalsIgnoreCase(Customer)){
					if(userRoleInfo.getCustomerRole().equalsIgnoreCase(role))
						userHasARole=true;
					logger.debug("The User has the role {} for Customer {}",role,Customer);
				}
			}
		return userHasARole;
	}

	public CharSequence getUserRolesAsString(String CustomerName){
		StringBuffer userRolesAsString = new StringBuffer();
		try {
			int count=0;
			
			for (CustomerInfo userRoleInfo : this.getUserRoleList()) {
				logger.debug("Number of Cust infos: {}", this.userRoleList.size());
				if(userRoleInfo.getCustomerName().equalsIgnoreCase(CustomerName.trim())){
					logger.debug("Matched the customer name: {}", CustomerName);
					if(count==0){
						userRolesAsString.append(userRoleInfo.getCustomerRole());
						 count++;
					 }else{
						 userRolesAsString.append(";");
						 userRolesAsString.append(userRoleInfo.getCustomerRole());
						  count++;
					 }
				}
			}
			logger.debug("UserRoles from getUserRoleAsString method: {}",userRolesAsString.toString());
		} catch (LinkNotFoundException e) {
			logger.debug("NO ROLES FOUND");
			userRolesAsString.append("UNABLE TO GET ROLES(APP SETTINGS MAY NOT BE LOADING PROPERLY)");
		}
		catch (NoCustomerAndRoleInfo e) {
			logger.debug("NO ROLES FOUND");
			userRolesAsString.append("UNABLE TO GET ROLES(APP SETTINGS MAY NOT BE LOADING PROPERLY)");
		}
		
		return userRolesAsString;
	}
}