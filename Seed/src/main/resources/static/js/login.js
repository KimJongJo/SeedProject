// console.log("login.js loaded.");

const memberId = document.querySelector("#memberId");   // 아이디
const memberPw = document.querySelector("#memberPw");   // 비밀번호
const loginBtn = document.querySelector("#loginBtn");   // 로그인 버튼

loginBtn.addEventListener("click", (e) => {
    if(memberId.value == ""){
        alert("아이디를 입력해주세요");
        e.preventDefault();
        return;
    }if(memberPw.value == ""){
        alert("비밀번호를 입력해주세요");
        e.preventDefault();
        return;
    }
});



// 쿠키 -------------------------------------

const getCookie = (key) => {

    const cookies = document.cookie; // "K=V; K=V"

    const cookieList = cookies.split("; ") // ["K=V", "K=V", "K=V"]
                    .map( el => el.split("="));

    const obj = {};

    for(let i=0; i < cookieList.length; i++) {
        const k = cookieList[i][0];
        const v = cookieList[i][1];
        obj[k] = v;
    }

    return obj[key];
    
}


const loginId = document.querySelector("#login-area input[name='memberId']");
const inputSaveId = document.querySelector("#login-area input[name='saveId']");


if(loginId != null) {

    const saveId = getCookie("saveId");

    if(saveId != undefined) {
        loginId.value = saveId;

        document.querySelector("input[name='saveId']").checked = true;

    }


}