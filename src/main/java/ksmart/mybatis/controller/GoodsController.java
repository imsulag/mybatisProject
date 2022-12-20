package ksmart.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.mapper.GoodsMapper;
import ksmart.mybatis.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	
	private static final Logger log = LoggerFactory.getLogger(GoodsController.class);

	
	// 의존성 주입(DI)
	private final GoodsService goodsService;
	private final GoodsMapper goodsMapper;
	
	public GoodsController(GoodsService goodsService, GoodsMapper goodsMapper) {
		this.goodsService = goodsService;
		this.goodsMapper = goodsMapper;
	}
	
	/**
	 * 상품목록조회
	 * @param model
	 * @return goods/goodsList
	 */

	@GetMapping("/goodsList")
	public String getGoodsList(Model model, @RequestParam(value = "searchKey", required = false) String searchKey, @RequestParam(value = "searchValue", required = false, defaultValue = "")String searchValue){
		List<Goods> goodsList = goodsService.getGoodsList(searchKey, searchValue);

		model.addAttribute("title", "상품목록");
		model.addAttribute("goodsList", goodsList);

		return "goods/goodsList";
	}
	
	/**
	 * 상품등록 화면
	 * @param model
	 * @return goods/addgoods
	 */
	@GetMapping("/addgoods")
	public String addgoods(Model model) {
		
		
		List<Map<String, Object>> sellerIdList = goodsMapper.goodsSellerIdList();
		log.info("판매자 아이디 조회: {}", sellerIdList);
		model.addAttribute("title", "상품등록");
		model.addAttribute("sellerIdList", sellerIdList);
		
		return "goods/addgoods";
	}
	/**
	 * 상품등록처리
	 * @param goods
	 * @return redirect:/goods/goodsList
	 */
	@PostMapping("/addgoods")
	public String addgoods(Goods goods) {
		
		log.info("상품등록: {}", goods);
		goodsService.addGoods(goods);
		
		return "redirect:/goods/goodsList";
	}
	
	/**
	 * 상품수정화면
	 * @param goodsCode
	 * @return goods/modifyGoods
	 */
	@GetMapping("/modifyGoods")
	public String modifyGoods(@RequestParam(value = "goodsCode", required = false)String goodsCode, Model model) {
		
		Goods goodsInfo = goodsService.modifyGoods(goodsCode);
		
		log.info("상품코드: {}", goodsCode);
		model.addAttribute("title", "상품수정");
		model.addAttribute("goodsInfo", goodsInfo);
		
		return "goods/modifyGoods";
	}
	
	/**
	 * 상품수정처리
	 * @param goods
	 * @return
	 */
	@PostMapping("/modifyGoods")
	public String modifyGoods(Goods goods) {
		
		log.info("수정할 상품 정보: {}", goods);
		goodsService.modifyGoodsInfo(goods);
		return "redirect:/goods/goodsList";
	}
	
	@GetMapping("/removeGoods")
	public String removeGoods(@RequestParam(value = "goodsCode")String goodsCodeString, Model model) {
		log.info("상품코드: {}", goodsCodeString);
		
		
		
		
		
		return "goods/removeGoods";
	}
	
	
}
