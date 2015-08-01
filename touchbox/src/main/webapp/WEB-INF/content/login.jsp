<%@ page language="java" pageEncoding="UTF-8"%><%@ include file="includes/head.jsp" %>
  <title>Home Login Page</title>
  <link rel="alternate icon" type="image/png" href="AmazeUI/assets/i/favicon.png">
  <link rel="stylesheet" href="AmazeUI/assets/css/amazeui.min.css"/>
  <style>
    .header {
      text-align: center;
    }
    .header h1 {
      font-size: 200%;
      color: #333;
      margin-top: 30px;
    }
    .header p {
      font-size: 14px;
    }
  </style>
</head>
<body>
<div class="header">
  <div class="am-g">
    <h1>Web Touchbox</h1>
    <p>Integrated Development Environment<br/>小创客、创意盒子、商城、会员</p>
  </div>
  <hr />
</div>
<div class="am-g">
  <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
    <br>
    <br>

    <form method="post" class="am-form" action="">
      <label for="username">用户名:</label>
      <input type="text" name="username" id="username" value="">
      <br>
      <label for="password">密码:</label>
      <input type="password" name="password" id="password" value="">
      <br>
      <%-- 
      <label for="remember-me">
        <input id="remember-me" type="checkbox">
        记住密码
      </label>--%>
      <br />
      <div class="am-cf">
        <input type="submit" name="" value="登 录" class="am-btn am-btn-primary am-btn-sm am-fl">
       <!--  <input type="submit" name="" value="忘记密码 ^_^? " class="am-btn am-btn-default am-btn-sm am-fr"> -->
      </div>
    </form>
    <hr>
    <p>© 2015 TOUCHBOX, Inc. Licensed under MIT license.</p>
  </div>
</div>
</body>
</html>