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
import qa.QaPage;
import qa.QaVO;
import qa.QaserviceImpl;
 
@Controller @SessionAttributes({"category", "title"})
public class QaController {
	@Autowired private QaserviceImpl service;
	@Autowired private CommonServiceImpl common;
	
	//���ǻ��� �ٿ�ε�
	@RequestMapping("/cmDownload.qu") @ResponseBody
	public File download(@RequestParam int no, HttpSession session, HttpServletResponse response) {
		QaVO vo = service.detail(no);
		
		return common.download(vo.getFileRealPath(), vo.getFileName(), session, response);
	}
	//���ǻ��� �� ����
	@RequestMapping("/delete.qu")
	public String delete(@RequestParam int no) {
		service.delete(no);
		return "redirect:list.qu";
	}
	
	//���ǻ��� �۾��� �����ͺ��̽� ����
	@RequestMapping("/insertData.qu")
	public String insertData(QaVO vo, HttpSession session, @RequestParam MultipartFile attach_file) {
		
		if(attach_file.getSize()>0) {
			HashMap<String, String> map = common.upload("upload", attach_file, session);
			vo.setFileRealPath(map.get("fileRealPath"));
			vo.setFileName(map.get("fileName"));
			vo.setFilePath(CommonVO.ip + "/AA/resources/images/upload/"+map.get("fileName"));
			System.out.println(map.get("fileRealPath"));
			System.out.println(map.get("fileName"));
			System.out.println(CommonVO.ip + "/AA/resources/images/upload/"+map.get("fileName"));
		}
		service.insert(vo);
		return "redirect:list.qu";
	}
	//���ǻ��� �� ���� ������Ʈ
	@RequestMapping("/update.qu")
	public String update(QaVO vo, HttpSession session, @RequestParam MultipartFile attach_file){
		System.out.println("uploadType = "+vo.getUploadType());
		if(attach_file.getSize()>0) {
			HashMap<String, String> map = common.upload("upload", attach_file, session);
			vo.setFileRealPath(map.get("fileRealPath"));
			vo.setFileName(map.get("fileName"));
			vo.setFilePath(CommonVO.ip + "/AA/resources/images/upload/"+map.get("fileName"));
			System.out.println(map.get("fileRealPath"));
			System.out.println(map.get("fileName"));
			System.out.println(CommonVO.ip + "/AA/resources/images/upload/"+map.get("fileName"));

		}else if(vo.getUploadType() == null){
			vo.setUploadType("");
			System.out.println("�η� ����");
		}
		service.updateFile(vo);
		return "redirect:detail.qu?no="+vo.getNo();
	}
	//���ǻ��� ���������� ��û
	@RequestMapping("/modifyPage.qu")
	public String modifyPage(@RequestParam int no, Model model) {
		model.addAttribute("vo", service.detail(no));
		return  "question/modifyPage_qu";
	}
	
	//���ǻ��� ��� ��û
	@Autowired private QaPage page;
	@RequestMapping("/list.qu")
	public String readQa(Model model, 
		@RequestParam(defaultValue="1") int curPage,
		@RequestParam(defaultValue="all")String search, 
		@RequestParam(defaultValue="")String keyword
		, HttpSession session) {
	
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		session.removeAttribute("status_info");
		session.removeAttribute("myHomeExistense");
		model.addAttribute("category", "qu");
		model.addAttribute("title", "���ǻ���");
		model.addAttribute("page", service.readQa(page));
		return "question/list_qu";
	}
		
	//���ǻ��� ���γ��� ��û
	@RequestMapping("/detail.qu")
	public String questionDetail(@RequestParam int no, Model model) {
		QaVO tmp = service.detail(no);
		model.addAttribute("vo", service.detail(no));
		return "question/detail_qu";
	}
	
	//���ǻ��� �۾��� ȭ�� ��û
	@RequestMapping("/insert.qu")
	public String insertForm() {
		return "question/insertForm_qu";
	}
	
}
