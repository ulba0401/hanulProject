package status;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import common.CommonVO;

@Repository
public class StatusDAO {

	@Autowired
	private SqlSession sql;
	
	public void statusBoiler(String status, String id) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("status", status);
		map.put("id",id);
		sql.update("status-mapper.statusBoiler", map);
	}
	
	//â�� �ڵ� �¿��� ���
	public void statusWindowAuto(String id) {
		sql.update("status-mapper.statusWindowAuto", id);
	}
	
	//â�� �ڵ� �¿��� üũ
	public String autoWindowSetting(String id) {
		return sql.selectOne("status-mapper.autoWindowSetting", id);
	}
	
	//������ �����ͺ��̽� ������Ʈ
	public void doorOpen(String id) {
		sql.update("status-mapper.doorOpen",id);
	}
	
	//������ �����ͺ��̽� ������Ʈ
	public void doorClose(String id) {
		sql.update("status-mapper.doorClose",id);
	}
	
	//���� ����
	public void gas_off(String id) {
		sql.update("status-mapper.gas_off", id);
	}
	
	//���Ϸ� �¿���
	public void boiler_on_off(String id) {
		sql.update("status-mapper.boiler_on_off", id);
		String now = sql.selectOne("status-mapper.now_boiler", id);
		Boiler_on_off_status_Thread thread = new Boiler_on_off_status_Thread(now);
		thread.start();
	}
	
	//�µ� ������Ʈ
	public void updateTem(String id, int temper) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("temper", temper);
		sql.update("status-mapper.updateTem", map);
	}
	
	//â������
	public String windowOpen(String id) {
		sql.update("status-mapper.windowOpen",id);
		return null;
	}

	//â���ݱ�
	public String windowClose(String id) {
		sql.update("status-mapper.windowClose",id);
		return null;
	}
	
	//�Ƶ��̳� �� �о����
	public void getArduinoValue(String id) {
		Snowpiercer snowpiercer = new Snowpiercer(id);
		snowpiercer.start();
	}
	
	// ���� �¿��� ���
	public StatusVO light_on_off(String id) {
		StatusVO vo = sql.selectOne("status-mapper.select", id);
		sql.update("status-mapper.light_on_off", id);
		return vo;
	}

	// status ���� ��������
	public StatusVO select(String id) {
		return sql.selectOne("status-mapper.select", id);
	}

	// �ּ� �޾ƿ���
	public List<AddrVO> getAddrs(String id) {
		return sql.selectList("status-mapper.getAddrs", id);
	}
	
	//�� �޼ҵ� ����������===================
	public AddrVO getAddr(String id) {
		return sql.selectOne("status-mapper.getAddr", id);
	}
	//===============��� ����

	// ������ �޾ƿ���
	public GetUserVO start(String addr) {
		return sql.selectOne("status-mapper.info", addr);
	}

	// ���̵� ���� ������ �ޱ�
	public StatusVO sinfo(String id) {
		return sql.selectOne("status-mapper.sinfo", id);
	};

	// �� ���� ���� �޾ƿ���
	public StatusVO water_info_print(HashMap<String, String> map) {
		sql.update("status-mapper.water_ctrl", map);
		return null;
	}

	// ���� ���� ���� �޾ƿ���
	public StatusVO light_ctrl(HashMap<String, String> map) {
		sql.update("status-mapper.light_ctrl", map);
		return null;
	}

	// ���ȼ��� on/off ���� ����
	public StatusVO secure_ctrl(HashMap<String, String> map) {
		sql.update("status-mapper.secure_ctrl", map);
		return null;
	}

	// �̼�����
	public ArrayList<StatusDTO> dust_info() {
		BufferedReader br = null;
		String result = "";
		String pm10Value = "";
		String pm25Value = "";
		String cityName = "";
		JSONParser jsonParse = new JSONParser();
		JSONObject jsonObj = new JSONObject();
		JSONObject obj = new JSONObject();
		JSONArray listArray = new JSONArray();
		StatusDTO ddto = null;
		ArrayList<StatusDTO> list = new ArrayList<StatusDTO>();

		try {
			String urlstr = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst"
					+ "?serviceKey=EJmGtsKFK8swoD%2FB3ayPRQswYDu25icg76t6HqcxEF1fX%2B5A4nek3ruj2fJTqImB9Ok6z3TVUcK5AVFVZjdT0Q%3D%3D"
					+ "&numOfRows=6&pageNo=1" + "&sidoName=%EA%B4%91%EC%A3%BC" + "&searchCondition=DAILY" + "&ver=1.3"
					+ "&_returnType=json";
			URL url = new URL(urlstr);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "utf-8"));
			result = "";
			/*
			 * HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
			 * urlconnection.setRequestMethod("GET"); br = new BufferedReader(new
			 * InputStreamReader(urlconnection.getInputStream(), "UTF-8")); result = "";
			 */
			String line;
			while ((line = br.readLine()) != null) {
				result = result + line + "\n";
			}
			// System.out.println(result);

			jsonObj = (JSONObject) jsonParse.parse(result);
			listArray = (JSONArray) jsonObj.get("list");

			for (int i = 0; i < listArray.size(); i++) {
				obj = (JSONObject) listArray.get(i);
				cityName = (String) obj.get("cityName");
				pm10Value = (String) obj.get("pm10Value");
				pm25Value = (String) obj.get("pm25Value");

				ddto = new StatusDTO(cityName, pm10Value, pm25Value);
				list.add(ddto);
				// System.out.println(list);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return list;
	}

	public void waterVal(String id) {
		HttpURLConnection urlconnection = null;
		
		try {
			URL url = new URL(CommonVO.arduino_ip);
	        urlconnection = (HttpURLConnection) url.openConnection();
	        urlconnection.setRequestMethod("GET");
	        BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
	        String result = "";
	        String line;
	        while ((line = br.readLine()) != null) {
	           result = result + line + "\n";
	        }
	        System.out.println(result);
	        
	        JSONParser jsonParser = new JSONParser();
	        Object obj = jsonParser.parse(result);
	        JSONObject jsonObject = (JSONObject) obj;
	        String light = jsonObject.get("light").toString();
	        String door = jsonObject.get("door").toString();
	        String boiler = jsonObject.get("boiler").toString();
	        updateStatus(light, door, boiler, id);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateStatus(String light, String door, String boiler, String id) throws Exception {
		URL url = new URL(CommonVO.serverIP + "/AA/curStatusUpdate?light="+light+"&door="+door+"&boiler="+boiler+"&id="+id);
        HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
        urlconnection.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
	}
	
	public void curStatusUpdate(HashMap<String, String> map) {
		sql.update("status-mapper.curStatusUpdate", map);
	}
}
