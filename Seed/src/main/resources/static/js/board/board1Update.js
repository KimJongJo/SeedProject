// 게시글 수정
const updateBtn = document.getElementById("updateBtn"); // 수정하기 버튼
const boardNo = document.getElementById("boardNo"); // 게시글 번호
const boardTitle = document.querySelector(".board-title"); // 게시글 제목
const boardContent = document.querySelector(".board-content"); //게시글 내용

updateBtn.addEventListener("click", (e) => {

    const board = {
        "boardNo" : boardNo.value,
        "boardTitle" : boardTitle.value,
        "boardContent" : boardContent.value
    }

    fetch("/board/1/board1Update", {
        method : "PUT",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(board)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result > 0) {
            alert("수정되었습니다.");
            location.href="/board/1/" + boardNo.value;
        } else {
            alert("수정 실패");
        }
    })

})



// 게시글 삭제
const deleteBtn = document.getElementById("deleteBtn");
const boardNo1 = document.getElementById("boardNo1");

deleteBtn.addEventListener("click", () => {


    const answer = confirm("게시글을 삭제하시겠습니까?");
    
    
    if(!answer) {
        alert("취소되었습니다.");
        return;
    } else {

        fetch("/board/1/board1Delete", {
            method : "DELETE",
            headers : {"Content-Type" : "application/json"},
            body : boardNo1.value
        })
        .then(resp => resp.text())
        .then(result => {
            if(result > 0) {
                alert("삭제되었습니다.");
                location.href="/board/1";
            } else {
                alert("삭제 실패");
            }
        })

    }


})