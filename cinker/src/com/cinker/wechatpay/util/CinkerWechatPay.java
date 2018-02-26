package com.cinker.wechatpay.util;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import com.cinker.constant.Constant;
public class CinkerWechatPay {
	@Value("${APPID}")
	private String APPID;
	@Value("${MCH_ID}")
	private String MCH_ID;
	@Value("${API_KEY}")
	private String API_KEY;
	//����Ԥ����
	public String getImageUrl(String body,String out_trade_no,String product_id,int total_fee) throws Exception{
        Map<String, Object> paramMap = new HashMap<String, Object>();
        CommonUtil  commonUtil=new CommonUtil();
        paramMap.put("appid",APPID);//appid��ÿ�����ںŶ���һ��appid
        paramMap.put("body",body);//��Ʒ���� 
        paramMap.put("mch_id",MCH_ID);//�̻��ţ���ͨ΢��֧������� 
        paramMap.put("nonce_str",commonUtil.getRandomStringByLength(32));// ����ַ���
        paramMap.put("notify_url","http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");//֧���ɹ��󣬻ص���ַ
        paramMap.put("out_trade_no",out_trade_no);//�̻�������
        paramMap.put("product_id",product_id);//�̻������Լ�ҵ�񴫵ݵĲ��� ��trade_type=NATIVEʱ����
        paramMap.put("spbill_create_ip",commonUtil.getLocalIp());//������Ip
        paramMap.put("total_fee",total_fee);//������Ϊ����  ��λΪ��
        paramMap.put("trade_type","NATIVE");//��������
        paramMap.put("sign",commonUtil.getSign(paramMap,API_KEY));//����΢��ǩ����������ǩ������������������̻���̨����ϵͳ�н������á�
        String xmlData = commonUtil.map2xmlBody(paramMap,"xml");//�Ѳ���ת����XML���ݸ�ʽ
        String codeUrl = commonUtil.getCodeUrl(xmlData);   //��ȡ��ά������*/
        return codeUrl;
    }
	//��ѯ΢�Ŷ����ӿ�
	public String queryOrder(String out_trade_no) throws Exception{
        Map<String, Object> paramMap = new HashMap<String, Object>();
        CommonUtil  commonUtil=new CommonUtil();
        paramMap.put("appid",APPID);
        paramMap.put("mch_id",MCH_ID);
        paramMap.put("nonce_str",commonUtil.getRandomStringByLength(32));
        paramMap.put("out_trade_no",out_trade_no);
        paramMap.put("sign",commonUtil.getQuerySign(paramMap,API_KEY));
        String xmlData = commonUtil.map2xmlBody(paramMap,"xml");
        String result = commonUtil.sendPost(Constant.WX_QUERY_ORDER_URL,xmlData);
        return result;
    }
	public static void main(String[] args) {
		CinkerWechatPay cwp=new CinkerWechatPay();
		try {
			System.out.println(cwp.queryOrder("1415659995"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
