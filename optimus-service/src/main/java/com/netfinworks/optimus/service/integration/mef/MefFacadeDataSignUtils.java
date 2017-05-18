package com.netfinworks.optimus.service.integration.mef;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.optimus.service.integration.ErrorKind;
import com.netfinworks.optimus.service.integration.ServiceException;
import com.netfinworks.util.RSA;
import com.netfinworks.util.SignUtils;
import com.vf.fsn.lang.tools.EmptyChecker;
import com.vf.fsn.lang.tools.JsonTools;
import com.vf.mef.jaxws.WsFaceRequest;
import com.vf.mef.jaxws.WsFaceResponse;
import com.vf.mef.jaxws.WsFaceService;
/**
 * <p>mef调用签名</p>
 *
 * @author 
 * @version 
 */
public class MefFacadeDataSignUtils {
    
    private static final Logger              logger = LoggerFactory.getLogger(MefFacadeDataSignUtils.class);
    
    //是否签名
    public  boolean issign = true; 
    
    //购买产品编号
    private String productCode;
    //购买产品名
    private String productName;
    //渠道编号
    private String chanNo;
    //机构编号
    private String instNo;
    //我的密钥
    private String thisPrivateKey;
    //我的密钥
    private PrivateKey privateKey;
    //FSN公钥
    private String thatPublicKey;
    //FSN公钥
    private PublicKey publicKey;
    
    //固定格式
    public static String CHARSET = "UTF-8";
    //签名方式
    public static String SIGN_TYPE = "RSA";
    
    //初始化
    public void init(){
        
        try {
            publicKey = RSA.getPublicKey(thatPublicKey);
        }catch (Throwable e) {
            
            logger.error("RSA公钥不合法",e);
            throw new ServiceException(ErrorKind.FSN_SIGN_ERROR, "RSA配置错误",e);
        }
        
        try {
            privateKey = RSA.getPrivateKey(thisPrivateKey);
            
        }catch (Throwable e) {
            
            logger.error("RSA密钥不合法",e);
            throw new ServiceException(ErrorKind.FSN_SIGN_ERROR, "RSA配置错误",e);
        }
    }
    
    /**
     * 调用外部ws服务，并做签名、验签功能
     * @param wsFaceService
     * @param request
     * @param resClass
     * @return
     */
    public <Res,Req> Res send(WsFaceService wsFaceService,Req request,Class<Res> resClass){
        
        WsFaceRequest wsRequest = convertWsFaceRequest(request);
        try {
            WsFaceResponse wsResponse = wsFaceService.execute(wsRequest);
            return convertWsFaceResponse(wsResponse,resClass);
        } catch (Throwable e) {
            logger.error("FSN服务调用失败",e);
            throw new ServiceException(ErrorKind.FSN_EXE_ERROR, "FSN服务调用失败",e);
        }
        
    }
    
    /**
     * 转换请求参数
     * @param reqObj
     * @return
     */
    public <T> WsFaceRequest convertWsFaceRequest(T reqObj){

        WsFaceRequest wsRR = new WsFaceRequest();
        
        String jsonData = JsonTools.toJson(reqObj);
        
        logger.info("fsn_request_data:{}",jsonData);
        
        if (!issign) {
            //wsRR.setRrPlainMesg(jsonData);
            wsRR.setRrSignMesg(jsonData);
            return wsRR;
        }
        
        String[] signMsg;
        try {
            //对请求报文做签名数据，经过64位编码后的JSON格式业务参数串。
            signMsg = SignUtils.encrypt(jsonData, SIGN_TYPE, CHARSET, getPrivateKey(),getPublicKey());
            wsRR.setRrChanNo(chanNo);
            //签名
            wsRR.setRrSign(signMsg[0]);
            //密文
            wsRR.setRrSignMesg(signMsg[1]);
            wsRR.setRrSignType(SIGN_TYPE);
            wsRR.setRrCharset(CHARSET);
        } catch (Exception e) {
            logger.error("FSN签名异常",e);
            throw new ServiceException(ErrorKind.FSN_SIGN_ERROR, "FSN签名异常",e);
        }

        return wsRR;
    }
    
    /**
     * 转回返回信息
     * @param wsRS
     * @param destClazz
     * @return
     */
    public <T> T convertWsFaceResponse(WsFaceResponse wsRS,Class<T> destClazz){
        if (!issign){
            return JsonTools.parse2Bean(wsRS.getRsPlainMesg(), destClazz);
        }
        
        //如果没有签名数据则无需验签，直接返回
        if (EmptyChecker.isEmpty(wsRS.getRsSignMesg())){
            return JsonTools.parse2Bean(wsRS.getRsPlainMesg(), destClazz);
        }

        String rs;
        try {
        	//对服务端返回报文做验签是否非法
            rs = SignUtils.decrypt(wsRS.getRsSign(), wsRS.getRsSignMesg(), wsRS.getRsSignType(), CHARSET,getPrivateKey(), getPublicKey());
            return JsonTools.parse2Bean(rs, destClazz);
        } catch (Exception e) {
            logger.error("FSN解签异常",e);
            throw new ServiceException(ErrorKind.FSN_SIGN_ERROR, "FSN签名异常",e);
        }
    }


    public boolean isIssign() {
        return issign;
    }
    public void setIssign(boolean issign) {
        this.issign = issign;
    }
    public String getChanNo() {
        return chanNo;
    }

    public void setChanNo(String chanNo) {
        this.chanNo = chanNo;
    }

    public String getThisPrivateKey() {
        return thisPrivateKey;
    }

    public void setThisPrivateKey(String thisPrivateKey) {
        this.thisPrivateKey = thisPrivateKey;
    }

    public String getThatPublicKey() {
        return thatPublicKey;
    }

    public void setThatPublicKey(String thatPublicKey) {
        this.thatPublicKey = thatPublicKey;
    }


    public PrivateKey getPrivateKey() {
        return privateKey;
    }


    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }


    public PublicKey getPublicKey() {
        
        return publicKey;
    }


    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    
}
