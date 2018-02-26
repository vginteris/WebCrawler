package SAS.service.Impl;

import SAS.models.StockholmLondonData;
import SAS.service.IFileService;
import SAS.service.IHtmlReadService;
import SAS.service.ITimeService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vgint on 2/25/2018.
 */
public class HtmlReadServiceImp implements IHtmlReadService {
    boolean checkDirect;
    public List<StockholmLondonData> getFlightDataFromPage(Document htmlDocument) {
        ITimeService timeService = new TimeServiceImp();
        List<StockholmLondonData> flightDataList = new ArrayList<StockholmLondonData>();
        StockholmLondonData stockholmLondonData = null;
        Elements allFlights = htmlDocument.select("[id^=idLine]");
        for (Element currentFlight : allFlights) {
            checkDirect = true;

            String currentFlightId = getCurrentFlightIdNumber(currentFlight);
            stockholmLondonData = chechDirectFlight(htmlDocument, currentFlightId);
            int returnFlight = Integer.parseInt(currentFlightId.substring(0, 1));

            stockholmLondonData.setCheapestPrice(getCheapestPrice(currentFlight, currentFlightId));
            stockholmLondonData.setTaxes(getCheapestFlightTaxes(stockholmLondonData.getCheapestPrice(),htmlDocument));
            stockholmLondonData.setDepartureAirport(currentFlight.child(6 + returnFlight).child(0).text());
            stockholmLondonData.setArrivalAirport(currentFlight.child(6 + returnFlight).child(2).text());
            stockholmLondonData.setDepartureTime(timeService.getDepartureTime(returnFlight, htmlDocument, currentFlight));
            stockholmLondonData.setArrivalTime(timeService.getArrivalTime(returnFlight, htmlDocument, currentFlight));


            if (checkDirect) {flightDataList.add(stockholmLondonData);}

        }

            return flightDataList;
    }

    private Double getCheapestFlightTaxes(Double cheapestPrice, Document htmlDocument) {
        Element jsHtmlWithTaxes = htmlDocument.select("script").get(17);
        String regexString = "'price':'"+cheapestPrice+"',\\s+'tax':'(.*?)'";
        Pattern taxesPaternRegex = Pattern.compile(regexString);
        Matcher regexMathTax = taxesPaternRegex.matcher(jsHtmlWithTaxes.html());
        Double cheapestFlightTax = null;
        while (regexMathTax.find()) {
        cheapestFlightTax = Double.valueOf(regexMathTax.group(1));
        }

        return cheapestFlightTax;
    }

    private StockholmLondonData chechDirectFlight(Document htmlDocument, String currentFlightId) {
        StockholmLondonData stockholmLondonData = new StockholmLondonData();
        String airport = htmlDocument.select("#toggleId_"+currentFlightId+"").select("span.route").get(1).text();
        if (airport.equals("Oslo"))
        {
            stockholmLondonData.setConnectionAirport("OSL");
            checkDirect = true;

        } else if (airport.equals("Stockholm, Arlanda  (Terminal 5)") || airport.equals("London, Heathrow  (Terminal 2)")) checkDirect = true;
        else checkDirect = false;
        return stockholmLondonData;
    }

    private String getCurrentFlightIdNumber(Element currentFlight) {
        return currentFlight.id().substring(7);

    }

    private Double getCheapestPrice(Element currentFlight, String currentFlightId) {
        String sasGoLightPriceID = "price_" + currentFlightId +"_ECONBG";
        String sasGoPriceID = "price_" + currentFlightId +"_ECOA";
        String sasPlusPriceID = "price_" + currentFlightId +"_PREMB";
        Double cheapestFlightPrice = null;

        if (currentFlight.child(0).select("#"+sasGoLightPriceID).size() != 0)
        {
        cheapestFlightPrice = Double.valueOf(currentFlight.getElementById(sasGoLightPriceID).attr("data-price"));
        } else if (currentFlight.child(1).select("#"+sasGoPriceID).size() != 0)
        {
            cheapestFlightPrice = Double.valueOf(currentFlight.getElementById(sasGoPriceID).attr("data-price"));
        } else if (currentFlight.child(2).select("#"+sasPlusPriceID).size() != 0){
            cheapestFlightPrice = Double.valueOf(currentFlight.getElementById(sasPlusPriceID).attr("data-price"));
        } else cheapestFlightPrice = 0.0;

        return cheapestFlightPrice;

    }
}
