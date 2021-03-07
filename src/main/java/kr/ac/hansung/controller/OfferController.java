package kr.ac.hansung.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.model.Offer;
import kr.ac.hansung.service.OfferService;

@Controller
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	@RequestMapping("/offers")
	public String showOffer(Model model) {
		List<Offer> offers = offerService.getCurrent();
		model.addAttribute("offers",offers);
		return "offers";
	}
	
	@RequestMapping("/createoffer")
	public String createOffer(Model model) {
		//Data Buffering 을 위해 빈 model 인  Offer()를 만든다.
		model.addAttribute("offer",new Offer());
		return "createoffer";
	}
	
	@RequestMapping("/docreate")
	public String doCreate(Model model, @Valid Offer offer, BindingResult result) {
		
		if(result.hasErrors()) {
			System.out.println("== Form data does not validated ==");
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error:errors) {
				System.out.println(error.getDefaultMessage());
			}
			return "createoffer"; //다시 돌려줌
		}
		
		//Form에서 넘어온 데이터를 Data Binding 시켜야 한다.
		//Controller  -> Service -> DAO
		offerService.insert(offer);
		
		System.out.println(offer);
		return "offercreated";
	}
}
