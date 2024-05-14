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


if(payment != null){
    payment.addEventListener("click", () => {
        alert("준비중입니다...");
    });
}









document.addEventListener("DOMContentLoaded", function() {

    refreshCart();
    total();



});


const total = () => {

    const checkBoxs2 = document.querySelectorAll(".checkBox");
    const counts2 = document.querySelectorAll(".count");
    const seedPrices2 = document.querySelectorAll(".seedPrice");
    console.log(checkBoxs2);

    let payMoney2 = 0;

    for(let i = 0; i < checkBoxs2.length; i++){
    
        if(checkBoxs2[i].checked){
            var priceString = seedPrices2[i].innerText;
            var priceInt = parseInt(priceString.replace(/[^\d]/g, ""));
    
            payMoney2 += priceInt * parseInt(counts2[i].innerText);
        }
    
    }

   
    
    document.getElementById('money').innerText = payMoney2.toLocaleString() + "원";


}


// checkBoxs.forEach((seed, index) => {
//     seed.addEventListener("click", () => {
//         let payMoney = 0;
//         for(let i = 0; i < checkBoxs.length; i++){
//             if(checkBoxs[i].checked){
//                 var priceString = document.querySelectorAll('.seedPrice')[i].innerText;
//                 // String으로 저장된 1,000원 -> 1000 숫자로 바꿈
//                 var priceInt = parseInt(priceString.replace(/[^\d]/g, ""));
//                 payMoney += priceInt * parseInt(document.querySelectorAll('.count')[i].innerText);
//             }
//         }
//         // 1000 -> 1,000원 String 으로 바꿈
//         document.getElementById('money').innerText = payMoney.toLocaleString() + "원";
//     });
// });








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

                // 장바구니를 삭제하는 기능 추가
                const crossBtns = document.querySelectorAll('.cross');
                crossBtns.forEach((cross, index) => {
                    cross.addEventListener("click", () => {
                        const answer = confirm("씨앗을 장바구니에서 삭제하시겠습니까?");
                        if (answer) {
                            deleteCart(memberNo, cartList[index].seedName);
                        }
                    });
                });

                // 체크박스에 체크가 되어 있으면 결제 금액에 가격 x 수량 추가
                const checkBoxs = document.querySelectorAll('.checkbox');
                checkBoxs.forEach((seed, index) => {
                    seed.addEventListener("click", () => {
                        let payMoney = 0;
                        for(let i = 0; i < checkBoxs.length; i++){
                            if(checkBoxs[i].checked){
                                var priceString = document.querySelectorAll('.seedPrice')[i].innerText;
                                // String으로 저장된 1,000원 -> 1000 숫자로 바꿈
                                var priceInt = parseInt(priceString.replace(/[^\d]/g, ""));
                                payMoney += priceInt * parseInt(document.querySelectorAll('.count')[i].innerText);
                            }
                        }
                        // 1000 -> 1,000원 String 으로 바꿈
                        document.getElementById('money').innerText = payMoney.toLocaleString() + "원";
                    });
                });

                // 플러스 버튼에 이벤트 리스너 추가
                const plusBtns = document.querySelectorAll('.plusBtn');
                plusBtns.forEach((plus, index) => {
                    plus.addEventListener("click", () => {
                        let payMoney = 0;
                        const counts = document.querySelectorAll('.count');
                        const seedPrices = document.querySelectorAll('.seedPrice');
                        const checkBoxs = document.querySelectorAll('.checkbox');
                        counts[index].innerText = parseInt(counts[index].innerText) + 1;
                        for(let i = 0; i < checkBoxs.length; i++){
                            if(checkBoxs[i].checked){
                                var priceString = seedPrices[i].innerText;
                                // String으로 저장된 1,000원 -> 1000 숫자로 바꿈
                                var priceInt = parseInt(priceString.replace(/[^\d]/g, ""));
                                payMoney += priceInt * parseInt(counts[i].innerText);
                            }
                        }
                        // 수량 증가하는 함수
                        seedUp(memberNo, cartList[index].seedName);
                        // 1000 -> 1,000원 String 으로 바꿈
                        document.getElementById('money').innerText = payMoney.toLocaleString() + "원";
                    });
                });

                // 마이너스 버튼에 이벤트 리스너 추가
                const minusBtns = document.querySelectorAll('.minusBtn');
                minusBtns.forEach((minus, index) => {
                    minus.addEventListener("click", () => {
                        let payMoney = 0;
                        const counts = document.querySelectorAll('.count');
                        const seedPrices = document.querySelectorAll('.seedPrice');
                        const checkBoxs = document.querySelectorAll('.checkbox');

                        // 현재 수량을 가져옴
                        let currentCount = parseInt(counts[index].innerText);

                        // 수량이 0이 되면 장바구니에서 제거
                        if(currentCount === 1){
                            const answer = confirm("씨앗을 장바구니에서 삭제하시겠습니까?");
                            if(answer){
                                deleteCart(memberNo, cartList[index].seedName);
                                return;
                            } else {
                                return;
                            }
                        }

                        // 수량 감소
                        currentCount--;

                        // 수량 업데이트
                        counts[index].innerText = currentCount;

                        for(let i = 0; i < checkBoxs.length; i++){
                            if(checkBoxs[i].checked){
                                var priceString = seedPrices[i].innerText;
                                // String으로 저장된 1,000원 -> 1000 숫자로 바꿈
                                var priceInt = parseInt(priceString.replace(/[^\d]/g, ""));
                                payMoney += priceInt * parseInt(counts[i].innerText);
                            } else {
                                continue;
                            }
                        }

                        // 수량 감소 함수
                        seedDown(memberNo, cartList[index].seedName);

                        // 1000 -> 1,000원 String 으로 바꿈
                        document.getElementById('money').innerText = payMoney.toLocaleString() + "원";
                    });
                });

                

                const seedNames = document.querySelectorAll(".seedName");
            
                let payMoney2 = 0;
            
                for(let i = 0; i < checkBoxs.length; i++){
            
                    if(checkBoxs[i].checked){
                        var priceString = seedPrices[i].innerText;
                        var priceInt = parseInt(priceString.replace(/[^\d]/g, ""));
            
                        payMoney2 += priceInt * parseInt(counts[i].innerText);
                        
                        console.log(seedNames[i].innerText);
                        console.log(counts[i].innerText);
                    }
            
                }
            
                document.getElementById('money').innerText = payMoney2.toLocaleString() + "원";


            }



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




