package com.logistics.dto;

public class MonthlyIncomeDTO {
    private String month;
    private double amount;

    public MonthlyIncomeDTO(String month, double amount) {
        this.month = month;
        this.amount = amount;
    }

    public MonthlyIncomeDTO() {
    }

    public String getMonth() {
        return this.month;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MonthlyIncomeDTO)) return false;
        final MonthlyIncomeDTO other = (MonthlyIncomeDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$month = this.getMonth();
        final Object other$month = other.getMonth();
        if (this$month == null ? other$month != null : !this$month.equals(other$month)) return false;
        if (Double.compare(this.getAmount(), other.getAmount()) != 0) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MonthlyIncomeDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $month = this.getMonth();
        result = result * PRIME + ($month == null ? 43 : $month.hashCode());
        final long $amount = Double.doubleToLongBits(this.getAmount());
        result = result * PRIME + (int) ($amount >>> 32 ^ $amount);
        return result;
    }

    public String toString() {
        return "MonthlyIncomeDTO(month=" + this.getMonth() + ", amount=" + this.getAmount() + ")";
    }
}
