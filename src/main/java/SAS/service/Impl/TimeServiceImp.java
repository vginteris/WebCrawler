package SAS.service.Impl;

import SAS.service.ITimeService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Date;

/**
 * Created by vgint on 2/26/2018.
 */
public class TimeServiceImp implements ITimeService {
    public Date getDepartureTime(int returnFlight, Document htmlDocument, Element currentFlight) {
        Date departureDate = setDateWithautTime(returnFlight,htmlDocument, currentFlight);
        String[] timeSplitedArray = currentFlight.child(4+returnFlight).child(0).text().split(":");
        departureDate.setHours(Integer.parseInt(timeSplitedArray[0]));
        departureDate.setMinutes(Integer.parseInt(timeSplitedArray[1]));


        return departureDate;
    }


    public Date getArrivalTime(int returnFlight, Document htmlDocument, Element currentFlight) {
        Date arrivalDate = setDateWithautTime(returnFlight,htmlDocument, currentFlight);
        String[] timeSplitedArray = currentFlight.child(4+returnFlight).child(2).text().split(":");
        arrivalDate.setHours(Integer.parseInt(timeSplitedArray[0]));
        arrivalDate.setMinutes(Integer.parseInt(timeSplitedArray[1]));
        if (currentFlight.child(4).select(".days").size() != 0)
        {
            arrivalDate.setDate(arrivalDate.getDay() + 1);
        }
        return arrivalDate;
    }
    private Date setDateWithautTime(int returnFlight,Document htmlDocument, Element currentFlight) {
        Date dateWithoutTime;
        if (currentFlight.child(6+returnFlight).child(0).text().equals("ARN"))
            dateWithoutTime = new Date(htmlDocument.getElementById("date1Right").text());
        else dateWithoutTime = new Date(htmlDocument.getElementById("date2Right").text());

        return dateWithoutTime;
    }


}
