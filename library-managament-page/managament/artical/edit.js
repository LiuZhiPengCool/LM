// 获取所有数据
let bt = document.querySelector('.bt');
let f = document.querySelector('.f')
let html = ''
let page = 1
axios.defaults.baseURL = 'http://lzp123.nat300.top';
let bs1 = [];
let ids = [];
let p = [];

// 获取所有数据
axiosGetAllBooks = function(page) {
    // Get请求
    axios.get('/artical/all/' + page + '').then((res) => {
        console.log(page)
        showAllBook(res.data.data);
        let btc = bt.childNodes;
        console.log(p)
        for(let i = 0; i < btc.length; i++) {
            let header = btc[i].childNodes[2].textContent
            let b1 = btc[i].childNodes[4].childNodes[0]
            let b2 = btc[i].childNodes[4].childNodes[1]
            let b3 = btc[i].childNodes[4].childNodes[2]
            if(p.length >= 6) {
                b1.disabled = true
            } else {
                b1.disabled = false
            }

            b2.disabled = true

            for(let i = 0; i < p.length; i++) {
                console.log(p[i].header)
                console.log(header)
                if(p[i].header === header) {
                    b1.disabled = true
                    b2.disabled = false
                }
            }

            b1.onclick = function() {
                let pushArtical = {
                    header : header
                }
                axios({
                    method:'POST',
                    url: '/artical/push',
                    data: pushArtical
                })
                .then(res => {
                    location.reload()
                })
                .catch((err) => console.log(err))
                alert('推送成功')
                location.reload()
            }

            b2.onclick = function() {
                let pushArtical = {
                    header : header
                }
                axios({
                    method:'POST',
                    url: '/artical/backpush',
                    data: pushArtical
                })
                .then(res => {
                    location.reload()
                })
                .catch((err) => console.log(err))
                alert('撤销推送成功')
                location.reload()
            }

            b3.onclick = function() {
                let pushArtical = {
                    header : header
                }
                let yn = confirm('确认删除？')
                console.log(yn)
                if(yn) {
                    axios({
                        method:'POST',
                        url: '/artical/del',
                        data: pushArtical
                    })
                    .then(res => {
                        location.reload()
                    })
                    .catch((err) => console.log(err))
                    alert('删除成功')
                } else {
                    alert('删除失败!')
                }
                location.reload()
            }
        }
        
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
        let name = data.name;
        let header = data.header;
        let time = data.time;
        let t = data.text;
        if(data.push === '推送') {
            p.push(data)
        }
        let b= ''
        // b += '<div>'
        let tr = document.createElement('tr');
        b += '<td><p><input type="checkbox" class="os' + i + '" id="oneselect"></p></input></td>'
        b += '<td><p>' + name + '</p></td>'
        b += '<td><p>' + header +'</p></td>'
        b += '<td><p>' + time +'</p></td>'
        b += '<td>'
        b += '<button id="" class=b' + i + '>推送</button>'
        b += '<button id="" class=b' + i + '>撤销推送</button>'
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