package norwegian.service.impl;

import norwegian.models.OsloRigaData;
import norwegian.service.IDateService;
import norwegian.service.IPageReadService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by vgint on 2/23/2018.
 */
public class PageReadServiceImp implements IPageReadService {
    boolean samePage;

    public List<OsloRigaData> getFlightDataFromPage(String pageURL) {
        List<OsloRigaData> osloRigaDataList = new ArrayList<OsloRigaData>();

        System.setProperty("webdriver.chrome.driver", "C:/Selenium/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(pageURL);

        for (int flightDay = 1; flightDay < 31; flightDay++) {

            String day = getFlightDay(flightDay, driver);


            if (!day.equals("01")) pageURL = driver.getCurrentUrl();
            Map<String, String> parsedMap = getParsedMapFromUrl(pageURL);

            List<WebElement> element = driver.findElements(By.cssSelector("div td.arrdest"));
            for (int flightCount = 1; flightCount <= element.size()/2 ; flightCount++) {
            OsloRigaData osloRigaData = fillOsloRigaObjectWithData(driver, parsedMap, flightCount);

            System.out.println(osloRigaData.toString());
            osloRigaDataList.add(osloRigaData);

            }
            if (element.size()==0)
            {
                String nextDayURL = "https://www.norwegian.com/en/ipc/availability/avaday?D_City=OSL&A_City=RIX&TripType=1&D_SelectedDay=" + (flightDay + 1) + "&D_Day=" + (flightDay + 1) + "&D_Month=201803&R_SelectedDay=31&R_Day=31&R_Month=201803&dFare=60&IncludeTransit=false&AgreementCodeFK=-1&CurrencyCode=EUR&mode=ab";
                driver.get(nextDayURL);
                samePage = false;
            }
        }
        driver.close();
        return osloRigaDataList;
    }

    private String getFlightDay(int flightDay, WebDriver driver) {
        String tempScript = "";
        String currentFlightDay = "";
        try {
            if (flightDay != 1 && samePage == true) {
                if (flightDay < 10)currentFlightDay += "0" + flightDay;
                else currentFlightDay= String.valueOf(flightDay);
                tempScript = "$('#ctl00_MainContent_ipcAvaDay_ipcAvaDaySearchBar_ddlDepartureDay').val(\"" + currentFlightDay + "\").change();";
                runJavascript(driver, tempScript);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return currentFlightDay;
    }

    private OsloRigaData fillOsloRigaObjectWithData(WebDriver driver, Map<String, String> parsedMap,int flightCount) {
        IDateService dateService = new DateServiceImp();
        OsloRigaData osloRigaData = new OsloRigaData();
        if (driver.getCurrentUrl().substring(0, 46).equals("https://www.norwegian.com/en/ipc/availability/")) {
            try {
                String jsClickForTaxes = "$('#FlightSelectOutboundStandardLowFare"+(flightCount-1)+"').click();";
                runJavascript(driver, jsClickForTaxes);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int tempFlightCount = 0;
            osloRigaData.setDepartureAirport(parsedMap.get("D_City"));
            osloRigaData.setArrivalAirport(parsedMap.get("A_City"));
            if (flightCount == 1) tempFlightCount = 0;
            else tempFlightCount = (flightCount - 1) * 3;
            String jsCheapestPrice ="return $($('label.seatsokfare')["+tempFlightCount+"]).text();";
            osloRigaData.setCheapestPrice(Double.valueOf(runJavascript(driver, jsCheapestPrice)));
            osloRigaData.setTaxes(Double.valueOf(runJavascript(driver, "if($('td.rightcell.emphasize').length > 0){return $($('td.rightcell.emphasize')[1]).text();} else {return '$0'}").substring(1)));
            if (flightCount == 1) tempFlightCount = 0;
            else tempFlightCount = (flightCount - 1) * 2;
            String jsDetpartureTime = "return $($('div td.depdest')["+tempFlightCount+"]).text();";
            osloRigaData.setDepartureTime(dateService.getDepartureTime(runJavascript(driver,jsDetpartureTime ), parsedMap));
            String jsArrivalTime = "return $($('div td.arrdest')["+tempFlightCount+"]).text();";
            osloRigaData.setArrivalTime(dateService.getArrivalTime(runJavascript(driver, jsArrivalTime), parsedMap));
            if (parsedMap.get("IncludeTransit").equals("false")) osloRigaData.setConnectionAirport("-");
            samePage = true;


        }
        return osloRigaData;
    }

    public Map<String, String> getParsedMapFromUrl(String url) {
        URL parsedUrl = null;
        try {
            parsedUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Map<String, String> urlQueryMap = new HashMap<String, String>();
        String[] query = parsedUrl.getQuery().split("&");
        for (String qur : query) {
            String[] splited = qur.split("=");
            urlQueryMap.put(splited[0], splited[1]);
        }
        return urlQueryMap;
    }

    public String runJavascript(WebDriver driver, String script) {
        WebElement body = driver.findElement(By.cssSelector("body"));
        return (String) ((JavascriptExecutor) driver).executeScript(script, body);
    }
}
