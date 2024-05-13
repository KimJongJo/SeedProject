// console.log("상품 상세");


const minus = document.getElementById("minus"); // 마이너스 버튼
const plus = document.getElementById("plus");   // 플러스 버튼
const count = document.getElementById("count"); // 수량

const add = document.getElementById("add"); // 담기 버튼
const buy = document.getElementById("buy"); // 구매 버튼


// minus 버튼을 눌렀을때 수량 -1
minus.addEventListener("click", () => {
    if(parseInt(count.innerText) == 1){
        alert("1개 이상의 씨앗을 담아주세요");
        return;
    }

    count.innerText = parseInt(count.innerText) - 1;


    total();

});

// plus 버튼을 눌렀을때 수량 +1
plus.addEventListener("click", () => {
    
    count.innerText = parseInt(count.innerText) + 1;

    total();

});


const total = () => {

    let payMoney = 0;
    var count = parseInt(document.getElementById("count").innerText); // 개수
    var seedPriceInt = parseInt(seedPrice.replace(/[^\d]/g, "")); // 씨앗 가격

    payMoney = count * seedPriceInt;

    document.getElementById("total").innerText = payMoney.toLocaleString() + "원";

}




buy.addEventListener("click", () => {
    alert("준비중입니다...");
});



// 장바구니에 추가하는 기능
add.addEventListener("click", () => {

    if(memberNo == null){
        alert("로그인 후 이용해주세요");
        location.href = "/member/login";
        return;
    }

    const obj = {
        "memberNo" : memberNo,
        "seedNo" : seedNo,
        "count" : parseInt(count.innerText)
    }
    
    fetch("/cart/seedAdd",{
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result > 0){
            alert("장바구니에 추가 했습니다!");
            confirm("장바구니 페이지로 이동하시겠습니까?") ? location.href="/myPage/basket" : alert("쇼핑을 계속합니다...");
        }else{
            console.log("장바구니 추가 실패...");
        }
    
    })

});