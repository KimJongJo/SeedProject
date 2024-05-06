// 회원 정보 수정(닉네임, 전화번호, 이메일)
const updateInfo = document.querySelector("#updateInfo");

if(updateInfo != null) {

    updateInfo.addEventListener("submit", e => {

        const nickName = document.querySelector(".nickName");
        const telNo = document.querySelector(".telNo");
        const address = document.querySelectorAll("[class='address-input']");

        // 닉네임 유효성검사
        if(nickName.value.trim().length === 0) {
            alert("닉네임을 입력해주세요");
            e.preventDefault();
            // 닉네임이 입력이 안되어있을때 화면은 안바뀌고 alert창뜨게 함
            return;
        }

        // 닉네임 정규표현식 확인
        let regExp = /^[가-힣\w\d]{2,10}$/;
        if(!regExp.test(nickName.value)) {
            alert("닉네임이 유효하지 않습니다");
            e.preventDefault();
            return;
        }

        // 전화번호 유효성검사
        if(telNo.value.trim().length === 0) {
            alert("전화번호를 입력해 주세요");
            e.preventDefault();
            return;
        }

        // 전화번호 정규표현식 확인
        regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;
        if(!regExp.test(telNo.value)) {
            alert("전화번호가 유효하지 않습니다");
            e.preventDefault();
            return;
        }
    })

}



// 회원 비밀번호 수정
const updatePw = document.querySelector("#updatePw");

if(updatePw != null) {

    updatePw.addEventListener("submit", e => {

        const nowPw = document.querySelector("#nowPw");
        const updatePw = document.querySelector("#updatePw");
        const updatePw2 = document.querySelector("#updatePw2");

        let str;
        if(nowPw.value.trim().length === 0) str = "현재 비밀번호를 입력해주세요";
        else if(updatePw.value.trim().length === 0) str = "새 비밀번호를 입력해주세요";
        else if(updatePw2.value.trim().length === 0) str = "새 비밀번호 확인을 입력해주세요";

        // 비밀번호 입력하는 곳에 하나라도 안써있으면 alert창뜨게 함
        if(str != undefined) {
            alert(str);
            e.preventDefault(); // 페이지 새로고침되는걸 막음
            return;
        }

        // 비밀번호 정규식
        const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;

        // 새 비밀번호 정규식 확인
        if(!regExp.test(updatePw.value)) {
            alert("새 비밀번호가 유효하지 않습니다");
            e.preventDefault();
            return;
        }

        // 새 비밀번호 확인 정규식 확인
        if(!regExp.test(updatePw2.value)) {
            alert("새 비밀번혹 확인이 유효하지 않습니다");
            e.preventDefault();
            return;
        }

        // 현재 비번, 새 비번, 비번2 가 일치하는지는 자바에서 함
    });
}