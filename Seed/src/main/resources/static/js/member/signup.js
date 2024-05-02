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
};

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

    })    
    .catch(error => {
        console.log(error); 
    });


});

// -----------------------------------------------------------------------


// ***** 2. 아이디 유효성 검사 *******
// const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");

const pwMsg = document.getElementById("pwMsg");
const pwConfirmMsg = document.querySelector("#pwConfirmMsg");

// 1) 비번 == 비번 확인 체크 함수 정의
const checkPw = () => {
    
    // 같음
    if(memberPw.value === memberPwConfirm.value) {
        pwConfirmMsg.innerText = "비밀번호가 일치합니다.";
        pwConfirmMsg.classList.add("confirm");
        pwConfirmMsg.classList.remove("error");
        checkObj.memberPwConfirm = true;
        return;
    }

    pwConfirmMsg.innerText = "비밀번호가 일치하지 않습니다.";
    pwConfirmMsg.classList.add("error");
    pwConfirmMsg.classList.remove("confirm");
    checkObj.memberPwConfirm = false;
};


// 2) 유효성 검사
memberPw.addEventListener("input", e => {

    const inputPw = e.target.value;

    // 미입력
    if(inputPw.trim().length === 0 ) {
        pwMsg.innerText = "6~20자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.";
        pwMsg.classList.remove("confirm", "error");
        checkObj.memberPw = false;
        memberPw.value = ""; // 처음 공백 방지
        return;
    }

    
    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

    // 정규식 X
    if(!regExp.test(inputPw)) {
        pwMsg.innerText = "입력하신 비밀번호의 형식이 유효하지 않습니다.";
        pwMsg.classList.add("error");
        pwMsg.classList.remove("confirm");
        checkObj.memberPw = false;
        return;
    }

    // 정규식 O
    pwMsg.innerText = "유효한 비밀번호 형식입니다.";
    pwMsg.classList.add("confirm");
    pwMsg.classList.remove("error");
    checkObj.memberPw = true;

    if(memberPwConfirm.value.length > 0) {
        checkPw();
    } 

});

// 비밀번호가 유효하다면 일치 여부 확인
memberPwConfirm.addEventListener("input", e => {
    
    if(checkObj.memberPw) {
        checkPw(); // 비교하는 함수 호출
        return;
    }

    // memberPw = false
    checkObj.memberPwConfirm = false;
});



// ---------------------------------------------------------------------

// modal_background : 뒷 배경
// popupLayer : 팝업창
// popup-exit : X 아이콘


// =============================== 유효성 검사 다하고, 메일 인증 연계 예정 ===============================

// * 인증 버튼 클릭 시
document.getElementById("emailAuth").addEventListener("click", () => {

    const modalBackground = document.getElementById("modalBackground"); // 팝업 뒷 배경
    const popupLayer = document.getElementById("popupLayer"); // 팝업창

    modalBackground.classList.remove('popup-hidden');
    popupLayer.classList.remove('popup-hidden');

    
    const popupExit = document.getElementById("popupExit"); // X 아이콘

    popupExit.addEventListener("click", () => {

        modalBackground.classList.add('popup-hidden');
        popupLayer.classList.add('popup-hidden');
    })
;
})

// ======================================================================================================


// ***** 3. 이메일 유효성 검사 *******
const memberEmail = document.getElementById("memberEmail");
const emailMsg = document.getElementById("emailMsg"); // 이메일 입력 시 span
const authMsg = document.getElementById("authMsg"); // 인증 팝업 span

// 1) 이메일 입력 이벤트 생성
memberEmail.addEventListener("input", e => {

    // 이메일 인증 후 이메일이 변경될 경우
    // checkObj.authKey = false;
    // document.querySelector("#authKeyMessage").innerText ="";

    const inputEmail = e.target.value;

    
    if(inputEmail.trim().length === 0) {
        emailMsg.innerText = "실제 사용하고 계신 이메일을 입력해 주세요.";

        emailMsg.classList.remove('confirm', 'error');

        checkObj.memberEmail = false;

        memberEmail.value = "";

        return;
    }

    // 2) 정규식
    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    
    // 입력 받은 이메일이 정규식과 일치하지 않는 경우
    // (알맞은 이메일 형태가 아닌 경우)
    if( !regExp.test(inputEmail) ) {
        emailMsg.innerText = "올바른 이메일 형식으로 입력해 주세요.";
        emailMsg.classList.add('error'); 
        emailMsg.classList.remove('confirm');
        checkObj.memberEmail = false; 
        return;
    }




    // fetch("/member/checkEmail?memberEmail=" + inputEmail)
    // .then( resp => resp.text() )
    // .then( count => {

    //     if(count == 1) { // 중복 O
    //         emailMsg.innerText = "이미 사용중인 이메일입니다.";
    //         emailMsg.classList.add('error');
    //         emailMsg.classList.remove('confirm');
    //         checkObj.memberEmail = false; 
    //         return;
    //     }
        
        // 중복 X 경우
        emailMsg.innerText = "사용 가능한 이메일입니다.";
        emailMsg.classList.add('confirm');
        emailMsg.classList.remove('error'); 
        checkObj.memberEmail = true; 

    // })
    // .catch(error => {
    //     console.log(error); 
    // });

});





// ----------------------------------------------------------------------------------


// ***** 4. 닉네임 유효성 검사 *******

const memberNickname = document.querySelector("#memberNickname");
const nickMsg = document.querySelector("#nickMsg");

memberNickname.addEventListener("input", e => {

    const inputNickname = e.target.value;

    // 입력 x
    if(inputNickname.trim().length === 0) {
        nickMsg.innerText = "2~5자의 한글만 사용해 주세요.";
        nickMsg.classList.remove("error", "confirm");
        
        checkObj.memberNickname = false;
        nickMsg.value = "";
        return;

    }

    const regExp = /^[가-힣\w\d]{2,5}$/;

    // 닉네임 유효성 검사
    if(!regExp.test(inputNickname)) { // 유효 X
        nickMsg.innerText = "닉네임 형식이 유효하지 않습니다."
        nickMsg.classList.add("error");
        nickMsg.classList.remove("confirm");
        checkObj.memberNickname = false;
        return;
    }

    // fetch("/member/checkNickname?memberNickname=" + inputNickname)
    // .then(resp => resp.text())
    // .then(count => {

    //     if(count == 1) { // 중복 O
    //         nickMsg.innerText = "이미 사용중인 닉네임입니다.";
    //         nickMsg.classList.add("error");
    //         nickMsg.classList.remove("confirm");
    //         checkObj.memberNickname = false;
    //         return;
    //     }

        nickMsg.innerText = "사용 가능한 닉네임입니다.";
        nickMsg.classList.add("confirm");
        nickMsg.classList.remove("error");
        checkObj.memberNickname = true;

    // });
    
});



// ----------------------------------------------------------


// 자동 하이픈 기능 함수
const phoneAutoHyphen = (target) => {
    target.value = target.value
    .replace(/[^0-9]/g, '')
    .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
    
};


// ***** 5. 전화번호 유효성 검사 *******
const memberTel = document.querySelector("#memberTel");
const telMsg = document.querySelector("#telMsg");


memberTel.addEventListener("input", e => {

    const inputTel = e.target.value;

    // 입력 X
    if(inputTel.trim().length === 0) {
        telMsg.innerText = "010-****-**** 형식으로 전화번호를 입력해 주세요.";
        telMsg.classList.remove("confirm", "error");
        memberTel.value = "";
        checkObj.memberTel = false;

        return;
    }



    // 정규식 검사
    const regExp = /^01[0-9]{1}-[0-9]{3,4}-[0-9]{4}$/;

    if(!regExp.test(inputTel)) { // 유효 x
        telMsg.innerText = "전화번호 형식을 올바르게 입력해 주세요."
        telMsg.classList.add("error");
        telMsg.classList.remove("confirm");
        checkObj.memberTel = false;
        return;
    }

    telMsg.innerText = "유효한 전화번호 형식입니다.";
    telMsg.classList.add("confirm");
    telMsg.classList.remove("error");
    checkObj.memberTel = true;
    
});





// ------------------------------------------------------



// ********** 회원 가입 버튼 클릭 시 **************** / 

