package com.estsoft.mysite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.domain.Guestbook;
import com.estsoft.mysite.service.GuestbookService;

@Controller
@RequestMapping( "/guestbook" )
public class GuestbookController {
	@Autowired
	GuestbookService guestbookService;
	
	@RequestMapping("")
	public String index( Model model ) {
		List<Guestbook> list = guestbookService.getMessageList();
		model.addAttribute( "list", list );
		return "/guestbook/list";
	}
	

	@RequestMapping( value="/add", method=RequestMethod.POST  )
	public String add( @ModelAttribute Guestbook vo ) {
		guestbookService.insertMessage(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping( value="/ajax-add", method=RequestMethod.POST  )
	@ResponseBody
	public Object ajaxAdd( @ModelAttribute Guestbook vo ) {
		boolean isAjax = true;
		Map<String, Object> map = guestbookService.insertMessage(vo, isAjax);
		return map;
	}
	
	@RequestMapping( value="/ajax-delete", method=RequestMethod.POST  )
	@ResponseBody
	public Object ajaxDelete( @ModelAttribute Guestbook vo ) {
		boolean isAjax = true;
		return guestbookService.deleteMessage(vo, isAjax);
	}

	@RequestMapping( value="/ajax-list", method=RequestMethod.GET  )
	public String ajaxIndex() {
		return "/guestbook/ajax-main";
	}
	
	@RequestMapping( value="/ajax-list/{page}", method=RequestMethod.GET  )
	@ResponseBody
	public Object ajaxList( @PathVariable( "page" ) int page ) {
		Map<String, Object> map = guestbookService.getList(page);
		return map;
	}
	
	@RequestMapping( value="/delete/{no}", method=RequestMethod.GET )
	public String deleteform( @PathVariable( "no" ) Long no, Model model ) {
		model.addAttribute( "no", no );
		return "/guestbook/deleteform";
	}
	
	@RequestMapping( value="/delete", method=RequestMethod.POST  )
	public String delete( @ModelAttribute Guestbook vo ) {
		guestbookService.deleteMessage(vo);
		return "redirect:/guestbook";
	}

	
}
