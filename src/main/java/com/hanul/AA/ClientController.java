package com.hanul.AA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import client.ClientServiceImpl;
import client.ClientVO;
import status.AddrVO;

@Controller
@SessionAttributes({ "category" })
public class ClientController {
	@Autowired
	private ClientServiceImpl service;

	// ���̵� ã��
	@ResponseBody @RequestMapping("/findId")
	public String findId(@RequestParam String name, @RequestParam String email) {
		ClientVO vo = new ClientVO();
		vo.setName(name);
		vo.setEmail(email);
		return service.findId(vo);
	}

	// ��� ã��
	@ResponseBody @RequestMapping("/findPw")
	public String findPw(@RequestParam String name, @RequestParam String email, @RequestParam String id) {
		ClientVO vo = new ClientVO();
		vo.setName(name);
		vo.setEmail(email);
		vo.setId(id);
		return service.findPw(vo);
	}

	// ȸ�� ���̵� ã�� ȭ�� ��û
	@RequestMapping("/findId.cs")
	public String findIdPage() {
		return "client/findId";
	}

	// ȸ�� ��� ã�� ȭ�� ��û
	@RequestMapping("/findPw.cs")
	public String findPwPage() {
		return "client/findPw";
	}

	// ���ο� �ּ� �߰�
	@RequestMapping("/insertAddr.cs")
	public String insertAddr(AddrVO vo) {
		service.insertAddr(vo);
		return "status/status";
	}

	// ���ο� �ּ� �߰� ȭ�� ��û
	@RequestMapping("/newAddr")
	public String newAddr() {
		return "client/newAddr";
	}

	// ȸ������ȭ�� ��û
	@RequestMapping("/client.cs")
	public String Client(Model model) {
		model.addAttribute("category", "");
		return "client/join";
	}

	// �ű԰����� ����ó�� ��û
	@RequestMapping("/insert.cs")
	public String insert(ClientVO vo) {
		// ȭ�鿡�� �Է��� ������ ������ DB�� ������ ��,
		service.insert(vo);

		// ���ȭ������ ����
		return "redirect:home";
	}

	// �ű԰����ȭ�� ��û
	@RequestMapping("/new.cs")
	public String customer() {
		return "customer/new";
	}

	// ���̵� �ߺ� Ȯ�� ��û (�� ��ü�� ��û)
	@ResponseBody
	@RequestMapping("/id_check")
	public String id_check(@RequestParam String id) {
		// ȭ�鿡�� �Է��� ���̵� DB�� �ִ����� ��ȸ�� ��
		// ��밡�� ���θ� ��ȯ�Ѵ�.
		return String.valueOf(service.id_check(id));

	}

}
