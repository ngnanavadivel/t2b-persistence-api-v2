package com.t2b.api.persistence.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Membership extends AuditableEntity {

    private static final long serialVersionUID = 4591375810571419180L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long              id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date              startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date              endDate;

    @NotNull
    private boolean           cancelled        = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Member            member;

    public Long getId() {
        return id;
    }

    public void setId(Long membershipId) {
        this.id = membershipId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Membership [id=");
        builder.append(id);
        builder.append(", startDate=");
        builder.append(startDate);
        builder.append(", endDate=");
        builder.append(endDate);
        builder.append(", cancelled=");
        builder.append(cancelled);
        builder.append(", member=");
        builder.append(member);
        builder.append("]");
        return builder.toString();
    }

}
