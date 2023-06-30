axios.defaults.baseURL = 'http://lzp123.nat300.top';

axios.get('user/back/oneborrowbook')
.then(res => {
    console.log(res.data.data)
    showBook(res.data.data)
})
.catch(err => {
    console.log(err)
})

// 拼接每一页
let bt = document.querySelector('.bt')
showBook = function(arr) {
    let n = arr.length
    for(let i = 0; i < n; i++) {
        let data = arr[i];
        console.log(data)
        let book_name = data.book_name;
        let brt = data.borrow_time;
        let bct = data.back_time;
        let b = '';
        let tr = document.createElement('tr');
        b += '<td>' + book_name + '</td>'
        b += '<td>'+ brt +'</td>'
        b += '<td>' + bct +'</td>'
        tr.innerHTML = b
        bt.appendChild(tr)
    }
}

function swift(data) {
    let book_type = ''
    switch(data.bookType) {
        case 1:
            book_type = '科幻'
            break
        case 2:
            book_type = '现实'
            break
        case 3:
            book_type = '悬疑'
            break
        case 4:
            book_type = '推理'
            break
        case 5:
            book_type = '玄幻'
            break
        case 6:
            book_type = '战争幻想'
            break
        case 7:
            book_type = '人物传记'
            break
        case 8:
            book_type = '历史传记'
            break
        case 9:
            book_type = '散文'
            break
        case 10:
            book_type = '短篇'
            break    
        case 11:
            book_type = '武侠'
            break
        case 12:
            book_type = '奇幻'
            break    
    }
    return book_type
}