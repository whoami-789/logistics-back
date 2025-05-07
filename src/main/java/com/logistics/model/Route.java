package com.logistics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
    @Table(name = "routes")
    public class Route {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String countryFrom;
        private String cityFrom;
        private String addressFrom;
        private String countryTo;
        private String cityTo;
        private String addressTo;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "order_id", nullable = false)
        @JsonIgnore // Игнорируем это поле при сериализации
        private Orders order; // Связь с заказом

    public Route(Long id, String countryFrom, String cityFrom, String addressFrom, String countryTo, String cityTo, String addressTo, Orders order) {
        this.id = id;
        this.countryFrom = countryFrom;
        this.cityFrom = cityFrom;
        this.addressFrom = addressFrom;
        this.countryTo = countryTo;
        this.cityTo = cityTo;
        this.addressTo = addressTo;
        this.order = order;
    }

    public Route() {
    }

    public Long getId() {
        return this.id;
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

    public Orders getOrder() {
        return this.order;
    }

    public void setId(Long id) {
        this.id = id;
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

    @JsonIgnore
    public void setOrder(Orders order) {
        this.order = order;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Route)) return false;
        final Route other = (Route) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
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
        final Object this$order = this.getOrder();
        final Object other$order = other.getOrder();
        if (this$order == null ? other$order != null : !this$order.equals(other$order)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Route;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
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
        final Object $order = this.getOrder();
        result = result * PRIME + ($order == null ? 43 : $order.hashCode());
        return result;
    }

    public String toString() {
        return "Route(id=" + this.getId() + ", countryFrom=" + this.getCountryFrom() + ", cityFrom=" + this.getCityFrom() + ", addressFrom=" + this.getAddressFrom() + ", countryTo=" + this.getCountryTo() + ", cityTo=" + this.getCityTo() + ", addressTo=" + this.getAddressTo() + ", order=" + this.getOrder() + ")";
    }
}

