package com.logistics.dto;

public class AvailableOrderDTO {
    private String country;
    private long count; // Используйте long для количества заказов

    public AvailableOrderDTO(String country, long count) {
        this.country = country;
        this.count = count;
    }

    public AvailableOrderDTO() {
    }

    public String getCountry() {
        return this.country;
    }

    public long getCount() {
        return this.count;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AvailableOrderDTO)) return false;
        final AvailableOrderDTO other = (AvailableOrderDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$country = this.getCountry();
        final Object other$country = other.getCountry();
        if (this$country == null ? other$country != null : !this$country.equals(other$country)) return false;
        if (this.getCount() != other.getCount()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AvailableOrderDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $country = this.getCountry();
        result = result * PRIME + ($country == null ? 43 : $country.hashCode());
        final long $count = this.getCount();
        result = result * PRIME + (int) ($count >>> 32 ^ $count);
        return result;
    }

    public String toString() {
        return "AvailableOrderDTO(country=" + this.getCountry() + ", count=" + this.getCount() + ")";
    }
}
