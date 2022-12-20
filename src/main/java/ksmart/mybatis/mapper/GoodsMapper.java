package ksmart.mybatis.mapper;

import java.util.List;
import java.util.Map;

import ksmart.mybatis.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.dto.Goods;

@Mapper
public interface GoodsMapper {
	
	// 상품목록조회
	public List<Goods> getGoodsList();
	
	// 판매자 조회
	public List<Map<String, Object>> goodsSellerIdList(); 
	
	// 상품 등록
	public void addGoods(Goods goods);
	
	// 상품 수정화면
	public Goods modifyGoods(String goodsCode);
	
	// 상품 수정처리
	public int modifyGoodsInfo(Goods goods);
	
	// 상품 삭제화면
	public Goods removeGoodsInfo(String goodsCode);
	public List<Goods> getGoodsList(String searchKey, String searchValue);
}
