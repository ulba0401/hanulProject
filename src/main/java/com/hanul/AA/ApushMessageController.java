package com.hanul.AA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import android.AndroidServiceImpl;
import android.TokenVO;
import common.CommonService;
import community.PushVO;
import status.StatusService;

@Controller
public class ApushMessageController {

	@Autowired
	AndroidServiceImpl service;
	@Autowired
	CommonService common_service;
	@Autowired
	StatusService status_service;

	// ������ �޼��� �˸�
	@RequestMapping("/openDoor.status")
	@ResponseBody
	public void openDoor(Model model, HttpServletRequest request, HttpSession session, String id,
			@RequestParam String doorCheck) throws Exception {
		if (doorCheck.equals("push")) {
			System.out.println("PushComment");
			String deviceToken = service.getToken(id);
			if (deviceToken != null) {
				// System.out.println(deviceToken);
				final String apiKey = "AAAAzXriQRU:APA91bHGaW_VIqlWYkd7e0znmEQnG9GGBEh-lzHHHzgWiW7ezqLo_ICZKSu2NxRQQRGeo3rt0c1dIvuLFKCXpw4zqJk-fr3Cb0jpCBS22lgH-6bU8x0iSuuAruaBh911h98PG0aCw4TQ";
				URL url = new URL("https://fcm.googleapis.com/fcm/send");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Authorization", "key=" + apiKey);

				conn.setDoOutput(true);

				String userId = (String) request.getSession().getAttribute("ssUserId");

				// �̷��� ������ ������ ALL�� �����س��� ��� ��������� �˸��� �����ش�.
				// String input = "{\"notification\" : {\"title\" : \"����� ���� �ֱ� \", \"body\" :
				// \"����� ���� �ֱ�\"}, \"to\":\"/topics/ALL\"}";

				// �̰ɷ� ������ Ư�� ��ū�� �������ִ� ���ÿ��� �˸��� �����ش� ���� ���߿� �Ѱ� ��� ��������
				String input = "{\"notification\" : {\"title\" : \" �˸� \", \"body\" : \" �������� ���Ƚ��ϴ�. \", \"id\" : \""
						+ id + "\" }, \"to\":\"" + deviceToken + "\"}";

				OutputStream os = conn.getOutputStream();

				// �������� ������ �ѱ� ������ ����� �Ʒ�ó�� UTF-8�� ���ڵ��ؼ� ��������
				os.write(input.getBytes("UTF-8"));
				os.flush();
				os.close();

				int responseCode = conn.getResponseCode();
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + input);
				System.out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				// print result
				System.out.println(response.toString());
			} else {
				System.out.println("�˸� �Ⱥ���");
			}
		}
		// ������ ���·� �����ͺ��̽� ������Ʈ
		status_service.doorOpen(id);

	}

	// ������ �޼��� �˸�
	@RequestMapping("/closeDoor.status")
	@ResponseBody
	public void closeDoor(Model model, HttpServletRequest request, HttpSession session, String id,
			@RequestParam String doorCheck) throws Exception {
		if (doorCheck.equals("push")) {
			System.out.println("PushComment");
			String deviceToken = service.getToken(id);
			if (deviceToken != null) {
				// System.out.println(deviceToken);
				final String apiKey = "AAAAzXriQRU:APA91bHGaW_VIqlWYkd7e0znmEQnG9GGBEh-lzHHHzgWiW7ezqLo_ICZKSu2NxRQQRGeo3rt0c1dIvuLFKCXpw4zqJk-fr3Cb0jpCBS22lgH-6bU8x0iSuuAruaBh911h98PG0aCw4TQ";
				URL url = new URL("https://fcm.googleapis.com/fcm/send");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Authorization", "key=" + apiKey);

				conn.setDoOutput(true);

				String userId = (String) request.getSession().getAttribute("ssUserId");

				// �̷��� ������ ������ ALL�� �����س��� ��� ��������� �˸��� �����ش�.
				// String input = "{\"notification\" : {\"title\" : \"����� ���� �ֱ� \", \"body\" :
				// \"����� ���� �ֱ�\"}, \"to\":\"/topics/ALL\"}";

				// �̰ɷ� ������ Ư�� ��ū�� �������ִ� ���ÿ��� �˸��� �����ش� ���� ���߿� �Ѱ� ��� ��������
				String input = "{\"notification\" : {\"title\" : \" �˸� \", \"body\" : \" �������� �������ϴ�. \", \"id\" : \""
						+ id + "\" }, \"to\":\"" + deviceToken + "\"}";

				OutputStream os = conn.getOutputStream();

				// �������� ������ �ѱ� ������ ����� �Ʒ�ó�� UTF-8�� ���ڵ��ؼ� ��������
				os.write(input.getBytes("UTF-8"));
				os.flush();
				os.close();

				int responseCode = conn.getResponseCode();
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + input);
				System.out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				// print result
				System.out.println(response.toString());
			} else {
				System.out.println("�˸� �Ⱥ���");
			}
		}
		// ������ ���·� �����ͺ��̽� ������Ʈ
		status_service.doorClose(id);

	}

	// ��ü�˸��� ���� �������� �̵�
	@RequestMapping("/allPush.push.android")
	public String messagePage() {
		return "notice/all_message_page";
	}

	// â������
	@RequestMapping("/window_close_message")
	@ResponseBody
	public void windowClose(Model model, HttpServletRequest request, HttpSession session, @RequestParam String id)
			throws Exception {
		System.out.println("PushComment");
		String deviceToken = service.getToken(id);
		if (deviceToken != null) {
			// System.out.println(deviceToken);
			final String apiKey = "AAAAzXriQRU:APA91bHGaW_VIqlWYkd7e0znmEQnG9GGBEh-lzHHHzgWiW7ezqLo_ICZKSu2NxRQQRGeo3rt0c1dIvuLFKCXpw4zqJk-fr3Cb0jpCBS22lgH-6bU8x0iSuuAruaBh911h98PG0aCw4TQ";
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + apiKey);

			conn.setDoOutput(true);

			String userId = (String) request.getSession().getAttribute("ssUserId");

			// �̷��� ������ ������ ALL�� �����س��� ��� ��������� �˸��� �����ش�.
			// String input = "{\"notification\" : {\"title\" : \"����� ���� �ֱ� \", \"body\" :
			// \"����� ���� �ֱ�\"}, \"to\":\"/topics/ALL\"}";

			// �̰ɷ� ������ Ư�� ��ū�� �������ִ� ���ÿ��� �˸��� �����ش� ���� ���߿� �Ѱ� ��� ��������
			String input = "{\"notification\" : {\"title\" : \" �˸� \", \"body\" : \" â���� �������ϴ�. \", \"id\" : \"" + id
					+ "\" }, \"to\":\"" + deviceToken + "\"}";

			OutputStream os = conn.getOutputStream();

			// �������� ������ �ѱ� ������ ����� �Ʒ�ó�� UTF-8�� ���ڵ��ؼ� ��������
			os.write(input.getBytes("UTF-8"));
			os.flush();
			os.close();

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + input);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("�˸� �Ⱥ���");
		}
		// return "main/android/jsonView";
	}

	// â������
	@RequestMapping("/window_open_message")
	@ResponseBody
	public void windowOpen(Model model, HttpServletRequest request, HttpSession session, @RequestParam String id)
			throws Exception {
		System.out.println("PushComment");
		String deviceToken = service.getToken(id);
		if (deviceToken != null) {
			// System.out.println(deviceToken);
			final String apiKey = "AAAAzXriQRU:APA91bHGaW_VIqlWYkd7e0znmEQnG9GGBEh-lzHHHzgWiW7ezqLo_ICZKSu2NxRQQRGeo3rt0c1dIvuLFKCXpw4zqJk-fr3Cb0jpCBS22lgH-6bU8x0iSuuAruaBh911h98PG0aCw4TQ";
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + apiKey);

			conn.setDoOutput(true);

			String userId = (String) request.getSession().getAttribute("ssUserId");

			// �̷��� ������ ������ ALL�� �����س��� ��� ��������� �˸��� �����ش�.
			// String input = "{\"notification\" : {\"title\" : \"����� ���� �ֱ� \", \"body\" :
			// \"����� ���� �ֱ�\"}, \"to\":\"/topics/ALL\"}";

			// �̰ɷ� ������ Ư�� ��ū�� �������ִ� ���ÿ��� �˸��� �����ش� ���� ���߿� �Ѱ� ��� ��������
			String input = "{\"notification\" : {\"title\" : \" �˸� \", \"body\" : \"â���� ���Ƚ��ϴ�. \", \"id\" : \"" + id
					+ "\" }, \"to\":\"" + deviceToken + "\"}";

			OutputStream os = conn.getOutputStream();

			// �������� ������ �ѱ� ������ ����� �Ʒ�ó�� UTF-8�� ���ڵ��ؼ� ��������
			os.write(input.getBytes("UTF-8"));
			os.flush();
			os.close();

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + input);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("�˸� �Ⱥ���");
		}
		// return "main/android/jsonView";
	}

	// ���� �˸�
	@RequestMapping("/PushComment")
	@ResponseBody
	public void sendOne(Model model, HttpServletRequest request, HttpSession session, @RequestParam String id)
			throws Exception {
		System.out.println("PushComment");
		String deviceToken = service.getToken(id);
		if (deviceToken != null) {
			// System.out.println(deviceToken);
			final String apiKey = "AAAAzXriQRU:APA91bHGaW_VIqlWYkd7e0znmEQnG9GGBEh-lzHHHzgWiW7ezqLo_ICZKSu2NxRQQRGeo3rt0c1dIvuLFKCXpw4zqJk-fr3Cb0jpCBS22lgH-6bU8x0iSuuAruaBh911h98PG0aCw4TQ";
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + apiKey);

			conn.setDoOutput(true);

			String userId = (String) request.getSession().getAttribute("ssUserId");

			// �̷��� ������ ������ ALL�� �����س��� ��� ��������� �˸��� �����ش�.
			// String input = "{\"notification\" : {\"title\" : \"����� ���� �ֱ� \", \"body\" :
			// \"����� ���� �ֱ�\"}, \"to\":\"/topics/ALL\"}";

			// �̰ɷ� ������ Ư�� ��ū�� �������ִ� ���ÿ��� �˸��� �����ش� ���� ���߿� �Ѱ� ��� ��������
			String input = "{\"notification\" : {\"title\" : \" �˸� \", \"body\" : \" ����� �ԷµǾ����ϴ�. \", \"id\" : \"" + id
					+ "\" }, \"to\":\"" + deviceToken + "\"}";

			OutputStream os = conn.getOutputStream();

			// �������� ������ �ѱ� ������ ����� �Ʒ�ó�� UTF-8�� ���ڵ��ؼ� ��������
			os.write(input.getBytes("UTF-8"));
			os.flush();
			os.close();

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + input);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("�˸� �Ⱥ���");
		}
		// return "main/android/jsonView";
	}

	// ����̽� ��ū�� �Է�
	@RequestMapping("/pushMessege")
	public String pushMessege(@RequestParam String token, @RequestParam String id) {
		System.out.println("pushMessege");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("token", token);
		map.put("id", id);
		service.token(map);

		return "main/android/nothing";
	}

	// ��ü Ǫ�� �˸�
	@RequestMapping("/sendALL")
	public String sendAll(Model model, HttpServletRequest request, HttpSession session, PushVO vo) throws Exception {

		List<TokenVO> list = service.list();
		String title = vo.getTitle();
		String body = vo.getBody();

		for (TokenVO tokenVO : list) {
			String deviceToken = tokenVO.getToken();

			final String apiKey = "AAAAzXriQRU:APA91bHGaW_VIqlWYkd7e0znmEQnG9GGBEh-lzHHHzgWiW7ezqLo_ICZKSu2NxRQQRGeo3rt0c1dIvuLFKCXpw4zqJk-fr3Cb0jpCBS22lgH-6bU8x0iSuuAruaBh911h98PG0aCw4TQ";
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + apiKey);

			conn.setDoOutput(true);

			String userId = (String) request.getSession().getAttribute("ssUserId");

			// �̰ɷ� ������ Ư�� ��ū�� �������ִ� ���ÿ��� �˸��� �����ش� ���� ���߿� �Ѱ� ��� ��������
			String input = "{\"notification\" : {\"title\" : \"" + title + "\", \"body\" : \"" + body
					+ "\", \"id\" : \"\"}, \"to\":\"" + deviceToken + "\"}";

			OutputStream os = conn.getOutputStream();

			// �������� ������ �ѱ� ������ ����� �Ʒ�ó�� UTF-8�� ���ڵ��ؼ� ��������
			os.write(input.getBytes("UTF-8"));
			os.flush();
			os.close();

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + input);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		}

		return "redirect:allPush.push.android";
	}

	// ����̽� ��ū �� ����
	@RequestMapping("/tokenLogout")
	public String tokenLogout(@RequestParam String id) {
		System.out.println("tokenLogout");
		service.tokenLogout(id);
		return "main/android/nothing";
	}

	// ����̽� ��ū is_token �� // 'N'�� �������� 'Y'�� ��������
	@RequestMapping("/tokenSetting")
	public String tokenSetting(@RequestParam String id) {
		System.out.println("tokenSetting");
		service.tokenSetting(id);
		return "main/android/nothing";
	}

	// ��ü �˸� �� ���� ���
	@RequestMapping("/tokenSettingAll")
	public String tokenSettingAll(@RequestParam String id) {
		System.out.println("tokenSettingAll");
		service.tokenSettingAll(id);
		return "main/android/nothing";
	}

	// �ȵ���̵� ���� �˸���� �¿��� üũ
	@RequestMapping("/CheckCommentPushState")
	@ResponseBody
	public String CheckCommentPushState(@RequestParam String id) {
		System.out.println("checkCommentPushState");
		if (service.checkCommentPushState(id).equals("Y")) {
			System.out.println("Y");
			return "{\"result\":\"Y\"}";
		} else {
			System.out.println("N");
			return "{\"result\":\"N\"}";
		}
	}

	// �ȵ���̵� ��ü �˸� ��� �¿��� üũ
	@RequestMapping("/CheckALLPushState")
	@ResponseBody
	public String checkALLPushState(@RequestParam String id) {
		System.out.println("checkALLPushState");
		if (service.checkALLPushState(id).equals("Y")) {
			System.out.println("Y");
			return "{\"result\":\"Y\"}";
		} else {
			System.out.println("N");
			return "{\"result\":\"N\"}";
		}
	}
}
