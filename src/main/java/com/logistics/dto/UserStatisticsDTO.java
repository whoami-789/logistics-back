package com.logistics.dto;

import java.util.List;

public class UserStatisticsDTO {
    private double totalIncome;
    private int availableOrdersCount;
    private List<MonthlyIncomeDTO> monthlyIncome;
    private List<AvailableOrderDTO> availableOrdersByCountry; // Добавьте это поле

    public UserStatisticsDTO(double totalIncome, int availableOrdersCount, List<MonthlyIncomeDTO> monthlyIncome, List<AvailableOrderDTO> availableOrdersByCountry) {
        this.totalIncome = totalIncome;
        this.availableOrdersCount = availableOrdersCount;
        this.monthlyIncome = monthlyIncome;
        this.availableOrdersByCountry = availableOrdersByCountry;
    }

    public UserStatisticsDTO() {
    }

    public double getTotalIncome() {
        return this.totalIncome;
    }

    public int getAvailableOrdersCount() {
        return this.availableOrdersCount;
    }

    public List<MonthlyIncomeDTO> getMonthlyIncome() {
        return this.monthlyIncome;
    }

    public List<AvailableOrderDTO> getAvailableOrdersByCountry() {
        return this.availableOrdersByCountry;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setAvailableOrdersCount(int availableOrdersCount) {
        this.availableOrdersCount = availableOrdersCount;
    }

    public void setMonthlyIncome(List<MonthlyIncomeDTO> monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public void setAvailableOrdersByCountry(List<AvailableOrderDTO> availableOrdersByCountry) {
        this.availableOrdersByCountry = availableOrdersByCountry;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserStatisticsDTO)) return false;
        final UserStatisticsDTO other = (UserStatisticsDTO) o;
        if (!other.canEqual((Object) this)) return false;
        if (Double.compare(this.getTotalIncome(), other.getTotalIncome()) != 0) return false;
        if (this.getAvailableOrdersCount() != other.getAvailableOrdersCount()) return false;
        final Object this$monthlyIncome = this.getMonthlyIncome();
        final Object other$monthlyIncome = other.getMonthlyIncome();
        if (this$monthlyIncome == null ? other$monthlyIncome != null : !this$monthlyIncome.equals(other$monthlyIncome))
            return false;
        final Object this$availableOrdersByCountry = this.getAvailableOrdersByCountry();
        final Object other$availableOrdersByCountry = other.getAvailableOrdersByCountry();
        if (this$availableOrdersByCountry == null ? other$availableOrdersByCountry != null : !this$availableOrdersByCountry.equals(other$availableOrdersByCountry))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserStatisticsDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $totalIncome = Double.doubleToLongBits(this.getTotalIncome());
        result = result * PRIME + (int) ($totalIncome >>> 32 ^ $totalIncome);
        result = result * PRIME + this.getAvailableOrdersCount();
        final Object $monthlyIncome = this.getMonthlyIncome();
        result = result * PRIME + ($monthlyIncome == null ? 43 : $monthlyIncome.hashCode());
        final Object $availableOrdersByCountry = this.getAvailableOrdersByCountry();
        result = result * PRIME + ($availableOrdersByCountry == null ? 43 : $availableOrdersByCountry.hashCode());
        return result;
    }

    public String toString() {
        return "UserStatisticsDTO(totalIncome=" + this.getTotalIncome() + ", availableOrdersCount=" + this.getAvailableOrdersCount() + ", monthlyIncome=" + this.getMonthlyIncome() + ", availableOrdersByCountry=" + this.getAvailableOrdersByCountry() + ")";
    }
}

