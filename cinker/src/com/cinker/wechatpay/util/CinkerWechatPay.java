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
	//生成预订单
	public String getImageUrl(String body,String out_trade_no,String product_id,int total_fee) throws Exception{
        Map<String, Object> paramMap = new HashMap<String, Object>();
        CommonUtil  commonUtil=new CommonUtil();
        paramMap.put("appid",APPID);//appid：每个公众号都有一个appid
        paramMap.put("body",body);//商品描述 
        paramMap.put("mch_id",MCH_ID);//商户号：开通微信支付后分配 
        paramMap.put("nonce_str",commonUtil.getRandomStringByLength(32));// 随机字符串
        paramMap.put("notify_url","http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");//支付成功后，回调地址
        paramMap.put("out_trade_no",out_trade_no);//商户订单号
        paramMap.put("product_id",product_id);//商户根据自己业务传递的参数 当trade_type=NATIVE时必填
        paramMap.put("spbill_create_ip",commonUtil.getLocalIp());//本机的Ip
        paramMap.put("total_fee",total_fee);//金额必须为整数  单位为分
        paramMap.put("trade_type","NATIVE");//交易类型
        paramMap.put("sign",commonUtil.getSign(paramMap,API_KEY));//根据微信签名规则，生成签名。随机参数可以在商户后台管理系统中进行设置。
        String xmlData = commonUtil.map2xmlBody(paramMap,"xml");//把参数转换成XML数据格式
        String codeUrl = commonUtil.getCodeUrl(xmlData);   //获取二维码链接*/
        return codeUrl;
    }
	//查询微信订单接口
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
