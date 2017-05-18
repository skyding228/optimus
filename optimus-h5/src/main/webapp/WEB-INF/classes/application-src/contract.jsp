<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String root = request.getSession().getServletContext()
			.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<!--标准mui.css-->
<link rel="stylesheet" href="<%=root%>/static/css/mui.min.css">
<link rel="stylesheet" href="<%=root%>/static/css/app.css">
<title>合同</title>

<style id="wiz_custom_css">
html, body {
	font-size: 8px;
}

body {
	font-family: Helvetica, 'Hiragino Sans GB', '微软雅黑', 'Microsoft YaHei UI',
		SimSun, SimHei, arial, sans-serif;
	line-height: 1.6;
	margin: 0;
	padding: 20px 36px;
	padding: 1.33rem 2.4rem;
}

span.text {
	padding: 0 10px;
	border-bottom:1px solid #000;
}

h1, h2, h3, h4, h5, h6 {
	margin: 20px 0 10px;
	margin: 1.33rem 0 0.667rem;
	padding: 0;
	font-weight: bold;
}

h1 {
	font-size: 21px;
	font-size: 1.4rem;
}

h2 {
	font-size: 20px;
	font-size: 1.33rem;
}

h3 {
	font-size: 18px;
	font-size: 1.2rem;
}

h4 {
	font-size: 17px;
	font-size: 1.13rem;
}

h5 {
	font-size: 15px;
	font-size: 1rem;
}

h6 {
	font-size: 15px;
	font-size: 1rem;
	color: #777777;
	margin: 1rem 0;
}

div, p, ul, ol, dl, li {
	margin: 0;
}

blockquote, table, pre, code {
	margin: 8px 0;
}

ul, ol {
	padding-left: 32px;
	padding-left: 2.13rem;
}

blockquote {
	padding: 0 12px;
	padding: 0 0.8rem;
}

blockquote>:first-child {
	margin-top: 0;
}

blockquote>:last-child {
	margin-bottom: 0;
}

img {
	border: 0;
	max-width: 100%;
	height: auto !important;
	margin: 2px 0;
}

table {
	border-collapse: collapse;
	border: 1px solid #bbbbbb;
}

td {
	padding: 4px 8px;
	border-collapse: collapse;
	border: 1px solid #bbbbbb;
}

@media screen and (max-width: 660px) {
	body {
		padding: 20px 18px;
		padding: 1.33rem 1.2rem;
	}
}

@media only screen and (-webkit-max-device-width: 1024px) , only screen and
		(-o-max-device-width: 1024px) , only screen and (max-device-width:
		1024px) , only screen and (-webkit-min-device-pixel-ratio: 3) , only
		screen and (-o-min-device-pixel-ratio: 3) , only screen and
	(min-device-pixel-ratio: 3) {
	html, body {
		font-size: 8px;
	}
	body {
		line-height: 1.7;
		padding: 0.75rem 0.9375rem;
		color: #353c47;
	}
	h1 {
		font-size: 2.125rem;
	}
	h2 {
		font-size: 1.875rem;
	}
	h3 {
		font-size: 1.625rem;
	}
	h4 {
		font-size: 1.375rem;
	}
	h5 {
		font-size: 1.125rem;
	}
	h6 {
		color: inherit;
	}
	ul, ol {
		padding-left: 2.5rem;
	}
	blockquote {
		padding: 0 0.9375rem;
	}
}
</style>
</head>

<body>
	<header class="mui-bar mui-bar-nav header">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">合同信息</h1>
	</header>
	<div class="mui-content">
		<div>
			<div>
				<b><font face="宋体">重要提示：在接受本协议前，请各方认真阅读本协议全文。如果您不同意本协议的任意内容，或者无法准确理解条款，请不要进行后续操作。</font></b>
			</div>
			<div>
				<b><font face="宋体">通过网络页面进行勾选或点击确认或以其他方式选择接受本协议，即表示同意接受本协议的全部约定内容以及与本协议有关的各项规则。</font></b>
			</div>
			<div>
				<font face="宋体">&nbsp;</font>
			</div>
			<div>
				<font face="宋体">借入人：<span class="text">${data.exts.borrower }</span></font>
			</div>
			<div>
				<font face="宋体">营业执照号：<span class="text">${data.exts.businessLicense }</span></font>
			</div>
			<div>
				<font face="宋体">法人姓名：<span class="text">${data.exts.legalPerson }</span></font>
			</div>
			<div>
				<font face="宋体">法人身份证：<span class="text">${data.exts.legalPersonCert }</span></font>
			</div>
			<div>
				<font face="宋体">&nbsp;</font>
			</div>
			<div>
				<font face="宋体">&nbsp;</font>
			</div>
			<div>
				<font face="宋体">借出人：<span class="text">${data.member.chanUserRealname }</span></font>
			</div>
			<div>
				<font face="宋体">身份证号码：<span class="text">${data.member.chanUserIdNumber }</span></font>
			</div>
			<div>
				<font face="宋体">借出人注册号：<span class="text">${data.member.chanUserId }</span></font>
			</div>
			<div>
				<font face="宋体">借款人手机号：<span class="text">${data.member.mobile }</span></font>
			</div>
			<div>
				<font face="宋体">&nbsp;</font>
			</div>
			<div>
				<font face="宋体">本协议为借入人与借出人依据平等自愿的原则，经各方协商一致，兹共同遵守以下约定：</font>
			</div>
			<div>
				<b><font face="宋体">一、定义</font></b>
			</div>
			<div>
				<font face="宋体">1.1借入人：指直接或间接在星驿付理财平台发布融资需求信息，并在满足条件时筹得资金的自然人或法人。</font>
			</div>
			<div>
				<font face="宋体">1.2借出人：指持有资金并向在平台发布融资需求的借入人提供借款的一个或多个自然人。</font>
			</div>
			<div>
				<font face="宋体">1.3理财账户：指由借出人在星驿付申请注册的，并指定为借款过程中资金划出或转入的账户。</font>
			</div>
			<div>
				<font face="宋体">1.4借款总额：是指借入方直接或间接在平台发布借款需求时所设定的所有借款金额。借款总额以平台页面展示及系统记录为准。</font>
			</div>
			<div>
				<font face="宋体">1.5筹集期：是指借入方直接或间接在平台发布借款需求时所设定的筹资期限。该期限以平台页面展示及系统记录为准。</font>
			</div>
			<div>
				<font face="宋体">1.6手续费：指借入人使用平台服务而向平台支付的费用。</font>
			</div>
			<div>
				<b><font face="宋体">二、借款基本信息</font></b>
			</div>
			<div>
				<font face="宋体">借款金额：<span class="text">${data.amountV }</span>
					元人民币
				</font>
			</div>
			<div>
				<font face="宋体">借款用途：补充流动资金</font>
			</div>
			<div>
				<font face="宋体">借款期限：<span class="text">${data.startDateV}</span>
					起至<span class="text">${data.endDateV }</span> 止，共计 <span
					class="text">${data.loanDays }</span> 日；
				</font>
			</div>
			<div>
				<font face="宋体">起息期限：<span class="text">${data.startDateV }</span>
					起至<span class="text">${data.endDateV }</span> 止
				</font>
			</div>
			<div>
				<font face="宋体">借款利率：年利率<span class="text">${data.rewardRateV }%</span>
					（根据实际借款天数按日计息）
				</font>
			</div>
			<div>
				<font face="宋体">还款日：<span class="text">${data.endDateV }</span>
					。资金实际到账时间一般为到期还款日或每月还款日后的1-3个工作日。
				</font>
			</div>
			<div>
				<font face="宋体">手续费：借款金额的<span class="text">${data.exts.fee }‰</span>
					，若服务费金额不足一分钱的将按一分钱收取。
				</font>
			</div>
			<div>
				<b><font face="宋体">三、借入人权利义务</font></b>
			</div>
			<div>
				<font face="宋体">3.1借入人承诺签署本协议并履行本协议项下的义务是真实意思表示。</font>
			</div>
			<div>
				<font face="宋体">3.2借入人承诺借款所得资金用于合法用途，不得进行非法活动。</font>
			</div>
			<div>
				<font face="宋体">3.3借入人承诺向平台提供的所有信息真实、有效、合法。如借入人信息包括但不限于资信状况变动、还款能力变化等对本协议签署及履行出现不利影响时，应及时通知平台及借出人。</font>
			</div>
			<div>
				<font face="宋体">3.4借入人同意并委托星驿付根据本协议约定进行借款资金的划转，并保证理财账户正常以完成资金划入、划出。如因理财账户不正常导致包括但不限于借款资金无法及时入账、还款资金无法划转等情况，所产生的损失由借入人自行承担。</font>
			</div>
			<div>
				<font face="宋体">3.5借入人理解并同意，在筹集期内借款金额未满额，视为借款不成功，已借资金委托星驿付原路退回借出人。在筹集期内借款金额满额且本协议生效的，视为借款成功，借款资金划转至借入人理财账户。</font>
			</div>
			<div>
				<font face="宋体">3.6借入人使用平台服务，应支付相应手续费以及第三方支付服务费。平台有权视情况对手续费率及第三方支付服务费进行调整并以一定形式公告及通知。</font>
			</div>
			<div>
				<font face="宋体">3.7借入人应按照本协议约定到期足额一次性向借出人偿还本金和支付利息。借入人按约定在到期还款日将还款总额划转至借出人的理财账户，视为还款完成。</font>
			</div>
			<div>
				<font face="宋体">3.8借入人不得提前还款，但本协议另有约定的除外。</font>
			</div>
			<div>
				<b><font face="宋体">四、借出人权利义务</font></b>
			</div>
			<div>
				<font face="宋体">4.1借出人承诺签署本协议并履行本协议项下的义务是真实意思表示。</font>
			</div>
			<div>
				<font face="宋体">4.2借出人承诺借出资金来源合法。</font>
			</div>
			<div>
				<font face="宋体">4.3借出人承诺向平台提供的所有信息真实、有效、合法。如借出人信息包括但不限于联系方式变动、账户变动等影响各项服务提供时，应及时通知平台及借入人，否则造成的损失由借出人自行承担。</font>
			</div>
			<div>
				<font face="宋体">4.4借出人认购借款产品，即向星驿付发送支付指令进行相应资金冻结。在本协议生效后，平台将本协议对应的借款金额从借出人的理财账户划转至借入人指定的银行账户。本协议未生效的，平台解冻借出人的理财账户中对应的冻结状态的认购资金。</font>
			</div>
			<div>
				<font face="宋体">4.5借出人应保证理财账户中有足额资金。因资金不足导致认购失败的，由借出人自行承担。</font>
			</div>
			<div>
				<font face="宋体">4.6借出人理解并同意，借款金额从其理财账户划转之日起至起息日（不含）的这段期间内不产生任何收益（包括但不限于利息）。</font>
			</div>
			<div>
				<font face="宋体">4.7借款到期后，借出人同意并授权通付根据本协议约定将借款本金及利息划转至借出人理财账户。</font>
			</div>
			<div>
				<font face="宋体">4.8借出人应保证理财账户状态正常，确保资金划入、划出交易的完成。如账户不正常导致的所有损失（如借出资金无法划转、还款资金无法及时入账等）应由其自行承担。</font>
			</div>
			<div>
				<font face="宋体">4.9借出人理解并同意，最终将本协议项下的借款本息或受偿款项划转至借出人的理财账户需要一定的时间，前述时间一般为到期还款日后的1-3个工作日，前述期间内不产生任何投资收益（包括但不限于利息）。</font>
			</div>
			<div>
				<b><font face="宋体">五、还款保障条款</font></b>
			</div>
			<div>
				<font face="宋体">5.1如借入人无法到期还款，借入人同意并授权平台以<span class="text">${data.exts.name }</span>所得资金直接用于偿还本协议项下的应付本金和利息，并授权星驿付对该项资金进行划转。</font>
			</div>
			<div>
				<font face="宋体">（1）借入人于本协议生效之日前持有的基金份额等其他任何资产或权益产品（<span class="text">${data.exts.name }&nbsp;/&nbsp;${data.exts.protocolNo }</span>）</font>
			</div>
			<div>
				<font face="宋体">（3）由<span class="text">/</span> 提供保险保障；</font>
			</div>
			<div>
				<font face="宋体">（4）由第三方担保人<span class="text">/</span>提供保证担保（担保函编号：<span class="text">/</span> ）；</font>
			</div>
			<div>
				<font face="宋体">（5）由<span class="text">/</span>
					开立的以借出人为受益人的借款保函（保函编号：<span class="text">/</span> ）；</font>
			</div>
			<div>
				<font face="宋体">（6）<span class="text">/</span> 银行到期兑付凭证（凭证编号：<span class="text">/</span> ）；</font>
			</div>
			<div>
				<font face="宋体">（7）其他资金还款保障方式<span class="text">/</span></font>
			</div>
			<div>
				<font face="宋体">5.2协议双方授权平台对上述还款保障方式中涉及的文件或相关证明，如保险合同、担保函、银行承兑票据或其他还款保障文件进行核查。</font>
			</div>
			<div>
				<font face="宋体">5.3本协议展示的上述还款保障方式仅供参考。借出人债权的实现，还须视以上还款保障方式涉及的还款保障机构及相应合同、函件的约定。平台对此不作任何承诺。借出人应依独立判断做出决策并自行承担相应的风险。</font>
			</div>
			<div>
				<font face="宋体">5.4如上述还款保障方式所得资金未能足额偿还本协议应付本金、利息及手续费，借出人、平台有权对借入人进行追索。</font>
			</div>
			<div>
				<b><font face="宋体">六、协议的签订、成立及生效</font></b>
			</div>
			<div>
				<font face="宋体">6.1协议双方同意以网络页面点击确认或其他方式（包括但不限于签字或签章确认等方式）签订本协议，并不得以此为由拒绝履行本协议项下的义务，即便在签订时本协议并没有协议对方的信息、第二条及第五条信息、签订日期等。协议双方同意以上述方式签订本协议后即视为不可撤销及变更地授权平台根据最终撮合结果自主生成前述信息，且未经平台及协议对方同意，不得否认本协议项下债权债务关系或以任何方式撤回、撤销本协议。</font>
			</div>
			<div>
				<font face="宋体">6.2本协议自借出人或借入人签订本协议之日（以两者孰后签订时间为准）成立。</font>
			</div>
			<div>
				<font face="宋体">6.3本协议为附条件生效协议，协议生效需同时满足如下条件：</font>
			</div>
			<div>
				<font face="宋体">（1）本协议已成立；</font>
			</div>
			<div>
				<font face="宋体">（2）借入人设定的借款总额在筹集期已全部募集完成；</font>
			</div>
			<div>
				<font face="宋体">（3）平台根据最终借款结果生成借出人与借入人信息、第二条与第五条信息及协议签订日期信息等要素，且本协议可在平台查询。</font>
			</div>
			<div>
				<font face="宋体">6.4本协议项下借款记录及协议文本均以平台生成的内容为准。借出人、借入人可以通过平台查询上述信息。</font>
			</div>
			<div>
				<font face="宋体">6.5如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，不影响其他条款的效力，并可被尽可能接近各方意图的、能够保留本协议要求的经济目的的、有效的新条款所取代。</font>
			</div>
			<div>
				<b><font face="宋体">七、其他</font></b>
			</div>
			<div>
				<font face="宋体">7.1协议项下的债权债务未经协议双方及平台书面同意不得转让。</font>
			</div>
			<div>
				<font face="宋体">7.2协议各方应按法律法规及相关规定各自承担与本协议相关的税费。</font>
			</div>
			<div>
				<font face="宋体">7.3双方理解并同意，平台不承担本协议项下任何资金损失，但因平台导致借出人资金损失的除外。</font>
			</div>
			<div>
				<font face="宋体">7.4双方理解并同意，平台仅提供信息服务，不是本协议借贷方，不承担借贷双方的权利义务，不对产品的任何信息做口头或书面等任何形式的承诺或担保。</font>
			</div>
			<div>
				<font face="宋体">7.5协议各方应对其他方提供的信息及本协议内容保密，未经其他方同意，任何一方不得向协议主体之外的任何人披露，但法律、行政法规另有强制性规定，或监管、审计等有权机关另有强制性要求的除外。</font>
			</div>
			<div>
				<font face="宋体">7.6协议各方知悉并遵守平台制定的相关规则、在平台签署的其他服务协议。</font>
			</div>
			<div>
				<font face="宋体">7.7借出人及借入人必须通过本协议约定的方式或其他平台认可的方式进行放款及还款,否则由此引起的法律后果及违约责任由借出人或借入人自行承担。</font>
			</div>
			<div>
				<b><font face="宋体">八、法律适用</font></b>
			</div>
			<div>
				<font face="宋体">8.1因本协议引起的或与本协议有关的争议，均适用中华人民共和国法律。</font>
			</div>
			<div>
				<font face="宋体">8.2因本协议引起的或与本协议有关的争议，争议各方协商解决。协商不成的，任何一方均有权提起诉讼，并以被告住所地法院为第一审管辖法院。</font>
			</div>
			<div>
				<font face="宋体">&nbsp;</font>
			</div>
			<div>
				<font face="宋体">&nbsp;</font>
			</div>
			<div style="text-align: right;">
				<font face="宋体">借出人签订日期：<span class="text">${data.startDateV }</span>
				</font>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=root%>/static/js/mui.min.js"></script>
<script type="text/javascript">
	mui.init({
		swipeBack : true
	//启用右滑关闭功能
	});
</script>
</html>