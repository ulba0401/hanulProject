package com.hanul.AA;


import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import common.CommonServiceImpl;
import common.CommonVO;
import notice.NoticePage;
import notice.NoticeServiceImpl;
import notice.NoticeVO;

@Controller @SessionAttributes({"category", "title"})
public class NoticeController {
	@Autowired private NoticeServiceImpl service;
	@Autowired private CommonServiceImpl common;
	
	//�������� �ٿ�ε� Ŭ���� ���� �޼ҵ�
	@RequestMapping("/download.no") @ResponseBody
	public File download(@RequestParam int no, HttpSession session, HttpServletResponse response) {
		NoticeVO vo = service.noticeDetail(no);
		return common.download(vo.getFileRealPath(), vo.getFileName(), session, response);
	}
	//�������� �� ����
	@RequestMapping("/delete.no")
	public String delete(@RequestParam int no) {
		service.delete(no);
		return "redirect:list.no";
	}
	//�������� �� �Է�
	@RequestMapping("/insertData.no")
	public String insertData(NoticeVO vo, @RequestParam MultipartFile attach_file, HttpSession session) {
		
		if(attach_file.getSize()>0) {
			HashMap<String, String> map = common.upload("notice", attach_file, session);
			vo.setFileRealPath(map.get("fileRealPath"));
			vo.setFileName(map.get("fileName"));
			vo.setFilePath(CommonVO.ip + "/AA/resources/images/notice/"+map.get("fileName"));
			System.out.println(map.get("fileRealPath"));
			System.out.println(map.get("fileName"));
			System.out.println(CommonVO.ip + "/AA/resources/images/notice/"+map.get("fileName"));
		}
		service.insertData(vo);
		return "redirect:list.no";
	}
	//�������� �Է� ȭ�� ��
	@RequestMapping("/insert.no")
	public String insertForm() {
		return "notice/insertForm_no";
	}
	
	@Autowired private NoticePage page;
	//�������� ��� ��û
	@RequestMapping("/list.no")
	public String readNotice(Model model,
			@RequestParam(defaultValue="1") int curPage,
			@RequestParam(defaultValue="all") String search,
			@RequestParam(defaultValue="") String keyword
			, HttpSession session) {
		
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		
		session.removeAttribute("status_info");
		session.removeAttribute("myHomeExistense");
		model.addAttribute("category", "no");
		model.addAttribute("title", "��������");
		model.addAttribute("page",service.readNotice(page));
		return "notice/list_no";
	}
	
	//�������� ���� ���� ��û
	@RequestMapping("/detail.no")
	public String noticeDetail(@RequestParam int no, Model model) {
		service.count(no);
		model.addAttribute("vo", service.noticeDetail(no));
		return "notice/detail_no";
	}
	//�������� ���� ������ ��û
	@RequestMapping("/modifyPage.no")
	public String modifyPage(Model model, @RequestParam int no) {
		model.addAttribute("vo", service.noticeDetail(no));
		return "notice/modifyPage_no";
	}
	//�������� ���� ������Ʈ
	@RequestMapping("/update.no")
	public String update(NoticeVO vo, HttpSession session, @RequestParam MultipartFile attach_file) {
		System.out.println("uploadType = "+vo.getUploadType());
		if(attach_file.getSize()>0) {
			HashMap<String, String> map = common.upload("notice", attach_file, session);
			vo.setFileRealPath(map.get("fileRealPath"));
			vo.setFileName(map.get("fileName"));
			vo.setFilePath(CommonVO.ip + "/AA/resources/images/notice/"+map.get("fileName"));
			System.out.println(map.get("fileRealPath"));
			System.out.println(map.get("fileName"));
			System.out.println(CommonVO.ip + "/AA/resources/images/notice/"+map.get("fileName"));
		}else if(vo.getUploadType() == null){
			vo.setUploadType("");
			System.out.println("�η� ����");
		}
		service.update(vo);
		return "redirect:detail.no?no="+vo.getNo();
	}
	
	
}
