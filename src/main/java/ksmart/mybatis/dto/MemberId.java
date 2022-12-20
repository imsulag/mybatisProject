package ksmart.mybatis.dto;

public class MemberId {

	private String memberId;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberId [memberId=");
		builder.append(memberId);
		builder.append("]");
		return builder.toString();
	}
	
	
}
