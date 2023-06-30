let bt = document.querySelector('.bt')
let show = document.querySelector('.show')
let imageshow = document.querySelector('#imgShow')
let clo = document.querySelector('.clo')
imageshow.classList.add('ds')

clo.onclick = function() {
    imageshow.classList.add('ds')
}

setInterval(() => {
}, 100);

axios.defaults.baseURL = 'http://lzp123.nat300.top';
// 获取所有数据
axiosGetAllBRBooks = function() {
    // Get请求
    axios.get('/book/books/allrecommend').then((res) => {
        // console.log(res.data.data);
        showAllBRBook(res.data.data);
        let br = bt.childNodes
        // console.log(br)
        for(let i = 1; i < br.length; i++) {
            console.log(br[i])
            let brimg = br[i].childNodes[1].childNodes[0]
            console.log(brimg)
            brimg.onclick = function() {
                let brimgpath = brimg.style.backgroundImage
                show.style.backgroundImage = brimgpath
                imageshow.classList.remove('ds')
                // console.log(brimgpath)
            }

            let del = br[i].childNodes[2].childNodes[0]
            console.log(del)
            del.onclick = function() {
                let book_name = br[i].childNodes[0].textContent
                let book = {
                    book_name: book_name
                }
                axios({
                    method:'POST',
                    url: '/book/brdel',
                    data: book
                })
                .then(res => {
                    location.reload()
                })
                .catch((err) => {
                    console.log(err);
                    location.reload()
                }) 
            }
        }
    }).catch((err) => {
        console.log(err);
    });
}

let f = document.querySelector('.f')
f.onclick = function() {
    location.reload()
}

axiosGetAllBRBooks();

showAllBRBook = function(res) {
    for(let i = 0; i < res.length; i++) {
        let data = res[i];
        let book_name = data.bookName;
        let book_img = data.bookRImg;
        let b = '';
        let tr = document.createElement('tr');
        // b += '<td><input type="checkbox" class="os' + i + '" id="oneselect"></input></td>'
        b += '<td><p>' + book_name + '</p></td>'
        b += '<td><div class="brimg" style="background-image:url('+ book_img + ')"></div></td>'
        b += '<td>'
        // b += '<button id="be" class=b' + i + '>编辑</button>'
        b += '<p><button id="bd" class=b' + i + '>删除</button></p>'
        b += '</td>'
        tr.innerHTML = b;
        bt.appendChild(tr);
    }
}

// 新增
let add = document.querySelector('.addB')
let bk = document.querySelector('.bk')
bk.classList.add('ds')
add.onclick = function() {
    bk.classList.remove('ds')
}

// 关闭页面
let bk_close = document.querySelector('.bkclose')

bk_close.onclick = function() {
    bk.classList.add('ds')
}

// 保存操作
let save = document.querySelector('.bksave');

console.log(save);
save.onclick = function() {
    let bn = document.querySelector('#bn').value
    let au = document.querySelector('#au').value

    let book = {
        book_name : bn,
        book_author: au,
    }
    console.log(book)
    axios({
        method:'POST',
        url: '/book/books/brsave',
        data: book
    })
    .then(res => {
        alert('插入成功，由于封页推荐最多数量为4，删除后可见')
        location.reload()
    })
    .catch((err) => {
        console.log(err);
        location.reload()
    })  
    bk.classList.add('ds')
}