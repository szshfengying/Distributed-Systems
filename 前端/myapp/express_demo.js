//express_demo.js 文件
var express = require('express');
var app = express();

 app.all('*', function(req, res, next) {
 
res.header("Access-Control-Allow-Origin", "*");//项目上线后改成页面的地址
 
res.header("Access-Control-Allow-Headers", "X-Requested-With,Content-Type");
 
res.header("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
 
next();
 
});

//app.post('/login', (req,res) =>{
//	console.log("账号",req.query.accid);
//	console.log("密码",req.query.password);
//   var response ={
//	"code":0
//	}
//   res.send(JSON.stringify(response));
//})

app.post('/login', (req,res) =>{
	if(req.query.accid!="1234567890123452")
	{
		console.log(req.query);
   		var response ={
			"code":1,
			"msg":"没有该账号"
		}
  	 	res.send(JSON.stringify(response));
	}
	else if(req.query.password != "asd123")
	{
		console.log(req.query);
   		var response ={
			"code":2,
			"msg":"密码错误",
		}
  	 	res.send(JSON.stringify(response));
	}
	else
	{
		console.log(req.query);
   		var response ={
			"code":0,
			"msg":"登录成功"
		}
  	 	res.send(JSON.stringify(response));
	}	
})


app.post('/info', (req,res) =>{
		console.log(req.query);
   		var response ={
			"addr":"广东省汕头市濠江区",
			"currtype":"人民币",
			"name":"张炜杰"
		}
  	 	res.send(JSON.stringify(response));	
})


app.post('/bal', (req,res) =>{
	console.log(req.query);
	   var response ={
		"balance":"100000000",
		"currtype":"人民币",
	}
	   res.send(JSON.stringify(response));	
})

app.post('/trans', (req,res) =>{
	console.log(req.query);
	   var response ={
		"code":"1",
		"msg":'余额不足'
	}
	   res.send(JSON.stringify(response));	
})

app.post('/openacc', (req,res) =>{
	console.log(req.query);
	   var response ={
		"code":"0",
		"msg":'1234567890123452'
	}
	   res.send(JSON.stringify(response));	
})

app.get('/login', (req,res) =>{
	console.log(req);
   var response ={
	"code":0
	}
   res.send(JSON.stringify(response));
})



 
var server = app.listen(8081, function () {
	// var server = app.listen(25001, function () {
 
  var host = server.address().address
  var port = server.address().port
 
  console.log("应用实例，访问地址为 http://%s:%s", host, port)
 
})







