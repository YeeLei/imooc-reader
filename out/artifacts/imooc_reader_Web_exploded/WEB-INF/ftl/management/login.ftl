<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>会员登录-慕课书评网</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="/resources/bootstrap/bootstrap.css">

    <script src="/resources/jquery.3.3.1.min.js"></script>
    <script src="/resources/bootstrap/bootstrap.min.js"></script>
    <style>
        html,
        body {
            height: 100%;
        }

        body {
            display: -ms-flexbox;
            display: flex;
            -ms-flex-align: center;
            align-items: center;
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }
        #frmLogin {
            box-shadow: 0 4px 10px 0 rgba(0, 0, 0, .2);
        }
        .form-signin {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: auto;
        }

        .form-signin .checkbox {
            font-weight: 400;
        }

        .form-signin .form-control {
            position: relative;
            box-sizing: border-box;
            height: auto;
            padding: 10px;
            font-size: 16px;
        }

        .form-signin .form-control:focus {
            z-index: 2;
        }

        .form-signin input[type="email"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }

        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
</head>
<body class="text-center">

<form class="form-signin" id="frmLogin">
    <img class="mb-4" src="/images/logo.svg" alt="" width="75" height="75">
    <h1 class="h3 mb-3 font-weight-normal">图书后台管理系统</h1>
    <div class="alert d-none mt-2" id="tips" role="alert"></div>
    <label for="inputEmail" class="sr-only">Enter Username</label>
    <input type="username" id="username" name="username" class="form-control" placeholder="Enter Username" required autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit" id="btnSubmit">登录</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
</form>
<script>
    function showTips(isShow, css, text) {
        if (isShow) {
            $("#tips").removeClass("d-none")
            $("#tips").hide();
            $("#tips").addClass(css);
            $("#tips").text(text);
            $("#tips").fadeIn(200);
        } else {
            $("#tips").text("");
            $("#tips").fadeOut(200);
            $("#tips").removeClass();
            $("#tips").addClass("alert")
        }
    }


    $("#btnSubmit").click(function () {
        var username = $.trim($("#username").val());
        var regex = /^.{1,10}$/;
        if (!regex.test(username)) {
            showTips(true, "alert-danger", "用户名请输入正确格式（1-10位）");
            return;
        } else {
            showTips(false);
        }

        var password = $.trim($("#password").val());

        if (!regex.test(password)) {
            showTips(true, "alert-danger", "密码请输入正确格式（1-10位）");
            return;
        } else {
            showTips(false);
        }

        $btnReg = $(this);
        $btnReg.text("正在处理...");
        $btnReg.attr("disabled", "disabled");
        //发送请求
        $.ajax({
            url: "/management/checkLogin",
            type: "post",
            dataType: "json",
            data: $("#frmLogin").serialize(),
            success: function (data) {
                console.info(data);
                if (data.code == "0") {
                    window.location = "/management/index.html?ts=" + new Date().getTime();
                } else {
                    showTips(true, "alert-danger", data.msg);
                    $btnReg.text("登录");
                    $btnReg.removeAttr("disabled");
                }
            }
        });
        return false;
    })
</script>
</body>
</html>
