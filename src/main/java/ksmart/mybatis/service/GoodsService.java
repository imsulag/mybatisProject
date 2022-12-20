package ksmart.mybatis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import groovyjarjarantlr4.v4.gui.TestRig;
import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.mapper.GoodsMapper;

@Service
@Transactional
public class GoodsService {
	
	// 의존성주입
	private final GoodsMapper goodsMapper;
	
	public GoodsService(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}
	
	/**
	 * 상품목록조회
	 * @return goodsList
	 */

	
	/**
	 * 상품등록처리
	 * @param goods
	 */
	public void addGoods(Goods goods) {
		 goodsMapper.addGoods(goods);
	}
	
	/**
	 * 상품수정화면
	 * @param goodsCode
	 * @return goodsInfo
	 */
	public Goods modifyGoods(String goodsCode) {
		
		Goods goodsInfo = goodsMapper.modifyGoods(goodsCode);
		
		return goodsInfo;
	}
	
	/**
	 * 상품수정처리
	 * @param goods
	 * @return
	 */
	public int modifyGoodsInfo(Goods goods) {
		
		int result = goodsMapper.modifyGoodsInfo(goods);
		
		return result;
	}
	
	public Goods removeGoodsInfo(String goodsCode) {
		
		Goods removeInfo = goodsMapper.removeGoodsInfo(goodsCode);
		
		return removeInfo;
	}

    public List<Goods> getGoodsList(String searchKey, String searchValue) {
		List<Goods> goodsList = goodsMapper.getGoodsList(searchKey, searchValue);
		if(searchKey != null){
			switch(searchKey){
				case "goodsCode":
				searchKey = "g.g_code";
			break;
				case "goodsName":
					searchKey = "g.g_name";
					break;
				case "memberId":
					searchKey = "g.g_seller_id";
					break;
				case "memberName":
					searchKey = "m.m_name";
					break;
			}
		}
		return goodslist;
    }
}
