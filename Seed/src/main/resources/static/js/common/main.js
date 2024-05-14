// console.log("메인 js 연결");


const seedNo = document.querySelectorAll(".seedNo");

// 검색어
const searchBtn = document.getElementById("searchBtn"); // 검색 버튼
const key = document.getElementById("key"); // 검색어



// 장바구니에 담는 함수
const cartAdd = (seedNo, count) => {

    if(memberNo == null){
        alert("로그인 후 이용해주세요");
        location.href = "/member/login";
        return;
    }

    

    const obj = {
        "seedNo" : seedNo,
        "memberNo" : memberNo,
        "count" : count
    }


    fetch("/cart/addCart",{
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {


        if(result > 0){
            alert("씨앗이 장바구니에 추가되었습니다!");
            
            if(confirm("장바구니 페이지로 이동하시겠습니까?")){
                location.href="/myPage/basket";
            }

        }else{
            alert("씨앗 추가 실패...");
        }

    })
}





// 이벤트 리스너를 추가하는 함수
const addEventListeners = () => {

    const minusBtns = document.querySelectorAll(".leftBtn");
    const counts = document.querySelectorAll(".count");
    const plusBtns = document.querySelectorAll(".rightBtn");

    const seedNos = document.querySelectorAll(".seedNo");

    const addBtns = document.querySelectorAll(".cartAdd");






    for(let i = 0; i < addBtns.length; i++){

        // 수량 감소시키는 버튼
        minusBtns[i].addEventListener("click", () => {

        let countString = counts[i].innerText;
        let countInt = parseInt(countString);

        if(countInt == 1){
            alert("1개 이상의 씨앗을 담아주세요!");
            return;
        }
        
        countInt -= 1;
        counts[i].innerText = countInt;
    
    
        });
    

        // 수량 증가시키는 버튼
        plusBtns[i].addEventListener("click", () => {
    
            let countString = counts[i].innerText;
            let countInt = parseInt(countString);
    
            countInt += 1;
            counts[i].innerText = countInt;
    
        });

        
        // 수량만큼 장바구니에 담는 버튼
        addBtns[i].addEventListener("click", () => {

            const count = parseInt(counts[i].innerText);
            const seedNo = seedNos[i].value;


            cartAdd(seedNo, count);

        })

        
        
        
    }

    
    
    // console.log("초기화!!");

}







// 시작 하자마자 이벤트 리스너를 추가하는 함수를 작동해서 이벤트 리스너 추가(마이너스, 플러스, 담기)
addEventListeners();













// 새로고침 하는 함수
// 1 -> 높은 가격 순
// 2 -> 낮은 가격 순
// 3 -> 종류 별
const seedSort = sortType => {
    fetch("/seed/sort?sortType=" + sortType)
    .then(resp => resp.json())
    .then(result => {
        
        const content2 = document.querySelector('.content2');

        // 기존 내용을 지우고 새로운 내용을 추가합니다.
        content2.innerHTML = '';

        result.forEach(seed => {
            const seedItem = document.createElement('div');
            seedItem.classList.add('allBox', 'seed-item');

            const seedLink = document.createElement('a');
            seedLink.href = '/seed/detail?seedNo=' + seed.seedNo;

            const imgBox = document.createElement('div');
            imgBox.classList.add('imgBox');

            const img = document.createElement('img');
            img.src = seed.seedImgPath ? seed.seedImgPath : '/images/중비중 이미지.gif';
            img.classList.add('seedImg');

            const br = document.createElement('br');

            const selectDiv = document.createElement('div');
            selectDiv.classList.add('select');

            const namePriceDiv = document.createElement('div');
            namePriceDiv.classList.add('select-div');

            const seedName = document.createElement('a');
            seedName.classList.add('select-a');
            seedName.textContent = seed.seedName;

            const seedPrice = document.createElement('p');
            seedPrice.classList.add('select-p');
            seedPrice.textContent = seed.seedPrice;

            const selectSecond = document.createElement('div');
            selectSecond.classList.add('select_second');



            const buttonDiv = document.createElement('div');
            buttonDiv.classList.add('button_div');
            

            // 마이너스 버튼 조립
            const minusButton = document.createElement('button');
            minusButton.classList.add('leftBtn');

            const minus = document.createElement('i');
            minus.classList.add('fa-solid', 'fa-minus');

            minusButton.appendChild(minus);

            // 수량
            const countSpan = document.createElement('span');
            countSpan.textContent = '1';
            countSpan.classList.add('count');


            // 플러스 버튼 조립
            const plusButton = document.createElement('button');
            plusButton.classList.add('rightBtn');

            const plus = document.createElement('i');
            plus.classList.add('fa-solid', 'fa-plus');

            plusButton.appendChild(plus);


            // 버튼 조립
            buttonDiv.appendChild(minusButton);
            buttonDiv.appendChild(countSpan);
            buttonDiv.appendChild(plusButton);

            // select_second 조립

            const addButton = document.createElement('button');
            addButton.textContent = '담기';
            addButton.classList.add('cartAdd');


            const buttonDiv2 = document.createElement('div');
            buttonDiv2.classList.add('select-div');

            const seedNoInput = document.createElement('input');
            seedNoInput.classList.add('seedNo');
            seedNoInput.value = seed.seedNo;
            seedNoInput.type = "hidden";
            


            buttonDiv2.appendChild(seedNoInput);
            buttonDiv2.appendChild(addButton);

            selectSecond.appendChild(buttonDiv);
            selectSecond.appendChild(buttonDiv2);
            


            // 조립
            imgBox.appendChild(img);
            seedLink.appendChild(imgBox);
            

            namePriceDiv.appendChild(seedName);
            namePriceDiv.appendChild(seedPrice);
            selectDiv.appendChild(namePriceDiv);

            selectDiv.appendChild(selectSecond);

            

            seedItem.appendChild(seedLink);
            seedItem.appendChild(br);
            seedItem.appendChild(selectDiv);

            content2.appendChild(seedItem);

        });


        // 이벤트 리스너를 추가하는 함수 작동
        addEventListeners();

    });
}




const selectBoxChange = sort => {
    
    let sortType = parseInt(sort);
    
    if(sortType == 0){
        return;
    }

    // 정렬코드로 새로고침하는 함수
    seedSort(sortType);

};

