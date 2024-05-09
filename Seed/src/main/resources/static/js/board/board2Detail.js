// console.log("sdsdse");

// boardDetail
const boardDelete = document.getElementById("boardDelete"); // 게시글 삭제 버튼
const comment = document.getElementById("comment-content"); // 댓글 내용
const commentBtn = document.getElementById("comment-plus"); // 댓글 등록 버튼
const commentUpdate = document.getElementById("commentUpdate"); // 댓글 수정
const commentDelete = document.querySelectorAll(".commentDelete"); // 댓글 삭제
const commentNo = document.getElementById("commentNo"); // 댓글 번호

// boardWrite
const pushBtn = document.getElementById("write-push-btn");  // 글쓰기 버튼
const boardTitle = document.getElementById("boardTitle");   // 글 제목
const boardContent = document.getElementById("boardContent");   // 글 내용



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


// 댓글 목록 불러오는 함수
const commentList = () => {

    fetch("/board/2/commentList2?boardNo=" + boardNo)
    .then(resp => resp.json())
    .then(commentList => {
        let html = '';
        commentList.forEach(comment => {
            html += `
                <div class="comment">
                    <div class="comment-top">
                        <div class="memberNickname">${comment.memberNickname}</div>
                        <div class="commentWriteDate">${comment.commentWriteDate}</div>
                        ${sessionMemberNo == comment.memberNo ? `
                            <div class="UorD">
                                <button class="update" data-commentNo="${comment.commentNo}">수정</button>
                                <button class="delete" data-commentNo="${comment.commentNo}">삭제</button>
                            </div>
                        ` : ''}
                    </div>
                    <div class="message">
                        <div class="comment-box">${comment.commentContent}</div>
                    </div>
                </div>
            `;
        });
        // 생성된 HTML을 commentContainer에 추가
        document.getElementById('commentContainer').innerHTML = html;

        // 수정 버튼 클릭시 함수 만들어주기
        document.querySelectorAll(".update").forEach(btn => {
            btn.addEventListener("click", function() {
                const commentNo = this.getAttribute("data-commentNo");
                updateComment(commentNo);
            });
        })

        // 삭제 버튼 클릭시 함수 만들어주기
        document.querySelectorAll(".delete").forEach(btn => {
            btn.addEventListener('click', function() {
                const commentNo = this.getAttribute("data-commentNo");
                deleteComment(commentNo);
            });
        });
    });


}



// 댓글 작성
if(commentBtn != null){
    commentBtn.addEventListener("click", (e) => {

        if(comment.value == ""){
            alert("내용을 입력해주세요");
            e.preventDefault();
            return;
        }
    
        const obj = {
            "boardNo" : boardNo,
            "memberNo" : sessionMemberNo,
            "comment" : comment.value
        }
    
        fetch("/board/2/comment", {
            method : "POST",
            headers : {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        })
        .then(resp => resp.text())
        .then(result => {
            if(result == 0){
                console.log("댓글 등록 실패..");
                return;
            }
    
            alert("댓글이 등록 되었습니다.");
            comment.value = "";

            // 댓글 목록 다시 조회
            commentList();
            
    
        })
    
    
    });
    
}



// 수정 전 댓글


// 댓글 수정
const updateComment = commentNo => {

    const texts = document.querySelectorAll(".text");
    const textareas = document.querySelectorAll(".textarea");
    const commentBox = document.querySelectorAll(".comment-box");
    const commentNos = document.querySelectorAll(".commentNo");

    let beforeDate = "";

    /* 수정이 열려 있는지 모두 확인 */
    for(let i = 0; i < texts.length; i++){

        /* 내가 클릭 한 것이 맞으면 if문 통과 */
        if(commentNos[i].value == commentNo){
           
            /* 클릭한 수정 버튼이 이미 열려 있을때 닫아주기 */
            if(texts[i].style.display == 'block'){
                texts[i].style.display = 'none';
                commentBox[i].style.display = 'block';

            /* 클릭한 수정 버튼이 닫혀 있을때 열어주기 */
            }else{
                texts[i].style.display = 'block';
                commentBox[i].style.display = 'none';
            }

        /* 아니면 닫기 수정 닫기 */
        }else{
            beforeDate = commentBox[i].innerText;
            textareas[i].innerText = beforeDate;
            texts[i].style.display = 'none';
            commentBox[i].style.display = 'block';
        }
    }

};






// 댓글 삭제
const deleteComment = commentNo => {
    
    const answer =  confirm("정말 삭제하시겠습니까?");

    if(!answer){
        alert("삭제 취소");
        return;
    }

    fetch("/board/2/commentDelete", {
        method : "DELETE",
        headers : {"Content-Type" : "application/json"},
        body : commentNo
    })
    .then(resp => resp.text())
    .then(result => {
        if(result == 0){
            console.log("삭제 오류..");
            return;
        }

        alert("댓글이 삭제 되었습니다.");

        // 댓글 목록 다시 조회
        commentList();
    })

};