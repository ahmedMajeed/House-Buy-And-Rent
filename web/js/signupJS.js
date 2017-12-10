$(document).ready(function(){
    $("#signUpButton").attr('disabled', 'disabled');
    
});

$(document).ready(function() {
    var frm = $('#formSignup');
    $('#warning').html("");
    frm.submit(function(e){
        var name=$("#user").val();
        var password=$("#pass").val();
        var confirmPassword=$("#cPass").val();
        $("#passWarn").html("");
        $("#userWarn").html(""); 
        e.preventDefault();
        if((password.length!==confirmPassword.length || (password!==confirmPassword)))
        {
            $("#passWarn").html("confirm password must match password");
        }
        if(name.length<3)
        {
            $("#userWarn").html("name must be greater than or equal 3 characters");
        }
        else{
        $.ajax({
            type: "POST",
            url: "/UserController?action=validateUserName",
            data:"name="+name,
            success: function(data){
                if(data==="true")
                {
                    $("#formSignup").off("submit");
                    $("#formSignup").submit();
                }
                else
                {
                    $('#userWarn').html("name already exists");
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#userWarn').html(textStatus);
            }
        });
    }
  });
});
/*
function validateInputs()
    {
        var passwordValid=false;
        var Name=document.getElementById("user").value;
        var password=document.getElementById("pass").value;
        var confirmPassword=document.getElementById("cPass").value;
        if(password.length!=confirmPassword.length || (password!=confirmPassword))
        {
            document.getElememtById("passWarn").innerHTML="confirm password doesn't match password";
            return false;
        }
        else if(password.length==confirmPassword.length && password==confirmPassword)
        {
            passwordValid=true;
        }

       var valid=false;
       var xmlhttp=new XMLHttpRequest();
        xmlhttp.open("GET","UserController?ACTION=validateUserName?name="+Name,true);
        xmlhttp.send();
        xmlhttp.onreadystatechange=function()
        {
            if(xmlhttp.readyState===4&&xmlhttp.status===200)
            {
                var result=xmlhttp.responseText==="true";
                if(result)
                {
                    valid=true;
                }
                else
                {
                    document.getElementById("userWarn").innerHTML="this name already exists";
                    valid=false;
                }
            }
        };   
        if(valid===true&&passwordValid===true)
            return true;
        else
            return false;
    }
*/
function validateCaptcha(){
        $("#signUpButton").removeAttr('disabled');
    }



