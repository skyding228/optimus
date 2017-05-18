//package com.netfinworks.optimus.test;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//
//import com.netfinworks.common.domain.OperationEnvironment;
//import com.netfinworks.common.util.money.Money;
//import com.netfinworks.invest.facade.InvestFacade;
//import com.netfinworks.invest.request.BidRequest;
//
//public class InvestServiceTest extends BaseTestCase{
//	@Resource
//    private InvestFacade investFacade;
//	
//	@Test
//    public void test() throws Throwable {
//		System.err.println("-----------------------------");
//		BidRequest request = new BidRequest();
//		request.setAmount( new Money("10")); 
//		request.setExtension(""); 
//		request.setMemberId("1"); 
//		request.setRemark("Remark"); 
//		request.setSubjectNo("20160301164923S60001"); 
//		request.setSubmitType("1"); 
//		
//		investFacade.apply(request, new OperationEnvironment()) ; 
//		System.err.println("-----------------------------");
//		
////		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
////		
////		FundApplyRequest request = new FundApplyRequest();
////		request.setApplyNo("applyNo11111111");
////		request.setSubjectName("理财宝08");
////		request.setDescription("理财宝08");
////		
////		request.setApplyAmount(new Money("10000"));
////		request.setApplyPurpose("applyPurpose");
////		request.setApplyUnit( new Money("1"));
////		request.setBidMinAmount(new Money("10"));
////		
////		request.setRewardRate(new BigDecimal("0.15"));
////		request.setRepayType("1");
////		
////		request.setBidBeginDate(format.parse("2016-02-26 00:00:00"));
////		request.setValidDate(format.parse("2016-03-10 00:00:00")); 
////		
////		
////		request.setCreditAssignmentConfEntry(new ArrayList<Entry>());
////		
////		request.setRemark("");
////		request.setExtension("");
////		
////		
////		request.setProductCode("productCode");
////		
////		request.setInstNo("instNo");
////		request.setChannelCode("xinyifu");		
////		request.setProductInterestDate(format.parse("2016-03-10 00:00:00"));
////		request.setDueDate(format.parse("2016-10-28 00:00:00"));		
////		request.setProductName("productName");		
////		request.setMemberId("innerMember");
////		
////		System.err.println(investClient.fundApply(request));
//    }
//}
