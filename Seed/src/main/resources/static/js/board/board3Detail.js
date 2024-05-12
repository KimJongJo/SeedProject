let currentUrl = window.location.href;

let currentQuery = window.location.search;

let currentPage = currentQuery.slice(1);

const goToBeforeBtn = document.getElementById('goToBeforeBtn');
const goToAfterBtn = document.getElementById('goToAfterBtn');

// 이전글
const beforeUrl =  `/board/${boardCode}/${beforePage}?${currentPage}`;

if(goToBeforeBtn != null) {
    
    goToBeforeBtn.addEventListener("click", () => {
        
        location.href = beforeUrl;
    });
}

// 다음글
const afterUrl =  `/board/${boardCode}/${afterPage}?${currentPage}`;

if(goToAfterBtn != null) {
    
    goToAfterBtn.addEventListener("click", () => {
        
        location.href = afterUrl;
    });
}

// 목록으로
const goToListBtn = document.getElementById('goToListBtn');

goToListBtn.addEventListener("click", () => {

    location.href=`/board/3?`+ currentPage;
});

const boardLike = document.getElementById('boardLike');


/* 좋아요 버튼 클릭 시 */
boardLike.addEventListener("click", e => {

    if(loginMemberNo == null) {
        alert('로그인 후 이용해 주세요.');
        return;
    }

    const obj = {
        "memberNo" : loginMemberNo,
        "boardNo" : boardNo,
        "likeCheck" : likeCheck
    };

    fetch("/board/3/like", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(count => {

        if(count == -1) {
            console.log("좋아요 실패")
            return;
        }

        likeCheck = likeCheck == 0 ? 1 : 0;

        e.target.classList.toggle("fa-regular");
        e.target.classList.toggle("fa-solid");

        e.target.nextElementSibling.innerText = count;

    });
});



// 게시글 수정
const updateBtn = document.getElementById('updateBtn');

if(updateBtn != null) {

    updateBtn.addEventListener("click", () => {

        location.href = `/board/${boardCode}/${boardNo}/update?`+currentPage;
    });
}




// 게시글 삭제
const deleteBtn = document.getElementById('deleteBtn');

if(deleteBtn != null) {

    deleteBtn.addEventListener("click", () => {

        const deleteConfirm = confirm("정말 삭제하시겠습니까?");

        if(!deleteConfirm) {
            
            alert('삭제 취소');
            
            return;
        } else {

            fetch("/board/3/delete", {
                method : "DELETE",
                headers : {"Content-Type" : "application/json"},
                body : boardNo
            })
            .then(resp => resp.text())
            .then(result => {
                if(result == 0) {
                    alert('삭제 실패하였습니다.')
                }
                
                alert('게시글이 삭제되었습니다.')
                
            })
        }

        // 현재 게시판의 목록으로 이동
        location.href=`/board/3?`+ currentPage;

    });
}





/* ***** 댓글 목록 조회(ajax) ***** */
const selectCommentList = () => {

    // [GET]
    // fetch(주소?쿼리스트링) 

    // [POST, PUT, DELETE]
    // fetch(주소, {method : "", header : {}, body : ""})

    // response.json() 
    // - 응답 받은 JSON 데이터 -> JS 객체로 변환

    fetch("/board/3/comment?boardNo=" + boardNo) // GET 방식 요청
    .then(response => response.json())
    .then(commentList => {
        console.log(commentList);

      // 화면에 존재하는 기존 댓글 목록 삭제 후
      // 조회된 commentList를 이용해서 새로운 댓글 목록 출력

      // ul태그(댓글 목록 감싸는 요소)
        const ul = document.querySelector("#commentList");
        ul.innerHTML = ""; // 기존 댓글 목록 삭제


         /* ******* 조회된 commentList를 이용해 댓글 출력 ******* */
        for(let comment of commentList){

            // 행(li) 생성 + 클래스 추가
            const commentRow = document.createElement("li");
            commentRow.classList.add("comment-row");

            // 대댓글(자식 댓글)인 경우 "child-comment" 클래스 추가
            if(comment.parentCommentNo != 0) 
            commentRow.classList.add("child-comment");

            // 만약 삭제된 댓글이지만 자식 댓글이 존재하는 경우
            if(comment.commentDelFl == 'Y') 
            commentRow.innerText = "삭제된 댓글 입니다";

            else{ // 삭제되지 않은 댓글
    
            // 프로필 이미지, 닉네임, 날짜 감싸는 요소
            const commentWriter = document.createElement("p");
            commentWriter.classList.add("comment-writer");

            // 프로필 이미지
            //   const profileImg = document.createElement("img");
    
            //   if(comment.profileImg == null)  
            //     profileImg.src = userDefaultImage; // 기본 이미지
            //   else                            
            //     profileImg.src = comment.profileImg; // 회원 이미지

            // 닉네임
            const nickname = document.createElement("span");
            nickname.innerText = comment.memberNickname;

            // 날짜(작성일)
            const commentDate = document.createElement("span");
            commentDate.classList.add("comment-date");
            commentDate.innerText = comment.commentWriteDate;

            // 작성자 영역(commentWriter)에 프로필, 닉네임, 날짜 추가
            commentWriter.append(nickname, commentDate);
        
            // 댓글 행에 작성자 영역 추가
            commentRow.append(commentWriter);

            // ----------------------------------------------------   
    
            // 댓글 내용 
            const content = document.createElement("p");
            content.classList.add("comment-content");
            content.innerText = comment.commentContent;
    
            commentRow.append(content); // 행에 내용 추가

            // ----------------------------------------------------
    
            // 버튼 영역
            const commentBtnArea = document.createElement("div");
            commentBtnArea.classList.add("comment-btn-area");


             // 답글 버튼
            const childCommentBtn = document.createElement("button");
            childCommentBtn.innerText = "답글";

            // 답글 버튼에 onclick 이벤트 리스너 추가 
            childCommentBtn.setAttribute("onclick", 
            `showInsertComment(${comment.commentNo}, this)`);     
            
            // 버튼 영역에 답글 추가
            commentBtnArea.append(childCommentBtn);


            // 로그인한 회원 번호가 댓글 작성자 번호와 같을 때
            // 댓글 수정/삭제 버튼 출력
    
            if(loginMemberNo != null && loginMemberNo == comment.memberNo){

                // 수정 버튼
                const updateBtn = document.createElement("button");
                updateBtn.innerText = "수정";
    
                // 수정 버튼에 onclick 이벤트 리스너 추가 
                updateBtn.setAttribute("onclick", 
                `showUpdateComment(${comment.commentNo}, this)`); 


                // 삭제 버튼
                const deleteBtn = document.createElement("button");
                deleteBtn.innerText = "삭제";
    
                // 삭제 버튼에 onclick 이벤트 리스너 추가 
                deleteBtn.setAttribute("onclick", 
                `deleteComment(${comment.commentNo})`); 
    
    
                // 버튼 영역에 수정, 삭제 버튼 추가
                commentBtnArea.append(updateBtn, deleteBtn);
            }
    
            // 행에 버튼 영역 추가
            commentRow.append(commentBtnArea);

        } // else 끝
    
        // 댓글 목록(ul)에 행(li) 추가
        ul.append(commentRow);

      } // for 끝

    });
}

// -----------------------------------------------------------------------

/* ***** 댓글 등록(ajax) ***** */

const addCommentBtn = document.querySelector("#addCommentBtn"); // button
const commentContent = document.querySelector("#commentContent"); // textarea

// 댓글 등록 버튼 클릭 시
addCommentBtn.addEventListener("click", e => {


    // 미로그인 시 readonly 조치 해놓음
    //   // 로그인이 되어있지 않은 경우
    //   if(loginMemberNo == null){
    //     alert("로그인 후 이용해 주세요");
    //     return; // early return;
    //   }

  // 댓글 내용이 작성되지 않은 경우
    if(commentContent.value.trim().length == 0){
        alert("내용 작성 후 등록 버튼을 클릭해 주세요");
        commentContent.focus();
        return;
    }


     // ajax를 이용해 댓글 등록 요청
    const data = {
        "commentContent" : commentContent.value,
        "boardNo"        : boardNo,
        "memberNo"       : loginMemberNo  // 또는 Session 회원 번호 이용도 가능
    };

    fetch("/board/3/comment", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(data) // data 객체를 JSON 문자열로 변환
    })

    .then(response => response.text())
    .then(result => {

        if(result > 0){
        alert("댓글이 등록 되었습니다");
        commentContent.value = ""; // 작성한 댓글 내용 지우기
        selectCommentList(); // 댓글 목록을 다시 조회해서 화면에 출력
    
        } else{
        alert("댓글 등록 실패");
        }

    })
    .catch(err => console.log(err));
})











selectCommentList();