package seed.project.myPage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seed.project.cart.model.dto.Cart;
import seed.project.cart.model.service.CartService;
import seed.project.member.model.dto.Member;
import seed.project.myPage.model.service.MyPageService;

@Slf4j
@Controller
@SessionAttributes({"loginMember"})
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MypageController {

	private final MyPageService service;
	
	private final CartService cartService;
	
	
	
	
	// 회원정보 변경 화면으로 이동
	@GetMapping("updateInfo")
	public String updateInfo() {
		return "/myPage/updateInfo";
	}
	
	
	
	/** 회원정보 변경하기
	 * @param inputMember : 회원정보 변경 화면에서 입력한 수정된 정보
	 * @param loginMember : 로그인한 회원 정보
	 * @param ra : 리다이렉트 시 request scope로 message 전달
	 * @return redirect:info
	 */
	@PostMapping("updateInfo")
	public String updateInfo(Member inputMember,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra) {
		
		// 수정한 정보가 어떤 회원의 정보를 수정하는지 알기 위해 씀
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
		
		int result = service.updateInfo(inputMember);
		
		String message = null;
		
		if(result > 0) {
			message = "회원정보가 수정되었습니다";
			
			loginMember.setMemberNickname(inputMember.getMemberNickname());
			loginMember.setMemberTel(inputMember.getMemberTel());
			loginMember.setMemberEmail(inputMember.getMemberEmail());
		} else {
			message = "회원정보가 수정 실패했습니다";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:updateInfo"; // 페이지 새로고침(상대주소)
	}
	
	
	
	
	// 비밀번호 변경 화면으로 이동
	@GetMapping("updatePw")
	public String updatePw() {
		return "/myPage/updatePw";
	}
	
	// 비밀번호 변경하기
	/**
	 * @param paramMap : 사용자가 입력한 현재 비번(nowPw),
	 * 					수정할비번(updatePw), 수정될비번(updatePw2)
	 * 					name값들을 Map으로 묶어서 가져옴
	 * @param loginMember : 수정할 회원의 기존 정보
	 * @param ra : 리다이렉트 시 request scope로 message 전달
	 * @return
	 */
	@PostMapping("updatePw")
	public String updatePw(
			@RequestParam Map<String, Object> paramMap,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra) {
		
		// 수정한 비번이 어떤회원 비번을 바꿀 건지 세션에서 가져옴
		int memberNo = loginMember.getMemberNo();
		
		// 비밀번호 수정하기
		int result = service.updatePw(paramMap, memberNo);
		
		String message = null;
		String path = null;
		
		if(result > 0) {
			message = "비밀번호가 수정되었습니다";
			path = "/myPage/updateInfo";
		} else {
			message = "현재 비밀번호가 일치하지 않습니다";
			path = "/myPage/updatePw";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	/** 주소록 관리 페이지 이동시 주소 조회
	 * @param loginMember : 주소 조회용
	 * @param model : 우편번호, 주소, 상세주소 전달용
	 * @return
	 */
	@GetMapping("address")
	public String address(
			@SessionAttribute("loginMember") Member loginMember,
			Model model
			) {
		
		String memberAddress = loginMember.getMemberAddress();
		
		
		if(memberAddress != null) {
			
			// 우편번호^^^메인주소^^^상세주소 로 있는 주소를 나누기
			String[] str = memberAddress.split("\\^\\^\\^");
			
			log.info(memberAddress);
			
			model.addAttribute("postCode", str[0]);
			model.addAttribute("address", str[1]);
			model.addAttribute("detailAddress", str[2]);
		
		}

		
		return "/myPage/address";
	}
	
	
	
	/** 저장된 주소가 3개인지 확인
	 * @param memberNo
	 * @return
	 */
	@ResponseBody
	@PostMapping("addressCount")
	public int addressCount(@RequestBody int memberNo) {
		return service.addressCount(memberNo);
	}
	
	@ResponseBody
	@PutMapping("address")
	public int addAddress(@RequestBody Map<String, Object> map,
			@SessionAttribute("loginMember") Member loginMember,
			Member inputMember,
			RedirectAttributes ra) {
		
		// 나뉘어져있는 주소를 DB에 저장하기 위해 한줄로 합치기 위해 씀
		
		int memberNo = loginMember.getMemberNo();
		
		String str = "";
		String postCode = (String) map.get("postCode");
		String address = (String) map.get("address");
		String detailAddress = (String) map.get("detailAddress");
		str += postCode + "^^^" + address + "^^^" + detailAddress;
		
		Map<String, Object> addressMap = new HashMap<>();
		addressMap.put("memberNo", memberNo);
		addressMap.put("address", str);
		
		int result = service.addressUpdate(addressMap);
		
		if(result > 0) {
			
			loginMember.setMemberAddress(str);
			return 1;
		}
		
//		String str = "";
//		String postCode = (String) map.get("postCode");
//		String address = (String) map.get("address");
//		String detailAddress = (String) map.get("detailAddress");
//		
//		str = postCode + "^^^" + address + "^^^" + detailAddress;
		
//		Map<String, Object> addressMap = new HashMap<>();
//		addressMap.put("memberNo", memberNo);
//		addressMap.put("address", str);
//		
////		log.info(memberNo);
//		
//		System.out.println("memberNo : " + memberNo);
//		System.out.println("addressMap : " + addressMap);
//		log.info(str);
////		log.info
//		
//		int result = service.addressUpdate(addressMap);
//		
//		if(result != 0) {
//			loginMember.setMemberAddress(inputMember.getMemberAddress());
//			
//			return 1;
//		}
		
		return -1;
	}
	
	
	
	
	
	
	
	/** 회원 탈퇴 페이지 이동
	 * @return
	 */
	@GetMapping("withdraw")
	public String withdraw() {
		
		return "/myPage/withdraw";
	}
	
	
	/** 회원 탈퇴
	 * @param map
	 * @return result
	 */
	@ResponseBody
	@DeleteMapping("withdraw")
	public int withdraw(@RequestBody Map<String, Object> map,
				SessionStatus status
			) {
		
		System.out.println("memberNo : " + map.get("memberNo"));
		System.out.println("memberPw : " + map.get("memberPw"));
		 
		if(service.withdraw(map) == 1) {
			status.setComplete();
			
			return 1;
		}
		
		return 0;
	}
	
	
	
	/** 장바구니 페이지로 이동
	 * @return
	 */
	@GetMapping("basket")
	public String basket(
					Model model,
					@SessionAttribute("loginMember") Member loginMember
			) {
		
		List<Cart> cartList = cartService.cartList(loginMember.getMemberNo());
		
		if(cartList.size() > 0) {
			model.addAttribute("cartList", cartList);
		}
		
		
		
		return "/myPage/basket";
	}
	
	
	
	
	/** 작성한 댓글 페이지 이동
	 * @return
	 */
	@GetMapping("comment")
	public String comment() {
		
		return "myPage/comment";
	}
	
	
	
	
	
	
		
	
}
