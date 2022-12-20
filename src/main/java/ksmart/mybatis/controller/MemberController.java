package ksmart.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberId;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.mapper.MemberMapper;
import ksmart.mybatis.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	private final MemberService memberService;
	private final MemberMapper memberMapper;
	
	public MemberController(MemberService memberService, MemberMapper memberMapper) {
		this.memberService = memberService;
		this.memberMapper = memberMapper;
	}
	
	/**
	 * 회원가입하기
	 * @param member
	 * @return redirect:/member/memberList
	 */
	@PostMapping("/addMember")
	public String addMember(Member member) {
		
		log.info("회원가입 쿼리파라미터: {}" , member);
		memberService.addMember(member);
		
		return "redirect:/member/memberList";
	}
	
	/**
	 * 회원가입 화면
	 * @param model
	 * @return member/addMember
	 */
	@GetMapping("/addMember")
	public String addMember(Model model) {
		
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원가입");
		model.addAttribute("memberLevelList", memberLevelList);
		
		return "member/addMember";
	}
	
	
	/**
	 * 회원 목록
	 * @param model
	 * @return member/memberList
	 */
	@GetMapping("/memberList")
	public String getMemberList(Model model,@RequestParam(value="searchKey", required = false) String searchKey,@RequestParam(value="searchValue", required = false, defaultValue = "") String searchValue){
		
		List<Member> memberList = memberService.getMemberList(searchKey, searchValue);
		
		model.addAttribute("title", "회원목록");
		model.addAttribute("memberList", memberList);
		
		return "member/memberList";
	}
	
	/**
	 * 숙제 셀렉트 카운트써서 중복확인하기
	 * @param memberId
	 * @return idCheck
	 */
	@GetMapping("/id/{memberId}")
	@ResponseBody
	public int memberIdCheck(@PathVariable(value = "memberId")String memberId) {
		System.out.println(memberId + " <--- memberId MemberController.java");
		
		int idCheck = memberService.getMemberIdCheck(memberId);
		System.out.println(idCheck + " <--- idCheck MemberController.java");
		
		return idCheck;
	}
	
	/**
	 * 숙제1 카운트 0과1비교 중복체크
	 * @param memberId
	 * @return isChecked
	 */
	@GetMapping("/checkId")
	@ResponseBody
	public boolean checkMemberId(@RequestParam(value = "memberId") String memberId) {
		boolean isChecked = false;
		
		isChecked = memberMapper.checkMemberId(memberId);
		
		return isChecked;
	}
	
	/**
	 * 숙제2 리스트불러서 중복체크
	 * @return memberService.idCheckList
	 */
	@GetMapping("/memberIdList")
	@ResponseBody
	public List<MemberId> idCheckList() {
		System.out.println("잘 넘어오는지 확인 MemberController.java");
				
		return memberService.idCheckList();
	}
	
	/**
	 * 회원 수정 화면
	 * @param memberId
	 * @param model
	 * @return member/modifyMember
	 */
	@GetMapping("/modifyMember")
	public String modifyMember(@RequestParam(value = "memberId", required = false)String memberId, Model model) {
		
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		Member memberInfo = memberService.getMemberInfoId(memberId);
		
		model.addAttribute("title", "회원수정");
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("memberLevelList", memberLevelList);
		
		System.out.println(memberId + " <--- MemberController.java");
		System.out.println(memberInfo + " <--- MemberController.java");
		System.out.println(memberLevelList + " <--- MemberController.java");

		return "member/modifyMember";
	}
	
	/**
	 * 회원 수정
	 * @param member
	 * @return
	 */
	@PostMapping("/modifyMember")
	public String modifymember(Member member) {
		
		log.info("회원수정 : {}", member);
		
		memberService.modifyMember(member);
		
		return "redirect:/member/memberList";
	}
	
	/**
	 * 회원탈퇴
	 * @param memberId
	 * @param model
	 * @return
	 */
	@GetMapping("/removeMember")
	public String removeMember(@RequestParam(value = "memberId")String memberId, Model model) {
		
		model.addAttribute("title", "회원 탈퇴");
		model.addAttribute("memberId", memberId);
		
		return "member/removeMember";
	}
	
	/**
	 * 숙제 비밀번호가 일치하지 않을시 회원탈퇴 폼으로 리다이렉트 완성 비밀번호가 일치시 관계테이블을 고려하여 탈퇴 진행
	 * @param memberId
	 * @param memberPw
	 * @param reAttr
	 * @return redirect:/member/removeMember
	 */
	//@PostMapping("/removeMember")
	public String removeMemberEx1(@RequestParam(value = "memberId")String memberId, @RequestParam(value = "memberPw")String memberPw, RedirectAttributes reAttr) {
		
		log.info("memberId : {}, memberPw : {}", memberId, memberPw);
		
		boolean result = memberService.removeMemberEx1(memberId, memberPw);
		
		if(result) {
			return "redirect:/member/memberList";
		}else {			
			reAttr.addAttribute("memberId", memberId);
			return "redirect:/member/removeMember";
		}
	}
	
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam(value="memberId") String memberId,@RequestParam(value="memberPw") String memberPw,RedirectAttributes reAttr) {
		
		log.info("memberId : {}, memberPw : {}", memberId, memberPw);
		
		Map<String,Object> checkResult = memberService.checkPwByMemberId(memberId, memberPw);
		
		boolean isChecked = (boolean) checkResult.get("result");
		
		String redirectURI = "redirect:/member/memberList";
		
		// 1. 비밀번호가 일치하지 않을 시에는 회원탈퇴 폼으로 리다이렉트
		// /member/removeMember?memberId=id001
		if(!isChecked) {			
			redirectURI = "redirect:/member/removeMember";
			reAttr.addAttribute("memberId", memberId);
			reAttr.addAttribute("msg", "입력하신 회원의 정보가 일치하지 않습니다.");
		}else {
			// 2. 비밀번호 일치 시 관계 테이블을 고려하여 탈퇴 진행 (서비스계층에서 탈퇴 프로세스 완성)
			Member member = (Member) checkResult.get("memberInfo");
			memberService.removeMember(member);
		}
		return redirectURI;
	}
}
