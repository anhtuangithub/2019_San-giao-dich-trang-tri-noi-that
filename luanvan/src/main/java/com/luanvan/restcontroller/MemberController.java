package com.luanvan.restcontroller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.model.Member;
import com.luanvan.service.MemberService;

@RestController
@RequestMapping("members")
public class MemberController {

	private MemberService memberService;
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping
	public List<Member> findAllMember(){
		return memberService.findAllMember();
	}
	
	@PostMapping
	public void create(@RequestBody Member member) {
		memberService.save(member);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		memberService.delete(id);
	}
	
	@GetMapping("/{id}")
	public Member findMemberById(@PathVariable Long id) {
		return memberService.findMemberById(id);
	}
	
	@GetMapping("/lich-su-dang-ki")
	public List<Member> historyMember(Authentication auth) {
		return memberService.HistoryMember(auth);
	}
	
	@GetMapping("/gia-han/{memberTypeId}")
	public void historyMember(Authentication auth,@PathVariable int memberTypeId) {
		memberService.giaHan(auth, memberTypeId);
	}
}
