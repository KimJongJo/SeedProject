/* 다음 주소 API 활용*/
console.log("ㅎㅇ")

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

// 주소 검색 버튼 클릭 시
document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);


// ***************** 회원 가입 유효성 검사 ***********************
const checkObj = {
    "memberId" : false,
    "memberPw" : false,
    "memberPwConfirm" : false,
    "memberEmail" : false,
    "memberNickname" : false,
    "memberTel" : false,
    "authKey" : false
}

// ***** 1. 아이디 유효성 검사 *******
const memberId = document.getElementById('memberId');
const idMsg = document.getElementById('idMsg');

memberId.addEventListener("input", e => {

    const inputId = e.target.value;

    // 입력된 아이디 없을 경우
    if(inputId.trim().length === 0) {
        
        idMsg.innerText = "아이디를 입력해 주세요.";

        idMsg.classList.remove('confirm', 'error');

        checkObj.memberId = false;

        memberId.value = "";

        return;

    }

    // 5~20자의 영문 소문자, 숫자만 사용 가능합니다.
    const regExp = /^[a-z0-9]{5,20}$/;

    if(!regExp.test(inputId)) {
        idMsg.innerText = "5~20자의 영문 소문자, 숫자만 사용 가능합니다.";
        idMsg.classList.add('error');
        idMsg.classList.remove('confirm');
        checkObj.memberId = false;
        return;
    }

    fetch("/member/checkId?memberId=" + inputId)
    .then(resp => resp.text()) 
    .then(count => {

        if(count == 1) { // 중복 O
            
            idMsg.innerText = "이미 사용중인 아이디입니다.";
            idMsg.classList.add('error');
            idMsg.classList.remove('confirm');
            checkObj.memberId = false;
            return;
        }

        idMsg.innerText = "사용 가능한 아이디입니다.";
        idMsg.classList.add('confirm');
        idMsg.classList.remove('error');
        checkObj.memberId = true;

    });


});