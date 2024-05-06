// console.log("비밀번호 변경 연결 확인");




const nowPw = document.querySelector("#nowPw");             // 현재 비밀번호
const newPw = document.querySelector("#newPw");       // 새 비밀번호
const newPw2 = document.querySelector("#newPw2");     // 새 비밀번호 확인


// form 태그의 정보가 submit 될 때 이벤트
document.getElementById("updatePw").addEventListener("submit", (e) => {

    if(nowPw.value == "" || newPw.value == "" || newPw2.value == ""){
        alert("모든 정보를 입력하신 후 변경하기를 눌러주세요");
        e.preventDefault();
        return;
    }

    if(newPw.value !== newPw2.value){
        alert("비밀번호가 일치하지 않습니다.");
        e.preventDefault();
        return;
    }


});
