package SAS.service;

import SAS.models.StockholmLondonData;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by vgint on 2/25/2018.
 */
public interface IHtmlReadService {
     List<StockholmLondonData> getFlightDataFromPage(Document htmlDocument);
}
