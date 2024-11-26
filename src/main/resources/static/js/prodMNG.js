var csrfToken = $('meta[name="_csrf"]').attr('content');
var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

/* 상품관리 start */

$(document).ready(function() {
	
	// 검색
	$(document).on('click', '#ProdsearchBtn', function() {
		console.log("검색");
		var keyword = $('#keyword').val(); // 입력된 검색어

		// 페이지 번호를 명시적으로 설정 (기본값 0)
		var page = 0;

		// AJAX 호출
		$.ajax({
			url: '/searchProds',
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
	
	

	// 상품 등록
	$('#saveBtn').click(function() {

		var productType = $('#productType').val();
		var productName = $('#productName').val();
		var productCode = $('#productCode').val();
		var productPrice = $('#productPrice').val();
		var productQuantity = $('#productQuantity').val();
		var prodImg = $('#prodImg')[0].files[0];
		var prodDetailImg = $('#prodDetailImg')[0].files[0];
		
		// 상품 종류를 선택 하지 않았을 경우
		if(productType == 'select'){
			alert('상품 종류를 선택해주세요');
			return;
		}
		
		 // 필수 입력값이 빠졌을 경우 체크
		if (!productType || !productName || !productCode || !productPrice || !productQuantity || !prodImg || !prodDetailImg) {
			alert('모든 항목을 입력해주세요.');
			return;
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

		$.ajax({
			url: '/saveProduct',
			type: 'POST',
			data: formData,
			headers: { [csrfHeader]: csrfToken },
			contentType: false,
			processData: false,
			success: function(res) {

				// 성공
				alert('상품이 성공적으로 등록되었습니다!');
				closeModal();
				location.reload();
			},
			error: function(xhr, status, error) {
				// 오류
				console.error('상품 등록 중 오류 발생:', error);
				alert('상품 등록에 실패했습니다. 다시 시도해주세요.');
			}
		});
	});

	// 상품 삭제 ajax
	$('#prodDelBtn').on('click', function() {
		var selectedIds = getSelectedProdIds();

		console.log("selectedIds :: " + selectedIds);

		if (selectedIds.length === 0) {
			alert('삭제할 대상을 체크해주세요.');
			return;
		}

		// 사용자에게 삭제 확인 요청
		if (confirm('선택한 상품을 삭제하시겠습니까?')) {

			var reqData = selectedIds.map(id => ({ id: id }));

			$.ajax({
				url: '/deleteProd',
				type: 'POST',
				contentType: 'application/json',
				headers: { [csrfHeader]: csrfToken },
				data: JSON.stringify(reqData),
				success: function(res) {
					if (res.success) {

						window.location.href = '/prodList';

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

});

// 상품 체크박스 
function getSelectedProdIds() {

	var selectedIds = [];

	$('.prodCheckbox:checked').each(function() { // 체크박스 선택
		selectedIds.push($(this).val()); // 체크된 체크박스의 값을 배열에 추가
	});

	console.log("확인 :: " + selectedIds);

	return selectedIds;
}

// 모달 창 닫기
function closeModal() {
	$('#regModal').modal('hide');
}

// 상품 등록 모달창
function regProdModal() {
	$.ajax({
		url: '/modal/regModal',
		type: 'GET',
		success: function(res) {

			console.log('가져온 데이터:', res);
			$('#regModal .modal-content').html(res);
			$('#regModal').modal('show');
		},
		error: function(xhr, status, error) {
			console.error('모달 로드 중 오류 발생:', error);
		}
	});
}

// 상품 상세보기
function detailProdModal() {
    var selectedProd = $('.prodCheckbox:checked');
    
    console.log("id : " + selectedProd);
    
    if (selectedProd.length === 1) {
        var prodId = selectedProd.val();
	    console.log("id2 : " + prodId);
        
        $.ajax({
            url: '/modal/detailModal/' + prodId,
            type: 'GET',
            success: function(data) {
				
                $('#regModal .modal-content').html(data);
                $('#regModal').modal('show');
            },
            error: function() {
                alert("상품 정보를 불러오는데 실패했습니다.");
            }
        });
    } else {
        alert("상세보기를 위해 하나의 상품만 선택하세요.");
    }
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

// 파일이 선택되지 않으면 기존 값 유지
function checkImageUpload(fileInputId, hiddenInputId) {
    const fileInput = document.getElementById(fileInputId);
    const hiddenInput = document.getElementById(hiddenInputId);

    // 파일이 선택되면 hidden 필드 값 제거, 선택되지 않으면 기존 값을 유지
    if (fileInput.files.length > 0) {
        hiddenInput.value = '';  
    }
}

// 수정 버튼 클릭시
function toggleEditMode() {
    const editSaveBtn = document.getElementById("editSaveBtn");
    const productDetailView = document.getElementById("productDetailView");
    const productEditView = document.getElementById("productEditView");

    if (editSaveBtn.textContent === "수정") {
        // 수정 폼에 기존 값을 설정
        document.getElementById("productType").value = document.getElementById("productTypeDetail").value;
        document.getElementById("productName").value = document.getElementById("productNameDetail").value;
        document.getElementById("productCode").value = document.getElementById("productCodeDetail").value;
        document.getElementById("productPrice").value = document.getElementById("productPriceDetail").value;
        document.getElementById("productQuantity").value = document.getElementById("productQuantityDetail").value;

        // 상세 보기 -> 수정 보기
        productDetailView.style.display = "none";
        productEditView.style.display = "block";

        // 버튼 "수정" -> "저장"
        editSaveBtn.textContent = "저장";
    } else {
        // 저장 시 로직 실행
        saveProductDetails();

        // 수정 보기 -> 상세 보기
        productDetailView.style.display = "block";
        productEditView.style.display = "none";

        // 버튼 "저장" -> "수정"
        editSaveBtn.textContent = "수정";
    }
}

// 상품 수정
function saveProductDetails() {

	const formData = new FormData();

	// 텍스트 필드 값 추가
	formData.append("prodId", document.getElementById("productId").value);
	formData.append("prodType", document.getElementById("productType").value);
	formData.append("prodNm", document.getElementById("productName").value);
	formData.append("prodCd", document.getElementById("productCode").value);
	formData.append("prodPrice", document.getElementById("productPrice").value);
	formData.append("productQuantity", document.getElementById("productQuantity").value);

	// 이미지
	var prodImgFile = document.getElementById("prodImg").files[0];
	var prodDetailImgFile = document.getElementById("prodDetailImg").files[0];

	//상품 대표이미지
	if (prodImgFile) 
	{
		formData.append("prodImg", prodImgFile);
	} 
	else 
	{
		formData.append("prodImg", document.getElementById("hiddenProdImg").value);  // 기존 파일 이름을 보냄
	}

	
	// 상품 상세 이미지
	if (prodDetailImgFile) {
		formData.append("prodDetailImg", prodDetailImgFile);
	} else {
		formData.append("prodDetailImg", document.getElementById("hiddenProdDetailImg").value);  // 기존 파일 이름을 보냄
	}

	// 수정 ajax
	$.ajax({
		url: '/updateProd',
		type: 'POST',
		data: formData,
		processData: false, 
		contentType: false, 
		headers: { [csrfHeader]: csrfToken },
		success: function(res) {

			alert('상품이 성공적으로 수정되었습니다!');
			closeModal();

			location.reload();
		},
		error: function(xhr, status, error) {
			
			console.error('상품 수정 중 오류 발생:', error);
			alert('상품 수정에 실패했습니다. 다시 시도해주세요.');
		}
	});
}

	/* 상품관리 end */