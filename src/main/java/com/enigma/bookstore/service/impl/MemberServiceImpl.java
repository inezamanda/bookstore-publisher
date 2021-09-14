package com.enigma.bookstore.service.impl;

import com.enigma.bookstore.constant.ResponseMessage;
import com.enigma.bookstore.entity.Member;
import com.enigma.bookstore.repository.MemberRepository;
import com.enigma.bookstore.service.MemberService;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Integer id) {
        verify(id);
        return memberRepository.getById(id);
    }

    @Override
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Member member) {
        verify(member.getId());
        return memberRepository.save(member);
    }

    @Override
    public void deleteMember(Integer id) {
        verify(id);
        memberRepository.deleteById(id);
    }

    private void verify (Integer id) {
        if (!memberRepository.findById(id).isPresent()){
            String message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "member", id);
            throw new ResourceNotFoundException(message);
        }
    }
}
