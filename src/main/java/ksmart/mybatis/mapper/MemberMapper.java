package ksmart.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberId;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.dto.RemoveMember;

@Mapper
public interface MemberMapper {
	
	// 회원 가입
	public int addMember(Member member);
	
	// 회원등급조회
	public List<MemberLevel> getMemberLevelList();
	
	// 회원목록조회
	public List<Member> getMemberList(String searchKey, String searchValue);
	
	/**
	 * 숙제 셀렉트 카운트써서 중복확인하기
	 * @param memberId
	 * @return 
	 */
	// 아이디 중복 체크
	public int memberIdCheck(String memberId); 
	
	public List<MemberId> getMemberIdList();
	
	public boolean checkMemberId(String memberId);
	
	// 회원정보 조회
	public Member getMemberInfoById(String memberId);
	
	// 특정회원 정보 수정
	public int modifyMember(Member member);
	
	// 회원탈퇴 정보 확인
	public RemoveMember removeMember(String memberId, String memberPw);
	
	// 회원탈퇴(다중쿼리문)
	public void realremoveMember(String memberId, int memberLevel);
	
	// 판매자 등록한 상품에 대한 주문이력삭제
	public int removeOrderByGoodsCode(String memberId);
	
	// 판매자 등록한 상품 삭제
	public int removeGoodsById(String memberId);
	
	// 구매자 구매한 주문이력삭제
	public int removeOrderById(String memberId);
	
	// 회원 로그인 이력 삭제
	public int removeLoginHistoryById(String memberId);

	// 회원 탈퇴
	public int removeMemberById(String memberId);
	

	 
}
