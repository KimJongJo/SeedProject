// console.log("메인 js 연결");


const seedNo = document.querySelectorAll(".seedNo");

// 검색어
const searchBtn = document.getElementById("searchBtn"); // 검색 버튼
const key = document.getElementById("key"); // 검색어




// 장바구니에 담는 함수
const cartAdd = seedNo => {

    if(memberNo == null){
        alert("로그인 후 이용해주세요");
        location.href = "/member/login";
        return;
    }

    

    const obj = {
        "seedNo" : seedNo,
        "memberNo" : memberNo
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

            const buttonDiv = document.createElement('div');
            buttonDiv.classList.add('select-div');

            const addButton = document.createElement('button');
            addButton.textContent = '담기';
            addButton.classList.add('cartAdd');
            addButton.onclick = function() {
                cartAdd(seed.seedNo);
            };



            // 조립
            imgBox.appendChild(img);
            seedLink.appendChild(imgBox);
            

            namePriceDiv.appendChild(seedName);
            namePriceDiv.appendChild(seedPrice);
            selectDiv.appendChild(namePriceDiv);

            buttonDiv.appendChild(addButton);
            selectDiv.appendChild(buttonDiv);

            

            seedItem.appendChild(seedLink);
            seedItem.appendChild(br);
            seedItem.appendChild(selectDiv);

            content2.appendChild(seedItem);
        });
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


