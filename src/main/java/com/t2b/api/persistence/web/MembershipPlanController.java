package com.t2b.api.persistence.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.t2b.api.persistence.entity.MembershipPlan;
import com.t2b.api.persistence.error.ResourceNotFoundException;
import com.t2b.api.persistence.repository.MembershipPlanRepository;
import com.t2b.api.persistence.repository.MembershipRepository;

@RestController
public class MembershipPlanController {

    @Autowired
    private MembershipRepository     membershipRepo;

    @Autowired
    private MembershipPlanRepository membershipPlanRepo;

    @PostMapping(path = "/membership/{membershipId}/membershipPlan")
    public MembershipPlan createMembershipPlan(@PathVariable Long membershipId,
            @Valid @RequestBody MembershipPlan membershipPlan) {
        return membershipRepo.findById(membershipId)
                             .map(membership -> {
                                 membershipPlan.setMembership(membership);
                                 return membershipPlanRepo.save(membershipPlan);
                             })
                             .orElseThrow(() -> new ResourceNotFoundException("Membership with ID " + membershipId
                                     + " doesn't exist."));
    }

    @PutMapping(path = "/membership/{membershipId}/membershipPlan/{membershipPlanId}")
    public MembershipPlan updateMembershipPlan(@PathVariable Long membershipId, @PathVariable Long membershipPlanId,
            @Valid @RequestBody MembershipPlan membershipPlanRequest) {
        if (!membershipRepo.existsById(membershipId)) {
            throw new ResourceNotFoundException("Membership with ID " + membershipId + " doesn't exist.");
        }
        return membershipPlanRepo.findById(membershipPlanId)
                                 .map(membershipPlan -> {
                                     membershipPlan.setPlanName(membershipPlanRequest.getPlanName());
                                     membershipPlan.setPlanDescription(membershipPlanRequest.getPlanDescription());
                                     return membershipPlanRepo.save(membershipPlan);
                                 })
                                 .orElseThrow(() -> new ResourceNotFoundException("MembershipPlan with ID "
                                         + membershipPlanId + " doesn't exist."));
    }

    @DeleteMapping(path = "/membership/{membershipId}/membershipPlan/{membershipPlanId}")
    public ResponseEntity<?> deleteMembershipPlan(@PathVariable Long membershipId,
            @PathVariable Long membershipPlanId) {
        if (!membershipRepo.existsById(membershipId)) {
            throw new ResourceNotFoundException("Membership with ID " + membershipId + " doesn't exist.");
        }
        return membershipPlanRepo.findById(membershipPlanId)
                                 .map(membershipPlan -> {
                                     membershipPlanRepo.delete(membershipPlan);
                                     return ResponseEntity.ok()
                                                          .build();
                                 })
                                 .orElseThrow(() -> new ResourceNotFoundException("MembershipPlan with ID "
                                         + membershipPlanId + " doesn't exist."));
    }

    @GetMapping(path = "/membership/{membershipId}/membershipPlan/{membershipPlanId}")
    public MembershipPlan findMembershipPlan(@PathVariable Long membershipId, @PathVariable Long membershipPlanId) {
        if (!membershipRepo.existsById(membershipId)) {
            throw new ResourceNotFoundException("Membership with ID " + membershipId + " doesn't exist.");
        }
        return membershipPlanRepo.findById(membershipPlanId)
                                 .map(membershipPlan -> membershipPlan)
                                 .orElseThrow(() -> new ResourceNotFoundException("MembershipPlan with ID "
                                         + membershipPlanId + " doesn't exist."));
    }

    @GetMapping("/membership/{membershipId}/membershipPlan")
    public Page<MembershipPlan> getAllByMembershipId(@PathVariable Long membershipId, Pageable pageable) {
        return membershipPlanRepo.findAllByMembershipId(membershipId, pageable);
    }

}
