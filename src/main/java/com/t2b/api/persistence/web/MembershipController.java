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

import com.t2b.api.persistence.entity.Membership;
import com.t2b.api.persistence.error.ResourceNotFoundException;
import com.t2b.api.persistence.repository.MemberRepository;
import com.t2b.api.persistence.repository.MembershipRepository;

@RestController
public class MembershipController {

    @Autowired
    private MemberRepository     memberRepo;

    @Autowired
    private MembershipRepository membershipRepo;

    @PostMapping(path = "/member/{memberId}/membership")
    public Membership createMembership(@PathVariable Long memberId, @Valid @RequestBody Membership membership) {
        return memberRepo.findById(memberId)
                         .map(member -> {
                             membership.setMember(member);
                             return membershipRepo.save(membership);
                         })
                         .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + memberId
                                 + " doesn't exist."));
    }

    @PutMapping(path = "/member/{memberId}/membership/{membershipId}")
    public Membership updateMembership(@PathVariable Long memberId, @PathVariable Long membershipId,
            @Valid @RequestBody Membership membershipRequest) {
        if (!memberRepo.existsById(memberId)) {
            throw new ResourceNotFoundException("Member with ID " + memberId + " doesn't exist.");
        }
        return membershipRepo.findById(membershipId)
                             .map(membership -> {
                                 membership.setStartDate(membershipRequest.getStartDate());
                                 membership.setEndDate(membershipRequest.getEndDate());
                                 membership.setCancelled(membershipRequest.isCancelled());
                                 return membershipRepo.save(membership);
                             })
                             .orElseThrow(() -> new ResourceNotFoundException("Membership with ID " + membershipId
                                     + " doesn't exist."));
    }

    @DeleteMapping(path = "/member/{memberId}/membership/{membershipId}")
    public ResponseEntity<?> deleteMembership(@PathVariable Long memberId, @PathVariable Long membershipId) {
        if (!memberRepo.existsById(memberId)) {
            throw new ResourceNotFoundException("Member with ID " + memberId + " doesn't exist.");
        }
        return membershipRepo.findById(membershipId)
                             .map(membership -> {
                                 membershipRepo.delete(membership);
                                 return ResponseEntity.ok()
                                                      .build();
                             })
                             .orElseThrow(() -> new ResourceNotFoundException("Membership with ID " + membershipId
                                     + " doesn't exist."));
    }

    @GetMapping(path = "/member/{memberId}/membership/{membershipId}")
    public Membership findMembership(@PathVariable Long memberId, @PathVariable Long membershipId) {
        if (!memberRepo.existsById(memberId)) {
            throw new ResourceNotFoundException("Member with ID " + memberId + " doesn't exist.");
        }
        return membershipRepo.findById(membershipId)
                             .map(membership -> membership)
                             .orElseThrow(() -> new ResourceNotFoundException("Membership with ID " + membershipId
                                     + " doesn't exist."));
    }

    @GetMapping("/member/{memberId}/membership")
    public Page<Membership> getAllByMemberId(@PathVariable Long memberId, Pageable pageable) {
        return membershipRepo.findAllByMemberId(memberId, pageable);
    }

}
