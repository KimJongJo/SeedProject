// console.log("sdsdseaaaaaa");

// boardUpdate
const updateBtn = document.getElementById("write-update-btn");  // 글 수정 버튼
const boardNo = document.getElementById("boardNo").value; // 글 번호



// board2
const option = document.getElementById("option");   // 검색 필터
const keyWord = document.getElementById("keyWord"); // 검색어
const search = document.getElementById("search-btn");   // 검색버튼


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

