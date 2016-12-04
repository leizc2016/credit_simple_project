$(document).ready(function() {
	//header
	var wkHeader = $('<div class="container"><div class="row"><div class="pull-left">欢迎访问<a href="1_homepage.html">pharmacodia.com</a></div><ul class="pull-left list-inline"><li class="dropdown"><span class="icon icon-login"></span><a data-toggle="modal" data-target="#loginDialog">登录</a></li><li><span>|</span></li><li><a href="2_user_register.html">注册</a></li></ul><ul class="pull-right list-inline"><li><span class="icon icon-app"></span><a href="">APP下载</a></li><li><span>|</span></li><li class="weixin"><div class="dropdown"><a href="" class="dropdown-toggle js-activated" data-toggle="dropdown">微信关注</a><div class="dropdown-menu text-center"><img src="img/weixin.png" width="202" height="202" /><p>扫一扫</p><p>关注<a class="blue" href="">Pharmacodia</a>微信号</p></div></div></li><li><span>|</span></li><li><a href="3_register.html">English</a></li><li class="top-cart"><div class="dropdown"><a href="#" class="dropdown-toggle js-activated"><b class="caret"></b></a><ul class="dropdown-menu cart pull-right"><li><div class="row cart-title"><div class="col-xs-4">产品名称</div><div class="col-xs-3">规格</div><div class="col-xs-2">购买数量</div><div class="col-xs-3">应付金额</div></div><div class="row cart-content"><div class="col-xs-4">Empagliflozin</div><div class="col-xs-3">100mg</div><div class="col-xs-2"></div><div class="col-xs-3">200.00</div></div><div class="row cart-content"><div class="col-xs-4">Empagliflozin</div><div class="col-xs-3">100mg</div><div class="col-xs-2">4</div><div class="col-xs-3">200.00</div></div><div class="row total"><div class="col-xs-12 offset-b10">信息未完善，详情请进入购物车</div></div><div class="row btn-box"><div class="col-xs-12"><a class="btn btn-default btn-xs pull-left">继续购物</a><a class="btn btn-warning btn-xs pull-right">我的购物车</a></div></div></div></li></ul></li></ul></div></div>');
	$("#headerTop").append(wkHeader);

	//footer
	var wkFooter = $('<div class="footer"><div class="container clearfix"><div class="footer-box1"><ul class="list-unstyled"><li><h4>关于我们</h4></li><li><a href="#">药渡简介</a></li><li><a href="#">诚聘英才</a></li><li><a href="#">联系我们</a></li></ul><ul class="list-unstyled"><li><h4>用户中心</h4></li><li><a href="#">常见问题</a></li><li><a href="#">意见反馈</a></li><li><a href="#">隐私声明</a></li><li><a href="#">服务协议</a></li></ul></div><div class="footer-box2 text-center"><h4><a href="1_app_download.html">移动端下载</a></h4><div class="dropdown footer-weixin weixin"><a href="" class="dropdown-toggle" data-toggle="dropdown"><img src="img/footer_weixin.png" width="79" height="80"></a><div class="dropdown-menu text-center"><img src="img/footer_weixin_lg.png" width="202" height="202" /><p>扫一扫</p><p>下载<a class="blue" href="">Pharmacodia</a>APP客户端</p></div></div><ul class="list-inline offset-l10 offset-t5 footer-phone"><li><a href="#"><span class="icon icon-an"></span></a></li><li><span class="icon icon-footer-line"></span></li><li><a href="#"><span class="icon icon-ios"></span></a></li></ul></div><div class="footer-box3 clearfix"><div class="pull-left"><a href="#"><h4>分析报告下载</h4></a><a href="#"><span class="icon icon-download"></span></a></div><div class="pull-right"><a href="#"><img src="img/footer_img.png" width="112" height="144"></a></div></div></div></div><div class="footer-bottom clearfix"><a href="" class="link1"></a><a href="" class="link2"></a><a href="" class="link3"></a><a href="" class="link4"></a></div><div class="text-center"><ul class="list-inline font-sm"><li>CopyRight&copy2015 Pharmacodia.com</li><li><span>|</span></li><li><a class="black" href="#">京ICP备14047345号</a></li><li><span>|</span></li><li>京公网安备11010802017043号</li></ul></div>');
	$("#footerWrapper").append(wkFooter);


});


















