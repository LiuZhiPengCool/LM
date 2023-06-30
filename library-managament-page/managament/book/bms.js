// 获取所有数据
let bt = document.querySelector('.bt');
let f = document.querySelector('.f')
let html = ''
let page = 1
axios.defaults.baseURL = 'http://lzp123.nat300.top';
let bs1 = [];
let ids = [];
let headerbook = {
    book_name : '',
    book_author: '',
    book_type: ''
}

let hs = document.querySelector('.hs')
hs.onclick = function() {
    let bn = document.querySelector('.hn').value
    let ba = document.querySelector('.ha').value
    // let bt = document.querySelector('.ht').value
    headerbook.book_name = bn;
    headerbook.book_author = ba;
    // headerbook.book_type = bt;
    axios({
        method:'POST',
        url: '/book/search',
        data: headerbook
    })
    .then(res => {
        showAllBook(res.data.data)
    })
    .catch((err) => console.log(err))
}

// 获取所有数据
axiosGetAllBooks = function(p) {
    // Get请求
    axios.get('/book/books/back/' + p + '').then((res) => {
        // console.log(res);
        // console.log(res.data.data);
        showAllBook(res.data.data);
    }).catch((err) => {
        console.log(err);
    });
}

axiosGetAllBooks(page)
let size = true

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
        bs1.push(data);
        let book_name = data.bookName;
        let book_author = data.author;
        let book_type = swift(data);
        // let book_type = data.bookType;
        let book_stroe = data.store;
        let book_price = data.price;
        let book_borrow_num = data.borrowNumber;
        let book_place = data.place;
        let b = '';
        // b += '<div>'
        let tr = document.createElement('tr');
        b += '<td><p><input type="checkbox" class="os' + i + '" id="oneselect"></p></input></td>'
        b += '<td><p>' + book_name + '</p></td>'
        b += '<td><p>' + book_author +'</p></td>'
        b += '<td><p>'+ book_type +'</p></td>'
        b += '<td><p>'+ book_stroe +'</p></td>'
        b += '<td><p>'+ book_price +'</p></td>'
        b += '<td><p>'+ book_borrow_num +'</p></td>'
        b += '<td><p>' + book_place +'</p></td>'
        b += '<td>'
        b += '<button id="be" class=b' + i + '>编辑</button>'
        b += '<button id="bd" class=b' + i + '>删除</button>'
        b += '</td>'
        tr.innerHTML = b
        bt.appendChild(tr)
    }
}

console.log(bs1)
// 全选
let allselect = document.querySelector('#allselect')
allselect.onclick = function(){
    let boxs = document.querySelectorAll('#oneselect')
    for(let i = 0; i < boxs.length; i++) {
        console.log(boxs[i]);
        if(allselect.checked === true) {
            boxs[i].checked = true
        } else {
            boxs[i].checked = false
        }
        ids = [];
        ids.push(i);
    }
}

let bk = document.querySelector('.bk')
let bk_close = document.querySelector('.close')

// 编辑操作
// 删除
// 单选
let str = ""
let index = 0

bt.onclick = function(node) {
    str = node.srcElement.className.substring(1,node.srcElement.className.length);
    index = parseInt(str);

    if(node.srcElement.id === 'be') {
        let bn = document.querySelector('#bn')
        let au = document.querySelector('#au')
        let ty = document.querySelector('#ty')
        let store = document.querySelector('#store')
        let pri = document.querySelector('#pri')
        let bnum = document.querySelector('#bnum')
        let pla = document.querySelector('#pla')
        bn.value = bs1[index].bookName
        au.value = bs1[index].author
        ty.value = bs1[index].bookType
        store.value = bs1[index].store
        pri.value = bs1[index].price
        bnum.value = bs1[index].borrowNumber
        pla.value = bs1[index].place
        bk.classList.remove('dp')
    }

    if(node.srcElement.id === 'bd') {
        let yn = confirm('确认删除？')
        console.log(yn)
        if(yn) {
            alert('删除成功!')
            let data = bs1[index].id
            axios.get('/book/books/bd/' + data)
            .then(res => {
                location.reload()
            })
            .catch((err) => console.log(err))
        } else {
            alert('删除失败!')
        }
    }

    if(node.srcElement.id === 'oneselect') {
        str = node.srcElement.className.substring(2,node.srcElement.className.length);
        index = parseInt(str);
        let os = document.querySelector('.os' + index);

        if(os.checked === true) {
            ids.push(bs1[index].id)
        } else {
            newids = []
            for(let j = 0; j < ids.length; j++) {
                if(bs1[index].id != ids[j]) {
                    // console.log('newids加入');
                    newids.push(ids[j])
                }
            }
            ids = []
            for(let j = 0; j < newids.length; j++) {
                ids.push(newids[j])
            }
        }
    }
}

// 关闭页面
bk_close.onclick = function() {
    bk.classList.add('dp')
}


// 新增
let add = document.querySelector('.addB')
add.onclick = function() {
    let bn = document.querySelector('#bn').value
    let au = document.querySelector('#au').value
    let ty = document.querySelector('#ty').value
    let store = document.querySelector('#store').value
    let pri = document.querySelector('#pri').value
    let bnum = document.querySelector('#bnum').value
    let pla = document.querySelector('#pla').value
    bn.value = null
    au.value = null
    ty.value = null
    store.value = null
    pri.value = null
    bnum.value = null
    pla.value = null
    bk.classList.remove('dp')
}

// 刷新
f.onclick = function() {
    location.reload()
}

// 批量删除
let pitch = document.querySelector('.pitchDel');

pitch.onclick = function() {
    console.log(ids);
    axios({
        method:'POST',
        url: '/book/books/pitd',
        data: {
            pids: ids
        }
    })
    .then(res => {
        // ids = []
        location.reload()
    })
    .catch((err) => console.log(err))
}

// 保存操作
let save = document.querySelector('.save');

console.log(save);
save.onclick = function() {
    let bn = document.querySelector('#bn').value
    let au = document.querySelector('#au').value
    let ty = document.querySelector('#ty').value
    let store = document.querySelector('#store').value
    let pri = document.querySelector('#pri').value
    let bnum = document.querySelector('#bnum').value
    let pla = document.querySelector('#pla').value

    let book = {
        book_name : bn,
        book_author: au,
        book_type : ty,
        book_store : store,
        book_pri : pri,
        book_bnum : bnum,
        book_pla : pla,
    }
    book.pla = pla;

    axios({
        method:'POST',
        url: '/book/books/bsave',
        data: book
    })
    .then(res => {
        location.reload()
    })
    .catch((err) => {
        console.log(err);
        location.reload()
    })  
    bk.classList.add('dp')
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