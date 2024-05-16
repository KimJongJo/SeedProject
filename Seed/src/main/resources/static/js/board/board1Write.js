// console.log("연결 확인");

const updateBtn = document.getElementById("updateBtn");
const title = document.getElementById("title");
const content = document.getElementById("content");


// 글쓰기 버튼을 누를때
updateBtn.addEventListener("click", (e) => {

    if(title.value == "" || content.value == ""){
        alert("제목과 내용을 입력해주세요");
        e.preventDefault();
        
    }

});