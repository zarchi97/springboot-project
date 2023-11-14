package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberDetailsService implements UserDetailsService{
@Autowired
private MemberRepository memberRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Member member=memberRepo.findByUsername(username);
		if(member==null) {
			throw new UsernameNotFoundException("Couldn't found user!");
		}
		MemberDetails memberDetails=new MemberDetails(member);
		return memberDetails;
	}

}
