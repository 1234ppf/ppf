

//适用于jQuery的AMQ Ajax Adapter
//此类为jquery库提供了一个适配器接口来执行
//一些依赖于库的任务...即logging和ajax。

var org = org || {};
org.activemq = org.activemq || {};

org.activemq.AmqAdapter = {

    init：function（options）{
},


ajax：function（uri，options）{
    request = {
        url：uri，
			data：options.data，
			成功：options.success || 功能（）{}，
			错误：options.error || 功能（）{}
}
    var headers = {};
    if（options.headers）{
        headers = options.headers;
    }

    if（options.method =='post'）{
        request.type ='POST';
        / *强制“连接：关闭”以便Mozilla浏览器解决
        * XMLHttpReqeuest发送错误内容长度的错误
        *标题。见Mozilla Bugzilla＃246651。
    * /
        headers ['Connection'] ='close';
    } else {
        request.type ='GET';
        request.dataType ='xml';
    }

    if（headers）{
        request.beforeSend = function（xhr）{
            for（h in headers）{
                xhr.setRequestHeader（h，headers [h]）;
            }
        }
    }

    jQuery.ajax（request）;
}，

	log：function（message，exception）{
    if（typeof console！='undefined'&& console.log）console.log（message）;
}
};