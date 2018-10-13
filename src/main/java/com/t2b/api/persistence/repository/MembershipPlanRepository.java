package com.t2b.api.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.t2b.api.persistence.entity.MembershipPlan;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
    Page<MembershipPlan> findAllByMembershipId(Long membershipId, Pageable pageable);
}
