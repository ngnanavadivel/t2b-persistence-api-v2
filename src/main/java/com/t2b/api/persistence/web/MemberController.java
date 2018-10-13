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

import com.t2b.api.persistence.entity.Member;
import com.t2b.api.persistence.error.ResourceNotFoundException;
import com.t2b.api.persistence.repository.MemberRepository;

@RestController
public class MemberController {

    @Autowired
    private MemberRepository repo;

    @PostMapping(path = "/member")
    public Member createMember(@Valid @RequestBody Member member) {
        return repo.save(member);
    }

    @PutMapping(path = "/member/{memberId}")
    public Member updateMember(@PathVariable Long memberId, @RequestBody Member memberRequest) {
        return repo.findById(memberId)
                   .map(member -> {
                       member.setFirstName(memberRequest.getFirstName());
                       member.setLastName(memberRequest.getLastName());
                       member.setEmailAddress(memberRequest.getEmailAddress());
                       member.setPhoneNumber(memberRequest.getPhoneNumber());
                       member.setAddressLine1(memberRequest.getAddressLine1());
                       member.setAddressLine2(memberRequest.getAddressLine2());
                       member.setCity(memberRequest.getCity());
                       member.setState(memberRequest.getState());
                       member.setZipCode(memberRequest.getZipCode());
                       member.setCancel(memberRequest.isCancel());
                       return member;
                   })
                   .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + memberId + " doesn't exist."));
    }

    @DeleteMapping(path = "/member/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
        return repo.findById(memberId)
                   .map(member -> {
                       repo.delete(member);
                       return ResponseEntity.ok()
                                            .build();
                   })
                   .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + memberId + " doesn't exist."));
    }

    @GetMapping(path = "/member/{memberId}")
    public Member findMember(@PathVariable Long memberId) {
        return repo.findById(memberId)
                   .map(member -> member)
                   .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + memberId + " doesn't exist."));
    }

    @GetMapping("/member")
    public Page<Member> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

}
