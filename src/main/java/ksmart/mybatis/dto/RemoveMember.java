package ksmart.mybatis.dto;

public class RemoveMember {

		private boolean memberCheck;
		private int memberLevel;
		
		public boolean getisMemberCheck() {
			return memberCheck;
		}
		public void setMemberCheck(boolean memberCheck) {
			this.memberCheck = memberCheck;
		}
		public int getMemberLevel() {
			return memberLevel;
		}
		public void setMemberLevel(int memberLevel) {
			this.memberLevel = memberLevel;
		}
		
		@Override
		public String toString() {
			return "removeMember [memberCheck=" + memberCheck + ", memberLevel=" + memberLevel + "]";
		}
		
		
		
		
}
