var csrfToken = $('meta[name="_csrf"]').attr('content');
var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

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
			
			var reqData = selectedIds.map(id => ({id: id}));
			
            $.ajax({
                url: 'deleteUsers', // 삭제 요청을 보낼 URL
                type: 'POST',
                contentType: 'application/json',
                headers: {[csrfHeader]: csrfToken},
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