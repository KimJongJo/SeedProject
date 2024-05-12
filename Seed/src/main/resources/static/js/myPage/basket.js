// console.log("장바구니 연결");


const payment = document.getElementById("payment"); // 결제하기 버튼
const checkBoxs = document.querySelectorAll(".checkbox");    // 체크 박스
const seedNames =document.querySelectorAll(".seedName");    // 씨앗 이름
const seedPrices = document.querySelectorAll(".seedPrice");  // 씨앗 가격
const counts = document.querySelectorAll(".count");      // 씨앗 수량

const minusBtns = document.querySelectorAll(".minusBtn");   // 마이너스 버튼
const plusBtns = document.querySelectorAll(".plusBtn");     // 플러스 버튼

const crossBtns = document.querySelectorAll(".cross");  // 삭제 버튼

const money = document.getElementById("money"); // 결제 금액

payment.addEventListener("click", () => {
    alert("준비중입니다...");
});








// 장바구니 새로 고침 함수
function refreshCart() {
    fetch("/cart/basket?memberNo=" + memberNo) // 서버로부터 장바구니 목록을 가져오는 요청
        .then(response => response.json()) // JSON 형태로 응답을 받음
        .then(cartList => {
            // 새로운 장바구니 HTML 생성
            let cartHTML = ''; // 새로운 장바구니 HTML을 담을 변수 초기화
            cartList.forEach(cart => {
                cartHTML += `
                    <div class="allBox">
                        <div class="checkboxDiv">
                            <input type="checkbox" class="checkbox">
                        </div>
                        <div class="imgDiv">
                            <img src="${cart.seedImgPath}" class="seedImg">
                        </div>
                        <div class="seedinfo">
                            <div class="seedleft">
                                <span class="seedName" data-seedName="${cart.seedName}">${cart.seedName}</span>
                                <span class="seedPrice">${cart.seedPrice}</span>
                            </div>
                            <div class="span">
                                <button class="minusBtn"><i class="fa-solid fa-minus"></i></button>
                                <span class="count">${cart.count}</span>
                                <button class="plusBtn"><i class="fa-solid fa-plus"></i></button>
                            </div>
                        </div>
                        <span class="cross">&times;</span>
                    </div>
                `;
            });

            
            // 새로운 장바구니 HTML을 #cartList 요소에 삽입
            document.getElementById('cartList').innerHTML = cartHTML;
            

        })
        .catch(error => console.error('Error refreshing cart:', error));
        
}








// 장바구니에서 삭제하는 함수
const deleteCart = (memberNo, seedName) => {

    const obj = {
        "memberNo" : memberNo,
        "seedName" : seedName
    }


    fetch("/cart/cartDelete", {
        method : "DELETE",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result > 0){
            console.log("삭제 완료");

            // 장바구니를 새로 고침 하는 함수
            refreshCart();
        }else{
            console.log("삭제 실패..");
        }
    })

}

// 수량 감소 하는 함수
const seedDown = (memberNo, seedName) => {

    const obj = {
        "memberNo" : memberNo,
        "seedName" : seedName
    }

    fetch("/cart/seedMinus", {
        method : "PUT",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result > 0){
            console.log("수량 감소")
        }else{
            console.log("수량 감소 실패..")
        }
    })

}


// 수량 증가 하는 함수
const seedUp = (memberNo, seedName) => {

    const obj = {
        "memberNo" : memberNo,
        "seedName" : seedName
    }

    fetch("/cart/seedPlus", {
        method : "PUT",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {
        if(result > 0){
            console.log("수량 추가")
        }else{
            console.log("수량 추가 실패..")
        }
    })

}



// 체크박스에 체크가 되어 있으면 결제 금액에 가격 x 수량 추가
checkBoxs.forEach(seed => {

    seed.addEventListener("click", () => {

        let payMoney = 0;

        for(let i = 0; i < checkBoxs.length; i++){

            if(checkBoxs[i].checked){
                var priceString = seedPrices[i].innerText;

                // String으로 저장된 1,000원 -> 1000 숫자로 바꿈
                var priceInt = parseInt(priceString.replace(/[^\d]/g, ""));

                payMoney += priceInt * parseInt(counts[i].innerText);

            }else{
                continue;
            }

        }

        // 1000 -> 1,000원 String 으로 바꿈
        money.innerText = payMoney.toLocaleString() + "원";

    });
})


// 마이너스 버튼
minusBtns.forEach((minus, index) => {

    minus.addEventListener("click", () => {

        let payMoney = 0;

        // 수량이 0이 되면 장바구니에서 제거
        if(parseInt(counts[index].innerText) == 1){
            const answer = confirm("씨앗을 장바구니에서 삭제하시겠습니까?");

            if(answer){
                
                deleteCart(memberNo, seedNames[index].innerText);
                
                return;

            }
            else{
                return;
            }
            
        }

        counts[index].innerText = parseInt(counts[index].innerText) - 1;


        for(let i = 0; i < checkBoxs.length; i++){

            if(checkBoxs[i].checked){
                var priceString = seedPrices[i].innerText;

                // String으로 저장된 1,000원 -> 1000 숫자로 바꿈
                var priceInt = parseInt(priceString.replace(/[^\d]/g, ""));

                payMoney += priceInt * parseInt(counts[i].innerText);

            }else{
                continue;
            }

        }

        // 수량 감소 함수
        seedDown(memberNo, seedNames[index].innerText);
        


        // 1000 -> 1,000원 String 으로 바꿈
        money.innerText = payMoney.toLocaleString() + "원";

    })

})

// 플러스 버튼
plusBtns.forEach((plus, index) => {

    plus.addEventListener("click", () => {

        let payMoney = 0;

        counts[index].innerText = parseInt(counts[index].innerText) + 1;

        for(let i = 0; i < checkBoxs.length; i++){

            if(checkBoxs[i].checked){
                var priceString = seedPrices[i].innerText;

                // String으로 저장된 1,000원 -> 1000 숫자로 바꿈
                var priceInt = parseInt(priceString.replace(/[^\d]/g, ""));

                payMoney += priceInt * parseInt(counts[i].innerText);

            }else{
                continue;
            }

        }

        // 수량 증가하는 함수
        seedUp(memberNo, seedNames[index].innerText)


        // 1000 -> 1,000원 String 으로 바꿈
        money.innerText = payMoney.toLocaleString() + "원";

    })

})


// 삭제 버튼
crossBtns.forEach((cross, index) => {

    cross.addEventListener("click", () => {
        const answer = confirm("씨앗을 장바구니에서 삭제하시겠습니까?");

        if(answer){
                
            deleteCart(memberNo, seedNames[index].innerText)

            return;

        }
        else{
            return;
        }
    })

})




