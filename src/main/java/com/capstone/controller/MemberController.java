package com.capstone.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.entity.Member;
import com.capstone.repository.MemberRepository;
import com.capstone.service.EmailService;
import com.capstone.dto.EmailDTO;
import com.capstone.entity.Assist;
import com.capstone.repository.AssistRepository;

@CrossOrigin
@RestController
public class MemberController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AssistRepository assistRepository;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value="/submitMemberDetails",
					consumes=MediaType.APPLICATION_JSON_VALUE,
					method=RequestMethod.POST
					)
	public Member submitMemberDetails(@RequestBody Member member) {
		return memberRepository.save(member);
	}

	@RequestMapping(value="/submitAssistDetails",
					consumes=MediaType.APPLICATION_JSON_VALUE,
					method=RequestMethod.POST
					)
	public void submitAssistDetails(@RequestBody Assist assist, @RequestParam Long memberId) {
		Optional<Member>member = memberRepository.findById(memberId);
		if(member.isPresent()) {
			assist.setAssistMember(member.get());
		assistRepository.save(assist);
		}
	}
	
	@RequestMapping(value="/mail",
					consumes=MediaType.APPLICATION_JSON_VALUE,
					method=RequestMethod.POST
					)
	public void sendEmailMessage(@RequestBody EmailDTO email) {
		emailService.sendMail(email);
	}
	
	@RequestMapping(value="/findMemberId",
					produces=MediaType.APPLICATION_JSON_VALUE,
					method=RequestMethod.GET
					)
	@ResponseBody
	public ResponseEntity<Optional<Member>> findByEmail(String email) {
		Optional<Member>member = memberRepository.findByEmail(email);
		return new ResponseEntity<>(member, HttpStatus.OK);
	}

	@RequestMapping(value="/findAllMembers",
					produces=MediaType.APPLICATION_JSON_VALUE,
					method=RequestMethod.GET
					)
	@ResponseBody
	public ResponseEntity<List<Member>> findAllMembers(){
		List<Member> allMembers = memberRepository.findAll();
		return new ResponseEntity<>(allMembers, HttpStatus.OK);
	}

	@GetMapping("/findAllAssists")
	@ResponseBody
	public ResponseEntity<List<Assist>> findAllAssists(@RequestParam String communityCode) {
		List<Assist> allAssists = assistRepository.findAllByAssistMemberCommunityCode(communityCode);
		return new ResponseEntity<>(allAssists, HttpStatus.OK);
		}
	
	@PostMapping(value="/updateAssist/{assistId}")
	public void updateAssistDetails(@RequestBody Assist assist, @PathVariable Long assistId) {
		Optional<Assist> dbAssist = assistRepository.findById(assistId);
		dbAssist.ifPresent( record -> {
			record.setName(assist.getName());
			record.setAssistanceNeeded(assist.getAssistanceNeeded());
			record.setTopic(assist.getTopic());
			record.setDate(assist.getDate());
			record.setTime(assist.getTime());
			record.setPrice(assist.getPrice());
			assistRepository.save(record);
		});
	}
	
	@DeleteMapping(value="deleteAssist")
	public void deleteAssist(@RequestParam Long assistId) {
		assistRepository.deleteById(assistId);
	}
	
	@RequestMapping(value="/memberLogIn",
					consumes=MediaType.APPLICATION_JSON_VALUE,
					produces=MediaType.APPLICATION_JSON_VALUE,
					method=RequestMethod.POST
					)
	@ResponseBody
	private ResponseEntity<Member> memberLogIn(@RequestBody Member m){
		Optional<Member> member = memberRepository.findByEmail(m.getEmail());
		if(member.isPresent()) {
			Member login= member.get();
			if(login.getPassword().equals(m.getPassword())){
				return new ResponseEntity<>(login, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
