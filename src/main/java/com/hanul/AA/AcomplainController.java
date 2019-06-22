package com.hanul.AA;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import qa.QaVO;
import qa.QaserviceImpl;

@Controller
public class AcomplainController {
	@Autowired
	private QaserviceImpl qService;
	//���ǻ��� ���� ��δ� ������ ������ upload
	
	
	//�ȵ���̵� ���ǻ��� ������
	@RequestMapping(value = "/cpdetail", method = { RequestMethod.GET, RequestMethod.POST })
	public String detail(@RequestParam int no, Model model) {
		System.out.println("cmdetail");

		model.addAttribute("vo", qService.detail(no));
		return "main/android/complainDetail";
	}
	//�ȵ���̵� ���ǻ��� ����Ʈ
	@RequestMapping(value = "/cpselect", method = { RequestMethod.GET, RequestMethod.POST })
	public String questionSelect(Model model) {
		System.out.println("cpselect"); // ���࿩��

		model.addAttribute("list", qService.readQa());
		return "main/android/questionSelectList";
	}
	//�ȵ���̵� ���ǻ��� �� �Է�
	@RequestMapping(value = "/cpinsert", method = { RequestMethod.GET, RequestMethod.POST })
	public String Qinsert(HttpServletRequest req, Model model) {
		System.out.println("cpinsert"); // ���࿩��

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.getMessage();
		}
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String writerID = req.getParameter("writer");
		String uploadtype = (String) req.getParameter("uploadType");
		String dbImgPath = (String) req.getParameter("dbImgPath");
		String fileName = (String) req.getParameter("fileName");
		System.out.println(dbImgPath);

		MultipartRequest multi = (MultipartRequest) req;
		MultipartFile file = null;

		if (uploadtype != null && uploadtype.equals("image")) {
			file = multi.getFile("image");

			if (file != null) {
				// fileName = file.getOriginalFilename();
				System.out.println(fileName);

				// ���丮 �������� ������ ����
				makeDir(req);

				if (file.getSize() > 0) {
					String realImgPath = req.getSession().getServletContext().getRealPath("/resources/images/upload/");

					System.out.println(fileName + " : " + realImgPath);
					System.out.println("fileSize : " + file.getSize());

					// �̹������� ����
					try {
						file.transferTo(new File(realImgPath, fileName));
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					fileName = "FileFail.jpg";
					String realImgPath = req.getSession().getServletContext()
							.getRealPath("/resources/images/upload/" + fileName);
					System.out.println(fileName + " : " + realImgPath);
				}
			}
		}

		QaVO vo = new QaVO();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setWriterID(writerID);
		vo.setFilePath(dbImgPath);
		vo.setFileName(fileName);
		vo.setFileRealPath("\\images\\upload\\" + fileName);

		qService.insert(vo);

		return "main/android/nothing";
	}
	//�ȵ���̵� ���ǻ��� �� ���� ������Ʈ
	@RequestMapping(value = "/cpupdate", method = { RequestMethod.GET, RequestMethod.POST })
	public String qUpdate(HttpServletRequest req) {
		System.out.println("cpupdate"); // ���࿩��

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.getMessage();
		}
		int no = Integer.parseInt(req.getParameter("no"));
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String uploadtype = (String) req.getParameter("uploadType");
		String dbImgPath = (String) req.getParameter("dbImgPath");
		String fileName = (String) req.getParameter("fileName");
		System.out.println(dbImgPath);

		MultipartRequest multi = (MultipartRequest) req;
		MultipartFile file = null;

		if (uploadtype != null && uploadtype.equals("image")) {
			file = multi.getFile("image");

			if (file != null) {
				// fileName = file.getOriginalFilename();
				System.out.println(fileName);

				// ���丮 �������� ������ ����
				makeDir(req);

				if (file.getSize() > 0) {
					String realImgPath = req.getSession().getServletContext().getRealPath("/resources/images/upload/");

					System.out.println(fileName + " : " + realImgPath);
					System.out.println("fileSize : " + file.getSize());

					// �̹������� ����
					try {
						file.transferTo(new File(realImgPath, fileName));
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					fileName = "FileFail.jpg";
					String realImgPath = req.getSession().getServletContext()
							.getRealPath("\\images\\upload\\" + fileName);
					System.out.println(fileName + " : " + realImgPath);
				}
			}
		}

		if (uploadtype != null && uploadtype.equals("delete")) {
			uploadtype = "image";
			fileName = "";
			dbImgPath = "";
		}

		
		QaVO vo = new QaVO();
		vo.setNo(no);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setFileName(fileName);
		vo.setFilePath(dbImgPath);
		vo.setUploadType(uploadtype);
		if(uploadtype != null && uploadtype.equals("image")) {
			vo.setFileRealPath("\\images\\upload\\" + fileName);
		}

		qService.update(vo);

		return "main/android/nothing";
	}
	//�ȵ���̵� ���ǻ��� �� ����
	@RequestMapping(value = "/cpdelete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(HttpServletRequest req) {
		System.out.println("cpdelete"); // ���࿩��

		int no = Integer.parseInt(req.getParameter("no"));

		qService.delete(no);

		return "main/android/nothing";
	}
	//�ȵ���̵� ���ǻ��� ���� ��� �����
	public void makeDir(HttpServletRequest req) {
		File f = new File(req.getSession().getServletContext().getRealPath("/resources"));
		if (!f.isDirectory()) {
			f.mkdir();
		}

		File f1 = new File(req.getSession().getServletContext().getRealPath("/resources/images"));
		if (!f1.isDirectory()) {
			f1.mkdir();
		}

		File f2 = new File(req.getSession().getServletContext().getRealPath("/resources/images/upload"));
		if (!f2.isDirectory()) {
			f2.mkdir();
		}
	}

}
