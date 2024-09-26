/*$(document).ready(function() {
	
const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");
	
	
	$("#loginBtn").click(function() {
		// 입력 값 가져오기
		var userId = $("#userId").val();
		var password = $("#password").val();
		var rememberMe = $("#remember-me").is(":checked");

		console.log(userId);
		console.log(password);
		
		const userInfo = {
   							userId: userId,
    						password: password,
    					};

		// Ajax 요청 보내기
		$.ajax({
			url: "/loginChk",
			type: "POST",
			contentType: "application/json",
			headers: {[csrfHeader]: csrfToken},
			data: JSON.stringify(userInfo),
			success: function(result) {
				alert(result);
				
				if(result == 0){
				alert("성공");
				window.location.href = "/test";
					
				}
				else if(result == 9){
					alert("다시 확인");
				}
				// 성공 시 리다이렉트
			},
			error: function(xhr, status, error) {
				// 실패 시 에러 메시지 표시
				$("#error-message").text("로그인 실패: " + xhr.responseText).removeClass("d-none");
			}
		});
	});
});
*/


/*function performLogin() {
	
    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    
	
    const userId = $("#userId").val();
    const password = $("#password").val();


	alert("실행");
	console.log(userId);
	console.log(password);
	console.log("CSRF Token:", csrfToken);
		
    $.ajax({
        url: "/loginChk",
        type: "POST",
        contentType: "application/json",
        headers: {
             [csrfHeader]: csrfToken // CSRF 토큰 추가
        },
        data: JSON.stringify({
            userId: userId,
            password: password
        }),
        success: function(result) {
            if (result === 0) {
                alert("로그인 성공");
                window.location.href = "/test"; // 성공 시 리다이렉트
            } else if (result === 9) {
                $("#error-message").text("ID 또는 비밀번호를 확인하세요.").removeClass("d-none");
            }
        },
        error: function(xhr) {
            $("#error-message").text("로그인 실패: " + xhr.responseText).removeClass("d-none");
        }
    });
}*/