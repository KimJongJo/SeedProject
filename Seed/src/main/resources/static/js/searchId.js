// console.log("연결 확인");

const memberEmail = document.querySelector("#memberEmail");
const memberNickname = document.querySelector("#memberNickname");
const findIdBtn = document.querySelector("#findIdBtn");

findIdBtn.addEventListener("click", () => {
    if(memberEmail.value == ""){
        alert("이메일을 입력해주세요");
        return;
    }

    if(memberNickname.value == ""){
        alert("닉네임을 입력해주세요");
        return;
    }

    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if(!regExp.test(memberEmail.value)){
        alert("이메일 형식으로 입력해주세요");
        return;
    }

    

    const member = {
        "memberEmail" : memberEmail.value,
        "memberNickname" : memberNickname.value
    }

    fetch("/member/findId", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(member)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 0){
            alert("회원 정보와 일치하는 아이디가 없습니다.");
            return;
        }

        alert("회원 아이디를 이메일로 전송했습니다. 이메일을 확인해주세요");

    });
});