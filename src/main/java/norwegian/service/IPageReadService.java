package norwegian.service;

import norwegian.models.OsloRigaData;
import org.openqa.selenium.WebDriver;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by vgint on 2/22/2018.
 */
public interface IPageReadService {
    List<OsloRigaData> getFlightDataFromPage(String pageURL);

    Map<String, String> getParsedMapFromUrl(String url);
    String runJavascript(WebDriver driver, String script);

}
