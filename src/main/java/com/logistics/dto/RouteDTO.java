package com.logistics.dto;

public class RouteDTO {
    private String countryFrom;
    private String cityFrom;
    private String addressFrom;
    private String countryTo;
    private String cityTo;
    private String addressTo;

    public RouteDTO(String countryFrom, String cityFrom, String addressFrom, String countryTo, String cityTo, String addressTo) {
        this.countryFrom = countryFrom;
        this.cityFrom = cityFrom;
        this.addressFrom = addressFrom;
        this.countryTo = countryTo;
        this.cityTo = cityTo;
        this.addressTo = addressTo;
    }

    public RouteDTO() {
    }

    public String getCountryFrom() {
        return this.countryFrom;
    }

    public String getCityFrom() {
        return this.cityFrom;
    }

    public String getAddressFrom() {
        return this.addressFrom;
    }

    public String getCountryTo() {
        return this.countryTo;
    }

    public String getCityTo() {
        return this.cityTo;
    }

    public String getAddressTo() {
        return this.addressTo;
    }

    public void setCountryFrom(String countryFrom) {
        this.countryFrom = countryFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public void setCountryTo(String countryTo) {
        this.countryTo = countryTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RouteDTO)) return false;
        final RouteDTO other = (RouteDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$countryFrom = this.getCountryFrom();
        final Object other$countryFrom = other.getCountryFrom();
        if (this$countryFrom == null ? other$countryFrom != null : !this$countryFrom.equals(other$countryFrom))
            return false;
        final Object this$cityFrom = this.getCityFrom();
        final Object other$cityFrom = other.getCityFrom();
        if (this$cityFrom == null ? other$cityFrom != null : !this$cityFrom.equals(other$cityFrom)) return false;
        final Object this$addressFrom = this.getAddressFrom();
        final Object other$addressFrom = other.getAddressFrom();
        if (this$addressFrom == null ? other$addressFrom != null : !this$addressFrom.equals(other$addressFrom))
            return false;
        final Object this$countryTo = this.getCountryTo();
        final Object other$countryTo = other.getCountryTo();
        if (this$countryTo == null ? other$countryTo != null : !this$countryTo.equals(other$countryTo)) return false;
        final Object this$cityTo = this.getCityTo();
        final Object other$cityTo = other.getCityTo();
        if (this$cityTo == null ? other$cityTo != null : !this$cityTo.equals(other$cityTo)) return false;
        final Object this$addressTo = this.getAddressTo();
        final Object other$addressTo = other.getAddressTo();
        if (this$addressTo == null ? other$addressTo != null : !this$addressTo.equals(other$addressTo)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RouteDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $countryFrom = this.getCountryFrom();
        result = result * PRIME + ($countryFrom == null ? 43 : $countryFrom.hashCode());
        final Object $cityFrom = this.getCityFrom();
        result = result * PRIME + ($cityFrom == null ? 43 : $cityFrom.hashCode());
        final Object $addressFrom = this.getAddressFrom();
        result = result * PRIME + ($addressFrom == null ? 43 : $addressFrom.hashCode());
        final Object $countryTo = this.getCountryTo();
        result = result * PRIME + ($countryTo == null ? 43 : $countryTo.hashCode());
        final Object $cityTo = this.getCityTo();
        result = result * PRIME + ($cityTo == null ? 43 : $cityTo.hashCode());
        final Object $addressTo = this.getAddressTo();
        result = result * PRIME + ($addressTo == null ? 43 : $addressTo.hashCode());
        return result;
    }

    public String toString() {
        return "RouteDTO(countryFrom=" + this.getCountryFrom() + ", cityFrom=" + this.getCityFrom() + ", addressFrom=" + this.getAddressFrom() + ", countryTo=" + this.getCountryTo() + ", cityTo=" + this.getCityTo() + ", addressTo=" + this.getAddressTo() + ")";
    }
}
