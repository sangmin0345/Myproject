package controller;

import java.util.ArrayList;
import java.util.List;

import model.SuCartBean;

// 상품내용을 ArrayList에 저장하고 또는
// 기존에 상품이 있다면 수량만 증가시키는 클래스, 삭제기능과 모든 카트내용 삭제
public class Cart { // 수작업 공구 뿐만 아니라 전동공구메뉴에도 동시에 사용할 수 있기 때문에..
					// 일부러 하나의 Cart클래스를 만듬
	
	// 여러대의 SuCartBean상품(수작업공구상품, 전동공구상품)을 저장하기 위한 ArrayList 생성
	private List<SuCartBean> itemlist = new ArrayList<SuCartBean>();
	
	// ArrayList를 리턴하는 메서드
	public List<SuCartBean> getItemlist(){
		return itemlist;
	}
	
	// SujakInfo.jsp페이지에서 "카트담기"버튼을 눌렀을때..
	// ShoppingController.java의 sutoolCart(SuCartBean cartBean, HttpSession session)메서드의
	// 인자로 전달받은 cartBean객체를 다시 아래의 push메서드의 인자로 전달 받는다.
	// cartbean객체를 인자로 전달받아.. ArrayList객체에 상품을 추가하는 메서드인데..
	// 하는일:
	// 기존에 상품이 있다면~~ 수량만 증가하고 기존에 상품 있는지 없는지 알아내는 변수값을 true변경 해주고..
	// 기존에 상품이 없는경우에는 상품을 추가시켜주는일을 하는 메서드
	public void push(SuCartBean cartbean){

		// 기존에 상품이 있는지를 알아내는 변수
		boolean alreadysutool = false;
		
		// itemlist안에 데이터를 반복을 돌면서 기존에 있는 데이터가 있는지 비교
		for(SuCartBean suCartBean : itemlist){
			
			// push()메서드의 인자로 넘어온 cartbean객체의 공구번호와
			// ArrayList에 담겨있는 수작업 공구번호 같을때
			if(cartbean.getSuno() == suCartBean.getSuno()){
				// 동일한 상품이 존재하기에.. 수량만 증가
				suCartBean.setSuqty(suCartBean.getSuqty() + cartbean.getSuqty());
				// 기존에 상품이 있을때 판별값 저장 true
				alreadysutool = true;
				break;
			}
		} // for
		
		if(alreadysutool == false){
			// 어레이리스트에 상품추가
			itemlist.add(cartbean);
		}
	} // push()

	public void deleteCart(int suno) {

		for(SuCartBean suCartBean : itemlist){
			
			if(suCartBean.getSuno() == suno){
				itemlist.remove(suCartBean);
				break;
			}
		}
		
		
	}

	// 카트 비우기
	// arraylist내부에 있는 상품 삭제
	public void clearCart() {
		itemlist.removeAll(itemlist);
	}

	
	
	
	
	
}
