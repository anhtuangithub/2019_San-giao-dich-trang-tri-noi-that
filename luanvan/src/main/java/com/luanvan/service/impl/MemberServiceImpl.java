package com.luanvan.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.luanvan.exception.NotFoundException;
import com.luanvan.model.Member;
import com.luanvan.model.Store;
import com.luanvan.repo.MemberRepository;
import com.luanvan.repo.MemberTypeRepository;
import com.luanvan.repo.StoreRepository;
import com.luanvan.repo.UsersRepository;
import com.luanvan.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	
	private MemberRepository memberRepository;
	private UsersRepository userRepository;
	private StoreRepository storeRepository;
	private MemberTypeRepository memberTypeRepository;

	public MemberServiceImpl(MemberRepository memberRepository,
			StoreRepository storeRepository,
			UsersRepository userRepository,
			MemberTypeRepository memberTypeRepository) {
		this.memberRepository = memberRepository;
		this.storeRepository = storeRepository;
		this.userRepository = userRepository;
		this.memberTypeRepository = memberTypeRepository;
	}

	@Override
	public List<Member> findAllMember() {
	
		return memberRepository.findAll();
	}

	@Override
	public void save(Member member) {
		memberRepository.save(member);
		
	}

	@Override
	public void delete(Long id) {
		memberRepository.deleteById(id);
		
	}

	@Override
	public Member findMemberById(Long id) {
		return memberRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Override
	public List<Member> HistoryMember(Authentication auth) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Long storeid = storeRepository.findByUsersId(userid).getId();
		return memberRepository.findByStoresIdOrderByIdDesc(storeid);
	}
	
	@Override
	public void giaHan(Authentication auth, int memberTypeId) {
		Long userid = userRepository.findByEmail(auth.getName()).getId();
		Store store = storeRepository.findByUsersId(userid);
		Member member = memberRepository.findFirstByStoresIdOrderByIdDesc(store.getId());
		
		Member memberSave = new Member();
		Calendar ketThuc = Calendar.getInstance();
		ketThuc.setTime(member.getEnd_time());
		
		Calendar today = Calendar.getInstance();

		if(today.getTime().before(ketThuc.getTime())) {
			today.setTime(member.getEnd_time());
			memberSave.setStart_time(member.getEnd_time());
		}
		else {
			memberSave.setStart_time(today.getTime());
		}
		if(memberTypeId == 4) {
			today.add(Calendar.MONTH, 12);;
			memberSave.setEnd_time(today.getTime());
		}
		else if(memberTypeId == 3) {
			today.add(Calendar.MONTH, 6);;
			memberSave.setEnd_time(today.getTime());
		}
		else if(memberTypeId == 2) {
			today.add(Calendar.MONTH, 3);;
			memberSave.setEnd_time(today.getTime());
		}
		else if(memberTypeId == 1) {
			today.add(Calendar.MONTH, 1);;
			memberSave.setEnd_time(today.getTime());
		}
		memberSave.setMembertypes(memberTypeRepository.getOne((long)memberTypeId));
		memberSave.setStores(store);
		memberRepository.save(memberSave);
	}
	
	
}
