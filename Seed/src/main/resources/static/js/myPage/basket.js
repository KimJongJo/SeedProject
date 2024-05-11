// console.log("장바구니 연결");


const payment = document.getElementById("payment"); // 결제하기 버튼
const checkBoxs = document.querySelectorAll(".checkbox");    // 체크 박스
const seedPrices = document.querySelectorAll(".seedPrice");  // 씨앗 가격
const counts = document.querySelectorAll(".count");      // 씨앗 수량

const minusBtns = document.querySelectorAll(".minusBtn");   // 마이너스 버튼
const plusBtns = document.querySelectorAll(".plusBtn");     // 플러스 버튼

const money = document.getElementById("money"); // 결제 금액

payment.addEventListener("click", () => {
    alert("준비중입니다...");
});


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


        // 수량이 0이면 장바구니에서 지우기
        // if(counts[index].inntext == 0){
            
        // }
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

        // 1000 -> 1,000원 String 으로 바꿈
        money.innerText = payMoney.toLocaleString() + "원";

    })

})







