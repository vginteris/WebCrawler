package SAS.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Date;

/**
 * Created by vgint on 2/26/2018.
 */
public interface ITimeService {
    Date getDepartureTime(int returnFlight,Document htmlDocument, Element currentFlight);
    Date getArrivalTime(int returnFlight, Document htmlDocument, Element currentFlight);
}
