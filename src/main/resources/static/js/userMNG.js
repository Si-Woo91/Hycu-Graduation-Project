var csrfToken = $('meta[name="_csrf"]').attr('content');
var csrfHeader = $('meta[name="_csrf_header"]').attr('content');


/* 회원관리 start */

$(document).ready(function() {
	
	// 검색
	$(document).on('click', '#searchBtn', function() {
		console.log("검색");
		var keyword = $('#keyword').val(); // 입력된 검색어

		// 페이지 번호를 명시적으로 설정 (기본값 0)
		var page = 0;

		// AJAX 호출
		$.ajax({
			url: '/searchUsers',
			type: 'GET',
			headers: { [csrfHeader]: csrfToken },
			data: { keyword: keyword, page: page },
			success: function(res) {

				console.log("검색 기능 성공");
				console.log('검색 결과:', res);

				$('#content').html(res);

			},
			error: function(xhr, status, error) {
				console.error('검색 중 오류 발생:', error);
				alert('검색 중 오류가 발생했습니다. 다시 시도해주세요.');
			}
		});
	});

	// 회원 삭제 ajax
	$('#userDelBtn').on('click', function() {
		var selectedIds = getSelectedUserIds();

		console.log("selectedIds :: " + selectedIds);

		if (selectedIds.length === 0) {
			alert('삭제할 대상을 체크해주세요.');
			return;
		}

		// 사용자에게 삭제 확인 요청
		if (confirm('선택한 사용자를 삭제하시겠습니까?')) {

			var reqData = selectedIds.map(id => ({ id: id }));

			$.ajax({
				url: 'deleteUsers',
				type: 'POST',
				contentType: 'application/json',
				headers: { [csrfHeader]: csrfToken },
				data: JSON.stringify(reqData),
				success: function(res) {
					if (res.success) {

						window.location.href = '/userList';

					} else {

						alert('삭제에 실패했습니다.');

					}
				},

				error: function() {
					alert('서버 오류가 발생했습니다.'); // AJAX 실패
				}
			});
		}
	});

	// 유저 체크박스 데이터 받아옴
	function getSelectedUserIds() {

		var selectedIds = [];

		$('.userCheckbox:checked').each(function() { // 체크박스 선택
			selectedIds.push($(this).val()); // 체크된 체크박스의 값을 배열에 추가
		});

		console.log("확인 :: " + selectedIds);

		return selectedIds;
	}

});


/* 회원관리 end */





