/api/product/findMajor
商城页面	->	必玩创客盒 3-4岁，5-6岁，7-8岁 产品列表

/api/product/findSubject
商城页面	->	创意主题盒产品列表

/api/classroom/findOrder?userId=$(userId)
小创客页面	->	如果已登录，请传入userId参数，userId为可选参数，查询订购产品的及免费的视频

/api/classroom/findCollection?userId=$(userId)
小创客页面	->	如果已登录，请传入userId参数，userId为必填参数，查询用户收藏的视频

/api/order/find?keyword=$(keyword)&statusInstr=$(statusInstr)&startTime=$(startTime)&endTime=$(endTime)&userId=$(userId)
会员中心	->	我的订单
keyword:关键字，可选参数
statusInstr:状态条件，多个使用半角逗号“,”分隔，可选参数
startTime:订单开始时间，可选参数
endTime:订单截止时间，可选参数
userId:用户ID，必填参数
