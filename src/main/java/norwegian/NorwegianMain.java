package norwegian;

import norwegian.models.OsloRigaData;
import norwegian.service.IFileService;
import norwegian.service.impl.FileServiceImp;
import norwegian.service.impl.PageReadServiceImp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by vgint on 2/22/2018.
 */
public class NorwegianMain {
    public static void main(String[] args) {
        String pageURL = "https://www.norwegian.com/en/ipc/availability/avaday?D_City=OSL&A_City=RIX&TripType=1&D_SelectedDay=1&D_Day=1&D_Month=201803&R_SelectedDay=31&R_Day=31&R_Month=201803&dFare=60&IncludeTransit=false&AgreementCodeFK=-1&CurrencyCode=EUR&mode=ab";

        PageReadServiceImp pageReadServiceImp = new PageReadServiceImp();
        IFileService fileService = new FileServiceImp();
        List<OsloRigaData> osloRigaDataList = pageReadServiceImp.getFlightDataFromPage(pageURL);
        fileService.writeDataToFile(osloRigaDataList);
    }
}
