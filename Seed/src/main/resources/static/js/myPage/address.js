function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

           

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postCode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

const addPopup = document.querySelector("#addPopup");
// const postCode = document.querySelector("#postCode");
// const address = document.querySelector("#address");
// const detailAddress = document.querySelector("#detailAddress");
const popupClose = document.querySelector("#popupClose");

// const addAddress = (url) => {

//     fetch(url)
//     .then(resp => resp.json())
//     .then(result => {


//     })

// }


// 주소지 추가 버튼
const newAddressBtn = document.querySelector(".newAddressBtn");

// 주소지 추가 버튼을 누를 때
newAddressBtn.addEventListener("click", e => {

    // 저장된 주소가 3개 일때
    fetch("/myPage/addressCount", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberNo
    })
    .then(resp => resp.text())
    .then(result => {
        if(result >= 3) {
            addPopup.classList.add("popup-hidden");
            alert("주소는 최대 3개까지 저장됩니다\n추가를 원하시면 기존 주소 하나를 삭제해주세요");
        }else{
            // 모달창 보이게 하기
            addPopup.classList.remove("popup-hidden");
        }
    });
    


})

// 주소지 추가 모달창에 있는 X 버튼 클릭시 모달창 안보임
popupClose.addEventListener("click", () => {
    addPopup.classList.add("popup-hidden");
})

// 모달창에서 주소지 추가하는 버튼
const addBtn = document.querySelector("#addBtn");
// const memberAddress = document.querySelectorAll("[name='memberAddress']");
const add0 = document.querySelector("#postCode"); // 우편번호
const add1 = document.querySelector("#address"); // 주소
const add2 = document.querySelector("#detailAddress"); // 상세주소


addBtn.addEventListener("click", e => {
    
    

    console.log("주소 개수 확인 완료");

    const obj = {
        "memberNo" : memberNo,
        "postCode" : add0.value,
        "address"  : add1.value,
        "detailAddress" : add2.value
    };


    fetch("/myPage/address", {
        method  : "PUT",
        headers : {"Content-Type" : "application/json"},
        body    : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {

        if(result == -1) {
            console.log("주소 추가 실패");
            alert("주소 추가 실패");
            return;
        }

    })

    console.log("주소 추가 끝");

    alert("주소 추가 성공");
    location.href = "/myPage/address";

    
})






