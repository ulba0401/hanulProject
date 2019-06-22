package com.hanul.AA;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.SessionAttributes;


import client.ClientVO;
import common.CommonServiceImpl;
import common.CommonVO;
import community.Com_commentImpl;
import community.Com_commentService;
import community.CommunityPage;
import community.CommunityServiceImpl;
import community.CommunityVO;
import community.LikeVO;

@Controller @SessionAttributes({"category", "title"})
public class CommunityController {
	@Autowired private CommunityServiceImpl service;	
	@Autowired private Com_commentImpl com_service;
	@Autowired private CommonServiceImpl common;
	
	//Ŀ�´�Ƽ ����
	@RequestMapping("/delete.co")
	public String delete(@RequestParam int no) {
		service.delete(no);
		return "redirect:list.co";
	}
	
	//Ŀ�´�Ƽ ���ƿ� ����Ʈ üũ
	@ResponseBody @RequestMapping("/likeListCheck.co")
	public void likeListCheck(@RequestParam String id, LikeVO vo, Model model) {
		//System.out.println(id);
		model.addAttribute("page", service.communityLikeListCheck(page, id));
		CommunityPage p = service.communityLikeListCheck(page, id);
		List<CommunityVO> t = p.getList();
		for (CommunityVO communityVO : t) {
			System.out.println(communityVO.getTitle());  
		}
	}
	
	//Ŀ�´�Ƽ ���ƿ� ���
	@RequestMapping("/likeCancel.co")
	public String likeCancel(Model model, LikeVO vo,  @RequestParam int no) {
		model.addAttribute("like", service.communityLikeCancel(vo));
		return "redirect:detail.co?no="+vo.getNo()+"&id="+vo.getId();
	}
	
	//Ŀ�´�Ƽ ���ƿ�
	@RequestMapping("/liketo.co")
	public String liketo(Model model, LikeVO vo) {
		//System.out.println("123123123123123");
		model.addAttribute("like", service.communityLike(vo));
		return "redirect:detail.co?no="+vo.getNo()+"&id="+vo.getId();
	}
	
	//Ŀ�´�Ƽ ���� ���� ��û
	@RequestMapping("/detail.co")
	public String communityDetail(@RequestParam int no, Model model, LikeVO vo) {
		service.count(no);
		model.addAttribute("com_list",com_service.list(no));
		model.addAttribute("vo", service.communityDetail(no));
		/*if (vo.getId() == null) {
			
		}*/
		vo = service.communityLikeCheck(vo);
		model.addAttribute("like", vo);
		
		return "community/detail_co";
	}
	
	//Ŀ�´�Ƽ ���������� ������Ʈ
	@RequestMapping("/update.co")
	public String update(CommunityVO vo, HttpSession session, @RequestParam MultipartFile attach_file, Model model) {
		System.out.println("uploadType = "+vo.getUploadType());
		if(attach_file.getSize()>0) {
			HashMap<String, String> map = common.upload("community", attach_file, session);
			vo.setFileRealPath(map.get("fileRealPath"));
			vo.setFileName(map.get("fileName"));
			vo.setFilePath(CommonVO.serverIP + "/AA/resources/images/community/"+map.get("fileName"));
			//System.out.println(map.get("fileRealPath"));
			//System.out.println(map.get("fileName"));
			//System.out.println(CommonVO.ip + "/AA/resources/images/upload/"+map.get("fileName"));

		}else if(vo.getUploadType() == null){
			vo.setUploadType("");
			System.out.println("�η� ����");
		}
		service.updateFile(vo);
		
		System.out.println(vo.getFilePath());
		model.addAttribute(vo);
		return "redirect:detail.co?no="+vo.getNo()+"&id="+vo.getId();
	}
	
	//Ŀ�´�Ƽ ���������� ��û
	@RequestMapping("/modifyPage.co")
	public String modifyPage(Model model, @RequestParam int no) {
		model.addAttribute("vo", service.communityDetail(no));
		return "community/modifyPage_co";
	}
	
	//Ŀ�´�Ƽ ȭ�� ��û
	@Autowired private CommunityPage page;
	@RequestMapping("/list.co")
	public String readCommunity(Model model, 
		@RequestParam(defaultValue="1") int curPage,
		@RequestParam(defaultValue="all") String search,
		@RequestParam(defaultValue="") String keyword
		, HttpSession session, 
		@RequestParam(defaultValue="") String id,
		@RequestParam(defaultValue="") String like) {
		if(!(LoginController.login == null)) {
			id = LoginController.login.getId();
		}
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		model.addAttribute("category", "co");
		model.addAttribute("title", "Ŀ�´�Ƽ");
		session.removeAttribute("status_info");
		session.removeAttribute("myHomeExistense");
		model.addAttribute("page", service.readCommunity(page));
		if(like.equals("like")) {
			model.addAttribute("page", service.communityLikeListCheck(page, id));
		}
		model.addAttribute("like_list", service.likeList(id));
		
		return "community/list_co";
	}
	
	//Ŀ�´�Ƽ �۾��� ȭ�� ��û
	@RequestMapping("/insert.co")
	public String insertForm() {
		return "community/insertForm_co";
	}
	
	//Ŀ�´�Ƽ �۾��� ����
	@RequestMapping("/insertData.co")
	public String insertData(CommunityVO vo, HttpSession session, @RequestParam MultipartFile attach_file) {
		if(attach_file.getSize()>0) {
			HashMap<String, String> map = common.upload("community", attach_file, session);
			vo.setFileRealPath(map.get("fileRealPath"));
			vo.setFileName(map.get("fileName"));
			vo.setFilePath(CommonVO.serverIP + "/AA/resources/images/community/"+map.get("fileName"));
			System.out.println(map.get("fileRealPath"));
			System.out.println(map.get("fileName"));
			System.out.println(CommonVO.serverIP + "/AA/resources/images/community/"+map.get("fileName"));
		}
		service.insertData(vo);
		return "redirect:list.co?no="+vo.getNo()+"&id="+vo.getId();
	}
}
