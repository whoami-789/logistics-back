package com.logistics.model;

import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statusName;

    public Status(Long id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Status() {
    }

    public Long getId() {
        return this.id;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Status)) return false;
        final Status other = (Status) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$statusName = this.getStatusName();
        final Object other$statusName = other.getStatusName();
        if (this$statusName == null ? other$statusName != null : !this$statusName.equals(other$statusName))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Status;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $statusName = this.getStatusName();
        result = result * PRIME + ($statusName == null ? 43 : $statusName.hashCode());
        return result;
    }

    public String toString() {
        return "Status(id=" + this.getId() + ", statusName=" + this.getStatusName() + ")";
    }
}
