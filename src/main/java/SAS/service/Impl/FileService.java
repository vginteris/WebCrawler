package SAS.service.Impl;

import SAS.models.StockholmLondonData;
import SAS.service.IFileService;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by vgint on 2/25/2018.
 */
public class FileService implements IFileService {
    public Document getHtmlFromFile() {
        Document htmlDocument = null;
        try {
            File input = new File("C:/Users/vgint/Desktop/Programavimas/Infare/crawler/src/main/java/SAS/flysasPageSource.html");
            htmlDocument = Jsoup.parse(input, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlDocument;
    }

    public void writeDataToFile(List<StockholmLondonData> osloRigaDataList) {
        Gson gson = new Gson();

        try {
            FileWriter writer = new FileWriter("stockholmLondonData.json");
            String flightData = gson.toJson(osloRigaDataList);
            writer.write(flightData);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
