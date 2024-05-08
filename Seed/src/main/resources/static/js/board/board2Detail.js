// console.log("sdsdse");

// boardDetail
const boardDelete = document.getElementById("boardDelete"); // 삭제 버튼

// boardWrite
const pushBtn = document.getElementById("write-push-btn");  // 글쓰기 버튼
const boardTitle = document.getElementById("boardTitle");   // 글 제목
const boardContent = document.getElementById("boardContent");   // 글 내용


// boardUpdate
const updateBtn = document.getElementById("write-update-btn");  // 글 수정 버튼


// 게시글 삭제
if(boardDelete != null){
    boardDelete.addEventListener("click", () => {

        const answer = confirm("정말 삭제하시겠습니까?");
    
        if(!answer){
            alert("삭제 취소");
            return;
        }else{
            fetch("/board/2/delete",{
                method : "DELETE",
                headers : {"Content-Type" : "application/json"},
                body : boardNo
            })
            .then(resp => resp.text())
            .then(result => {
                if(result == 0){
                    console.log("삭제 오류");
                }
            });
    
            alert("삭제 되었습니다.");
    
        }
    
        // 게시글 목록으로 이동
        location.href = "/board/2";
    
       
    });
}







// 게시글 작성
if(pushBtn != null){

    pushBtn.addEventListener("click", (e) => {

        if(boardTitle.value == "" || boardContent.value == ""){
            console.log(memberNo);
            alert("제목과 내용을 입력해주세요");
            e.preventDefault();
            return;
        }


        const board = {
            "memberNo" : memberNo,
            "boardTitle" : boardTitle.value,
            "boardContent" : boardContent.value
        }

        fetch("/board/2/board2Write", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(board)
        })
        .then(resp => resp.text())
        .then(result => {
            if(result == 0){
                console.log("게시글 작성 실패");
            }else{
                alert("게시글 작성 완료");
                location.href = "/board/2";
            }
        });
    
    });

}



// 게시글 수정
if(updateBtn != null){
    updateBtn.addEventListener("click", () => {

        const board = {
            "boardNo" : boardNo,
            "boardTitle" : boardTitle.value,
            "boardContent" : boardContent.value
        }


        fetch("/board/2/board2Update", {
            method : "PUT",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(board)
        })
        .then(resp => resp.text())
        .then(result => {
            if(result > 0){
                alert("수정되었습니다.");
                location.href = "/board/2/detail?boardNo=" + boardNo;
            }else{
                alert("수정 실패...");
            }
        })

    })
}