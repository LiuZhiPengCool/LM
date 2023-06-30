axios.defaults.baseURL = 'http://lzp123.nat300.top';
let bt = document.querySelector('.bt')

axios.get('user/alluser')
.then(res => {
    // console.log(res.data.data)
    showBook(res.data.data)
    let br = bt.childNodes
    // console.log(br)
    for(let i = 1; i < br.length; i++) {
        // console.log(br[i])
        let auth = br[i].childNodes[1].textContent
        let count = 0
        let phone = br[i].childNodes[0].textContent
        let button = br[i].childNodes[2].childNodes[0]
        let op = document.querySelector('.op' + i)
        console.log(op)
        if(auth === '用户') {
            count = 0
            button.textContent = '升级为管理员'
            op.classList.add('b2')
        } else {
            count = 1
            button.textContent = '降为普通用户'
            op.classList.add('b1')
        }

        button.onclick = function() {
            axios.get('/user/updateuser/' + phone + '/' + count)
            .then(res => {
                location.reload()
            })
            .catch()
        }
    }
})
.catch(err => {
    console.log(err)
})

// 拼接每一页
showBook = function(arr) {
    let n = arr.length
    for(let i = 0; i < n; i++) {
        let data = arr[i];
        // console.log(data)
        let user = data.phone;
        let auth = data.auth;
        let b = '';
        let tr = document.createElement('tr');
        b += '<td>' + user + '</td>'
        b += '<td>'+ auth +'</td>'
        b += '<td><button class="op' + (i+1) + '"></button></td>'
        tr.innerHTML = b
        bt.appendChild(tr)
        console.log(bt)
    }
}