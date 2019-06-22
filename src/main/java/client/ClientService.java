package client;

import java.util.HashMap;
import java.util.List;

import status.AddrVO;

public interface ClientService {
	
	//CRUD: ���� create, ��ȸ read, ���� update, ���� delete
		//����
		void ainsert(ClientVO vo);
		void insert(ClientVO vo);
		//��ȸ
		public List<ClientVO> list();
		
		ClientVO detail(String id);
		//����
		void update(ClientVO vo);
		//����
		void delete(String id);
		//���̵�üũ
		boolean id_check(String id);
		
		ClientVO loginRequest(HashMap<String, String> map);
		
		boolean ID_PW_check(HashMap<String, String> map);
		//�ȵ���̵� ���̵� üũ
		boolean ID_check(HashMap<String, String> map);
		
		//�ȵ���̵� ���̵� ã��
		boolean Find_ID(HashMap<String, String> map);
		
		boolean Find_PW(HashMap<String, String> map);
		
		void androidUpdate(ClientVO vo);
		
		public void insertAddr(AddrVO vo);
		
		ClientVO snsloginRequest(HashMap<String, String> map);
		
		String findId(ClientVO vo);
		
		String findPw(ClientVO vo);
		
}
