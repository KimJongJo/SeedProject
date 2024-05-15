// console.log("d연결확인");

const modal = document.querySelector("#modal"); // 모달창
const crossBtn = document.querySelector("#crossBtn");  // 모달창 close 버튼

const checkBox = document.querySelector(".checkBox_box");

const withBtn = document.querySelector("#withdraw_btn"); // 탈퇴하기 버튼
const modalWithBtn = document.querySelector("#modal_withBtn"); // 모달창 안에 탈퇴하기 버튼
const memberPw = document.querySelector("#memberPw"); // 입력한 비밀번호

// const memberNo = document.querySelector("#memberNo"); // 로그인한 회원 번호

// 모달창 열기
withBtn.addEventListener("click", () => {

    if(checkBox.checked){
        modal.style.display = "block";
    }else{
        alert("회원 탈퇴 내용에 동의해주세요.");
    }



    
});

// 모달창 닫기
crossBtn.addEventListener("click", () => {
    modal.style.display = "none";
});

// 모달창 탈퇴하기 버튼
modalWithBtn.addEventListener("click", (e) => {

    

    if(newPw.value == ""){
        alert("비밀번호를 입력해주세요");
        return;
    }

    const member = {
        "memberNo" : memberNo,
        "memberPw" : newPw.value
    }


    fetch("/myPage/withdraw", {
        method : "DELETE",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(member)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result == 0){
            alert("비밀번호가 틀렸습니다.");
        }else{
            alert("언더 더 씨 탈퇴 완료");
            location.href = "/";
        }

    });
    

});
