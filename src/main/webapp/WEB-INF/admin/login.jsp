<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

%>
<base href="<%=basePath%>">
<html>
<head>
  <title></title>
  <jsp:include page="/WEB-INF/layout/header.jsp"/>

</head>
<body style="background-color: #666;">
<div style="height: 220px;display: block;">

</div>

<div id="dialog_add" class="easyui-dialog" title="用户登录"
     closed="false" buttons="#dlg-buttons" style="height: 180px;width:340px;margin: 0 auto;">
  <form id="fm" method="post" novalidate>
    <div class="u8_form_row" style="margin-top: 15px">
      <label style="width: 50px">用户名：</label>
      <input id = "username" type="text" class="easyui-textbox" name="username" maxlength="255"  />
    </div>

    <div class="u8_form_row" >
      <label style="width: 50px">密　码：</label>
      <input id="pwd" type="password" class="easyui-textbox" name="password" maxlength="255" />
    </div>

  </form>
</div>
<div id="dlg-buttons">
  <a href="javascript:void(0)" class="easyui-linkbutton c6" onclick="login();" style="width:90px">登　录</a>
</div>

<script type="text/javascript">

  function login(){

    var username = $("#username").val();
    var pwd = $("#pwd").val();
    pwd = $.md5(pwd);

    $.post('<%=basePath%>/admin/doLogin', {username:username, password:pwd}, function(result){
      if (result.state == 1) {

        location.href="<%=basePath%>/admin/index"

      }else{
        $.messager.show({
          title:'操作提示',
          msg:result.msg
        })
      }



    }, 'json');



  }

  $(document).ready(function(){
    $(":password").keypress(function(event){
      if(13 === event.which){
        login();
      }
    });
  });

</script>

</body>
</html>
