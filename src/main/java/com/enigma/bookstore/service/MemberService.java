package com.enigma.bookstore.service;

import com.enigma.bookstore.entity.Member;

import java.util.List;

public interface MemberService {
    public List<Member> getAllMembers();
    public Member getMemberById(String id);
    public Member createMember(Member member);
    public Member updateMember(Member member);
    public void deleteMember(String id);
}
