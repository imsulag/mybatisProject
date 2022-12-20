package ksmart.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberId;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.dto.RemoveMember;
import ksmart.mybatis.mapper.MemberMapper;

@Service
@Transactional
public class MemberService {
	
	
	private static final Logger log = LoggerFactory.getLogger(MemberService.class);

	
	private final MemberMapper memberMapper;

	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}

	/**
	 * 회원 가입
	 * 
	 * @param member
	 */
	public void addMember(Member member) {
		memberMapper.addMember(member);
	}

	/**
	 * 회원 등급 조회
	 * 
	 * @return List<MemberLevel>
	 */
	public List<MemberLevel> getMemberLevelList() {

		List<MemberLevel> memberLevelList = memberMapper.getMemberLevelList();

		return memberLevelList;
	}

	/**
	 * 회원목록 조회
	 * @param searchValue 
	 * @param searchKey 
	 * 
	 * @return List<Member>
	 */
	public List<Member> getMemberList(String searchKey, String searchValue) {

		if(searchKey != null) {
			switch (searchKey) {
			case "memberId":
				searchKey = "m.m_id";
				break;
			case "memberLevel":
				searchKey = "ml.level_name";
				break;
			case "memberName":
				searchKey = "m.m_name";
				break;
			case "memberEmail":
				searchKey = "m.m_email";
				break;
			}
		}
		
		List<Member> memberList = memberMapper.getMemberList(searchKey, searchValue);
		for(Member member : memberList) {
			int memberLevel = member.getMemberLevel();
		
			switch (memberLevel) {
				case 1: {
					member.setMemberLevelName("관리자");
					break;
				}
				case 2: {
					member.setMemberLevelName("판매자");
					break;
				}
				default:
					member.setMemberLevelName("구매자");
			}
		}
		
		return memberList;
	}

	/**
	 * 숙제 셀렉트 카운트써서 중복확인하기
	 * 
	 * @return idCheck
	 */
	public int getMemberIdCheck(String memberId) {
		System.out.println(memberId + " <--- memberId MemberService.java");

		int idCheck = memberMapper.memberIdCheck(memberId);
		System.out.println(idCheck + " <--- idCheck MemberService.java");

		return idCheck;
	}
	/**
	 * 숙제
	 * @return
	 */
	public List<MemberId> idCheckList() {
		System.out.println("잘 넘어오는지 확인 MemberService.java");
		List<MemberId> memberId = memberMapper.getMemberIdList();
		System.out.println(memberId + " <--- MemberService.java");

		return memberId;
	}

	/**
	 * 특정회원 조회
	 * 
	 * @param memberId
	 * @return Member
	 */
	public Member getMemberInfoId(String memberId) {
		System.out.println(memberId + " <--- updateMember MemberService.java");

		Member memberInfo = memberMapper.getMemberInfoById(memberId);

		return memberInfo;
	}

	/**
	 * 특정회원 수정
	 * 
	 * @param member
	 * @return int (update 실행 결과)
	 */
	public int modifyMember(Member member) {

		int result = memberMapper.modifyMember(member);

		return result;
	}

	/**
	 * 숙제
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public boolean removeMemberEx1(String memberId, String memberPw) {
		System.out.println(memberId + " <--- removeMember MemberService.java");
		System.out.println(memberPw + " <--- removeMember MemberService.java");

		RemoveMember removeMemberInfo = memberMapper.removeMember(memberId, memberPw);
		
		boolean result = false;
		
		if(removeMemberInfo.getisMemberCheck() == true) {
			result = removeMemberInfo.getisMemberCheck();
			int memberLevel = removeMemberInfo.getMemberLevel();
			System.out.println(memberId + " <--- if!!! removeMember MemberService.java");
			System.out.println(memberLevel + " <--- if!!! removeMember MemberService.java");
			memberMapper.realremoveMember(memberId, memberLevel);
		}
		return result;	
	} 
	
	/**
	 * 권한별 회원탈퇴
	 * @param member
	 * @return int
	 */
	public int removeMember(Member member) {
		int result = 0;
		log.info("탈퇴회원의 정보: {}", member);
		String memberId = member.getMemberId();
		String memberLevelName = member.getMemberLevelName();
		
		if("판매자".equals(memberLevelName)) {
			result += memberMapper.removeOrderByGoodsCode(memberId);
			result += memberMapper.removeGoodsById(memberId);
		}
		
		if("구매자".equals(memberLevelName)) {
			result += memberMapper.removeOrderById(memberId);
		}
		
		result += memberMapper.removeLoginHistoryById(memberId);
		result += memberMapper.removeMemberById(memberId);
	
		return result;
	}
	
	/**
	 * 회원정보(비밀번호) 확인
	 * @param memberId, memberPw
	 * @return
	 */
	public Map<String, Object> checkPwByMemberId(String memberId, String memberPw) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		boolean result = false;
		
		Member member = memberMapper.getMemberInfoById(memberId);
		if(member != null) {
			String checkPw = member.getMemberPw();
			if(memberPw.equals(checkPw)) {
				result = true;
			}
		}
		
		resultMap.put("result", result);
		resultMap.put("memberInfo", member);
		
		return resultMap;
	}
	
}
