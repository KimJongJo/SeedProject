// console.log("장바구니 연결");



// console.log("연결");



// 결제 하기 준비중...
const payment = document.getElementById("payment"); // 결제하기 버튼

if(payment != null){
    
    payment.addEventListener("click", () => {
        alert("준비중입니다...");
    });
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
            

            money.innerText = '0원';

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
            
        }else{
            console.log("수량 감소 실패..")
            console.log(result);
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
            
        }else{
            console.log("수량 추가 실패..")
        }
    })

}




// 체크된 씨앗만 계산하는 함수
const sumSeed = () => {

    const checkBoxs = document.querySelectorAll(".checkbox");
    const seedPrices = document.querySelectorAll(".seedPrice");
    const counts = document.querySelectorAll(".count"); 

    const money = document.getElementById("money");

    let payMoney = 0;

    for(let j = 0; j < checkBoxs.length; j++){
        if(checkBoxs[j].checked){
            var priceString = seedPrices[j].innerText;

            // String으로 저장된 1,000원 -> 1000 숫자로 바꿈
            var priceInt = parseInt(priceString.replace(/[^\d]/g, ""));

            payMoney += priceInt * parseInt(counts[j].innerText);
        }else{
            continue;
        }
    }

    money.innerText = payMoney.toLocaleString() + "원";

    return payMoney;

}




// 체크박스, 마이너스, 플러스, 삭제 기능이 들어가있는 함수
const allFunction = () => {

    

    const checkBoxs = document.querySelectorAll(".checkbox");    // 체크 박스
    const seedNames =document.querySelectorAll(".seedName");    // 씨앗 이름
    const counts = document.querySelectorAll(".count");      // 씨앗 수량

    const minusBtns = document.querySelectorAll(".minusBtn");   // 마이너스 버튼
    const plusBtns = document.querySelectorAll(".plusBtn");     // 플러스 버튼
    const crossBtns = document.querySelectorAll(".cross");  // 삭제 버튼



    

    for(let i = 0; i < checkBoxs.length; i++){
        // 체크박스를 누르면 체크된 것만 추가
        checkBoxs[i].addEventListener("click", () => {

            sumSeed();

        });


        // 삭제 버튼
        crossBtns[i].addEventListener("click", () => {
            const answer = confirm("씨앗을 장바구니에서 삭제하시겠습니까?");

            if(answer){
                deleteCart(memberNo, seedNames[i].innerText);

                return;
            }

        });


        // 플러스 버튼
        plusBtns[i].addEventListener("click", () => {

            counts[i].innerText = parseInt(counts[i].innerText) + 1;

            sumSeed();

            // 수량 증가 함수
            seedUp(memberNo, seedNames[i].innerText);

        });


        
        // 마이너스 버튼
        minusBtns[i].addEventListener("click", () => {

            if(parseInt(counts[i].innerText) == 1){
                const answer = confirm("씨앗을 장바구니에서 삭제하시겠습니까?");

                if(answer){

                    deleteCart(memberNo, seedNames[i].innerText);
                }
                return;
            }

            counts[i].innerText = parseInt(counts[i].innerText) - 1;

            sumSeed();

            // 수량 감소 함수
            seedDown(memberNo, seedNames[i].innerText);

        });
        
    }

}





// 장바구니 새로 고침 함수
function refreshCart() {
    fetch("/cart/basket?memberNo=" + memberNo) // 서버로부터 장바구니 목록을 가져오는 요청
        .then(response => response.json()) // JSON 형태로 응답을 받음
        .then(cartList => {

            let cartHTML = '';
            // 장바구니가 비어있을때
            
            if(cartList.length == 0){
                cartHTML = `

                        <div class="basket2">
                            <i class="fa-solid fa-basket-shopping"></i>
                            <span>장바구니가 비었습니다</span>

                            <div class="container2">
                                <a href="/" class="button btnPush btnBlueGreen" id="cart"><i class="fa-solid fa-cart-shopping"></i> 장보기 </a>
                            </div>
                        </div>
  
                `;

                document.querySelector(".buy").style.display = 'none';

                // 새로운 장바구니 HTML을 #cartList 요소에 삽입
                document.getElementById('basket').innerHTML = cartHTML;
            }


            // 새로운 장바구니 HTML 생성

            // 장바구니가 비어있지 않을때
            else{

                
                cartList.forEach(cart => {
                    cartHTML += `

                        <div class="cartList"> 
                            <div class="allBox">
                                <div class="checkboxDiv">
                                    <input type="checkbox" class="checkbox" checked>
                                </div>
                                <div class="imgDiv">
                                    <img src="${cart.seedImgPath}" class="seedImg">
                                </div>
                                <div class="seedinfo">
                                    <div class="seedleft">
                                        <span class="seedName">${cart.seedName}</span>
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
                        </div>
                    `;
                });
                

                
                // 새로운 장바구니 HTML을 #cartList 요소에 삽입
                document.getElementById('basket').innerHTML = cartHTML;

                allFunction();

                sumSeed();

            }

        })
        
}







// 체크박스, 마이너스, 플러스, 삭제 기능이 들어간 함수
allFunction();




document.addEventListener("load", () => {
    sumSeed();
});


// 새로운 항목을 체크할 때 로컬 스토리지에 저장
document.querySelectorAll('.checkbox').forEach((checkbox, index) => {
    checkbox.addEventListener('change', () => {
        localStorage.setItem(`checkbox_${index}`, checkbox.checked);
    });

    // 로컬 스토리지에서 체크 여부를 가져와 적용
    const isChecked = localStorage.getItem(`checkbox_${index}`);
    if (isChecked === 'true') {
        checkbox.checked = true;
    } else {
        checkbox.checked = false;
    }
});



document.addEventListener("DOMContentLoaded", function() {

    refreshCart();
    sumSeed();

});