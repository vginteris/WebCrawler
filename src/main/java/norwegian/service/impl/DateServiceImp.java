package norwegian.service.impl;

import norwegian.service.IDateService;

import java.util.Date;
import java.util.Map;

/**
 * Created by vgint on 2/23/2018.
 */
public class DateServiceImp implements IDateService {


    public Date getDepartureTime(String departureTime, Map<String, String> urlMap) {
        Date departureDate = setDateWithautTime(urlMap);
        String[] timeArray = departureTime.split(":");
        departureDate.setHours(Integer.parseInt(timeArray[0]));
        departureDate.setMinutes(Integer.parseInt(timeArray[1]));
        return departureDate;
    }


    public Date getArrivalTime(String arrivalTime, Map<String, String> urlMap) {
        Date arrivalDate = setDateWithautTime(urlMap);
        String[] timeArray = arrivalTime.split(":");
        arrivalDate.setHours(Integer.parseInt(timeArray[0]));
        arrivalDate.setMinutes(Integer.parseInt(timeArray[1]));
        return arrivalDate;
    }



    public Date setDateWithautTime(Map<String, String> urlMap) {
        Date dateWithoutTime = new Date();
        dateWithoutTime.setYear(Integer.parseInt(urlMap.get("D_Month").substring(0,4))-1900);
        dateWithoutTime.setMonth(Integer.parseInt(urlMap.get("D_Month").substring(4)));
        dateWithoutTime.setDate(Integer.parseInt(urlMap.get("D_Day")));
        return dateWithoutTime;
    }

}
