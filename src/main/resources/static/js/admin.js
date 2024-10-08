var csrfToken = $('meta[name="_csrf"]').attr('content');
var csrfHeader = $('meta[name="_csrf_header"]').attr('content');


/* 회원관리 start */

// 검색
$(document).ready(function() {
    $('#searchBtn').click(function() {
		console.log("검색");
        var keyword = $('#keyword').val(); // 입력된 검색어

        // AJAX 호출
        $.ajax({
            url: '/search',
            type: 'GET',
            data: { keyword: keyword },
            success: function(data) {
                console.log('검색 결과:', data);
            },
            error: function(xhr, status, error) {
                console.error('검색 중 오류 발생:', error);
                alert('검색 중 오류가 발생했습니다. 다시 시도해주세요.');
            }
        });
    });
});

// 회원 삭제 ajax
$(document).ready(function() {
	$('#deleteBtn').on('click', function() {
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

						window.location.href = '/admin';

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

	// 체크박스 데이터 받아옴
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


/* 상품관리 start */

// 상품 등록
$(document).ready(function() {
    $('#saveBtn').click(function() {

        var productName = $('#productName').val();
        var productCode = $('#productCode').val();
        var productPrice = $('#productPrice').val();
        var prodImg = $('#prodImg')[0].files[0];
        var prodDetailImg = $('#prodDetailImg')[0].files[0];

        // FormData 객체를 생성
        var formData = new FormData();
        formData.append('productName', productName);
        formData.append('productCode', productCode);
        formData.append('productPrice', productPrice);
        formData.append('prodImg', prodImg);
        formData.append('prodDetailImg', prodDetailImg);

        // AJAX
        $.ajax({
            url: '/saveProduct', // 데이터를 저장할 서버 URL
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
				
                // 성공
                alert('상품이 성공적으로 등록되었습니다!');
                closeModal();
            },
            error: function(xhr, status, error) {
                // 오류
                console.error('상품 등록 중 오류 발생:', error);
                alert('상품 등록에 실패했습니다. 다시 시도해주세요.');
            }
        });
    });
});

// 상품 등록 모달창
function loadModalContent() {
	$.ajax({
		url: '/modal/regModal',
		type: 'GET',
		success: function(data) {

			console.log('가져온 데이터:', data);
			$('#regModal .modal-content').html(data);
			$('#regModal').modal('show');
		},
		error: function(xhr, status, error) {
			console.error('모달 로드 중 오류 발생:', error);
		}
	});
}

// 모달 창 닫기
function closeModal() {
    $('#regModal').modal('hide');
}

/* 상품관리 end */

