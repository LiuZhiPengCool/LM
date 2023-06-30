let page = 1
axios.defaults.baseURL = 'http://lzp123.nat300.top';
// 获取所有数据
axiosGetAllBooks = function(p) {
    // Get请求
    axios.get('/book/books/back/' + p + '').then((res) => {
        // console.log(res);
        console.log(res.data.data);
        showAllBook(res.data.data);
    }).catch((err) => {
        console.log(err);
    });
}

axiosGetAllBooks(page)
let size = true

let bt = document.querySelector('.bt')
showAllBook = function(res) {
    let n = res.length

    if(n < 6) {
        size = false
    } else {
        size = true
    }

    bt.innerHTML = ''
    for(let i = 0; i <res.length; i++) {
        let data = res[i];
        let book_name = data.bookName;
        let book_author = data.author;
        let book_borrow_num = data.borrowNumber;
        let b = '';
        // b += '<div>'
        let tr = document.createElement('tr');
        b += '<td>' + book_name + '</td>'
        b += '<td>' + book_author +'</td>'
        b += '<td>'+ book_borrow_num +'</td>'
        tr.innerHTML = b
        bt.appendChild(tr)
    }
}

// 页面切换
let pre = document.querySelector('.pre')
let next = document.querySelector('.next')

pre.onclick = function() {
    bs1 = []
    page = page - 1
    axiosGetAllBooks(page)
}

next.onclick = function() {
    bs1 = []
    page = page + 1
    axiosGetAllBooks(page)
}

setInterval(() => {
    
    if(page == 1) {
        pre.disabled = true
    } else {
        pre.disabled = false
    }
    
    if(!size) {
        next.disabled = true
    } else {
        next.disabled = false
    }
}, 100);