/* 버튼 클릭 시 팝업 처리 */

// 이전 ...
const pageMoveBtn1 = document.getElementById("pageMoveBtn1");
const pagePopup1 = document.getElementById("pagePopup1");

// 다음...
const pageMoveBtn2 = document.getElementById("pageMoveBtn2");
const pagePopup2 = document.getElementById("pagePopup2");

// 페이지 버튼
const inputPageBtn1 = document.getElementById('inputPageBtn1');
const inputPageBtn2 = document.getElementById('inputPageBtn2');

// 페이지 입력창
const inputPage1 = document.getElementById('inputPage1');
const inputPage2 = document.getElementById('inputPage2');

const pageText1 = document.getElementById('pageText1');

// 이전 ... 클릭 시
if(pageMoveBtn1 != null) {
    pageMoveBtn1.addEventListener("click", () => {

        if(!pagePopup1.classList.contains('popup-hidden1')) {
            pagePopup1.classList.add('popup-hidden1');
        } else {
            pagePopup1.classList.remove('popup-hidden1');
        }
    
        // 다음 ... 열려있으면 없애기
        if(!pagePopup2.classList.contains('popup-hidden2')) {
            pagePopup2.classList.add('popup-hidden2');
        }
        
        // // 클릭 시 포커싱
        inputPage1.focus();
        inputPage1.value = 1;

        // 최대 페이지까지 입력 가능하게 하기
        inputPage1.setAttribute('max', lastPage);

        inputPage1.addEventListener("input", e => {
            
            if(e.target.value > lastPage) {
                alert('최대 페이지까지 입력 가능합니다.')
                e.target.value = "";
            }
        });
    });
}


// 다음 ... 클릭 시
if(pageMoveBtn2 != null) {
    pageMoveBtn2.addEventListener("click", () => {

        if(!pagePopup2.classList.contains('popup-hidden2')) {
            pagePopup2.classList.add('popup-hidden2');
        } else {
            pagePopup2.classList.remove('popup-hidden2');
        }
    
        // 이전 ... 열려있으면 없애기
        if(!pagePopup1.classList.contains('popup-hidden1')) {
            pagePopup1.classList.add('popup-hidden1');
        }

        // 클릭 시 포커싱
        inputPage2.focus();
        inputPage2.value = 1;

        // 최대 페이지까지 입력 가능하게 하기
        inputPage2.setAttribute('max', lastPage);

        inputPage2.addEventListener("input", e => {
            
            if(e.target.value > lastPage) {
                alert('최대 페이지까지 입력 가능합니다.')
                e.target.value = "";
            }
        });
    
    });
    
}


// 이전... 페이지 버튼 클릭 시
if(inputPageBtn1 != null) {
    inputPageBtn1.addEventListener("click", () => {

        const pageNumber = parseInt(inputPage1.value);
        
        if(!isNaN(pageNumber)) {
            if(pageNumber > 0) {
                location.href = `/board/${boardCode}?cp=` + pageNumber;
            } 
        } else {
            alert('페이지 번호를 숫자로만 올바르게 입력하세요.')
            inputPage1.value = "";
        }

    })
}

// 다음 ... 페이지 버튼 클릭 시 
if(inputPageBtn2 != null) {
    inputPageBtn2.addEventListener("click", () => {

        const pageNumber = parseInt(inputPage2.value);
        
        if(!isNaN(pageNumber)) {
            if(pageNumber > 0) {
                location.href = `/board/${boardCode}?cp=` + pageNumber;
            } 
        } else {
            alert('페이지 번호를 숫자로만 올바르게 입력하세요.')
            inputPage2.value = "";
        }

    })
}


/* 글쓰기 버튼 클릭 시 */
const writeBtn = document.getElementById('writeBtn');

if(writeBtn != null) {
    writeBtn.addEventListener("click", () => {

        location.href = `/Board/${boardCode}/write`;


    });


}

