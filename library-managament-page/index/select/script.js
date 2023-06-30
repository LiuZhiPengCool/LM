
let bookDom = document.querySelector('.books');
let currentPage = document.querySelector('.current-page');
let page = 1;
let size = true
// axios.defaults.baseURL = 'http://127.0.0.1:8080';
axios.defaults.baseURL = 'http://lzp123.nat300.top';

let filename = location.href
filename = filename.substring(filename.lastIndexOf('/') + 1)
filename = filename.substring(6)
filename = filename.split('.')
filename = filename[0]
console.log(filename)

// 获取所有数据
axiosGetBooks = function() {
    // Get请求
    axios.get('/book/typebooks/' + page + '/' + filename).then((res) => {
        showEachBook(res.data.data);
    }).catch((err) => {
        console.log(err);
    });
}
axiosGetBooks()

// 书籍分类显示
let select = document.querySelector('.select')
let idx = 1
let arr = []
let ss = select.childNodes;
for(let i = 0; i < ss.length; i++) {
    if(ss[i].nodeName === 'LI') {
        arr[i] = idx++
    }
    ss[i].onclick = function() {
        window.location.href = 'http://lzp456.natapp1.cc/index/select/select' + arr[i] + '.html'
    }
}

// 拼接每一页
showEachBook = function(arr) {
    let n = arr.length;
    if(n < 12) {
        size = false
    } else {
        size = true
    }
    let html = '';
    let index = 0;
    for(let i = 0; i < n; i++) {
        let data = arr[i];
        // console.log(data);
        let book_name = data.bookName;
        let book_author = data.author;

        let book_type = data.bookType;
        book_type = swift(data)
        let introduce = data.introduce;
        let book_img_path = data.img;
        let book = '';
        book += '<div id="bookcontainer">'
        book += '<div id="book">';
        book += '<img src='+ "\"../../" + book_img_path +"\"" +'alt="">';
        book += '<div id="ct">';
        book += '<h3>' + book_name + '</h3>';
        book += '<p>' + book_author + '</p>';
        book += '<p>' + book_type + '</p>';
        book += '<p id="bintro">' + introduce + '</p>';
        book += '</div>'
        book += '</div>';
        book += '</div>'
        html += book;
    }
    bookDom.innerHTML = html;
    currentPage.innerHTML = '第' + page + '页';
}

// 页面切换
let pre = document.querySelector('.pre')
let next = document.querySelector('.next')

pre.onclick = function() {
    if(page >= 2) {
        page = page - 1
        pb = false
        axiosBooks(page)
    } else {
        pb = true
    }
}

next.onclick = function() {
    if(size) {
        page = page + 1
        axiosBooks(page)
    }
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

axiosBooks = function(p) {
    // Get请求
    axios.get('/book/typebooks/' + p + '/' + filename).then((res) => {
        showEachBook(res.data.data);
    }).catch((err) => {
        console.log(err);
    });
}


// 后台管理
let managa = document.querySelector('#managa1')
managa.onclick = function() {
    axios.get('/user/auth')
    .then(res => {
        console.log(res.data.data)
        let auth = res.data.data

        if(auth === '管理员') {
            window.location.href = 'http://lzp456.natapp1.cc/managament/back-end.html'
        } else if(auth === '用户') {
            window.location.href = 'http://lzp456.natapp1.cc/user/back.html'
        } else if(auth === '未登录') {
            window.location.href = 'http://lzp456.natapp1.cc/sign-int-sign-up/index.html'
        }
    }).catch(err => {
        window.location.href = 'http://lzp456.natapp1.cc/sign-int-sign-up/index.html'
    }) 
}


// 分类栏显示不同背景色
addEachHrBc = function() {
    list = select.childNodes;
    // console.log(list);
    let arr = [];
    let index = 0;
    for(let i = 0; i < list.length; i++) {
        if(list[i].nodeName == 'LI') {
            arr.push(list[i])
        }
    }

    for(let i = 0; i < arr.length; i++) {
        if(i == 0 || i == 1 || i == 4 || i == 5 || i == 8 || i == 9) {
            arr[i].className = 'light';
        } else if(i == 2 || i == 3 || i == 7 || i == 6) {
            arr[i].className = 'dark';
        }
    }
}

addEachHrBc();

// 搜索
let search = document.querySelector('.search')
search.onkeydown = function() {
    if(event.keyCode === 13) {
        let searchContent = search.value
        axios.get('/book/search/' + searchContent)
        .then(res => {
            window.location.href = 'http://lzp456.natapp1.cc/index/search/search.html'
        })
        .catch(err => {
            console.log('查询失败')
            window.location.href = 'http://lzp456.natapp1.cc/index/index.html'
        })
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