// axios.defaults.baseURL = 'http://127.0.0.1:8080';
axios.defaults.baseURL = 'http://lzp123.nat300.top';
axios.defaults.withCredentials = true
let bookDom = document.querySelector('.books');
let currentPage = document.querySelector('.current-page');
let adh = document.querySelector('.bir311')
let m1 = document.querySelector('#managa1')
let page = 1;
let size = true;



// 获取登录信息
let userPhone = localStorage.getItem('user')
m1.textContent = '欢迎' + userPhone + ' 后台管理'

axiosGetAd = function() {
    axios.get('/artical/all').then(res => {
        console.log(res.data.data)
        showAd(res.data.data)
        console.log(adh.childNodes)
        for(let i = 0; i < adh.childNodes.length; i++) {
            let adhc = adh.childNodes[i]
            let adhca = adh.childNodes[i].childNodes[0]
            adhca.onclick = function() {
                let artical = {
                    header: adhca.textContent,
                    text: ''
                }
                axios({
                    method:'POST',
                    url: '/artical/news',
                    data: artical
                })
                .then(res => {
                    window.location.href = 'http://lzp456.natapp1.cc/index/artical/artical_detail.html'
                    // location.reload()
                })
                .catch((err) => console.log(err))
            }
        }
    })
}
axiosGetAd()

function showAd(res) {
    let html = ''
    html += '<li>超级公告栏</li>'
    for(let i = 0; i < res.length; i++) {
        let data = res[i];
        let header = data.header
        let push = data.push
        if(push === '推送') {
            let a = '<li><a>' +  header + '</li></a>'
            html += a
        }
    }
    adh.innerHTML = html
}

// 获取所有数据
axiosGetBooks = function() {
    // Get请求
    axios.get('/book/books/' + page).then((res) => {
        showEachBook(res.data.data);
    }).catch((err) => {
        console.log(err);
    });
}
axiosGetBooks()

// 拼接每一页
showEachBook = function(arr) {
    let n = arr.length;
    if(n < 6) {
        size = false
    } else {
        size = true
    }
    let html = '';
    let index = 0;
    console.log(n)
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
        book += '<img src='+ "\".." + book_img_path +"\"" +'alt="">';
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

let select = document.querySelector('.select')

// 书籍分类显示
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
console.log('arr='+arr)
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


// 推荐位 获取 + 点击切换
let book_r = []
// let bir11 = document.querySelector('.bir11');
// let bir12 = document.querySelector('.bir12');
axiosGetRBooks = function() {
    // Get请求
    axios.get('/book/books/recommend').then((res) => {
        // console.log(res.data.data);
        showEachBR(res.data.data);
    }).catch((err) => {
        console.log(err);
    });
}

// 推荐位书名
showEachBR = function(arr) {
    console.log(arr)
    for(let i = 0; i < arr.length; i++) {
        let d = document.querySelector('.d' + i);
        bookInfomation = arr[i];
        book_r.push(arr[i]);
        console.log(bookInfomation);
        let book_name = bookInfomation.bookName;
        d.textContent = book_name;
    }
}

// // 鼠标点击切换图片
// bir12.onclick = function changePhoto(node) {
//     console.log(node);
//     // console.log(node.srcElement.id);
//     let str = node.srcElement.className;
//     let no = str[1];
//     let bm = '';
//     let book = book_r[no];
//     let bm_path = book.bookRImg;
//     // console.log(book);
//     bm += '<img src="' + bm_path + '">';
//     // console.log(bm);
//     bir11.innerHTML = bm;
//     clearInterval(timer);
// }

// bir12.onmouseenter = function() {
//     clearInterval(timer);
// }

// 定时切换
let index = 0
let bir1 = document.querySelector('.bir1');

window.onload = function() {
    axiosGetRBooks()
}

timer = setInterval(() => {
    index = index % 4;
    let d = document.querySelector('.d' + index);
    d.classList.add('bd');
    // let bm = '';
    let book = book_r[index];
    let bm_path = book.bookRImg;
    // console.log(book);
    // bm += '<img src="' + bm_path + '">';
    // console.log(bm);
    // bir11.innerHTML = bm;
    bir1.style.backgroundImage="url(" + bm_path + ")"
    index++;
    setTimeout(() => {
        d.classList.remove('bd')
    }, 2000)
}, 2000);

// 借阅排行
let bs = document.querySelector('.bs');

// 获取所有数据
axiosGetBSBooks = function() {
    // Get请求
    axios.get('/book/books/bs').then((res) => {
        // console.log(res);
        // console.log(res.data.data);
        showBSBook(res.data.data);
    }).catch((err) => {
        console.log(err);
    });
}
axiosGetBSBooks();

showBSBook = function(res) {
    console.log(res);
    let html = '';
    for(let i = 0; i < res.length; i++) {
        let data = res[i];
        let bs = '';

        bs += '<li class="bs1">'
        bs += '<p>' + (i+1) + '&nbsp;&nbsp;' + data.bookName +'</p>&nbsp;&nbsp;&nbsp;'
        bs += '<p>借阅次数：' + data.borrowNumber +'</p>'
        bs += '</li>'
        html += bs;
    }
    bs.innerHTML = html;
}

// 页面切换
let pre = document.querySelector('.pre')
let next = document.querySelector('.next')

pre.onclick = function() {
    if(page >= 2) {
        page = page - 1
        axiosGetBooks(page)
    }
}

next.onclick = function() {
    if(size) {
        page = page + 1
        axiosGetBooks(page)
    }
}

axiosBooks = function(p) {
    // Get请求
    axios.get('/book/allbooks/' + p + '').then((res) => {
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

let logo = document.querySelector('.logo')

// 登出
logo.onclick = function() {
    localStorage.removeItem('user')
    window.location.href = 'http://lzp456.natapp1.cc/sign-int-sign-up/index.html'
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

