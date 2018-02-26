package SAS.models;

import java.util.Date;

/**
 * Created by vgint on 2/25/2018.
 */
public class StockholmLondonData {
    private String departureAirport;
    private String arrivalAirport;
    private String connectionAirport;
    private Date departureTime;
    private Date arrivalTime;
    private Double cheapestPrice;
    private Double taxes;


    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getConnectionAirport() {
        return connectionAirport;
    }

    public void setConnectionAirport(String connectionAirport) {
        this.connectionAirport = connectionAirport;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Double getCheapestPrice() {
        return cheapestPrice;
    }

    public void setCheapestPrice(Double cheapestPrice) {
        this.cheapestPrice = cheapestPrice;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    @Override
    public String toString() {
        return "StockholmLondonData{" +
                "departureAirport='" + departureAirport + '\'' +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", connectionAirport='" + connectionAirport + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", cheapestPrice=" + cheapestPrice +
                ", taxes=" + taxes +
                '}';
    }
}
