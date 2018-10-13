package com.t2b.api.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MembershipPlan extends AuditableEntity {
    private static final long serialVersionUID = 3013996932401904612L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long              membershipPlanId;

    @NotNull
    private String            planName;

    @NotNull
    @Lob
    private String            planDescription;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "membership_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Membership        membership;

    public Long getMembershipPlanId() {
        return membershipPlanId;
    }

    public void setMembershipPlanId(Long membershipPlanId) {
        this.membershipPlanId = membershipPlanId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MembershipPlan [membershipPlanId=");
        builder.append(membershipPlanId);
        builder.append(", planName=");
        builder.append(planName);
        builder.append(", planDescription=");
        builder.append(planDescription);
        builder.append(", membership=");
        builder.append(membership);
        builder.append("]");
        return builder.toString();
    }

}
