package norwegian.service;

import java.util.Date;
import java.util.Map;

/**
 * Created by vgint on 2/23/2018.
 */
public interface IDateService {
    Date getDepartureTime(String departureTime, Map<String,String> urlMap);
    Date getArrivalTime(String arrivalTime, Map<String,String> urlMap);
    Date setDateWithautTime(Map<String, String> urlMap);
}
