package SAS;

import SAS.models.StockholmLondonData;
import SAS.service.IFileService;
import SAS.service.IHtmlReadService;
import SAS.service.Impl.FileService;
import SAS.service.Impl.HtmlReadServiceImp;

import java.util.Date;
import java.util.List;

/**
 * Created by vgint on 2/24/2018.
 */
public class SASMain {
    public static void main(String[] args) {
        IFileService fileService = new FileService();
        IHtmlReadService pageReadService = new HtmlReadServiceImp();
        List<StockholmLondonData> directOrConnectOsloFlightList = pageReadService.getFlightDataFromPage(fileService.getHtmlFromFile());
        fileService.writeDataToFile(directOrConnectOsloFlightList);

    }
}
