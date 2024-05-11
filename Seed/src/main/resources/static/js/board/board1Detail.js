// 게시글 수정
const updateBtn = document.getElementById("updateBtn"); // 수정하기 버튼
const boardNo2 = document.getElementById("boardNo"); // 게시글 번호
const boardTitle = document.querySelector(".board-title"); // 게시글 제목
const boardContent = document.querySelector(".board-content"); //게시글 내용

updateBtn.addEventListener("click", () => {

    const board = {
        "boardNo" : boardNo2.value,
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
            location.href="/board/1/" + boardNo2.value;
        } else {
            alert("수정 실패");
        }
    })

});



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

});




// 댓글--------------------------------------------------


// 댓글 조회
const commentList = () => {


    fetch("/board/1/comment?boardNo=" + boardNo)
    .then(resp => resp.json())
    .then(commentList => {
        console.log(commentList);
        
        const ul = document.querySelector("#commentList");
        ul.innerHTML = ""; // 기존 댓글 삭제

        for(let comment of commentList) {

            // <li> 생성 + 클래스 추가
            const commentRow = document.createElement("li");
            commentRow.classList.add("comment-row");

            // 자식댓글이면 클래스 추가
            if(comment.parentCommentNo != 0)
                commentRow.classList.add("child-comment");

            // 삭제된 댓글인데 자식 댓글이 있을 때
            if(comment.commentDelFl == 'Y')
                commentRow.innerText = "삭제된 댓글입니다";

            // 삭제되지 않은 댓글
            else{

                // 닉네임, 날짜를 감싸는 태그
                const commentWriter = document.createElement("p");
                commentWriter.classList.add("comment-writer");

                // 닉네임
                const nickname = document.createElement("span");
                nickname.innerText = comment.memberNickname;

                // 작성일
                const commentDate = document.createElement("span");
                commentDate.classList.add("comment-date");
                commentDate.innerText = comment.commentWriteDate;

                commentWriter.append(nickname, commentDate);

                // 댓글 행에 작성자 역역 추가
                commentRow.append(commentWriter);

                
                // 댓글 내용
                const content = document.createElement("p");
                content.classList.add("comment-content");
                content.innerText = comment.commentContent;
                commentRow.append(content);
                
                
                // 버튼역역
                const commentBtnArea = document.createElement("div");
                commentBtnArea.classList.add("comment-btn-area");
                
                // 답글 버튼
                const childCommentBtn = document.createElement("button");
                childCommentBtn.innerText = "답글";
                
                childCommentBtn.setAttribute("onclick",
                `showInsertComment(${comment.commentNo}, this)`);

                commentBtnArea.append(childCommentBtn);
                

                if(loginMemberNo != null && loginMemberNo == comment.memberNo) {
                    
                    // 수정버튼
                    const updateBtn = document.createElement("button");
                    updateBtn.innerText = "수정";
                    
                    updateBtn.setAttribute("onclick",
                    `showUpdateComment(${comment.commentNo}, this)`);
                    
                    // 삭제 버튼
                    const deleteBtn = document.createElement("button");
                    deleteBtn.innerText = "삭제";
                    
                    deleteBtn.setAttribute("onclick",
                    `deleteComment(${comment.commentNo})`);

                    commentBtnArea.append(updateBtn, deleteBtn);
                
                }
            
                commentRow.append(commentBtnArea);
            
            }
        
            ul.append(commentRow);
        }

    });
}
commentList();


const addComment = document.querySelector("#addComment");
const commentContent = document.querySelector("#commentContent");

addComment.addEventListener("click", e => {
    console.log(loginMemberNo);
    // 로그인 안했을 때
    if(loginMemberNo == null){
        alert("로그인 후 이용해 주세요");
        return;
    }

    // 아무내용 안썻을 때
    if(commentContent.value.trim().length == 0) {
        alert("내용 입력후 버튼을 눌러주세요");
        commentContent.focus();
        return;
    }

    const data = {
        "commentContent" : commentContent.value,
        "boardNo"        : boardNo,
        "memberNo"       : loginMemberNo
    };

    fetch("/board/1/comment", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(data)
    })
    .then(resp => resp.text())
    .then(result => {
        console.log(result);
        if(result > 0) {
            alert("댓글 등록 완료");
            commentContent.value = "";
            commentList();
        } else {
            alert("댓글 등록 실패");
        }

    })
    .catch(err => console.log(err));

})