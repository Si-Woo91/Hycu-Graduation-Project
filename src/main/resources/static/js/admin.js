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

	// 회원 삭제 ajax
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

	/* 회원관리 end */


	/* 상품관리 start */

	// 상품 등록
	$('#saveBtn').click(function() {

		var productType = $('#productType').val(); 
		var productName = $('#productName').val();
		var productCode = $('#productCode').val();
		var productPrice = $('#productPrice').val();
		var productQuantity = $('#productQuantity').val();
		var prodImg = $('#prodImg')[0].files[0];
		var prodDetailImg = $('#prodDetailImg')[0].files[0];
		
		if (!productType || !productName || !productCode || !productPrice || !productQuantity || !prodImg || !prodDetailImg) {
        	alert('모든 항목을 입력해주세요.');
        	return; // 필수 입력값이 빠졌을 경우 요청을 중단
		}
		
		// FormData 객체를 생성
		var formData = new FormData();
		formData.append('productType', productType);
		formData.append('productName', productName);
		formData.append('productCode', productCode);
		formData.append('productPrice', productPrice);
		formData.append('productQuantity', productQuantity);
		formData.append('prodImg', prodImg);
		formData.append('prodDetailImg', prodDetailImg);

		// AJAX
		$.ajax({
			url: '/saveProduct', // 데이터를 저장할 서버 URL
			type: 'POST',
			data: formData,
			headers: { [csrfHeader]: csrfToken },
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



	/* 상품관리 end */
});

// 모달 창 닫기
function closeModal() {
	$('#regModal').modal('hide');
}
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

// 수량 증가 함수
function increaseQuantity() {
    var quantityInput = document.getElementById('productQuantity');
    var currentValue = parseInt(quantityInput.value);

    if (!isNaN(currentValue)) {
        quantityInput.value = currentValue + 1;
    }
}

// 수량 감소 함수
function decreaseQuantity() {
    var quantityInput = document.getElementById('productQuantity');
    var currentValue = parseInt(quantityInput.value);

    if (!isNaN(currentValue) && currentValue > 1) { // 수량은 1 이하로 줄어들지 않도록 설정
        quantityInput.value = currentValue - 1;
    }
}


